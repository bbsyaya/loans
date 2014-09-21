
package com.will.loans.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.utils.Toaster;

public class MessageCenter extends BaseCenter {
	private MessageAdapter mMessageAdapter;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {
		super.init();
		((TextView)findViewById(R.id.title_tv)).setText(R.string.more_message_center);
		((TextView)findViewById(R.id.title_btn_right)).setText(R.string.read);
		findViewById(R.id.title_tv).setOnClickListener(this);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_back)).setText(R.string.back);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Toaster.showShort(this, "已标记为已读");
	}

	@Override
	protected BaseAdapter getAdapter() {
		if (mMessageAdapter == null) {
			mMessageAdapter = new MessageAdapter();
		}
		return mMessageAdapter;
	}

	class MessageAdapter extends BaseAdapter {

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
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_message_center, null);
				holder.title = (TextView) convertView.findViewById(R.id.item_message_title);
				holder.time = (TextView) convertView.findViewById(R.id.item_message_time);
				holder.desc = (TextView) convertView.findViewById(R.id.item_message_desc);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();

			return convertView;
		}

		class ViewHolder {
			TextView title;

			TextView time;

			TextView desc;

		}

	}
}
