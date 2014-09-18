package com.will.loans.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.weight.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("NewApi")
public class Register extends BaseTextActivity implements
		OnCheckedChangeListener {
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
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
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

	private CustomProgressDialog mCustomProgressDialog = CustomProgressDialog
			.createDialog(getApplication());

	public void getLoginInfo() {
		mAQuery.ajax("", String.class, mRegisterCallback).dismiss(
				mCustomProgressDialog);
	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("phoneNum", "13799568671");
			jo.put("token", "1FBE22C74C30107226974F5EA89C6B8D");
			jo.put("verCode", "960295");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		mAQuery.ajax(
				"http://125.77.254.170:8086/daidaitongServer/mobile/loginFirst",
				params, JSONObject.class, new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {
						System.out.println(" " + object.toString());
					}
				});
	}

	AjaxCallback<String> mRegisterCallback = new AjaxCallback<String>() {
		@Override
		public void callback(String url, String object, AjaxStatus status) {
			super.callback(url, object, status);
			if (status.getCode() == 200) {
				mCustomProgressDialog.dismiss();
			}
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mNextBtn.setEnabled(true);
		}

	}
}
