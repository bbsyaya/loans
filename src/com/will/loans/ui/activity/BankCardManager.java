package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.will.loans.R;

public class BankCardManager extends BaseActivity implements OnItemClickListener {

	private BandCardAdapter mBankCard;
	private ListView mCardLv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bankcard);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_tv)).setText(R.string.fill_phone_num);

		mCardLv = (ListView) findViewById(R.id.trade_list);
		mBankCard = new BandCardAdapter();
		mCardLv.setAdapter(mBankCard);
		mCardLv.setOnItemClickListener(this);
	}

	@Override
	protected void onViewClick(View view) {
		super.onViewClick(view);
	}

	private class BandCardAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
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
			// TODO Auto-generated method stub
			return null;
		}

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
