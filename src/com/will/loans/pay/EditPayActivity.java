package com.will.loans.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.will.loans.beans.UserinfoCache;
import com.will.loans.ui.activity.LoansDetail;

public class EditPayActivity extends BasePayActivity {

	private EditText moneyET;

	private JSONObject jo = LoansDetail.pro;

	private AQuery aq;

	@Override
	public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
		UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,
				tn, mode);
	}

	@Override
	protected void afterSetContentView() {

		aq = new AQuery(this);

		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
				if (money == 0 || money % jo.optInt("startBuy") != 0) {
					nextBtn.setEnabled(false);
				} else {
					nextBtn.setEnabled(true);
				}
			}
		});
		updateView();
	}

	private void getDate() {
		// TODO EditPay完善参数
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("userid", UserinfoCache.userId);
			jo.put("token", UserinfoCache.token);
			jo.put("amount", moneyET.getText().toString());
			jo.put("proId", LoansDetail.pro.optInt("id"));
			jo.put("tradePsw", "66668888");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/buyProduct", params,
				JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json,
					AjaxStatus status) {
				mLoadingDialog.cancel();
				String tn = json.optString("tn");
				Message msg = mHandler.obtainMessage();
				msg.obj = tn;
				mHandler.sendMessage(msg);
			}
		});

	}

	private void updateView() {
		setTextView(R.id.nameTV, jo.optString("proName"), "");
		setTextView(R.id.moneyTV, "起投金额：" + jo.optInt("startBuy") + "元"
				+ "    手续费:无", "");
		setTextView(R.id.timeTV, "理财年限：限" + jo.optString("timeLimit") + "个月",
				"");
		setTextView(R.id.multipleTV, "投资倍数为：" + jo.optInt("startBuy") + "的整数倍",
				"");
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

}
