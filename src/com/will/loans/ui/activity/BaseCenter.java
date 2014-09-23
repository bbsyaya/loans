
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.will.loans.R;

public abstract class BaseCenter extends BaseActivity implements OnItemClickListener {
    protected ListView mListView;

    protected AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_center);
        aq = new AQuery(this);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(getAdapter());
        mListView.setOnItemClickListener(this);
        init();
    }

    protected void init() {

    }

    protected abstract BaseAdapter getAdapter();
}
