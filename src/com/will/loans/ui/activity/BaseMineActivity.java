
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.will.loans.R;
import com.will.loans.weight.AutoLoadPull2RefreshListView;

public class BaseMineActivity extends BaseActionBarActivity {
	private AutoLoadPull2RefreshListView mAutoLoadLv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);

		mAutoLoadLv = (AutoLoadPull2RefreshListView) findViewById(R.id.trade_list);
	}

	class TradeAdapter extends BaseAdapter{

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
}
