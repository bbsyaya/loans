package com.will.loans.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.ui.activity.LoansDetail;
import com.will.loans.ui.activity.Register;
import com.will.loans.ui.activity.SetPassword;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.utils.Toaster;

public class EditPayActivity extends BasePayActivity implements OnClickListener{

	private EditText moneyET;

	private final JSONObject product = LoansDetail.pro;

	private AQuery aq;

	@Override
	public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
		UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,
				tn, mode);
	}

	@Override
	protected void afterSetContentView() {

		aq = new AQuery(this);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_tv)).setText("投标");
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SharePreferenceUtil.getUserPref(EditPayActivity.this)
						.getTradePassword().equals("")) {
					startActivity(new Intent(EditPayActivity.this,
							SetPassword.class).putExtra(SetPassword.SETTYPE, 1));
					return;
				}
				mLoadingDialog = ProgressDialog.show(EditPayActivity.this, // context
						"", // title
						"正在努力的获取tn中,请稍候...", // message
						true); // 进度是否是不确定的，这只和创建进度条有关
				getDate();
			}
		});
		nextBtn.setEnabled(false);

		moneyET = (EditText) findViewById(R.id.moneyET);
		moneyET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (TextUtils.isEmpty(moneyET.getText().toString())) {
					return;
				}
				int money = Integer.parseInt(moneyET.getText().toString());
				if (money == 0 || money % product.optInt("startBuy") != 0) {
					nextBtn.setEnabled(false);
				} else {
					nextBtn.setEnabled(true);
				}
			}
		});
		updateView();

		if (SharePreferenceUtil.getUserPref(this).getToken().equals("")) {
			startActivity(new Intent(EditPayActivity.this, Register.class));
		} else {
			if (SharePreferenceUtil.getUserPref(this).getTradePassword()
					.equals("")) {
				startActivity(new Intent(EditPayActivity.this,
						SetPassword.class).putExtra(SetPassword.SETTYPE, 1));
			}
		}
	}

	private void getDate() {
		// TODO EditPay完善参数
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("userid",
					SharePreferenceUtil.getUserPref(EditPayActivity.this)
					.getUserId());
			jo.put("token",
					SharePreferenceUtil.getUserPref(EditPayActivity.this)
					.getToken());
			jo.put("amount", moneyET.getText().toString());
			jo.put("proId", product.optString("id"));
			jo.put("tradePsw",
					SharePreferenceUtil.getUserPref(EditPayActivity.this)
					.getTradePassword());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aq.ajax(ServerInfo.BUYPRODUCT, params, JSONObject.class,
				new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json,
					AjaxStatus status) {
				mLoadingDialog.cancel();
				String result = json.optString("resultflag");
				if (result.equals("0")) {
					String tn = json.optString("tn");
					Message msg = mHandler.obtainMessage();
					msg.obj = tn;
					mHandler.sendMessage(msg);
				}else if(result.equals("1")||result.equals("2")){
					Toaster.showShort(getParent(), json.optString("resultMsg"));
				}
			}
		});

	}

	private void updateView() {
		setTextView(R.id.nameTV, product.optString("proName"), "");
		setTextView(R.id.moneyTV, "起投金额：" + product.optInt("startBuy") + "元"
				+ "    手续费:无", "");
		setTextView(R.id.timeTV, "理财年限：限" + product.optString("timeLimit")
				+ "个月", "");
		setTextView(R.id.multipleTV, "投资倍数为：" + product.optInt("startBuy")
				+ "的整数倍", "");
	}

	/**
	 * 通过id设置text
	 * <p>
	 * 若text为null或"",则使用or
	 *
	 * @param resId
	 * @param text
	 * @param or
	 */
	private void setTextView(final int resId, String text, String or) {
		final String content;
		if (TextUtils.isEmpty(text)) {
			content = or;
		} else {
			content = text;
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				((TextView) findViewById(resId)).setText(content);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		default:
			break;
		}

	}
}
