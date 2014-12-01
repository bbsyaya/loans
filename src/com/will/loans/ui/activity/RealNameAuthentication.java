
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.utils.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RealNameAuthentication extends BaseTextActivity {
    private EditText mPersonName;

    private EditText mPersonIdNum;

    private Button mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.realname_auth);

        mPersonName = (EditText) findViewById(R.id.et_realname);
        mPersonIdNum = (EditText) findViewById(R.id.et_idnum);
        mAuth = (Button) findViewById(R.id.btn_next);

        mAuth.setOnClickListener(this);

    }

    public void requestDate() {
        time = System.currentTimeMillis();
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", time);
            jo.put("token", SharePreferenceUtil.getUserPref(RealNameAuthentication.this).getToken());
            jo.put("userid", SharePreferenceUtil.getUserPref(RealNameAuthentication.this)
                    .getUserId());
            jo.put("realName", mPersonName.getText().toString());
            jo.put("idcardNo", mPersonIdNum.getText().toString());
            jo.put("sign", getMD5Code(time));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        mAquery.ajax(ServerInfo.CHECKREALNAME, params, JSONObject.class,
                new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {
                        String result = object.optString("resultflag");
                        if (result.equals("0")) {
                            Toaster.showShort(RealNameAuthentication.this, "认证成功");
                            finish();
                        } else {
                            Toaster.showShort(
                                    RealNameAuthentication.this,
                                    object.optString("value") + " \n"
                                            + object.optString("resultMsg"));
                        }
                    }
                });
    }

    @Override
    protected void onViewClick(View view) {
        if (view.getId() == R.id.btn_next) {
            if (mPersonIdNum.getText().toString().equals("") || mPersonName.getText().equals("")) {
                Toaster.showShort(getParent(), "请输入完整的信息");
            } else {
                requestDate();
            }
        }
    }
}
