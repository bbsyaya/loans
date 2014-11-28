package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.will.loans.R;

/**
 * Created by will on 11/28/14.
 */
public class HelpDetail extends BaseActivity {
    public static String TITLE = "com.will.loans.title";
    public static String CONTENT = "com.will.loans.content";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_detail);

        ((TextView) findViewById(R.id.title_tv)).setText(R.string.more_help_center);
        findViewById(R.id.title_tv).setOnClickListener(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);

        ((TextView) findViewById(R.id.title)).setText(getIntent().getExtras().getString(TITLE));
        ((TextView) findViewById(R.id.content)).setText(getIntent().getExtras().getString(CONTENT));
    }
}
