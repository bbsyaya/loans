
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.json.PreIncomeJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.utils.Toaster;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends BaseActivity implements TextWatcher{
    /**
     * 设置密码类型，0是修改密码，1是修改交易密码
     */
    private int mType = 0;
    public static String TYPE_NAME = "RESETPASSWORDTYPE";
    private String token;
    private AQuery mAq;
    private CountDown mCountDownTimer;
    private EditText mRealName, mUserIdCard, mUsername, mVerifyCode;
    private Button mNextBtn, mGetVerifyCode;
    private final Long millisInFuture = 60 * 1000L, countDownInterval = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw);
        mType = getIntent().getExtras().getInt(TYPE_NAME);
        init();
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                checkUserId();
                break;
            case R.id.get_code:
                getVerifyCode();
                break;
        }

    }

    private void init() {
        mAq = new AQuery(this);
        mCountDownTimer = new CountDown(millisInFuture, countDownInterval);
        mRealName = (EditText) findViewById(R.id.realname);
        mUserIdCard = (EditText) findViewById(R.id.idcard);
        mUsername = (EditText) findViewById(R.id.username);
        mVerifyCode = (EditText) findViewById(R.id.message_code);
        mVerifyCode.addTextChangedListener(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        if (mType == 0) {
            ((TextView) findViewById(R.id.title_tv)).setText(R.string.reset_login_psw);
            mUsername.setText(SharePreferenceUtil.getUserPref(this).getUsername());
        } else {
            ((TextView) findViewById(R.id.title_tv)).setText(R.string.reset_trade_psw);
        }

        mNextBtn = (Button) findViewById(R.id.next_btn);
        mGetVerifyCode = (Button) findViewById(R.id.get_code);
        mNextBtn.setOnClickListener(this);
        mGetVerifyCode.setOnClickListener(this);

    }

    public void getVerifyCode() {
        mCountDownTimer.start();
        mGetVerifyCode.setEnabled(false);
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("realName", mRealName.getText().toString());
            jo.put("idcardNo", mUserIdCard.getText().toString());
            jo.put("phoneNum", mUsername.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAq.ajax(ServerInfo.GETVERCODE, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        if (object!=null&&object.optString("resultflag").equals("0")){
                            Toaster.showShort(ResetPassword.this,"验证码获取成功！");
                            token = object.optString("token");
                        }else{
                            Toaster.showShort(ResetPassword.this,object.optString("resultMsg"));
                        }

                    }
                });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mVerifyCode.getText().toString().equals("")){
            mNextBtn.setEnabled(false);
        }else{
            mNextBtn.setEnabled(true);
        }
    }

    class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mGetVerifyCode.setText(millisUntilFinished / 1000
                    + getResources().getString(R.string.countdown_code));

        }

        @Override
        public void onFinish() {
            mGetVerifyCode.setEnabled(true);
            mGetVerifyCode.setText(R.string.reget_verifycode);
        }

    }


    public void checkUserId() {
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("verCode", mVerifyCode.getText().toString());
            jo.put("token", token);
            jo.put("sign", GenerateMD5Password.encodeByMD5(token
                    + smf.format(time) + key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAq.ajax(ServerInfo.CHANGEPSWCHECK, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        if (object!=null&&object.optString("resultflag").equals("0")){
                            if (mType==0){
                                startActivity(new Intent(ResetPassword.this, SetPassword.class).putExtra(
                                        SetPassword.SETTYPE, 0));
                            }else{
                                startActivity(new Intent(ResetPassword.this, SetPassword.class).putExtra(
                                        SetPassword.SETTYPE, 1));
                            }
                        }else{
                            Toaster.showShort(ResetPassword.this,object.optString("resultMsg"));
                        }

                    }
                });

    }

}
