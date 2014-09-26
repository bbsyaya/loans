
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.ui.activity.LoansDetail;
import com.will.loans.weight.ProgressWheel;

import org.json.JSONObject;

public class ProductFirst extends BasePRoductLis {

    private LoansAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_loans, null);
                viewHolder.loans_title = (TextView) convertView.findViewById(R.id.loans_title);
                viewHolder.loans_plan = (TextView) convertView.findViewById(R.id.loans_plan);
                viewHolder.loans_number = (TextView) convertView.findViewById(R.id.loans_number);
                viewHolder.loans_persent = (TextView) convertView.findViewById(R.id.loans_persent);
                viewHolder.loans_low = (TextView) convertView.findViewById(R.id.loans_low);
                viewHolder.progressWheel = (ProgressWheel) convertView
                        .findViewById(R.id.progress_bar_two);
                viewHolder.progressWheel = (ProgressWheel) convertView
                        .findViewById(R.id.progress_bar_two);
                viewHolder.percentTV = (TextView) convertView.findViewById(R.id.percentTV);
                convertView.setTag(viewHolder);
            }
            JSONObject item = getItem(position);
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.loans_title.setText(item.optString("proName"));
            viewHolder.loans_plan.setText(item.optString("syms"));
            viewHolder.loans_number.setText(Html.fromHtml("<font color=#7CC0D9>限</font>"
                    + item.optInt("timeLimit") + "个月"));
            viewHolder.loans_persent.setText(item.optInt("percent") + "%");
            viewHolder.loans_low.setText(Html.fromHtml("<strong>" + item.optInt("startBuy")
                    + "</strong>元起购"));
            viewHolder.progressWheel.setProgress((int) (item.optInt("percent") * 3.6));
            viewHolder.percentTV.setText("" + item.optInt("percent"));
            return convertView;
        }

        class ViewHolder {
            TextView loans_title;

            TextView loans_plan;

            TextView loans_number;

            TextView loans_persent;

            TextView loans_low;

            TextView bigPersent;

            TextView bigPersentNum;

            TextView indicat;

            TextView status;

            TextView percentTV;

            ProgressWheel progressWheel;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LoansDetail.pro = mAdapter.getItem(position - 1);
        jump2Activity(new LoansDetail());
    }

    @Override
    BaseAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new LoansAdapter();
        }
        return mAdapter;
    }
}
