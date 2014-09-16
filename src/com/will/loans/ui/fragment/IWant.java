package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.will.loans.R;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_mine, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
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
		// TODO 网络请求
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

	/**
	 * 初始化下拉刷新監聽器
	 */
	private void initRefreshView() {
		homePRSV.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				getDate();
			}
		});
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
