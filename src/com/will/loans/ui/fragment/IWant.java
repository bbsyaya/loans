package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.will.loans.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IWant extends BaseFragment implements OnClickListener {

	private PullToRefreshScrollView homePRSV;

	private LinearLayout todayEarnBtn;
	private LinearLayout amountEarnBtn;
	private LinearLayout amountMoneyBtn;
	private LinearLayout hasMoneyBtn;
	private LinearLayout remainMoneyBtn;
	private LinearLayout tradeHistoryBtn;
	private LinearLayout amountPointBtn;

	/**
	 * 今日收益日期（09月12日收益（元））
	 */
	private TextView todayDateTV;
	/**
	 * 今日收益（元）
	 */
	private TextView todayEarnTV;
	/**
	 * 累计收益
	 */
	private TextView amountEarnTV;
	/**
	 * 总资产
	 */
	private TextView amountMoneyTV;
	/**
	 * 持有资产
	 */
	private TextView hasMoneyTV;
	/**
	 * 账户余额
	 */
	private TextView remainMoneyTV;
	/**
	 * 总积分
	 */
	private TextView amountPointTV;

	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_mine, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		aq = new AQuery(getActivity(), view);
		((TextView) view.findViewById(R.id.title_tv))
				.setText(R.string.tab_mine);
		((Button) view.findViewById(R.id.title_btn_left))
				.setText(R.string.login);
		((Button) view.findViewById(R.id.title_btn_right))
				.setText(R.string.refresh);

		initButton(view);
		initTextView(view);

		// homePRSV = (PullToRefreshScrollView)
		// view.findViewById(R.id.minePRSV);
		// initRefreshView();

		getDate();
	}

	/**
	 * 请求接口数据
	 */
	private void getDate() {
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
		aq.ajax("http://125.77.254.170:8086/daidaitongServer/mobile/registerOrLoginByMsg",
				params, JSONObject.class, new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {
						System.out.println(" " + object.toString());
					}
				});
	}

	private void updateView() {
		// TODO 请求结束后调用刷新视图
		todayDateTV.setText("");
		todayEarnTV.setText("");
		amountEarnTV.setText("");
		amountMoneyTV.setText("");
		hasMoneyTV.setText("");
		remainMoneyTV.setText("");
		amountPointTV.setText("");
	}

	private void initButton(View view) {
		todayEarnBtn = (LinearLayout) view.findViewById(R.id.todayEarnBtn);
		amountEarnBtn = (LinearLayout) view.findViewById(R.id.amountEarnBtn);
		amountMoneyBtn = (LinearLayout) view.findViewById(R.id.amountMoneyBtn);
		hasMoneyBtn = (LinearLayout) view.findViewById(R.id.hasMoneyBtn);
		remainMoneyBtn = (LinearLayout) view.findViewById(R.id.remainMoneyBtn);
		tradeHistoryBtn = (LinearLayout) view
				.findViewById(R.id.tradeHistoryBtn);
		amountPointBtn = (LinearLayout) view.findViewById(R.id.amountPointBtn);

		todayEarnBtn.setOnClickListener(this);
	}

	private void initTextView(View view) {
		todayDateTV = (TextView) view.findViewById(R.id.todayDateTV);
		todayEarnTV = (TextView) view.findViewById(R.id.todayEarnTV);
		amountEarnTV = (TextView) view.findViewById(R.id.amountEarnTV);
		amountMoneyTV = (TextView) view.findViewById(R.id.amountMoneyTV);
		hasMoneyTV = (TextView) view.findViewById(R.id.hasMoneyTV);
		remainMoneyTV = (TextView) view.findViewById(R.id.remainMoneyTV);
		amountPointTV = (TextView) view.findViewById(R.id.amountPointTV);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.todayEarnBtn:
			break;
		case R.id.amountEarnBtn:
			break;
		case R.id.amountMoneyBtn:
			break;
		case R.id.hasMoneyBtn:
			break;
		case R.id.remainMoneyBtn:
			break;
		case R.id.tradeHistoryBtn:
			break;
		case R.id.amountPointBtn:
			break;
		default:
			break;
		}
	}

}
