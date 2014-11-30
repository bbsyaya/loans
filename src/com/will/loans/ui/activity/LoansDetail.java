
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.will.loans.R;
import com.will.loans.beans.json.LoansDetailJson;
import com.will.loans.constant.ServerInfo;
import com.will.loans.pay.ConfirmPayActivity;
import com.will.loans.pay.EditPayActivity;
import com.will.loans.pay.UnionPayActivity;
import com.will.loans.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoansDetail extends BaseActivity {

    public static JSONObject pro;

    private AQuery aq;

    private String title;

    private PullToRefreshScrollView detailPRSV;

    public static String TITLETXT = "com.will.loansdetail.title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(this);
        setContentView(R.layout.loans_detail);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.title_btn_right).setOnClickListener(this);
        findViewById(R.id.buy_llyt).setOnClickListener(this);
        ((Button) findViewById(R.id.title_btn_right)).setText(R.string.refresh);
        ((TextView) findViewById(R.id.title_tv)).setText(pro.optString("proName"));
        detailPRSV = (PullToRefreshScrollView) findViewById(R.id.detailPRSV);
        detailPRSV.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getData();
            }
        });
        if (pro != null) {
            getData();
        }
    }

    private void getData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("proId", pro.optInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.PRODETAIL, params, LoansDetailJson.class,
                new AjaxCallback<LoansDetailJson>() {
                    @Override
                    public void callback(String url, LoansDetailJson json, AjaxStatus status) {
                        detailPRSV.onRefreshComplete();
                        if (!json.resultflag.equals("0")) {
                            return;
                        }
                        updateView(json);
                    }
                });

    }

    private void updateView(LoansDetailJson jo) {

        findViewById(R.id.detailLayout).setVisibility(View.VISIBLE);
        ((ProgressBar) findViewById(R.id.percentPB)).setProgress(pro.optInt("percent"));
        // 融资金额
        setTextView(R.id.amountMoneyTV, pro.optInt("totalAmount") + "", "");
        // 已融资
        setTextView(R.id.amountPercentTV, "已融资：" + pro.optInt("percent") + "%", "");
        // 剩余
        setTextView(R.id.remainMoneyTV, "剩余：" + (pro.optInt("totalAmount") - jo.detail.buyNum), "");
        // 高收益、第三方、限几个月、保障
        // setTextView(R.id.notifyTV1, jo.optString("benxiDesc"), "");
        setTextView(R.id.notifyTV2, jo.detail.benxiDesc, "");
        setTextView(R.id.notifyTV3, "限" + pro.optString("timeLimit") + "个月", "");
        setTextView(R.id.notifyTV4, jo.detail.securityTip, "");

        // 预期年化
        setTextView(R.id.forwardEarnTV, pro.optInt("nhsy") + "%", "");
        // 起投金额
        setTextView(R.id.startMoneyTV, pro.optInt("startBuy") + "", "");
        // 起投人数
        setTextView(R.id.payNumTV, jo.detail.buyPerNum.toString() + "人", "");
        //申购奖励
        if (jo.items != null && jo.items.size() > 0) {
            setTextView(R.id.rewardTV, jo.items.get(0).itemDesc, "");
            //项目描述
            setTextView(R.id.describeTV, jo.items.get(1).itemDesc, "");
            //资金保障
            setTextView(R.id.safeguardTV, jo.items.get(2).itemDesc, "");
            //
        }
        //还款提醒
        if (jo.start_day!=null&&!jo.start_day.equals("")) {
            setTextView(R.id.qixiri, "起息日：" + jo.start_day + "\n还款日：" + jo.pay_day + "\n还款提醒：" + jo.pay_attention, "");
        }
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
            case R.id.title_btn_right:
                detailPRSV.setRefreshing();
                break;
            case R.id.buy_llyt:
                //                startActivity(new Intent(LoansDetail.this, BuyList.class).putExtra(BuyList.MPROSTR,
                //                        pro.optInt("id")));
                break;
            default:
                break;
        }

    }

    public void enterBtn(View view) {
        EditPayActivity.product = pro;
        ConfirmPayActivity.product = pro;
        UnionPayActivity.product = pro;
        if (!SharePreferenceUtil.getUserPref(getParent()).getToken().equals("")) {
            startActivity(new Intent(LoansDetail.this, PaySelectionActivity.class));
        } else {
            startActivity(new Intent(LoansDetail.this, Register.class));
        }
    }

    public void calculatorView(View view) {
        startActivity(new Intent(LoansDetail.this, PreEncoming.class));
    }
}
