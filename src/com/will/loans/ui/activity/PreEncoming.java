package com.will.loans.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PreEncoming extends BaseActivity implements TextWatcher,
		OnClickListener {
	private final String mUrl = "http://daidaitong.imwanmei.com:8080/mobile/estimateProfit";
	private TextView mTitle, mMoney, mMonth, mPreEcoming;
	private EditText mNum;
	private Button mGetEncoming;
	private AQuery mAquery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_encoming);

		init();
	}

	private void init() {
		mAquery = new AQuery(this);
		mTitle = (TextView) findViewById(R.id.encomint_title);
		mMoney = (TextView) findViewById(R.id.total_money);
		mMonth = (TextView) findViewById(R.id.encoming_money);
		mPreEcoming = (TextView) findViewById(R.id.encomint_num);
		mNum = (EditText) findViewById(R.id.money_number);
		mNum.addTextChangedListener(this);
		mGetEncoming = (Button) findViewById(R.id.btn_pre_encoming);
		mGetEncoming.setOnClickListener(this);

		mTitle.setText(LoansDetail.pro.optString("proName"));
		mMoney.setText(LoansDetail.pro.optInt("totalAmount") + " 元");
		buildParams();
	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("proId", LoansDetail.pro.optInt("id"));
			jo.put("money", mNum.getText().toString().equals("") ? 10000 : mNum
					.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		mAquery.ajax(mUrl, params, JSONObject.class, mCallback);
	}

	AjaxCallback<JSONObject> mCallback = new AjaxCallback<JSONObject>() {
		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			System.out.println(" " + object.toString());
			try {
				mPreEcoming.setText(object.getLong("estimateProfit") + "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onClick(android.view.View v) {
		buildParams();
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	};
}
