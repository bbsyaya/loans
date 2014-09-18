package com.will.loans.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.weight.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("NewApi")
public class SetPassword extends BaseTextActivity implements
		OnCheckedChangeListener {
	private EditText pwET;
	private EditText epwET;

	private TextView mBigPhoneNum;

	private Button mNextBtn;

	private AQuery mAQuery;

	private ProgressDialog mLoadingDialog;

	/**
	 * type=0设置密码 ,type=1设置交易密码
	 */
	public static int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpassword);
		type = 0;
		if (type == 0) {
			((TextView) findViewById(R.id.title_tv)).setText("设置密码");
		} else {
			((TextView) findViewById(R.id.title_tv)).setText("设置交易密码");
		}
		initView();
	}

	private void initView() {
		mAQuery = new AQuery(this);
		pwET = (EditText) findViewById(R.id.pwET);
		epwET = (EditText) findViewById(R.id.epwET);

		pwET.addTextChangedListener(this);
		epwET.addTextChangedListener(this);
		mNextBtn = (Button) findViewById(R.id.btn_next);
		mNextBtn.setOnClickListener(this);

		// buildParams();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (pwET.getText().length() > 5
				&& pwET.getText().length() == epwET.getText().length()) {
			mNextBtn.setEnabled(true);
		} else {
			mNextBtn.setEnabled(false);
		}
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
			buildParams();
			break;
		default:
			break;
		}
	}

	private CustomProgressDialog mCustomProgressDialog;

	public void getLoginInfo() {
		mCustomProgressDialog = CustomProgressDialog.createDialog(this);
		mAQuery.ajax("", String.class, mRegisterCallback).dismiss(
				mCustomProgressDialog);
	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			if(type==0) {
				jo.put("loginPsw", epwET.getText().toString());
			}else {
				jo.put("tradePsw", epwET.getText().toString());
			}
			jo.put("userid", SharePreferenceUtil.getUserPref(SetPassword.this)
					.getUserId());
			jo.put("token", SharePreferenceUtil.getUserPref(SetPassword.this)
					.getToken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		mLoadingDialog = ProgressDialog.show(SetPassword.this, // context
				"", // title
				"正在努力的获取tn中,请稍候...", // message
				true);
		String url = "";
		if (type == 0) {
			url = "http://daidaitong.imwanmei.com:8080/mobile/setLoginPsw";
		} else {
			url = "http://daidaitong.imwanmei.com:8080/mobile/setTradePsw";
		}
		mAQuery.ajax(url, params, JSONObject.class,
				new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject json,
							AjaxStatus status) {
						mLoadingDialog.cancel();
						Log.e("11", "" + json.toString());
						if (json.optString("resultflag").equals("0")) {
							FillVerifyCode.registerInfo = json;
							startActivity(new Intent(SetPassword.this,
									FillVerifyCode.class));
							if (type == 0) {
								SharePreferenceUtil.getUserPref(SetPassword.this).setPasswd(epwET.getText().toString());
								Toast.makeText(SetPassword.this, "设置密码成功",
										Toast.LENGTH_SHORT).show();
								finish();
							} else {
								SharePreferenceUtil.getUserPref(SetPassword.this).setTradePassword(epwET.getText().toString());
								Toast.makeText(SetPassword.this, "设置交易密码成功",
										Toast.LENGTH_SHORT).show();
								finish();
							}
						} else {
							Toast.makeText(SetPassword.this,
									json.optString("resultMsg"),
									Toast.LENGTH_SHORT).show();
						}
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
		if (pwET.getText().length() > 5
				&& pwET.getText().length() == epwET.getText().length()) {
			mNextBtn.setEnabled(true);
		} else {
			mNextBtn.setEnabled(false);
		}
	}
}
