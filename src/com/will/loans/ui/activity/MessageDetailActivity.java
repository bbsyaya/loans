
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.will.loans.R;

import java.text.SimpleDateFormat;

public class MessageDetailActivity extends BaseActivity {
    public static String TITLE = "com.will.loans.message_title";

    public static String TIME = "com.will.loans.message_time";

    public static String CONTENT = "com.will.loans.message_content";

    private Long timeStr;

    private String titleStr, contentStr;

    private TextView time, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail);
        ((TextView) findViewById(R.id.title_tv)).setText("信息详情");
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        timeStr = getIntent().getExtras().getLong(TIME);
        titleStr = getIntent().getExtras().getString(TITLE);
        contentStr = getIntent().getExtras().getString(CONTENT);

        time = (TextView) findViewById(R.id.time);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);

        SimpleDateFormat smf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        time.setText(smf.format(timeStr));
        title.setText(titleStr);
        content.setText(contentStr);
    }
}
