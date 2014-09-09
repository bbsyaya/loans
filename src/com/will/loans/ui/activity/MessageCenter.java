package com.will.loans.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;

public class MessageCenter extends BaseCenter {
	private MessageAdapter mMessageAdapter;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	protected BaseAdapter getAdapter() {
		if (mMessageAdapter==null) {
			mMessageAdapter = new MessageAdapter();
		}
		return mMessageAdapter;
	}

	class MessageAdapter extends BaseAdapter{

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
				convertView = getLayoutInflater().inflate(R.layout.item_message_center, null);
				holder.tv = (TextView) convertView.findViewById(R.id.item_message_tv);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();

			return convertView;
		}

		class ViewHolder {
			TextView tv;

		}

	}
}
