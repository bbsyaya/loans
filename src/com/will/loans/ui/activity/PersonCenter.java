
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.json.UserInfoJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonCenter extends BaseActivity implements OnCheckedChangeListener {

    public static final String ISGESTUREOPEN = "com.will.loans.isgestureopen";

    private boolean mIsGestureOpen = false;

    private TextView mUserId, mUsername, mIdCard, mBankcard, mChangeLoginPsw, mChangeTradePsw;

    private CheckBox mButton;

    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        initView();
    }

    private void initView() {
        aq = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.account_center);
        mUserId = (TextView) findViewById(R.id.account_name);
        mUsername = (TextView) findViewById(R.id.account_realname);
        mIdCard = (TextView) findViewById(R.id.account_userid);
        mBankcard = (TextView) findViewById(R.id.account_bank);
        mChangeLoginPsw = (TextView) findViewById(R.id.account_change_psw);
        mChangeTradePsw = (TextView) findViewById(R.id.account_change_trade_psw);
        mButton = (CheckBox) findViewById(R.id.account_open_toggle);
        mButton.setChecked(mIsGestureOpen);
        initListener();
    }

    /**
     * 请求接口数据
     */
    private void getDate() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.USERMSG, params, UserInfoJson.class, new AjaxCallback<UserInfoJson>() {
            @Override
            public void callback(String url, UserInfoJson object, AjaxStatus status) {
                Log.d("loans", object.toString());
                if (object != null) {
                    updateView(object);
                }
            }

        });
    }

    private void updateView(UserInfoJson object) {
        mUserId.setText(object.userMsg.userName);
        mUsername.setText(object.userMsg.realName);
        mIdCard.setText(object.userMsg.idcardNo);
    }

    private void initListener() {
        mChangeLoginPsw.setOnClickListener(this);
        mChangeTradePsw.setOnClickListener(this);
        findViewById(R.id.account_bankcard).setOnClickListener(this);
        mButton.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.account_bankcard:

                break;
            case R.id.account_change_psw:
                startActivity(new Intent(PersonCenter.this, SetPassword.class).putExtra(
                        SetPassword.SETTYPE, 0));
                break;
            case R.id.account_change_trade_psw:
                startActivity(new Intent(PersonCenter.this, SetPassword.class).putExtra(
                        SetPassword.SETTYPE, 1));
                break;
            case R.id.account_open_toggle:

                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //			startActivity(new Intent(PersonCenter.this,GuideGesturePasswordActivity.class));
        } else {

        }

    }
}
