
package com.will.loans.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.bean.PreDayIncome;
import com.will.loans.beans.json.PreIncomeJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodayEarn extends BaseMineActivity {

    private String date;

    private SimpleDateFormat smf = new SimpleDateFormat("MM月dd日");

    private TodayAdapter todayAdapter;

    private int mPageNum = 1;

    private List<PreDayIncome> income = new ArrayList<PreDayIncome>();

    @Override
    protected void initView() {
        date = smf.format(System.currentTimeMillis() - 60 * 60 * 24 * 1000) + "收益";
        ((TextView) findViewById(R.id.title_tv)).setText(date);
        getDate();
    }

    class TodayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return income.size();
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
            ((TextView) convertView.findViewById(R.id.mine_title)).setText(smf.format(income
                    .get(position).profitDate) + "收益（元）");
            ((TextView) convertView.findViewById(R.id.mine_num))
                    .setText(income.get(position).profitMoney + "");
            return convertView;
        }

    }

    @Override
    protected void onViewClick(View view) {
        // TODO Auto-generated method stub
        super.onViewClick(view);
    }

    private boolean mIsHeadRefresh = false;

    @Override
    public void onRefresh() {
        mIsHeadRefresh = true;
        mPageNum = 1;
        mAutoLoadLv.onRefreshComplete();
    }

    @Override
    public void getDate() {
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("pageNum", mPageNum);
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.USERLASTDAYPROFIT, params, PreIncomeJson.class,
                new AjaxCallback<PreIncomeJson>() {
                    @Override
                    public void callback(String url, PreIncomeJson object, AjaxStatus status) {
                        System.out.println(" " + object.toString());
                        if (object != null && object.userProfits != null) {
                            if (mIsHeadRefresh) {
                                income.clear();
                            }
                            income.addAll(object.userProfits);
                            todayAdapter.notifyDataSetChanged();
                        }
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
