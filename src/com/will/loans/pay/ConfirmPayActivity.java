
package com.will.loans.pay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.ui.activity.FillPayCode;
import com.will.loans.ui.activity.RealNameAuthentication;
import com.will.loans.ui.activity.Register;
import com.will.loans.ui.activity.ResetPassword;
import com.will.loans.ui.activity.SetPassword;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ConfirmPayActivity extends BasePayActivity implements OnClickListener {

    private EditText mTradePsw, bankID, bankName, BankPhone;

    private LinearLayout llytBankId, llytBankName, llytBankPhone;

    private TextView moneyET;

    private Button nextBtn;

    public static JSONObject product;

    public static JSONObject bankInfo;

    private String cardNo = "";

    public static String cardNoName = "";

    public static String buyMoney = "";

    String money = "";

    public static String CARDNOSTRING = "com.will.activity.cardno";

    private AQuery aq;

    protected SimpleDateFormat smf = new SimpleDateFormat("yyyy-MMddHHmm:ss");

    protected String key = "qHdKC5yNgKwdi1BFa5EKOw29fwYeetV78EcSN04H93jBYvoLkP631rFcSa3OT3Np";

    protected String orderId;

    private ProgressDialog mLoadingDialo;

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
            jo.put("userid", SharePreferenceUtil.getUserPref(ConfirmPayActivity.this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(ConfirmPayActivity.this).getToken());
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
                        return;
                    } else {
                        startActivity(new Intent(ConfirmPayActivity.this, SetPassword.class)
                                .putExtra(SetPassword.SETTYPE, 1));
                    }
                }
            }
        });

    }

    @Override
    protected void afterSetContentView() {
        cardNo = getIntent().getExtras().getString(CARDNOSTRING);
        aq = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView)findViewById(R.id.title_back)).setText("修改金额");
        bankID = (EditText) findViewById(R.id.bank_id_card);
        bankName = (EditText) findViewById(R.id.bank_username);
        BankPhone = (EditText) findViewById(R.id.bank_phone_num);
        findViewById(R.id.forget_psw).setOnClickListener(this);

        llytBankId = (LinearLayout) findViewById(R.id.llyt_bank_id_card);
        llytBankName = (LinearLayout) findViewById(R.id.llyt_bank_name);
        llytBankPhone = (LinearLayout) findViewById(R.id.llyt_bank_phone);
        checkCardNum();
        ((TextView) findViewById(R.id.title_tv)).setText("投标");
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SharePreferenceUtil.getUserPref(ConfirmPayActivity.this).getToken().equals("")) {
                    startActivity(new Intent(ConfirmPayActivity.this, Register.class));
                } else {
                    // checkHaveTradePsw();
                    getData();
                }
            }
        });
        nextBtn.setEnabled(false);

        getIpAddress();

        moneyET = (TextView) findViewById(R.id.moneyET);
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
                if (mTradePsw.getText().toString().equals("")) {
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
        });
        updateView();
        checkHaveTradePsw();
    }

    private void checkCardNum() {
        String result = bankInfo.optString("cardtype");
        if (result.equals("1")) {
            bankID.setHint("请输入银行卡身份证号码");
            bankName.setHint("请输入姓名");
            BankPhone.setHint("请输入预留的电话号码");
        } else if (result.equals("2")) {
            bankID.setHint("请输入信用卡有效期");
            bankName.setHint("请信用卡背面3位数");
            BankPhone.setHint("请输入预留的电话号码");
        } else {
            llytBankId.setVisibility(View.GONE);
            llytBankName.setVisibility(View.GONE);
            llytBankPhone.setVisibility(View.GONE);
        }
    }

    public String getIpAddress() {
        //获取wifi服务  
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启  
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();

            return intToIp(ipAddress);
        } else {
            return getLocalIpAddress();
        }
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
                + (i >> 24 & 0xFF);
    }

    @Override
    protected void getData() {
        // TODO EditPay完善参数
        Long time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("userid", SharePreferenceUtil.getUserPref(ConfirmPayActivity.this).getUserId());
            jo.put("token", SharePreferenceUtil.getUserPref(ConfirmPayActivity.this).getToken());
            jo.put("amount", buyMoney);
            jo.put("proId", product.optString("id"));
            jo.put("tradePsw", GenerateMD5Password.getMD5Password(mTradePsw.getText().toString()));
            String result = bankInfo.optString("cardtype");
            if (result.equals("1")) {
                jo.put("idcard", bankID.getText().toString());
                jo.put("name", bankName.getText().toString());
                jo.put("phone", BankPhone.getText().toString());
            } else if (result.equals("2")) {
                jo.put("validthru", bankID.getText().toString());
                jo.put("cvv2", bankName.getText().toString());
                jo.put("phone", BankPhone.getText().toString());
            } else {
                jo.put("bindid", bankInfo.optString("bindid"));
            }
            jo.put("sign", getMD5Code(time));
            jo.put("cardNo", cardNo);
            jo.put("bankName", cardNoName);
            jo.put("payType", bankInfo.optString("payType"));
            jo.put("userip", getIpAddress());
            jo.put("terminaltype", "3");
            jo.put("terminalid", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.BUYPRODUCTYB, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                String result = json.optString("resultflag");
                if (result.equals("0")) {
                    orderId = json.optString("orderid");
                    Toast.makeText(getApplication(), "我们已收到您的投标申请，\n申请结果请到交易记录查看",
                            Toast.LENGTH_SHORT).show();
                } else if (result.equals("1")) {
                    Toast.makeText(getApplication(), json.optString("resultMsg"),
                            Toast.LENGTH_SHORT).show();
                } else if (result.equals("2")) {
                    Toast.makeText(getApplication(), json.optString("resultMsg"),
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ConfirmPayActivity.this, RealNameAuthentication.class));
                } else if (result.equals("3")) {
                    Toast.makeText(getApplication(), json.optString("resultMsg"),
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ConfirmPayActivity.this, FillPayCode.class));
                }
            }
        });

    }

    protected void queryIsPaySuccess() {
        // TODO EditPay完善参数
        Long time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("orderid", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax("http://daidaitong.imwanmei.com:8080/mobile/ybQueryOrder", params,
                JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {
                        String result = json.optString("resultflag");
                        mLoadingDialo.cancel();
                        if (result.equals("0")) {
                            if (json.optString("status").equals("1")) {
                                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } else if (result.equals("1")) {
                            Toast.makeText(getApplication(), json.optString("resultMsg"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateView() {
        moneyET.setText("" + product.optInt("startBuy"));
        setTextView(R.id.bank_card_num, "银行卡：" + product.optInt("startBuy") + ".00元", "");
        setTextView(R.id.moneyET, buyMoney + ".00元", "");
        setTextView(R.id.moneyTV, buyMoney + ".00元", "");
        setTextView(R.id.nameTV, product.optString("proName"), "");
        // setTextView(R.id.moneyTV, "起投金额：" + product.optInt("startBuy") + "元"
        // + "    手续费:无", "");
        // setTextView(R.id.timeTV, "理财年限：限" + product.optString("timeLimit") +
        // "个月", "");
        // setTextView(R.id.multipleTV, "投资倍数为：" + product.optInt("startBuy") +
        // "的整数倍", "");
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
            case R.id.forget_psw:
                startActivity(new Intent(ConfirmPayActivity.this, ResetPassword.class).putExtra(
                        ResetPassword.TYPE_NAME, 0));
                break;
            default:
                break;
        }

    }
}
