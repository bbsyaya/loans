package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.ui.activity.LoansDetail;
import com.will.loans.weight.AutoLoadPull2RefreshListView;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnLoadMoreListener;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnRefreshListener;
import com.will.loans.weight.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductFirst extends BaseFragment implements OnLoadMoreListener,
		OnRefreshListener, OnItemClickListener {
	private AutoLoadPull2RefreshListView mListView;

	private LoansAdapter mAdapter;

	private AQuery aq;

	List<JSONObject> products = new ArrayList<JSONObject>();

	private int mPageNum = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.product_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		aq = new AQuery(getActivity(), view);
		initView(view);
		getDate(false);
	}

	private void getDate(final boolean isRefresh) {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", new Date().getTime());
			jo.put("pageNum", mPageNum);
			// jo.put("token", "1FBE22C74C30107226974F5EA89C6B8D");
			// jo.put("verCode", "960295");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		// aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/registerOrLoginByMsg",
		// loginFirst
		// registerOrLoginByMsg
		aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/proList", params,
				JSONObject.class, new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject json,
							AjaxStatus status) {
						if (isRefresh) {
							products.clear();
							mListView.onRefreshComplete();
						} else {
							mListView.onLoadMoreComplete();
						}
						if (json == null) {
							return;
						}
						JSONArray ja = null;
						ja = json.optJSONArray("proList");
						for (int i = 0; i < ja.length(); i++) {
							products.add(ja.optJSONObject(i));
						}
						mAdapter.notifyDataSetChanged();
					}
				});

	}

	private void initView(View view) {
		mListView = (AutoLoadPull2RefreshListView) view
				.findViewById(R.id.refresh_listview);
		mAdapter = new LoansAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setAutoLoadMore(true);
		mListView.setOnRefreshListener(this);
		mListView.setOnLoadListener(this);
		mListView.setOnItemClickListener(this);
	}

	class LoansAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return products.size();
		}

		@Override
		public JSONObject getItem(int position) {
			return products.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.item_loans, null);
				viewHolder.loans_title = (TextView) convertView
						.findViewById(R.id.loans_title);
				viewHolder.loans_plan = (TextView) convertView
						.findViewById(R.id.loans_plan);
				viewHolder.loans_number = (TextView) convertView
						.findViewById(R.id.loans_number);
				viewHolder.loans_persent = (TextView) convertView
						.findViewById(R.id.loans_persent);
				viewHolder.loans_low = (TextView) convertView
						.findViewById(R.id.loans_low);
				viewHolder.progressWheel = (ProgressWheel) convertView
						.findViewById(R.id.progress_bar_two);
				viewHolder.progressWheel = (ProgressWheel) convertView
						.findViewById(R.id.progress_bar_two);
				viewHolder.percentTV = (TextView) convertView
						.findViewById(R.id.percentTV);
				convertView.setTag(viewHolder);
			}
			JSONObject item = getItem(position);
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.loans_title.setText(item.optString("proName"));
			viewHolder.loans_plan.setText(item.optString("syms"));
			viewHolder.loans_number.setText(Html
					.fromHtml("<font color=#7CC0D9>限</font>"
							+ item.optInt("timeLimit") + "个月"));
			viewHolder.loans_persent.setText(item.optInt("percent") + "%");
			viewHolder.loans_low.setText(Html.fromHtml("<strong>"
					+ item.optInt("startBuy") + "</strong>元起购"));
			viewHolder.progressWheel
					.setProgress((int) (item.optInt("percent") * 3.6));
			viewHolder.percentTV.setText("" + item.optInt("percent"));
			return convertView;
		}

		class ViewHolder {
			TextView loans_title;

			TextView loans_plan;

			TextView loans_number;

			TextView loans_persent;

			TextView loans_low;

			TextView bigPersent;

			TextView bigPersentNum;

			TextView indicat;

			TextView status;

			TextView percentTV;

			ProgressWheel progressWheel;
		}

	}

	@Override
	public void onRefresh() {
		mPageNum = 1;
		getDate(true);
	}

	@Override
	public void onLoadMore() {
		mPageNum++;
		getDate(false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		LoansDetail.pro = mAdapter.getItem(position - 1);
		jump2Activity(new LoansDetail());
	}
}
