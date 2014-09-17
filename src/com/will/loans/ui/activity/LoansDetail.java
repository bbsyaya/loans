package com.will.loans.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.will.loans.R;

public class LoansDetail extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loans_detail);
		((Button) findViewById(R.id.title_btn_left)).setText(R.string.back);
		((Button) findViewById(R.id.title_btn_right)).setText(R.string.refresh);
	}
}
