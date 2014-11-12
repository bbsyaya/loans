
package com.will.loans.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.ConfirmPayActivity;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FillPayCode extends BaseTextActivity {
    private EditText mVerifyCode;

    private Button mCountDown, mLogin;

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    // 生成广播处理
    private SmsBroadCastReceiver smsBroadCastReceiver;

    private IntentFilter intentFilter;

    private AQuery mAQuery;

    private CountDown mCountDownTimer;

    private final Long millisInFuture = 60 * 1000L, countDownInterval = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay_code);
        init();
    }

    public void buildParams() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("orderid", ConfirmPayActivity.product.optString("id"));
            jo.put("ybVerCode", mVerifyCode.getText().toString());
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAQuery.ajax(ServerInfo.YBCONFIRMPAY, params, JSONObject.class, mRegisterCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsBroadCastReceiver);
    }

    public class SmsBroadCastReceiver extends BroadcastReceiver {

        private EditText editText;

        public SmsBroadCastReceiver(EditText et) {
            editText = et;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] object = (Object[]) bundle.get("pdus");
            SmsMessage sms[] = new SmsMessage[object.length];
            for (int i = 0; i < object.length; i++) {
                sms[0] = SmsMessage.createFromPdu((byte[]) object[i]);
                String smsContent = sms[i].getDisplayMessageBody();
                System.out.println("code = " + NumberFilter(smsContent));
                if (smsContent.contains("安于心理财")) {
                    editText.setText(NumberFilter(smsContent));
                }
            }
            // 终止广播，在这里我们可以稍微处理，根据用户输入的号码可以实现短信防火墙。
            // abortBroadcast();
        }

    }

    public static String NumberFilter(String str) throws PatternSyntaxException {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String code = m.replaceAll("").trim().substring(1, m.replaceAll("").trim().length());
        return code;
    }

    AjaxCallback<JSONObject> mRegisterCallback = new AjaxCallback<JSONObject>() {
        @Override
        public void callback(String url, JSONObject object, AjaxStatus status) {
            super.callback(url, object, status);
            Log.d("", object.toString());
            if (status.getCode() == 200) {
                // mCustomProgressDialog.dismiss();
            }
            try {
                String state = object.optString("resultflag");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private void init() {
        initTop();
        mCountDownTimer = new CountDown(millisInFuture, countDownInterval);
        mAQuery = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.fill_verify_num);
        mVerifyCode = (EditText) findViewById(R.id.et_veri_code);
        //		mCountDown = (Button) findViewById(R.id.btn_count_down);
        //		((TextView) findViewById(R.id.tv_send_msg)).setText("已向" + mNum
        //				+ "发送短信，请再输入框中填写验证码完成注册");
        //		;

        mLogin = (Button) findViewById(R.id.btn_confirm);
        mLogin.setOnClickListener(this);
        mCountDown.setOnClickListener(this);
        mVerifyCode.addTextChangedListener(this);

        smsBroadCastReceiver = new SmsBroadCastReceiver(mVerifyCode);
        intentFilter = new IntentFilter(SMS_RECEIVED_ACTION);

        registerReceiver(smsBroadCastReceiver, intentFilter);

    }

    private void initTop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                buildParams();
                break;
            case R.id.btn_count_down:
                //			mCountDownTimer.start();
                //			mCountDown.setEnabled(false);
                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }

    }

    class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mCountDown.setText(millisUntilFinished / 1000
                    + getResources().getString(R.string.countdown_code));

        }

        @Override
        public void onFinish() {
            mCountDown.setEnabled(true);
            mCountDown.setText(R.string.reget_verifycode);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mLogin.setEnabled(true);
        } else {
            mLogin.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

}
