
package com.will.loans.beans;

import android.app.Activity;

public class InternetReturnedHandler<T> {

    private Activity activity;

    public InternetReturnedHandler(Activity activity) {
        this.activity = activity;
    }

    public boolean advanceHandle(T t) {
        if (t == null) {
            //			Log.e(this, "json return null");
            return false;
        }

        //        BaseJson baseBean = (BaseJson) t;
        //        if (baseBean.getStatus() == 200) {
        //            return true;
        //        }
        //
        //        Toaster.showShort(activity, baseBean.getMessage());
        return false;
    }

}
