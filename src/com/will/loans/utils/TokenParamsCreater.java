
package com.will.loans.utils;

import android.app.Activity;

import com.will.loans.beans.UserinfoCache;

import java.util.HashMap;
import java.util.Map;

public class TokenParamsCreater {

    public static Map<String, String> buildTokenParams(Activity context) {
        String token = UserinfoCache.getToken();
        if (token == null) {
            //			Toaster.showShort(context, R.string.please_login_first);
            //判断是否登录，没有登录跳转到登录页面
            //            LoginDialog.showPromptDialog(context, "您还未登录", "是否登录？");
            return null;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        return params;
    }

    public static Map<String, Object> buildTokenMultibleParams(Activity context) {
        String token = UserinfoCache.getToken();
        if (token == null) {
            //			Toaster.showShort(context, R.string.please_login_first);
            //            LoginDialog.showPromptDialog(context.getApplicationContext(), "您还未登录", "是否登录？");
            return null;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        return params;
    }
}
