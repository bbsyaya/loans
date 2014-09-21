package com.will.loans.ui.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;

public class HasMoney extends BaseMineActivity {

	private TodayAdapter todayAdapter;
	@Override
	protected void initView() {
		((TextView)findViewById(R.id.title_tv)).setText(R.string.has_money);
		todayAdapter = new TodayAdapter();
	}

	class TodayAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
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
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_mine, null);

			}
			((TextView)convertView.findViewById(R.id.mine_title)).setText(R.string.has_money);
			((TextView)convertView.findViewById(R.id.mine_num)).setText(10000.00+"");
			return convertView;
		}

	}


	@Override
	protected void onViewClick(View view) {
		// TODO Auto-generated method stub
		super.onViewClick(view);
	}

	@Override
	public void onRefresh() {
		mAutoLoadLv.onRefreshComplete();
	}

	@Override
	public void getDate() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", System.currentTimeMillis());
			//			jo.put("token", SharePreferenceUtil.getUserPref(getActivity()).getToken());
			//			jo.put("userid", SharePreferenceUtil.getUserPref(getActivity()).getUserId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aq.ajax(ServerInfo.USERMSG,params, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				System.out.println(" " + object.toString());
			}
		});

	}

	@Override
	public BaseAdapter getAdatper() {
		if (todayAdapter==null) {
			todayAdapter = new TodayAdapter();
		}
		return todayAdapter;
	}

}
