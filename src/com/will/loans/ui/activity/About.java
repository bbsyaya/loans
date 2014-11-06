
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.will.loans.R;

public class About extends BaseActivity {
    public static final String FIRST_LOAD_NO_FINISH = "com.will.loans.about";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.about_title);

        init();
    }

    private void init() {
        findViewById(R.id.about_welcome).setOnClickListener(this);
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()){
            case R.id.about_welcome:
                startActivity(new Intent(About.this,FirstLoad.class).putExtra(FIRST_LOAD_NO_FINISH,false));
                break;
        }
    }
}
