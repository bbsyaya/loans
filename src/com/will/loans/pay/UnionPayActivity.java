
package com.will.loans.pay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.ui.activity.RealNameAuthentication;
import com.will.loans.ui.activity.SetPassword;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class UnionPayActivity extends UnionBasePay implements OnClickListener {

    private EditText moneyET, mTradePsw;

    public static JSONObject product;

    private AQuery aq;

    protected SimpleDateFormat smf = new SimpleDateFormat("yyyy-MMddHHmm:ss");

    protected String key = "qHdKC5yNgKwdi1BFa5EKOw29fwYeetV78EcSN04H93jBYvoLkP631rFcSa3OT3Np";

    protected String getMD5Code(Long time) {
        return GenerateMD5Password.encodeByMD5(SharePreferenceUtil.getUserPref(this).getToken()
                + smf.format(time) + key);
    }

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null, tn, mode);
    }

    private void checkHaveTradePsw() {
        Long time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(UnionPayActivity.this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(UnionPayActivity.this).getToken());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.HASTRADEPSW, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    String flag = json.optString("resultflag");
                    if (!json.optString("tradePsw").equals("")) {
                        mLoadingDialog = ProgressDialog.show(UnionPayActivity.this, // context
                                "", // title
                                "任务正在执行,请稍候...", // message
                                true); // 进度是否是不确定的，这只和创建进度条有关
                        getDate();
                        return;
                    } else {
                        //                              Toaster.showShort(getParent(),
                        //                                      json.optString("resultMsg"));
                        startActivity(new Intent(UnionPayActivity.this, SetPassword.class)
                                .putExtra(SetPassword.SETTYPE, 1));
                    }
                }
            }
        });

    }

    @Override
    protected void afterSetContentView() {

        aq = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText("投标");
        //        nextBtn.setOnClickListener(new OnClickListener() {
        //
        //            @Override
        //            public void onClick(View v) {
        //                if (SharePreferenceUtil.getUserPref(UnionPayActivity.this).getToken().equals("")) {
        //                    startActivity(new Intent(UnionPayActivity.this, Register.class));
        //                } else {
        //                    checkHaveTradePsw();
        //                }
        //            }
        //        });
        nextBtn.setEnabled(false);

        moneyET = (EditText) findViewById(R.id.moneyET);
        mTradePsw = (EditText) findViewById(R.id.trade_psw);
        mTradePsw.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (TextUtils.isEmpty(moneyET.getText().toString())
                        || TextUtils.isEmpty(mTradePsw.getText().toString())) {
                    return;
                }
                int money = Integer.parseInt(moneyET.getText().toString());
                if (money == 0 || money % product.optInt("startBuy") != 0) {
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
        });

        updateView();
        checkHaveTradePsw();
    }

    @Override
    protected void getDate() {
        // TODO EditPay完善参数
        Long time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(UnionPayActivity.this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(UnionPayActivity.this).getToken());
            jo.put("amount", moneyET.getText().toString());
            jo.put("proId", product.optString("id"));
            jo.put("tradePsw", mTradePsw.getText().toString());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.BUYPRODUCT, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                mLoadingDialog.cancel();
                String result = json.optString("resultflag");
                if (result.equals("0")) {
                    String tn = json.optString("tn");
                    Message msg = mHandler.obtainMessage();
                    msg.obj = tn;
                    mHandler.sendMessage(msg);
                } else if (result.equals("1")) {
                    //                    Log.d("", json.optString("resultMsg"));
                    //                    Toast.makeText(getApplication(), json.optString("resultMsg"), 1 * 1000).show();
                } else {
                    Toast.makeText(getApplication(), json.optString("resultMsg"), 1 * 1000).show();
                    startActivity(new Intent(UnionPayActivity.this, RealNameAuthentication.class));
                    Log.d("", json.optString("resultMsg"));
                }
            }
        });

    }

    private void updateView() {
        moneyET.setHint("投资金需≥" + product.optInt("startBuy"));
        setTextView(R.id.nameTV, product.optString("proName"), "");
        setTextView(R.id.moneyTV, "起投金额：" + product.optInt("startBuy") + "元" + "    手续费:无", "");
        setTextView(R.id.timeTV, "理财年限：限" + product.optString("timeLimit") + "个月", "");
        setTextView(R.id.multipleTV, "投资倍数为：" + product.optInt("startBuy") + "的整数倍", "");
    }

    /**
     * 通过id设置text
     * <p>
     * 若text为null或"",则使用or
     * 
     * @param resId
     * @param text
     * @param or
     */
    private void setTextView(final int resId, String text, String or) {
        final String content;
        if (TextUtils.isEmpty(text)) {
            content = or;
        } else {
            content = text;
        }
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ((TextView) findViewById(resId)).setText(content);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }

    }
}
