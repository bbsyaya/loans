
package com.will.loans.ui.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.weight.AutoLoadPull2RefreshListView;
import com.will.loans.weight.AutoLoadPull2RefreshListView.OnRefreshListener;

public abstract class BaseMineActivity extends BaseActivity implements OnClickListener,OnRefreshListener{
	protected AutoLoadPull2RefreshListView mAutoLoadLv;
	protected AQuery aq;
	protected JSONObject jo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		aq = new AQuery(this);
		setContentView(R.layout.activity_mine);
		findViewById(R.id.title_back).setVisibility(View.VISIBLE);
		findViewById(R.id.title_back).setOnClickListener(this);
		((TextView)findViewById(R.id.title_back)).setText(R.string.back);

		mAutoLoadLv = (AutoLoadPull2RefreshListView) findViewById(R.id.trade_list);
		mAutoLoadLv.setAdapter(getAdatper());
		initView();
	}

	protected void initView(){

	}

	/**
	 * 请求接口数据
	 */
	public abstract void getDate();

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

	protected void onViewClick(View view){

	}

	public abstract BaseAdapter getAdatper();

}
