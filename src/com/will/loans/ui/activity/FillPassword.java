
package com.will.loans.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.ConfirmPayActivity;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FillPassword extends BaseTextActivity {
    private EditText mPsw;

    private Button mLogin;

    private AQuery mAQuery;

    private String mNum, token;

    public static final String NUM = "com.will.loans.num";

    public static final String TOKEN = "com.will.loans.token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_psw);

        initView();
    }

    private void initView() {
        initTop();
        mNum = getIntent().getExtras().getString(NUM);
        token = getIntent().getExtras().getString(TOKEN);
        mAQuery = new AQuery(this);
        mPsw = (EditText) findViewById(R.id.et_psw);
        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);
        findViewById(R.id.forget_psw).setOnClickListener(this);
        findViewById(R.id.title_btn_left).setOnClickListener(this);
        mPsw.addTextChangedListener(this);
        // 设置下划线
        ((TextView) findViewById(R.id.forget_psw)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void buildParams() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", new Date().getTime());
            jo.put("phoneNum", mNum);
            jo.put("loginPsw", GenerateMD5Password.getMD5Password(mPsw.getText().toString()));
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAQuery.ajax(ServerInfo.LOGINBYPSW, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        if (object != null) {
                            try {
                                String result = object.optString("resultflag");
                                SharePreferenceUtil.getUserPref(FillPassword.this).setToken(
                                        object.optString("token"));
                                SharePreferenceUtil.getUserPref(FillPassword.this).setUserId(
                                        object.optString("userid"));
                                SharePreferenceUtil.getUserPref(FillPassword.this)
                                        .setUsername(mNum);
                                if (result.equals("0")) {
                                    Toast.makeText(getApplication(), "登陆成功", 1 * 1000).show();
                                    checkRealName();
                                    if (ConfirmPayActivity.product != null) {
                                        startActivity(new Intent(FillPassword.this,
                                                PaySelectionActivity.class));
                                    } else {
                                        FillPassword.this.finish();
                                    }
                                } else {
                                    Toast.makeText(getApplication(), object.optString("resultMsg"),
                                            1 * 1000).show();
                                }
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void checkRealName() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", new Date().getTime());
            jo.put("phoneNum", mNum);
            //            jo.put("loginPsw", GenerateMD5Password.getMD5Password(mPsw.getText().toString()));
            //            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAQuery.ajax(ServerInfo.REALNAMECHECK, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        String flag = object.optString("resultflag");
                        if (flag.equals("0")) {

                        } else if (flag.equals("1")) {
                            Log.e("autonavi", "" + object.toString() + "，请先进行实名认证！");
                            startActivity(new Intent(FillPassword.this,
                                    RealNameAuthentication.class));
                            FillPassword.this.finish();
                            return;
                        } else if (flag.equals("2")) {

                        }
                    }
                });

    }

    private void initTop() {
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.title_btn_right).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.fill_password);
        ((TextView) findViewById(R.id.title_btn_right)).setText(R.string.swicth_account);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.forget_psw:
                startActivity(new Intent(FillPassword.this, ResetPassword.class).putExtra(
                        ResetPassword.TYPE_NAME, 0));
                break;
            case R.id.btn_login:
                buildParams();
                break;
            case R.id.title_btn_right:
                startActivity(new Intent(FillPassword.this, Register.class));
                break;
            default:
                break;
        }
    }
}
