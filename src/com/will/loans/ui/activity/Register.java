
package com.will.loans.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.will.loans.R;

@SuppressLint("NewApi")
public class Register extends BaseActivity implements OnCheckedChangeListener, OnClickListener,
        TextWatcher {
    private EditText mPhoneNum;

    private TextView mBigPhoneNum;

    private CheckBox mCheckBox;

    private Button mNextBtn;

    private AQuery mAQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mAQuery = new AQuery(this);
        mPhoneNum = (EditText) findViewById(R.id.phone_num);
        mBigPhoneNum = (TextView) findViewById(R.id.phone_num_big);
        mCheckBox = (CheckBox) findViewById(R.id.cb_agree);
        mPhoneNum.addTextChangedListener(this);
        mCheckBox.setOnCheckedChangeListener(this);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        findViewById(R.id.user_use).setOnClickListener(this);
        findViewById(R.id.user_use_self).setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            mBigPhoneNum.setVisibility(View.VISIBLE);
            mBigPhoneNum.setText(s);
        } else {
            mBigPhoneNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_use:

                break;
            case R.id.user_use_self:

                break;
            case R.id.btn_next:

                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mNextBtn.setEnabled(true);
        }

    }
}
