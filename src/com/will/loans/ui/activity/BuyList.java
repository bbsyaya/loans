package com.will.loans.ui.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.bean.BuyInfo;
import com.will.loans.beans.json.BuyListJson;
import com.will.loans.constant.ServerInfo;

public class BuyList extends BaseCenter {
	private int mProId;
	public static String MPROSTR = "com.will.loans.buylist";
	private MyAdapter mMyAdapter;
	private SimpleDateFormat smf = new SimpleDateFormat("yy-MM-dd hh:mm");

	private List<BuyInfo> list = new ArrayList<BuyInfo>();
	@Override
	protected void init() {
		super.init();
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		mProId = getIntent().getExtras().getInt(MPROSTR);
		((TextView)findViewById(R.id.title_tv)).setText("投标情况");
		getData();
	}


	public void getData(){
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", System.currentTimeMillis());
			jo.put("proId", mProId);
			jo.put("pageNum", 1);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aq.ajax(ServerInfo.PROBUYLIST, params, BuyListJson.class,
				new AjaxCallback<BuyListJson>() {
			@Override
			public void callback(String url, BuyListJson json, AjaxStatus status) {
				if (json!=null) {
					updateView(json);
				}
			}

		});
	}
	private void updateView(BuyListJson json) {
		list.addAll(json.results);
		mMyAdapter.notifyDataSetChanged();
	}

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView==null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_buy, null);
				holder.userid = (TextView) convertView.findViewById(R.id.userid);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.buyNum = (TextView) convertView.findViewById(R.id.money);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.userid.setText(list.get(position).userName);
			holder.time.setText(list.get(position).tradeMoney+" 元");
			holder.buyNum.setText(smf.format(list.get(position).tradeTime));
			return convertView;
		}

		class ViewHolder {
			TextView userid;
			TextView time;
			TextView buyNum;

		}

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	protected BaseAdapter getAdapter() {
		if (mMyAdapter==null) {
			mMyAdapter = new MyAdapter();
		}
		return mMyAdapter;
	}

}
