
package com.will.loans.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import com.will.loans.application.AppContext;
import com.will.loans.utils.Toaster;

public class BaseActionBarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.getInstance().addActivity(this);
    }

    @SuppressLint("NewApi")
    public void setNoTitle() {
        getActionBar().setDisplayShowCustomEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean clearOrExit() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() == 0) {
            return true;
        } else {
            manager.popBackStackImmediate();
            return false;
        }

    }

    private Long keyDownTime = 0L;

    /**
     * 按两次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (clearOrExit()) {
                if (System.currentTimeMillis() - keyDownTime < 3000) {
                    AppContext.getInstance().exit();
                } else {
                    keyDownTime = System.currentTimeMillis();
                    Toaster.showShort(BaseActionBarActivity.this, "再按一次退出！");
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event); //To change body of overridden methods use File | Settings | File Templates.
    }
}
