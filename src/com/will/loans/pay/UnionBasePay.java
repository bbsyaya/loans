
package com.will.loans.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.will.loans.R;
import com.will.loans.constant.ServerInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class UnionBasePay extends Activity implements Callback, Runnable {
    public static final String LOG_TAG = "PayDemo";

    private Context mContext = null;

    protected Handler mHandler = null;

    protected ProgressDialog mLoadingDialog = null;

    public static final int PLUGIN_VALID = 0;

    public static final int PLUGIN_NOT_INSTALLED = -1;

    public static final int PLUGIN_NEED_UPGRADE = 2;

    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "00";

    private static final String TN_URL_01 = ServerInfo.BUYPRODUCT;

    protected Button nextBtn;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLoadingDialog = ProgressDialog.show(mContext, // context
                    "", // title
                    "任务正在执行,请稍候...", // message
                    true); // 进度是否是不确定的，这只和创建进度条有关
            getDate();
            /*************************************************
             * 步骤1：从网络开始,获取交易流水号即TN
             ************************************************/
            new Thread(UnionBasePay.this).start();
        }
    };

    protected void getDate() {

    }

    public abstract void doStartUnionPayPlugin(Activity activity, String tn, String mode);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mHandler = new Handler(this);

        setContentView(R.layout.activity_union_pay);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(mClickListener);
        afterSetContentView();

    }

    protected void afterSetContentView() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.e(LOG_TAG, " " + "" + msg.obj);
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {

            tn = (String) msg.obj;
            Log.e(LOG_TAG, " " + "" + tn);
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/
            doStartUnionPayPlugin(this, tn, mMode);
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UnionBasePay.this.finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {

            String url = TN_URL_01;

            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }

    int startpay(Activity act, String tn, int serverIdentifier) {
        return 0;
    }
}
