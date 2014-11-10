
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.beans.json.ProductDetailJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.ConfirmPayActivity;
import com.will.loans.pay.EditPayActivity;
import com.will.loans.pay.UnionPayActivity;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ProductDetail extends BaseActivity {
    public static JSONObject proDetail;

    private AQuery aQuery;

    private ProductDetailJson jo;

    private SimpleDateFormat smf = new SimpleDateFormat("yy-dd-MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        aQuery = new AQuery(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((Button) findViewById(R.id.title_btn_right)).setText(R.string.refresh);
        findViewById(R.id.detail_about).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(proDetail.optString("proName"));

        getData();
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.detail_about:
                startActivity(new Intent(ProductDetail.this, FundCompany.class).putExtra(
                        FundCompany.FUNDCOMPANYID, jo.fundcomp.id));
                break;

            default:
                break;
        }
    }

    private void getData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("proId", proDetail.optInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aQuery.ajax(ServerInfo.PRODETAIL, params, ProductDetailJson.class,
                new AjaxCallback<ProductDetailJson>() {
                    @Override
                    public void callback(String url, ProductDetailJson json, AjaxStatus status) {
                        if (json != null) {
                            updateView(json);
                        }
                    }
                });

    }

    private void updateView(ProductDetailJson jo) {
        this.jo = jo;
        ((TextView) findViewById(R.id.limit_num)).setText("" + jo.proInfo.startBuy);
        ((TextView) findViewById(R.id.earnig_money)).setText("" + jo.proInfo.wfsy);
        ((TextView) findViewById(R.id.person_num)).setText("" + jo.proInfo.totalAmount);
        ((TextView) findViewById(R.id.earnig_num)).setText("" + jo.proInfo.qrsy + "%");
        ((TextView) findViewById(R.id.earnig_num_total)).setText(""
                + jo.detail.totalMoney.longValue() + " 元");
        ((TextView) findViewById(R.id.pay_number))
                .setText("" + jo.detail.buyNum.longValue() + " 人");
        ((TextView) findViewById(R.id.capital_num)).setText("" + jo.detail.buyPerNum.longValue()
                + " 元");
        ((TextView) findViewById(R.id.earning_number_money)).setText("" + jo.fundcomp.wfsy + "元");
        ((TextView) findViewById(R.id.one_year_number)).setText("" + jo.fundcomp.yearRose + "%");
        ((TextView) findViewById(R.id.seven_day_number)).setText("" + jo.fundcomp.qrnh + "%");
        ((TextView) findViewById(R.id.six_month_number)).setText("" + jo.fundcomp.sixMonRose + "%");
        ((TextView) findViewById(R.id.sale_company)).setText("" + jo.fundcomp.saleComName);
        ((TextView) findViewById(R.id.loans_company)).setText("" + jo.fundcomp.comName);
        ((TextView) findViewById(R.id.loans_desc)).setText("" + jo.fundcomp.detailDesc1);
        ((TextView) findViewById(R.id.loans_type)).setText("" + jo.fundcomp.fundType);
        ((TextView) findViewById(R.id.loans_range_number)).setText(""
                + jo.fundcomp.fundScale.longValue() + "");
        ((TextView) findViewById(R.id.product_tv_total_day)).setText(smf.format(System
                .currentTimeMillis() - 60 * 60 * 24 * 1000));

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

    public void enterBtn(View view) {
        EditPayActivity.product = proDetail;
        ConfirmPayActivity.product = proDetail;
        UnionPayActivity.product = proDetail;
        if (!SharePreferenceUtil.getUserPref(getParent()).getToken().equals("")) {
            //            startActivity(new Intent(ProductDetail.this, EditPayActivity.class));
            startActivity(new Intent(ProductDetail.this, PaySelectionActivity.class));
        } else {
            startActivity(new Intent(ProductDetail.this, Register.class));
        }
    }

    public void calculatorView(View view) {
        startActivity(new Intent(ProductDetail.this, PreEncoming.class));
    }

}
