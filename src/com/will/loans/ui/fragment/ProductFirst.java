package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.ui.activity.LoansDetail;
import com.will.loans.weight.AutoLoadPull2RefreshListView;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnLoadMoreListener;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnRefreshListener;
import com.will.loans.weight.ProgressWheel;

public class ProductFirst extends BaseFragment implements OnLoadMoreListener,
OnRefreshListener, OnItemClickListener {
	private AutoLoadPull2RefreshListView mListView;

	private LoansAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.product_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
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
			ViewHolder viewHolder;
			if (convertView==null) {
				viewHolder = new ViewHolder();
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.item_loans, null);
				viewHolder.progressWheel = (ProgressWheel) convertView.findViewById(R.id.progress_bar_two);
				convertView.setTag(viewHolder);
			}
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.progressWheel.setProgress(100);
			viewHolder.progressWheel.setText("21%");
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

			ProgressWheel progressWheel;
		}

	}

	@Override
	public void onRefresh() {
		mListView.onRefreshComplete();

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mListView.onLoadMoreComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		jump2Activity(new LoansDetail());
		// startActivity(new Intent(getActivity(), ProductDetail.class));
	}
}
