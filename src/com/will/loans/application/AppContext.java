
package com.will.loans.application;

import android.app.Activity;
import android.app.Application;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;

import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.util.AQUtility;
import com.will.loans.getsurepassword.view.LockPatternUtils;
import com.will.loans.share.Laiwang;
import com.will.loans.utils.GsonTransformer;

import java.util.ArrayList;
import java.util.List;

public class AppContext extends Application {
    private final String TAG = "AppContext";

    private static int SOCKET_TIMEOUT = 20 * 1000;

    private List<Activity> list = new ArrayList<Activity>();

    private LockPatternUtils mLockPatternUtils;

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

    public LockPatternUtils getLockPatternUtils(Context context) {
        if (mLockPatternUtils == null) {
            mLockPatternUtils = new LockPatternUtils(context);
        }
        return mLockPatternUtils;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        AQUtility.setDebug(false);
        AbstractAjaxCallback.setTimeout(SOCKET_TIMEOUT);
        AbstractAjaxCallback.setTransformer(new GsonTransformer());

        ShareSDK.initSDK(this);
        ShareSDK.registerPlatform(Laiwang.class);
        ShareSDK.setConnTimeout(5000);
        ShareSDK.setReadTimeout(10000);

    }
}
