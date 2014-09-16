
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.will.loans.R;

public class FillVerifyCode extends BaseTextActivity {
    private EditText mVerifyCode;

    private Button mCountDown, mLogin;

    private AQuery mAQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_vrify_code);
        init();
    }

    private void init() {
        initTop();
        mAQuery = new AQuery(this);
        mVerifyCode = (EditText) findViewById(R.id.et_veri_code);
        mCountDown = (Button) findViewById(R.id.btn_count_down);
        mLogin = (Button) findViewById(R.id.btn_confirm);
        mLogin.setOnClickListener(this);
        mCountDown.setOnClickListener(this);
        mVerifyCode.addTextChangedListener(this);
    }

    private void initTop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub

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
