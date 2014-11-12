
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.bean.BankCardInfo;
import com.will.loans.beans.json.BankInfoJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.EditPayActivity;
import com.will.loans.pay.UnionPayActivity;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaySelectionActivity extends BaseActivity {
    private AQuery mAQuery;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_select);
        mAquery = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText("我的银行卡");
        findViewById(R.id.yibao_pay).setOnClickListener(this);
        findViewById(R.id.union_pay).setOnClickListener(this);
        adapter = new MyAdapter();
        ((ListView) findViewById(R.id.bank_lv)).setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {
        private List<BankCardInfo> bankList;

        public void setBankList(List<BankCardInfo> bankList) {
            this.bankList = bankList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (bankList == null) {
                return 0;
            }
            return bankList.size();
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
                String cardNo = bankList.get(position).cardNo;
                convertView = getLayoutInflater().inflate(R.layout.item_bank, null);
                ((TextView) convertView.findViewById(R.id.bank_name)).setText(bankList
                        .get(position).bankName);
                ((TextView) convertView.findViewById(R.id.bank_msg)).setText("尾号为"
                        + cardNo.substring(cardNo.length() - 4, cardNo.length()) + " 的卡");
            }
            return convertView;
        }

    }

    public void getBindBankList() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAQuery.ajax(ServerInfo.GETBINDBANKLIST, params, BankInfoJson.class,
                new AjaxCallback<BankInfoJson>() {
                    @Override
                    public void callback(String url, BankInfoJson json, AjaxStatus status) {
                        Log.d("loans", "" + json.toString());
                        if (json == null) {
                            return;
                        }
                        if (json.bankList != null && json.bankList.get(0) != null) {
                            adapter.setBankList(json.bankList);
                        }
                    }
                });

    }

    @Override
    protected void onViewClick(View view) {
        // TODO Auto-generated method stub
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.yibao_pay:
                startActivity(new Intent(PaySelectionActivity.this, EditPayActivity.class));
                break;

            case R.id.union_pay:
                startActivity(new Intent(PaySelectionActivity.this, UnionPayActivity.class));
                break;

            default:
                break;
        }
    }
}
