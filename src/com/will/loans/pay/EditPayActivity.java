
package com.will.loans.pay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.ui.activity.AddBank;
import com.will.loans.ui.activity.BaseTextActivity;
import com.will.loans.ui.activity.Register;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class EditPayActivity extends BaseTextActivity implements OnClickListener {

    private EditText moneyET, mTradePsw;

    public static JSONObject product;

    private AQuery aq;

    protected Button nextBtn;

    protected SimpleDateFormat smf = new SimpleDateFormat("yyyy-MMddHHmm:ss");

    protected String key = "qHdKC5yNgKwdi1BFa5EKOw29fwYeetV78EcSN04H93jBYvoLkP631rFcSa3OT3Np";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        afterSetContentView();
    }

    @Override
    protected String getMD5Code(Long time) {
        return GenerateMD5Password.encodeByMD5(SharePreferenceUtil.getUserPref(this).getToken()
                + smf.format(time) + key);
    }

    private void afterSetContentView() {

        aq = new AQuery(this);
        nextBtn = (Button) findViewById(R.id.nextBtn);

        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText("投标");
        nextBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SharePreferenceUtil.getUserPref(EditPayActivity.this).getToken().equals("")) {
                    startActivity(new Intent(EditPayActivity.this, Register.class));
                } else {
                    //                    checkHaveTradePsw();
                    startActivity(new Intent(EditPayActivity.this, AddBank.class));
                }
            }
        });
        nextBtn.setEnabled(false);

        moneyET = (EditText) findViewById(R.id.moneyET);
        mTradePsw = (EditText) findViewById(R.id.trade_psw);
        moneyET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (TextUtils.isEmpty(moneyET.getText().toString())) {
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
