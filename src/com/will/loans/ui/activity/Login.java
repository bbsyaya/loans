
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.will.loans.R;

public class Login extends BaseTextActivity {
    private Button mBtnLogin;

    private EditText mPsw;

    private TextView mPhoneNum;

    private AQuery mAQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_login);

        initView();
    }

    private void initView() {
        initTop();
        mAQuery = new AQuery(this);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mPsw = (EditText) findViewById(R.id.et_psw);
        mPhoneNum = (TextView) findViewById(R.id.text_phone_num);
    }

    private void initTop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
