
package com.will.loans.ui.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.utils.Toaster;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageCenter extends BaseCenter {
    private MessageAdapter mMessageAdapter;

    private int mPageNum = 1;

    List<JSONObject> action = new ArrayList<JSONObject>();

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void init() {
        super.init();
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.more_message_center);
        ((TextView) findViewById(R.id.title_btn_right)).setText(R.string.read);
        findViewById(R.id.title_tv).setOnClickListener(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_back)).setText(R.string.back);
        getData();
    }

    private void getData() {
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("pageNum", mPageNum);
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.MSGLIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
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
                if (mMessageAdapter != null) {
                    mMessageAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.more_message_center:
                Toaster.showShort(this, "已标记为已读");
                break;

            default:
                break;
        }
    }

    @Override
    protected BaseAdapter getAdapter() {
        if (mMessageAdapter == null) {
            mMessageAdapter = new MessageAdapter();
        }
        return mMessageAdapter;
    }

    class MessageAdapter extends BaseAdapter {

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
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_message_center, null);
                holder.title = (TextView) convertView.findViewById(R.id.item_message_title);
                holder.time = (TextView) convertView.findViewById(R.id.item_message_time);
                holder.desc = (TextView) convertView.findViewById(R.id.item_message_desc);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();

            return convertView;
        }

        class ViewHolder {
            TextView title;

            TextView time;

            TextView desc;

        }

    }
}
