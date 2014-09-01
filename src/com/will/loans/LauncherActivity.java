
package com.will.loans;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.beans.InternetReturnedHandler;
import com.will.loans.beans.bean.UserInfo;
import com.will.loans.beans.json.BaseJson;
import com.will.loans.ui.activity.BaseActivity;
import com.will.loans.ui.activity.FirstLoadActivity;
import com.will.loans.ui.activity.HomePageActivity;
import com.will.loans.utils.ScreenProperties;
import com.will.loans.utils.SharePreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: ys.peng Date: 13-12-18 Time: 下午2:12 To
 * change this template use File | Settings | File Templates.
 */
public class LauncherActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */

    private SharePreferenceUtil util;

    private Handler mHandler;

    //    private ThirdPlatLogin thirdPlatLogin;

    private SharePreferenceUtil sharePreferenceUtil;

    private AQuery aq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);
        initView();
    }

    public void initView() {
        aq = new AQuery(this);
        login();
        ScreenProperties.initScreenProperties(this);
        //        util = new SharePreferenceUtil(this, AppInfo.USER_INFO);
        //        aq.ajax(ServerUrl.LOADINGIMG,LoadingImgJson.class,imgAjaxCallback);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (util.getisFirst()) {
                    createShut();// 创建快捷方式
                    goFirstActivity(FirstLoadActivity.class);
                } else {
                    goFirstActivity(HomePageActivity.class);
                }
            }
        }, 3 * 1000);
    }

    public void login() {
        sharePreferenceUtil = new SharePreferenceUtil(this, "userinfo");
        //        thirdPlatLogin = new ThirdPlatLogin(this);
        //        if (sharePreferenceUtil.getIsThirdLogin()) {
        //            thirdPlatLogin.thirdPartLogin(sharePreferenceUtil.getPlatName());
        //        } else if (!sharePreferenceUtil.getUsername().equals("")) {
        //            sendRequestForLogin(sharePreferenceUtil.getUsername(), sharePreferenceUtil.getPasswd());
        //        }
    }

    private void sendRequestForLogin(String username, String password) {
        Map<String, String> params = buildParams(username, password);

        //        aq.ajax(ServerUrl.LOGIN, params, LoginJson.class,
        //                loginCallback);
    }

    private Map<String, String> buildParams(String username, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        return params;
    }

    private AjaxCallback<BaseJson> loginCallback = new AjaxCallback<BaseJson>() {

        @Override
        public void callback(String url, BaseJson object, AjaxStatus status) {
            InternetReturnedHandler<BaseJson> prefixHandler = new InternetReturnedHandler<BaseJson>(
                    LauncherActivity.this);
            if (prefixHandler.advanceHandle(object) == false) {
                return;
            }

            //            UserInfo userInfo = object.getData();
            //            UserinfoCache.setUserInfo(userInfo);
            //            UserinfoCache.setToken(object.getToken());
            //            saveUserInfo(userInfo, object.getToken());
        }
    };

    private void saveUserInfo(UserInfo userInfo, String token) {
        sharePreferenceUtil.setIsLogin(true);
        //        sharePreferenceUtil.setIsThirdLogin(AppInfo.isThirdPlat);
    }

    //    private AjaxCallback<LoadingImgJson> imgAjaxCallback = new AjaxCallback<LoadingImgJson>(){
    //        @Override
    //        public void callback(String url, LoadingImgJson object, AjaxStatus status) {
    //            aq.id(R.id.start_iv_ad).image(ServerUrl.DOMAIN+object.getData().getLoadingImgUrl(),true,true);
    //        }
    //    };

    /**
     * 进入首页
     */
    public void goFirstActivity(Class activity) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        startActivity(intent);
        finish();
    }

    /**
     * 创建桌面快捷方式
     */
    public void createShut() {
        // 创建添加快捷方式的Intent
        Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        String title = getResources().getString(R.string.app_name);
        // 加载快捷方式的图标
        Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
        // 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
        Intent myIntent = new Intent();
        myIntent.setClassName(this, this.getClass().getName());
        myIntent.setAction(Intent.ACTION_MAIN);
        myIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 设置快捷方式的标题
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 设置快捷方式的图标
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 设置快捷方式对应的Intent
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
        // 发送广播添加快捷方式
        sendBroadcast(addIntent);
        util.setIsFirst(false);
    }
}
