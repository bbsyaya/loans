
package com.will.loans.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created with IntelliJ IDEA. User: ys.peng Date: 13-12-18 Time: 下午2:12 To
 * change this template use File | Settings | File Templates.
 */

public class ScreenProperties {

    private static DisplayMetrics dm;

    public ScreenProperties(Activity activity) {
        initScreenProperties(activity);
    }

    public static void initScreenProperties(Activity activity) {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        if (dm == null) {
            initScreenProperties(activity);
        }
    }

    public static int getScreenHeight() {
        return dm.heightPixels;
    }

    public static int getScreenWidth() {
        return dm.widthPixels;
    }

    public static float getScreenDensity() {
        return dm.density;
    }

    public static final float getXdpi() {
        return dm.xdpi;
    }

    public static final float getYdpi() {
        return dm.ydpi;
    }

}
