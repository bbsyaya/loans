package com.will.loans.ui.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.json.LoginFirst;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;

/**
 * 登陆页面
 *
 * @author will
 *
 */
@SuppressLint("NewApi")
public class Register extends BaseTextActivity implements OnCheckedChangeListener {
	private EditText mPhoneNum;

	private TextView mBigPhoneNum;

	private CheckBox mCheckBox;

	private ImageView mImageView;

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
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_tv)).setText(R.string.fill_phone_num);
		mPhoneNum = (EditText) findViewById(R.id.phone_num);
		mBigPhoneNum = (TextView) findViewById(R.id.phone_num_big);
		mCheckBox = (CheckBox) findViewById(R.id.cb_agree);
		mImageView = (ImageView) findViewById(R.id.del_img);
		mImageView.setOnClickListener(this);
		mPhoneNum.addTextChangedListener(this);
		mCheckBox.setOnCheckedChangeListener(this);
		mNextBtn = (Button) findViewById(R.id.btn_next);
		mNextBtn.setOnClickListener(this);
		findViewById(R.id.user_use).setOnClickListener(this);
		findViewById(R.id.user_use_self).setOnClickListener(this);
		((TextView) findViewById(R.id.user_use)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		((TextView) findViewById(R.id.user_use_self)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0) {
			mNextBtn.setEnabled(true);
			mBigPhoneNum.setVisibility(View.VISIBLE);
			mBigPhoneNum.setText(s);
			mImageView.setVisibility(View.VISIBLE);
		} else {
			mNextBtn.setEnabled(false);
			mImageView.setVisibility(View.INVISIBLE);
			mBigPhoneNum.setVisibility(View.GONE);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_use:
			startActivity(new Intent(Register.this,WebBrowser.class).putExtra(WebBrowser.URL_STRING, "http://www.baidu.com"));
			break;
		case R.id.user_use_self:
			startActivity(new Intent(Register.this,WebBrowser.class).putExtra(WebBrowser.URL_STRING, "http://www.baidu.com"));
			break;
		case R.id.btn_next:
			buildParams();
			break;
		case R.id.del_img:
			mPhoneNum.setText("");
			mNextBtn.setEnabled(false);
			break;
		default:
			break;
		}
	}

	// private CustomProgressDialog mCustomProgressDialog = CustomProgressDialog
	// .createDialog(getApplication());

	public void getLoginInfo() {

	}

	public void buildParams() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("phoneNum", mPhoneNum.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		mAQuery.ajax(ServerInfo.LOGINFIRST,params, LoginFirst.class, mRegisterCallback);
	}

	AjaxCallback<LoginFirst> mRegisterCallback = new AjaxCallback<LoginFirst>() {
		@Override
		public void callback(String url, LoginFirst object, AjaxStatus status) {
			super.callback(url, object, status);
			Log.d("", object.resultflag+"  " + object.resultMsg);
			if (status!=null&&status.getCode() == 200) {
				// mCustomProgressDialog.dismiss();
				try {
					Intent intent = null;
					String state = object.resultflag;
					String token = object.token;
					SharePreferenceUtil.getUserPref(Register.this).setToken(token);
					if (state.equals("0")) {
						intent = new Intent(Register.this, FillPassword.class)
						.putExtra(FillVerifyCode.NUM,
								mPhoneNum.getText().toString()).putExtra(
										FillVerifyCode.TOKEN, token);
					} else if (state.equals("1")) {

					} else if (state.equals("3")) {
						intent = new Intent(Register.this, FillVerifyCode.class)
						.putExtra(FillVerifyCode.NUM,
								mPhoneNum.getText().toString()).putExtra(
										FillVerifyCode.TOKEN, token);
					} else if (state.equals("2")) {
						intent = new Intent(Register.this, SetPassword.class);
						intent.putExtra(SetPassword.SETTYPE, 0);
					}

					//					intent = new Intent(Register.this, FillPassword.class)
					//					.putExtra(FillVerifyCode.NUM,
					//							mPhoneNum.getText().toString()).putExtra(
					//									FillVerifyCode.TOKEN, token);
					if (intent != null) {
						startActivity(intent);
						Register.this.finish();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
