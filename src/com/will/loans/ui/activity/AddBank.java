package com.will.loans.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 11/10/14.
 */
public class AddBank extends BaseTextActivity {
    private TextView mBigPhoneNum;
    private Button mNextBtn;
    private EditText mBankNum;
    private AQuery mAQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        initView();
    }

    private void initView() {
        mAQuery = new AQuery(this);

        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView)findViewById(R.id.title_tv)).setText(R.string.add_bank);
        mBankNum = (EditText) findViewById(R.id.phone_num);
        mBigPhoneNum = (TextView) findViewById(R.id.phone_num_big);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
        getBindBankList();
    }

    public void getBindBankList(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAQuery.ajax(ServerInfo.GETBINDBANKLIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                Log.d("loans", "" + json.toString());
                if (json == null) {
                    return;
                }
                JSONArray ja = null;
                ja = json.optJSONArray("bankList");

            }
        });

    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:

                break;
        }
    }

    public void checkBankCard(){

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mNextBtn.setEnabled(true);
            mBigPhoneNum.setVisibility(View.VISIBLE);
            mBigPhoneNum.setText(s);
        } else {
            mNextBtn.setEnabled(false);
            mBigPhoneNum.setVisibility(View.GONE);
        }
    }
}
