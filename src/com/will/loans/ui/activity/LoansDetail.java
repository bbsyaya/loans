package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.will.loans.R;
import com.will.loans.pay.EditPayActivity;

public class LoansDetail extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loans_detail);
		((Button) findViewById(R.id.title_btn_left)).setText(R.string.back);
		((Button) findViewById(R.id.title_btn_right)).setText(R.string.refresh);
	}

	public void enterBtn(View view) {
		startActivity(new Intent(LoansDetail.this, EditPayActivity.class));
	}
}
