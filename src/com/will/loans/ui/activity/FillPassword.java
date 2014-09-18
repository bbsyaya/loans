
package com.will.loans.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FillPassword extends BaseTextActivity {
    private EditText mPsw;

    private Button mLogin;

    private AQuery mAQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_psw);

        initView();
    }

    private void initView() {
        initTop();
        mAQuery = new AQuery(this);
        mPsw = (EditText) findViewById(R.id.et_psw);
        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);
        findViewById(R.id.forget_psw).setOnClickListener(this);
        mPsw.addTextChangedListener(this);
        //设置下划线
        ((TextView) findViewById(R.id.forget_psw)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void buildParams() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", new Date().getTime());
            jo.put("phoneNum", "13799568671");
            jo.put("token", "1FBE22C74C30107226974F5EA89C6B8D");
            jo.put("verCode", "960295");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        // aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
        // loginFirst
        // registerOrLoginByMsg
        mAQuery.ajax("http://125.77.254.170:8086/daidaitongServer/mobile/loginFirst", params,
                JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        System.out.println(" " + object.toString());
                    }
                });
    }

    private void initTop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            mLogin.setEnabled(true);
        } else {
            mLogin.setEnabled(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_psw:

                break;
            case R.id.btn_login:

                break;
            default:
                break;
        }
    }
}
