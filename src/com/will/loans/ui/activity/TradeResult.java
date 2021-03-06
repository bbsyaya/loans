
package com.will.loans.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TradeResult extends BaseActivity {

    private ListView lv;

    private AQuery aq;

    private Adatper mAdatper;

    /**
     * <pre>
     * 0 交易记录
     * </pre>
     */
    public static int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(this);
        setContentView(R.layout.activity_traderesult);
        type = 0;
        if (type == 0) {
            ((TextView) findViewById(R.id.title_tv)).setText("交易记录");
        } else if (type == 1) {

        }

        lv = (ListView) findViewById(R.id.resultLV);
        mAdatper = new Adatper(this);
        lv.setAdapter(mAdatper);

        getData();
    }

    private void getData() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", new Date().getTime());
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("pageNum", "1");
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.TRADERECLIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
            }
        });
    }

    class Adatper extends BaseAdapter {

        private final Context mContext;

        public Adatper(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup arg2) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.item_traderesult, null);
                viewHolder.title = (TextView) view.findViewById(R.id.product_title);
                viewHolder.time = (TextView) view.findViewById(R.id.product_time);
                viewHolder.status = (TextView) view.findViewById(R.id.product_status);
                viewHolder.product_status = (TextView) view.findViewById(R.id.product_trade_status);
                viewHolder.money = (TextView) view.findViewById(R.id.product_money);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            viewHolder.time.setText(sdf.format(new Date()));
            viewHolder.money.setText(Html.fromHtml("<font color=#ff0000>" + 50 * position
                    + "</font>" + "元"));
            return view;
        }

        class ViewHolder {
            TextView title;

            TextView time;

            TextView status;

            TextView product_status;

            TextView money;
        }
    }

}
