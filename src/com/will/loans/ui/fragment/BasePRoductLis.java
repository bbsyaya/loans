
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
import com.will.loans.ui.activity.ProductDetail;
import com.will.loans.weight.AutoLoadPull2RefreshListView;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnLoadMoreListener;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnRefreshListener;

/**
 * 产品列表 其他产品列表父页面
 * 
 * @author yushan.peng
 */
public class BasePRoductLis extends BaseFragment implements OnLoadMoreListener, OnRefreshListener,
        OnItemClickListener {
    private AutoLoadPull2RefreshListView mListView;

    private LoansAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mListView = (AutoLoadPull2RefreshListView) view.findViewById(R.id.refresh_listview);
        mListView.setOnItemClickListener(this);
        mAdapter = new LoansAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setAutoLoadMore(true);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);
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
    public void onRefresh() {
        mListView.onRefreshComplete();

    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        mListView.onLoadMoreComplete();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        jump2Activity(new ProductDetail());
    }
}
