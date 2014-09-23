
package com.will.loans.ui.fragment;

import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.ui.activity.ProductDetail;

public class ProductListCoins extends BasePRoductLis {
	private LoansAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		type = "JJ";
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
			ViewHolder viewholder;
			if (convertView==null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_loans_others,null);
				convertView.findViewById(R.id.flag).setVisibility(View.INVISIBLE);
				viewholder = new ViewHolder();
				viewholder.title = (TextView) convertView.findViewById(R.id.loans_title);
				viewholder.plam = (TextView) convertView.findViewById(R.id.loans_plan);
				viewholder.persent = (TextView) convertView.findViewById(R.id.loans_persent);
				viewholder.limit = (TextView) convertView.findViewById(R.id.loans_limit);
				viewholder.earn = (TextView) convertView.findViewById(R.id.loans_low);
				viewholder.type = (TextView) convertView.findViewById(R.id.loans_buy_type);
				viewholder.buyNum = (TextView) convertView.findViewById(R.id.loans_buy_num);
				convertView.setTag(viewholder);
			}
			viewholder = (ViewHolder) convertView.getTag();
			JSONObject item = getItem(position);
			viewholder.title.setText(item.optString("proName"));
			viewholder.plam.setText(item.optString("syms"));
			viewholder.earn.setText(item.optString("wfsy")+" 元");

			viewholder.persent.setText(item.optInt("percent") + "%");
			viewholder.limit.setText(Html.fromHtml("<strong>"
					+ item.optInt("startBuy") + "</strong>元起购"));
			return convertView;
		}

		class ViewHolder {
			TextView title;

			TextView plam;

			TextView persent;

			TextView limit;

			TextView earn;

			TextView type;

			TextView buyNum;
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ProductDetail.proDetail = mAdapter.getItem(position);
		jump2Activity(new ProductDetail());
	}
}
