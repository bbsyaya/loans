package com.will.loans.ui.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;

public class FundCompany extends BaseActivity {
	private int mFundCompanyId;
	public static String FUNDCOMPANYID = "com.will.loans.fundcom";
	private AQuery aQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fundcom);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_tv)).setText("关于");
		mFundCompanyId = getIntent().getExtras().getInt(FUNDCOMPANYID);
		aQuery = new AQuery(this);
		getData();
	}

	public void getData(){
		JSONObject jo = new JSONObject();
		try {
			jo.put("timeStamp", System.currentTimeMillis());
			jo.put("fundCompId", mFundCompanyId);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsonData", jo.toString());
		aQuery.ajax(ServerInfo.FUNDCOMPINFO, params, JSONObject.class,
				new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {
				if (json!=null) {
					updateView(json.optJSONObject("fundcomp"));
				}
			}

		});
	}

	private void updateView(JSONObject json) {
		((TextView)findViewById(R.id.sale_company)).setText("销售公司："+json.optString("saleComName"));
		((TextView)findViewById(R.id.sale_type)).setText("理财类型："+json.optString("fundType"));
		((TextView)findViewById(R.id.desc1)).setText(json.optString("detailDesc1"));
		((TextView)findViewById(R.id.desc2)).setText(json.optString("detailDesc2"));
		((TextView)findViewById(R.id.desc3)).setText(json.optString("detailDesc3"));

	}
}
