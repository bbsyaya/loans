
package com.will.loans.application;

import android.app.Activity;
import android.app.Application;

import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.util.AQUtility;
import com.will.loans.utils.GsonTransformer;

import java.util.ArrayList;
import java.util.List;

public class AppContext extends Application {
    private final String TAG = "AppContext";

    private static int SOCKET_TIMEOUT = 20 * 1000;

    private List<Activity> list = new ArrayList<Activity>();

    private static AppContext appContext;

    public static AppContext getInstance() {
        if (appContext == null) {
            appContext = new AppContext();
        }
        return appContext;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit() {
        AQUtility.cleanCacheAsync(this, 1, 1);

        for (Activity activity : list) {
            activity.finish();
        }

        System.exit(0);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        AQUtility.setDebug(true);
        AbstractAjaxCallback.setTimeout(SOCKET_TIMEOUT);
        AbstractAjaxCallback.setTransformer(new GsonTransformer());
    }
}
