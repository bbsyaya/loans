
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.json.BankInfoJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.ConfirmPayActivity;
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
    private TextView mBigPhoneNum,mBankName;

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
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.add_bank);
        mBankName = (TextView) findViewById(R.id.bank_name);
        mBankNum = (EditText) findViewById(R.id.bank_card_id);
        mBankNum.addTextChangedListener(this);
        mBigPhoneNum = (TextView) findViewById(R.id.phone_num_big);
        mNextBtn = (Button) findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
        mBankName.setOnClickListener(this);
        getBindBankList();
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
                        if (json == null) {
                            return;
                        }
                        if (json.bankList!=null&&json.bankList.get(0)!=null){
                            mBankNum.setText(json.bankList.get(0).cardNo);
                            mBankName.setVisibility(View.VISIBLE);
                            mBankName.setText(json.bankList.get(0).bankName);
                            ConfirmPayActivity.cardNoName = json.bankList.get(0).bankName;
                        }
                    }
                });

    }

    private void checkBankcard() {
        // TODO EditPay完善参数
        Long time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(this).getUserId());
            jo.put("cardNo", mBankNum.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAQuery.ajax(ServerInfo.BANKCARDCHECK, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {
                        String result = json.optString("resultflag");
                        if (result.equals("0") || result.equals("2")) {
                            ConfirmPayActivity.bankInfo = json;
                            startActivity(new Intent(AddBank.this, ConfirmPayActivity.class)
                                    .putExtra(ConfirmPayActivity.CARDNOSTRING, mBankNum.getText()
                                            .toString()));
                            finish();
                        } else if (result.equals("1")) {
                            Toast.makeText(getBaseContext(), "" + json.optString("resultMsg"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                checkBankcard();
                break;
            case R.id.bank_name:
                startActivityForResult(new Intent(AddBank.this,SelectBank.class),SelectBank.REQUEST_CODE_BANK_ID);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==SelectBank.REQUEST_CODE_BANK_ID){
            mBankName.setText(data.getExtras().getString(SelectBank.BANK_TITLE));
        }
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
