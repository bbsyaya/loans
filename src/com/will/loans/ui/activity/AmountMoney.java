
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户总资产界面
 */
public class AmountMoney extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalassert);
        init();
    }

    private void init() {
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText("总资产(元)");
        findViewById(R.id.title_tv_phone).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.title_tv_phone)).setText("0.0");
        getDate();
    }

    public void getDate() {
        JSONObject jo = new JSONObject();
        time = System.currentTimeMillis();
        try {
            jo.put("timeStamp", time);
            jo.put("token", SharePreferenceUtil.getUserPref(this).getToken());
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAquery.ajax(ServerInfo.TOTALASSETS, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        System.out.println(" " + object.toString());
                    }
                });

    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
    }
}
