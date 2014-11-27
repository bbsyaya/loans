
package com.will.loans.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.util.Log;
import org.json.JSONArray;
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
import com.will.loans.constant.ServerInfo;

public class HelpCenter extends BaseCenter {
	private HelpAbapter mAdapter;

	private int mPageNum = 1;

	List<JSONObject> helpaction = new ArrayList<JSONObject>();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(HelpCenter.this,HelpDetail.class).putExtra(HelpDetail.TITLE,helpaction.get(arg2).optString("helpTitle"))
                .putExtra(HelpDetail.CONTENT,helpaction.get(arg2).optString("helpDesc")));

	}

	@Override
	protected void init() {
		super.init();
		((TextView) findViewById(R.id.title_tv)).setText(R.string.more_help_center);
		findViewById(R.id.title_tv).setOnClickListener(this);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView) findViewById(R.id.title_back)).setText(R.string.back);
		getData();
	}

	private void getData() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", System.currentTimeMillis());
			jo.put("pageNum", mPageNum);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aq.ajax(ServerInfo.HELPLIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.d("",""+json.toString());
                if (json == null) {
					return;
				}
				JSONArray ja = null;
				ja = json.optJSONArray("helpList");
				for (int i = 0; i < ja.length(); i++) {
					helpaction.add(ja.optJSONObject(i));
				}
				if (mAdapter!=null) {
					mAdapter.notifyDataSetChanged();
				}
			}
		});

	}

	@Override
	protected BaseAdapter getAdapter() {
		if (mAdapter == null) {
			mAdapter = new HelpAbapter();
		}
		return mAdapter;
	}

	class HelpAbapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return helpaction.size();
		}

		@Override
		public JSONObject getItem(int position) {
			// TODO Auto-generated method stub
			return helpaction.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_help_center, null);
				holder.title = (TextView) convertView.findViewById(R.id.item_help_title_tv);
				holder.desc = (TextView) convertView.findViewById(R.id.item_help_tv);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.title.setText(getItem(position).optString("helpTitle"));
			holder.desc.setText(getItem(position).optString("helpDesc"));
			return convertView;
		}

		class ViewHolder {
			TextView title;

			TextView desc;
		}

	}

}
