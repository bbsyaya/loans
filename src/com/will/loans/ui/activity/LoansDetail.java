package com.will.loans.ui.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.EditPayActivity;

public class LoansDetail extends BaseActivity {

	public static JSONObject pro;
	private AQuery aq;

	private String title;

	public static String TITLETXT = "com.will.loansdetail.title";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
		setContentView(R.layout.loans_detail);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((Button) findViewById(R.id.title_btn_right)).setText(R.string.refresh);
		((TextView)findViewById(R.id.title_tv)).setText(pro.optString("proName"));
		if (pro != null) {
			getData();
		}
	}

	private void getData() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("proId", pro.optInt("id"));
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aq.ajax(ServerInfo.PRODETAIL, params,
				JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json,
					AjaxStatus status) {
				Log.e("11", json.toString());
				// updateView();
				if (!json.optString("resultflag").equals("0")) {
					return;
				}
				updateView(json.optJSONObject("detail"));
			}
		});

	}

	private void updateView(JSONObject jo) {

		findViewById(R.id.detailLayout).setVisibility(View.VISIBLE);
		((ProgressBar) findViewById(R.id.percentPB)).setProgress(pro
				.optInt("percent"));
		// 融资金额
		setTextView(R.id.amountMoneyTV, pro.optInt("totalAmount") + "", "");
		// 已融资
		setTextView(R.id.amountPercentTV, "已融资：" + jo.optInt("buyNum") + "%",
				"");
		// 剩余
		setTextView(R.id.remainMoneyTV,
				"剩余：" + (pro.optInt("totalAmount") - jo.optInt("buyNum")), "");
		// 高收益、第三方、限几个月、保障
		// setTextView(R.id.notifyTV1, jo.optString("benxiDesc"), "");
		setTextView(R.id.notifyTV2, jo.optString("benxiDesc"), "");
		setTextView(R.id.notifyTV3, "限" + pro.optString("timeLimit") + "个月", "");
		setTextView(R.id.notifyTV4, jo.optString("securityTip"), "");
		// 预期年化
		setTextView(R.id.forwardEarnTV, pro.optInt("percent") + "%", "");
		// 起投金额
		setTextView(R.id.startMoneyTV, pro.optInt("startBuy") + "", "");
		// 起投人数
		setTextView(R.id.payNumTV, pro.optInt("buyPerNum") + "人", "");
		//
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

	public void enterBtn(View view) {
		EditPayActivity.product = pro;
		startActivity(new Intent(LoansDetail.this, EditPayActivity.class));
	}

	public void calculatorView(View view) {
		startActivity(new Intent(LoansDetail.this, PreEncoming.class));
	}
}
