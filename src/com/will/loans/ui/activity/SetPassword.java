
package com.will.loans.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.weight.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("NewApi")
public class SetPassword extends BaseTextActivity implements OnCheckedChangeListener {
    private EditText pwET;

    private EditText epwET;

    private AQuery aq;

    private TextView mBigPhoneNum;

    private Button mNextBtn;

    private AQuery mAQuery;

    private ProgressDialog mLoadingDialog;

    /**
     * type=0设置密码 ,type=1设置交易密码
     */
    private int type = 0;

    public static String SETTYPE = "com.will.setpassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        aq = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        type = getIntent().getExtras().getInt(SETTYPE);

        if (type == 0) {
            ((TextView) findViewById(R.id.title_tv)).setText("设置密码");
        } else {
            ((TextView) findViewById(R.id.title_tv)).setText("设置交易密码");
        }
        initView();
    }

    private void checkHaveTradePsw() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(SetPassword.this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(SetPassword.this).getToken());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.HASTRADEPSW, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    if (json.optString("tradePsw").equals("")) {
                        startActivity(new Intent(SetPassword.this, SetPassword.class).putExtra(
                                SetPassword.SETTYPE, 1));
                        return;
                    } else {
                        mLoadingDialog = ProgressDialog.show(SetPassword.this, // context
                                "", // title
                                "正在努力的获取tn中,请稍候...", // message
                                true); // 进度是否是不确定的，这只和创建进度条有关
                    }
                }
            }
        });

    }

    private void initView() {
        mAQuery = new AQuery(this);
        pwET = (EditText) findViewById(R.id.pwET);
        epwET = (EditText) findViewById(R.id.epwET);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);

        pwET.addTextChangedListener(this);
        epwET.addTextChangedListener(this);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (pwET.getText().length() > 5 && pwET.getText().length() == epwET.getText().length()) {
            mNextBtn.setEnabled(true);
        } else {
            mNextBtn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.user_use:

                break;
            case R.id.user_use_self:

                break;
            case R.id.btn_next:
                buildParams();
                break;
            default:
                break;
        }
    }

    private CustomProgressDialog mCustomProgressDialog;

    public void getLoginInfo() {
        mCustomProgressDialog = CustomProgressDialog.createDialog(this);
        mAQuery.ajax("", String.class, mRegisterCallback).dismiss(mCustomProgressDialog);
    }

    public void buildParams() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            if (type == 0) {
                jo.put("loginPsw", GenerateMD5Password.encodeByMD5(epwET.getText().toString()));
            } else {
                jo.put("tradePsw", epwET.getText().toString());
            }
            jo.put("userid", SharePreferenceUtil.getUserPref(SetPassword.this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(SetPassword.this).getToken());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mLoadingDialog = ProgressDialog.show(SetPassword.this, // context
                "", // title
                "正在努力的获取tn中,请稍候...", // message
                true);
        String url = "";
        if (type == 0) {
            url = ServerInfo.SETLOGINPSW;
        } else {
            url = ServerInfo.SETTRADEPSW;
        }
        mAQuery.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                mLoadingDialog.cancel();
                Log.e("11", "" + json.toString());
                if (json.optString("resultflag").equals("0")) {
                    if (type == 0) {
                        SharePreferenceUtil.getUserPref(SetPassword.this).setPasswd(
                                epwET.getText().toString());
                        Toast.makeText(SetPassword.this, "设置密码成功", Toast.LENGTH_SHORT).show();
                        checkHaveTradePsw();
                        Intent intent = new Intent(SetPassword.this, HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        SharePreferenceUtil.getUserPref(SetPassword.this).setTradePassword(
                                epwET.getText().toString());
                        Toast.makeText(SetPassword.this, "设置交易密码成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(SetPassword.this, json.optString("resultMsg"),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    AjaxCallback<String> mRegisterCallback = new AjaxCallback<String>() {
        @Override
        public void callback(String url, String object, AjaxStatus status) {
            super.callback(url, object, status);
            if (status.getCode() == 200) {
                mCustomProgressDialog.dismiss();
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (pwET.getText().length() > 5 && pwET.getText().length() == epwET.getText().length()) {
            mNextBtn.setEnabled(true);
        } else {
            mNextBtn.setEnabled(false);
        }
    }
}
