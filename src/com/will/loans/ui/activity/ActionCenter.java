
package com.will.loans.ui.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionCenter extends BaseCenter {
    private ActionAdapter mAdapter;

    private int mPageNum = 1;

    List<JSONObject> action = new ArrayList<JSONObject>();

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void init() {
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.more_action_center);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        getData();
    }

    private void getData() {
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("pageNum", mPageNum);
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.ACTIVELIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.d("loans", "" + json.toString());
                if (json == null) {
                    return;
                }
                JSONArray ja = null;
                ja = json.optJSONArray("activeList");
                for (int i = 0; i < ja.length(); i++) {
                    action.add(ja.optJSONObject(i));
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected BaseAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ActionAdapter();
        }
        return mAdapter;
    }

    class ActionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return action.size();
        }

        @Override
        public JSONObject getItem(int position) {
            // TODO Auto-generated method stub
            return action.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_action_center, null);
                holder.iv = (ImageView) convertView.findViewById(R.id.item_action_center_iv);
                holder.tv = (TextView) convertView.findViewById(R.id.item_action_center_tv);

                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            JSONObject item = getItem(position);
            aq.id(holder.iv).image(item.optString("activeUrl"), true, true);
            holder.tv.setText(item.optString("activeDesc"));

            return convertView;
        }

    }

    class ViewHolder {
        ImageView iv;

        TextView tv;
    }

}
