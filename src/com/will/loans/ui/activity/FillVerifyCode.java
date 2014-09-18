
package com.will.loans.ui.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.will.loans.beans.UserinfoCache;

public class FillVerifyCode extends BaseTextActivity {
	private EditText mVerifyCode;

	private Button mCountDown, mLogin;

	private String mNum,token;
	public static final String  NUM = "com.will.loans.num";
	public static final String  TOKEN = "com.will.loans.token";
	private AQuery mAQuery;

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
			jo.put("token", TOKEN);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		mAQuery.ajax(
				"http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
				params, JSONObject.class, mRegisterCallback);
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
					UserinfoCache.setToken(object.getString("token"));
					UserinfoCache.userId = object.getString("userid");
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
		mAQuery = new AQuery(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			buildParams();
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
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

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
