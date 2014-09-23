package com.will.loans.ui.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.pay.EditPayActivity;

public class ProductDetail extends BaseActivity {
	public static JSONObject proDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((Button) findViewById(R.id.title_btn_right)).setText(R.string.refresh);
		((TextView)findViewById(R.id.title_tv)).setText(proDetail.optString("proName"));
	}

	public void enterBtn(View view) {
		EditPayActivity.product = proDetail;
		startActivity(new Intent(ProductDetail.this, EditPayActivity.class));
	}

	public void calculatorView(View view) {
		startActivity(new Intent(ProductDetail.this, PreEncoming.class));
	}

}
