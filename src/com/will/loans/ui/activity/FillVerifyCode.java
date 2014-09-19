package com.will.loans.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.utils.Configs;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FillVerifyCode extends BaseTextActivity {
	private EditText mVerifyCode;

	private Button nextBtn;

	private AQuery mAQuery;

	public static JSONObject registerInfo;

	private ProgressDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mAQuery = new AQuery(this);
		setContentView(R.layout.activity_fill_vrify_code);
		((TextView) findViewById(R.id.title_tv)).setText("填写验证码");
		init();
	}

	private void init() {
		initTop();
		mAQuery = new AQuery(this);
		mVerifyCode = (EditText) findViewById(R.id.et_psw);
		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);
		mVerifyCode.addTextChangedListener(this);
	}

	private void initTop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextBtn:
			buildParams();
			break;

		default:
			break;
		}
	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("phoneNum", registerInfo.optString("phoneNum"));
			jo.put("token", registerInfo.optString("token"));
			jo.put("verCode", mVerifyCode.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		mLoadingDialog = ProgressDialog.show(FillVerifyCode.this, // context
				"", // title
				"正在努力加载中,请稍候...", // message
				true);
		mAQuery.ajax(Configs.HOST + "/registerOrLoginByMsg", params,
				JSONObject.class, new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject json,
							AjaxStatus status) {
						mLoadingDialog.cancel();
						if (json.optString("resultflag").equals("0")) {
							SharePreferenceUtil
									.getUserPref(FillVerifyCode.this)
									.setUserId(json.optString("userid"));
							SharePreferenceUtil
									.getUserPref(FillVerifyCode.this).setToken(
											json.optString("token"));
							SharePreferenceUtil
									.getUserPref(FillVerifyCode.this)
									.setUsername(json.optString("phoneNum"));
							Intent intent = new Intent(FillVerifyCode.this,
									SetPassword.class);
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(FillVerifyCode.this,
									json.optString("resultMsg"),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
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
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0 && s.length() == 6) {
			nextBtn.setEnabled(true);
		} else {
			nextBtn.setEnabled(false);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
