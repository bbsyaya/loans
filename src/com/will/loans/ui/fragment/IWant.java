
package com.will.loans.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.will.loans.beans.json.UserAccount;
import com.will.loans.constant.ServerInfo;
import com.will.loans.ui.activity.AmountEarn;
import com.will.loans.ui.activity.AmountMoney;
import com.will.loans.ui.activity.AmountPoint;
import com.will.loans.ui.activity.AppHelp;
import com.will.loans.ui.activity.HasMoney;
import com.will.loans.ui.activity.MessageCenter;
import com.will.loans.ui.activity.PersonCenter;
import com.will.loans.ui.activity.RemainMoney;
import com.will.loans.ui.activity.TodayEarn;
import com.will.loans.ui.activity.TradeHistory;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;

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
	 * 今日收益日期
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

	private SimpleDateFormat smf = new SimpleDateFormat("MM月dd日");

	private String date;

	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_mine, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("loans", "onresume");
		//        getDate();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d("loans", "onDetach");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d("loans", "onAttach");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		aq = new AQuery(getActivity(), view);
		((TextView) view.findViewById(R.id.title_tv)).setText(R.string.tab_mine);
		((TextView) view.findViewById(R.id.title_tv_phone)).setText(SharePreferenceUtil
				.getUserPref(getActivity()).getUsername());
		((Button) view.findViewById(R.id.title_btn_left)).setText(R.string.help);
		date = smf.format(System.currentTimeMillis() - 60 * 60 * 24 * 1000) + "收益（元）";
		view.findViewById(R.id.title_btn_left).setVisibility(View.VISIBLE);
		view.findViewById(R.id.title_btn_right_msg_center).setVisibility(View.VISIBLE);
		view.findViewById(R.id.title_btn_right_msg_center).setOnClickListener(this);
		view.findViewById(R.id.title_btn_left).setOnClickListener(this);
		view.findViewById(R.id.title_tv).setOnClickListener(this);
		view.findViewById(R.id.title_tv_phone).setOnClickListener(this);
		view.findViewById(R.id.title_tv_phone).setVisibility(View.VISIBLE);

		initButton(view);
		initTextView(view);
		todayDateTV.setText(date);
		getDate();
	}

	protected SimpleDateFormat smfJson = new SimpleDateFormat("yyyy-MMddHHmm:ss");

	protected String key = "qHdKC5yNgKwdi1BFa5EKOw29fwYeetV78EcSN04H93jBYvoLkP631rFcSa3OT3Np";

	/**
	 * 请求接口数据
	 */
	private void getDate() {
		Long time = System.currentTimeMillis();
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", time);
			jo.put("token", SharePreferenceUtil.getUserPref(getActivity()).getToken());
			jo.put("userid", SharePreferenceUtil.getUserPref(getActivity()).getUserId());
			jo.put("sign",
					GenerateMD5Password.encodeByMD5(SharePreferenceUtil.getUserPref(getActivity())
							.getToken() + smfJson.format(time) + key));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aq.ajax(ServerInfo.USERACCOUNT, params, UserAccount.class, new AjaxCallback<UserAccount>() {
			@Override
			public void callback(String url, UserAccount object, AjaxStatus status) {
				Log.d("loans", object.toString());
				if (object != null) {
					updateView(object);
				}
			}
		});
	}

	private void updateView(UserAccount object) {
		// TODO 请求结束后调用刷新视图
		//		todayDateTV.setText(object.optString(""));
		todayEarnTV.setText(object.lastDayProfit+ "元");
		amountEarnTV.setText(object.totalProfit + "元");
		amountMoneyTV.setText(object.totalAssets+"");
		hasMoneyTV.setText(object.holdAssets+"");
		remainMoneyTV.setText(object.balance+"");
		amountPointTV.setText(""+object.totalIntegral);
	}

	private void initButton(View view) {
		todayEarnBtn = (LinearLayout) view.findViewById(R.id.todayEarnBtn);
		amountEarnBtn = (LinearLayout) view.findViewById(R.id.amountEarnBtn);
		amountMoneyBtn = (LinearLayout) view.findViewById(R.id.amountMoneyBtn);
		hasMoneyBtn = (LinearLayout) view.findViewById(R.id.hasMoneyBtn);
		remainMoneyBtn = (LinearLayout) view.findViewById(R.id.remainMoneyBtn);
		tradeHistoryBtn = (LinearLayout) view.findViewById(R.id.tradeHistoryBtn);
		amountPointBtn = (LinearLayout) view.findViewById(R.id.amountPointBtn);

		todayEarnBtn.setOnClickListener(this);
		amountEarnBtn.setOnClickListener(this);
		amountMoneyBtn.setOnClickListener(this);
		hasMoneyBtn.setOnClickListener(this);
		remainMoneyBtn.setOnClickListener(this);
		tradeHistoryBtn.setOnClickListener(this);
		amountPointBtn.setOnClickListener(this);
		view.findViewById(R.id.title_btn_right_msg_center).setOnClickListener(this);
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
			jump2Activity(new TodayEarn());
			break;
		case R.id.amountEarnBtn:
			jump2Activity(new AmountEarn());
			break;
		case R.id.amountMoneyBtn:
			jump2Activity(new AmountMoney());
			break;
		case R.id.hasMoneyBtn:
			jump2Activity(new HasMoney());
			break;
		case R.id.remainMoneyBtn:
			jump2Activity(new RemainMoney());
			break;
		case R.id.tradeHistoryBtn:
			jump2Activity(new TradeHistory());
			break;
		case R.id.amountPointBtn:
			jump2Activity(new AmountPoint());
			break;
		case R.id.title_btn_right_msg_center:
			jump2Activity(new MessageCenter());
			break;
		case R.id.title_tv_phone:
			jump2Activity(new PersonCenter());
			break;
		case R.id.title_btn_left:
			jump2Activity(new AppHelp());
			break;
		case R.id.title_tv:
			jump2Activity(new PersonCenter());
			break;
		default:
			break;
		}
	}

}
