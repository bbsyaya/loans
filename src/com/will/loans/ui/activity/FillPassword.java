
package com.will.loans.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.will.loans.R;

public class FillPassword extends BaseActivity implements TextWatcher, OnClickListener {
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
