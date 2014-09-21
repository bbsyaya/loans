
package com.will.loans.ui.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.json.LoginFirst;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.utils.Toaster;

public class FillVerifyCode extends BaseTextActivity {
	private EditText mVerifyCode;

	private Button mCountDown, mLogin;

	private String mNum,token;
	public static final String  NUM = "com.will.loans.num";
	public static final String  TOKEN = "com.will.loans.token";
	private AQuery mAQuery;
	private CountDown mCountDownTimer;
	private Long millisInFuture = 60*1000L, countDownInterval=1000L;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mNum = getIntent().getExtras().getString(NUM);
		token = getIntent().getExtras().getString(TOKEN);
		setContentView(R.layout.activity_fill_vrify_code);
		init();
	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("phoneNum", mNum);
			jo.put("verCode", mVerifyCode.getText().toString());
			jo.put("token", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		mAQuery.ajax(ServerInfo.REGISTERORLOGINBYMSG,params, JSONObject.class, mRegisterCallback);
	}

	AjaxCallback<JSONObject> mRegisterCallback = new AjaxCallback<JSONObject>() {
		@Override
		public void callback(String url, JSONObject object, AjaxStatus status) {
			super.callback(url, object, status);
			Log.d("", object.toString());
			if (status.getCode() == 200) {
				//				mCustomProgressDialog.dismiss();
			}
			try {
				String state = object.getString("resultflag");
				if (state.equals("0")) {
					SharePreferenceUtil.getUserPref(FillVerifyCode.this).setToken(object.getString("token"));
					SharePreferenceUtil.getUserPref(FillVerifyCode.this).setUserId(object.getString("userid"));
					SharePreferenceUtil.getUserPref(FillVerifyCode.this).setUsername(mNum);
					startActivity(new Intent(FillVerifyCode.this,SetPassword.class).putExtra(SetPassword.SETTYPE, 0));
					FillVerifyCode.this.finish();
				}else{
					Toast.makeText(getApplication(), object.getString("resultMsg"), 1*1000).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};


	private void init() {
		initTop();
		mCountDownTimer = new CountDown(millisInFuture, countDownInterval);
		mAQuery = new AQuery(this);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_tv)).setText(R.string.fill_verify_num);
		mVerifyCode = (EditText) findViewById(R.id.et_veri_code);
		mCountDown = (Button) findViewById(R.id.btn_count_down);
		((TextView) findViewById(R.id.tv_send_msg)).setText("已向"+mNum+"发送短信，请再输入框中填写验证码完成注册");;

		mLogin = (Button) findViewById(R.id.btn_confirm);
		mLogin.setOnClickListener(this);
		mCountDown.setOnClickListener(this);
		mVerifyCode.addTextChangedListener(this);

	}

	private void initTop() {
		// TODO Auto-generated method stub

	}

	public void reGetVerycode() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", System.currentTimeMillis());
			jo.put("phoneNum", mNum);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		mAQuery.ajax(ServerInfo.LOGINFIRST,params, LoginFirst.class, new AjaxCallback<LoginFirst>(){
			@Override
			public void callback(String url, LoginFirst object,
					AjaxStatus status) {
				if (status!=null&&status.getCode() == 200) {
					Toaster.showShort(FillVerifyCode.this, "获取验证码成功！");
				}
			}
		});
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			buildParams();
			break;
		case R.id.btn_count_down:
			reGetVerycode();
			mCountDownTimer.start();
			mCountDown.setEnabled(false);
			break;
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}

	}

	class CountDown extends CountDownTimer {

		public CountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mCountDown.setText(millisUntilFinished/1000+getResources().getString(R.string.countdown_code));

		}

		@Override
		public void onFinish() {
			mCountDown.setEnabled(true);
			mCountDown.setText(R.string.reget_verifycode);
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

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

}
