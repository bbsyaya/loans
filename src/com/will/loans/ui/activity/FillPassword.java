package com.will.loans.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class FillPassword extends BaseTextActivity {

	protected static JSONObject registerInfo;

	private EditText mPsw;

	private Button mLogin;

	private AQuery mAQuery;

	private ProgressDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_psw);
		((TextView) findViewById(R.id.title_tv)).setText("填写登录密码");
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
		// 设置下划线
		((TextView) findViewById(R.id.forget_psw)).getPaint().setFlags(
				Paint.UNDERLINE_TEXT_FLAG);
	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("phoneNum", registerInfo.optString("phoneNum"));
			jo.put("loginPsw", mPsw.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		mLoadingDialog = ProgressDialog.show(FillPassword.this, // context
				"", // title
				"正在努力加载中，请稍候...", // message
				true);
		mAQuery.ajax(Configs.HOST + "/loginByPsw", params, JSONObject.class,
				new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject json,
							AjaxStatus status) {
						mLoadingDialog.cancel();
						Log.e("11", json.toString());
						if (json.optString("resultflag").equals("0")) {
							SharePreferenceUtil.getUserPref(FillPassword.this)
									.setUserId(json.optString("userid"));
							SharePreferenceUtil.getUserPref(FillPassword.this)
									.setToken(json.optString("token"));
							SharePreferenceUtil.getUserPref(FillPassword.this)
									.setUsername(
											registerInfo.optString("phoneNum"));
							// Intent intent = new Intent(FillPassword.this,
							// HomePage.class);
							// startActivity(intent);
							finish();
						}
					}
				});
	}

	private void initTop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0) {
			mLogin.setEnabled(true);
		} else {
			mLogin.setEnabled(false);
		}
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
			buildParams();
			break;
		default:
			break;
		}
	}
}
