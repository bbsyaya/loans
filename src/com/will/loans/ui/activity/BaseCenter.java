package com.will.loans.ui.activity;

import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.will.loans.R;

public abstract class BaseCenter extends BaseActionBarActivity implements OnItemClickListener{
	protected ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_center);
		initView();
	}
	
	private void initView() {
		mListView = (ListView) findViewById(R.id.list);
		mListView.setAdapter(getAdapter());
		mListView.setOnItemClickListener(this);
		
	}

	protected abstract BaseAdapter getAdapter();
}
