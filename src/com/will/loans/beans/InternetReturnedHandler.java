
package com.will.loans.beans;

import android.app.Activity;

import com.will.loans.beans.json.BaseJson;
import com.will.loans.utils.Toaster;

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

        BaseJson baseBean = (BaseJson) t;
        if (baseBean.getStatus() == 200) {
            return true;
        }

        Toaster.showShort(activity, baseBean.getMessage());
        return false;
    }

}
