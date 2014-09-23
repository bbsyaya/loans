
package com.will.loans.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.application.AppContext;

public class BaseActivity extends Activity implements OnClickListener {
	protected AQuery mAquery;
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

	protected void onViewClick(View view) {

	}
}
