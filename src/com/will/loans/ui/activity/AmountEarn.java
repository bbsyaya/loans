
package com.will.loans.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.bean.TotalProfit;
import com.will.loans.beans.json.TotalProfitJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmountEarn extends BaseMineActivity {

    private TodayAdapter todayAdapter;

    private List<TotalProfit> json = new ArrayList<TotalProfit>();

    private SimpleDateFormat smf = new SimpleDateFormat("MM月dd日");

    private int mCurrentPage = 1;

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.amonut_earn);
        getDate();
    }

    class TodayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return json.size();
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
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_mine, null);
            }
            ((TextView) convertView.findViewById(R.id.mine_title)).setText(smf.format(json
                    .get(position).profitDate) + "收益（元）");
            ((TextView) convertView.findViewById(R.id.mine_num))
                    .setText(json.get(position).profitMoney + "");
            return convertView;
        }

    }

    @Override
    protected void onViewClick(View view) {
        // TODO Auto-generated method stub
        super.onViewClick(view);
    }

    @Override
    public void onRefresh() {
        mCurrentPage++;
        getDate();
    }

    @Override
    public void getDate() {
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("pageNum", mCurrentPage);
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.USERTOTALPROFIT, params, TotalProfitJson.class,
                new AjaxCallback<TotalProfitJson>() {
                    @Override
                    public void callback(String url, TotalProfitJson object, AjaxStatus status) {
                        System.out.println(" " + object.toString());
                        mAutoLoadLv.onRefreshComplete();
                        json.addAll(object.userProfits);
                        todayAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public BaseAdapter getAdatper() {
        if (todayAdapter == null) {
            todayAdapter = new TodayAdapter();
        }
        return todayAdapter;
    }

}
