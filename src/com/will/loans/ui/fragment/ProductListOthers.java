
package com.will.loans.ui.fragment;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.ui.activity.ProductDetail;

public class ProductListOthers extends BasePRoductLis {
	private LoansAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getActivity().getLayoutInflater().inflate(R.layout.item_loans_others,
					null);
			return convertView;
		}

		class ViewHolder {
			TextView title;

			TextView time;

			TextView persent;

			TextView limit;

			TextView bigPersent;

			TextView bigPersentNum;

			TextView indicat;

			TextView status;
		}

	}

	@Override
	BaseAdapter getAdapter() {
		if (mAdapter == null) {
			mAdapter = new LoansAdapter();
		}
		return mAdapter;
	}


	@Override
	public void onRefresh() {
		mListView.onRefreshComplete();

	}

	@Override
	public void onLoadMore() {
		mListView.onLoadMoreComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ProductDetail.proDetail = mAdapter.getItem(position);
		jump2Activity(new ProductDetail());
	}
}
