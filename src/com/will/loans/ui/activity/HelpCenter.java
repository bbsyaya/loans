package com.will.loans.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;

public class HelpCenter extends BaseCenter {
	private HelpAbapter mAdapter;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	protected BaseAdapter getAdapter() {
		if (mAdapter == null) {
			mAdapter = new HelpAbapter();
		}
		return mAdapter;
	}

	class HelpAbapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
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
				convertView = getLayoutInflater().inflate(R.layout.item_help_center, null);
				holder.title = (TextView) convertView.findViewById(R.id.item_help_title_tv);
				holder.desc = (TextView) convertView.findViewById(R.id.item_help_tv);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView desc;
		}

	}

}
