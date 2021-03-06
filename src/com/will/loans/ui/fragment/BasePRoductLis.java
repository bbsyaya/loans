
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.weight.AutoLoadPull2RefreshListView;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnLoadMoreListener;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品列表 其他产品列表父页面
 * 
 * @author yushan.peng
 */
public abstract class BasePRoductLis extends BaseFragment implements OnLoadMoreListener,
        OnRefreshListener, OnItemClickListener {
    protected AutoLoadPull2RefreshListView mListView;

    private BaseAdapter mAdapter;

    private AQuery aq;

    protected String type = "WYD";

    protected List<JSONObject> products = new ArrayList<JSONObject>();

    private int mPageNum = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aq = new AQuery(getActivity(), view);
        initView(view);
        getDate(false);
    }

    private void initView(View view) {
        mListView = (AutoLoadPull2RefreshListView) view.findViewById(R.id.refresh_listview);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(getAdapter());
        mListView.setAutoLoadMore(true);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);
    }

    private void getDate(final boolean isRefresh) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", new Date().getTime());
            jo.put("pageNum", mPageNum);
            jo.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.PROLIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.d("json",""+json.toString());
                if (isRefresh) {
                    products.clear();
                    mListView.onRefreshComplete();
                } else {
                    mListView.onLoadMoreComplete();
                }
                if (json == null) {
                    return;
                }
                JSONArray ja = null;
                ja = json.optJSONArray("proList");
                for (int i = 0; i < ja.length(); i++) {
                    products.add(ja.optJSONObject(i));
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    abstract BaseAdapter getAdapter();

    @Override
    public void onRefresh() {
        mPageNum = 1;
        getDate(true);
        Log.d("loans", "ProductFirst  onRefresh");
    }

    @Override
    public void onLoadMore() {
        mPageNum++;
        getDate(false);
        Log.d("loans", "ProductFirst  onLoadMore");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
