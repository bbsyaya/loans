
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import com.will.loans.R;

public class PersonCenter extends BaseActivity {
    private TextView mUserId, mUsername, mIdCard, mBankcard, mChangeLoginPsw, mChangeTradePsw;

    private RadioButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
    }
}
