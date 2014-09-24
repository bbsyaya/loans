
package com.will.loans.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.application.AppContext;
import com.will.loans.utils.GenerateMD5Password;
import com.will.loans.utils.SharePreferenceUtil;

import java.text.SimpleDateFormat;

public class BaseActivity extends Activity implements OnClickListener {
    protected AQuery mAquery;

    protected Long time;

    protected SimpleDateFormat smf = new SimpleDateFormat("yyyy-MMddHHmm:ss");

    protected String key = "qHdKC5yNgKwdi1BFa5EKOw29fwYeetV78EcSN04H93jBYvoLkP631rFcSa3OT3Np";

    /**
     * 签名参数名：sign 生成方法：sign = md5(token+时间字符串+密钥) 时间字符串格式：yyyy-MMddHHmm:ss
     * 密钥：qHdKC5yNgKwdi1BFa5EKOw29fwYeetV78EcSN04H93jBYvoLkP631rFcSa3OT3Np
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.getInstance().addActivity(this);
        mAquery = new AQuery(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                onViewClick(v);
                break;
        }

    }

    protected String getMD5Code(Long time) {
        return GenerateMD5Password.encodeByMD5(SharePreferenceUtil.getUserPref(this).getToken()
                + smf.format(time) + key);
    }

    protected void onViewClick(View view) {

    }
}
