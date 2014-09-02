
package com.will.loans.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.will.loans.application.AppContext;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.getInstance().addActivity(this);
    }
}
