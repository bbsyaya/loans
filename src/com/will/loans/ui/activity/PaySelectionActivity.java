
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.pay.EditPayActivity;
import com.will.loans.pay.UnionPayActivity;

public class PaySelectionActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_select);

        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText("我的银行卡");
        findViewById(R.id.yibao_pay).setOnClickListener(this);
        findViewById(R.id.union_pay).setOnClickListener(this);
    }

    @Override
    protected void onViewClick(View view) {
        // TODO Auto-generated method stub
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.yibao_pay:
                startActivity(new Intent(PaySelectionActivity.this, EditPayActivity.class));
                break;

            case R.id.union_pay:
                startActivity(new Intent(PaySelectionActivity.this, UnionPayActivity.class));
                break;

            default:
                break;
        }
    }
}
