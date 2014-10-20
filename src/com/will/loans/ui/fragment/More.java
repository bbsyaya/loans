
package com.will.loans.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;

import com.will.loans.R;
import com.will.loans.share.onekeyshare.OnekeyShare;
import com.will.loans.ui.activity.About;
import com.will.loans.ui.activity.ActionCenter;
import com.will.loans.ui.activity.Feedback;
import com.will.loans.ui.activity.HelpCenter;
import com.will.loans.ui.activity.MessageCenter;
import com.will.loans.utils.SharePreferenceUtil;

import java.util.HashMap;

public class More extends BaseFragment implements OnClickListener, PlatformActionListener, Callback {

    private View logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_more, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.title_tv)).setText(R.string.tab_more);

        view.findViewById(R.id.more_user_center).setOnClickListener(this);
        view.findViewById(R.id.more_about).setOnClickListener(this);
        view.findViewById(R.id.more_message_center).setOnClickListener(this);
        view.findViewById(R.id.more_help_center).setOnClickListener(this);
        view.findViewById(R.id.more_attention).setOnClickListener(this);
        logout = view.findViewById(R.id.more_exit);
        logout.setOnClickListener(this);
        view.findViewById(R.id.more_rate).setOnClickListener(this);
        view.findViewById(R.id.more_share).setOnClickListener(this);
        view.findViewById(R.id.more_feedback).setOnClickListener(this);
        view.findViewById(R.id.check_update_llyt).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        if (SharePreferenceUtil.getUserPref(getActivity()).getToken().equals("")) {
            logout.setVisibility(View.GONE);
        } else {
            logout.setVisibility(View.VISIBLE);
        }
    }

    private void showExitDlg() {
        new AlertDialog.Builder(getActivity()).setTitle("退出").setMessage("是否退出？")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "成功退出登录", Toast.LENGTH_SHORT).show();
                        SharePreferenceUtil.getUserPref(getActivity()).clear();
                        updateView();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_user_center:
                jump2Activity(new ActionCenter());
                break;
            case R.id.more_about:
                jump2Activity(new About());
                break;
            case R.id.more_message_center:
                jump2Activity(new MessageCenter());
                break;
            case R.id.more_help_center:
                jump2Activity(new HelpCenter());
                break;
            case R.id.more_attention:
                showShare(false, null, false);
                break;
            case R.id.more_exit:
                showExitDlg();
                break;
            case R.id.more_rate:
                showShare(false, null, false);
                break;
            case R.id.more_share:
                showShare(false, null, false);
                break;
            case R.id.more_feedback:
                jump2Activity(new Feedback());
                break;
            case R.id.check_update_llyt:

                break;
            default:
                break;
        }

    }

    private static final String FILE_NAME = "pic_beauty_on_sofa.jpg";

    public static String TEST_IMAGE;

    public static String TEST_IMAGE_URL;

    public static HashMap<Integer, String> TEST_TEXT;

    // 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
    /**
     * ShareSDK集成方法有两种</br>
     * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
     * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br> 请看“ShareSDK
     * 使用说明文档”，SDK下载目录中 </br> 或者看网络集成文档
     * http://wiki.mob.com/Android_%E5%BF%AB%E9%
     * 80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
     * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中 平台配置信息有三种方式：
     * 1、在我们后台配置各个微博平台的key
     * 2、在代码中配置各个微博平台的key，http://mob.com/androidDoc/cn/sharesdk
     * /framework/ShareSDK.html 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
     */
    private void showShare(boolean silent, String platform, boolean captureView) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        oks.setAddress("12345678901");
        oks.setTitle(getString(R.string.evenote_title));
        oks.setTitleUrl("http://mob.com");
        if (TEST_TEXT != null && TEST_TEXT.containsKey(0)) {
            oks.setText(TEST_TEXT.get(0));
        } else {
            oks.setText(getString(R.string.share_content));
        }
        if (captureView) {
            //            oks.setViewToShare(getPage());
        } else {
            oks.setImagePath(TEST_IMAGE);
            oks.setImageUrl(TEST_IMAGE_URL);
        }
        oks.setUrl("http://www.mob.com");
        oks.setFilePath(TEST_IMAGE);
        oks.setComment(getString(R.string.share));
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl("http://mob.com");
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("This is a beautiful place!");
        oks.setLatitude(23.056081f);
        oks.setLongitude(113.385708f);
        oks.setSilent(silent);
        //        oks.setShareFromQQAuthSupport(shareFromQQLogin);
        if (platform != null) {
            oks.setPlatform(platform);
        }

        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();

        // 在自动授权时可以禁用SSO方式
        //if(!shareFromQQLogin)
        //  oks.disableSSOWhenAuthorize();

        // 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
        //      oks.setCallback(new OneKeyShareCallback());

        // 去自定义不同平台的字段内容
        //        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

        // 去除注释，演示在九宫格设置自定义的图标
        //      Bitmap logo = BitmapFactory.decodeResource(menu.getResources(), R.drawable.ic_launcher);
        //      String label = menu.getResources().getString(R.string.app_name);
        //      OnClickListener listener = new OnClickListener() {
        //          public void onClick(View v) {
        //              String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
        //              Toast.makeText(menu.getContext(), text, Toast.LENGTH_SHORT).show();
        //              oks.finish();
        //          }
        //      };
        //      oks.setCustomerLogo(logo, label, listener);

        // 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
        //      oks.addHiddenPlatform(SinaWeibo.NAME);
        //      oks.addHiddenPlatform(TencentWeibo.NAME);

        // 为EditPage设置一个背景的View
        //        oks.setEditPageBackground(getPage());

        //设置kakaoTalk分享链接时，点击分享信息时，如果应用不存在，跳转到应用的下载地址
        oks.setInstallUrl("http://www.mob.com");
        //设置kakaoTalk分享链接时，点击分享信息时，如果应用存在，打开相应的app
        oks.setExecuteUrl("kakaoTalkTest://starActivity");

        oks.show(getActivity());
    }

    /** 将action转换为String */
    public static String actionToString(int action) {
        switch (action) {
            case Platform.ACTION_AUTHORIZING:
                return "ACTION_AUTHORIZING";
            case Platform.ACTION_GETTING_FRIEND_LIST:
                return "ACTION_GETTING_FRIEND_LIST";
            case Platform.ACTION_FOLLOWING_USER:
                return "ACTION_FOLLOWING_USER";
            case Platform.ACTION_SENDING_DIRECT_MESSAGE:
                return "ACTION_SENDING_DIRECT_MESSAGE";
            case Platform.ACTION_TIMELINE:
                return "ACTION_TIMELINE";
            case Platform.ACTION_USER_INFOR:
                return "ACTION_USER_INFOR";
            case Platform.ACTION_SHARE:
                return "ACTION_SHARE";
            default: {
                return "UNKNOWN";
            }
        }
    }

    @Override
    public void onComplete(Platform plat, int action, HashMap<String, Object> res) {

        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform palt, int action) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = palt;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform palt, int action, Throwable t) {
        t.printStackTrace();

        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = palt;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Platform plat = (Platform) msg.obj;
        String text = actionToString(msg.arg2);
        switch (msg.arg1) {
            case 1: {
                // 成功
                text = plat.getName() + " completed at " + text;
            }
                break;
            case 2: {
                // 失败
                text = plat.getName() + " caught error at " + text;
            }
                break;
            case 3: {
                // 取消
                text = plat.getName() + " canceled at " + text;
            }
                break;
        }

        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        return false;
    }

}
