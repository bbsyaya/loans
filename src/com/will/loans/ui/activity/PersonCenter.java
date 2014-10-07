
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.beans.json.UserAccount;
import com.will.loans.ui.fragment.IWant;

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
        if (IWant.useraccount != null) {
            updateView(IWant.useraccount);
        }
    }

    private void updateView(UserAccount object) {
        mUserId.setText(object.userName);
        mUsername.setText(object.realName);
        mIdCard.setText(object.idcardNo);
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
                startActivity(new Intent(PersonCenter.this, ResetPassword.class).putExtra(
                        ResetPassword.TYPE_NAME, 0));
                break;
            case R.id.account_change_trade_psw:
                startActivity(new Intent(PersonCenter.this, ResetPassword.class).putExtra(
                        ResetPassword.TYPE_NAME, 1));
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
