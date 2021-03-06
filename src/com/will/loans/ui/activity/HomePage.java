
package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.will.loans.R;
import com.will.loans.utils.SharePreferenceUtil;

public class HomePage extends FragmentActivity implements OnCheckedChangeListener {
    private String MAIN_TAB = "tab_main";

    private String MINE_TAB = "tab_mine";

    private String LIST_TAB = "tab_list";

    private String MORE_TAB = "tab_more";

    private TabHost mTabHost;

    private String mCurrentTab = MAIN_TAB;

    private boolean mIsJump2Person = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTabHost != null && mIsJump2Person
                && !SharePreferenceUtil.getUserPref(getParent()).getToken().equals("")) {
            mTabHost.setCurrentTabByTag(MINE_TAB);
            mIsJump2Person = false;
        } else {
            setCurrentTab();
        }
    }

    private void setCurrentTab() {
        if (mCurrentTab.equals(MAIN_TAB)) {
            ((RadioButton) findViewById(R.id.main_tab_home)).setChecked(true);
            ((RadioButton) findViewById(R.id.main_tab_home)).setSelected(true);
        } else if (mCurrentTab.equals(MINE_TAB)) {
            ((RadioButton) findViewById(R.id.main_tab_mine)).setChecked(true);
            ((RadioButton) findViewById(R.id.main_tab_mine)).setSelected(true);
        } else if (mCurrentTab.equals(LIST_TAB)) {
            ((RadioButton) findViewById(R.id.main_tab_product_list)).setChecked(true);
            ((RadioButton) findViewById(R.id.main_tab_product_list)).setSelected(true);
        } else if (mCurrentTab.equals(MORE_TAB)) {
            ((RadioButton) findViewById(R.id.main_tab_more)).setChecked(true);
            ((RadioButton) findViewById(R.id.main_tab_more)).setSelected(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initView() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        addTab(MAIN_TAB, "首页", R.id.fragment_home);
        addTab(LIST_TAB, "列表", R.id.fragment_list);
        addTab(MINE_TAB, "我的", R.id.fragment_mine);
        addTab(MORE_TAB, "更多", R.id.fragment_more);
        initListener();
    }

    private void initListener() {
        ((RadioButton) findViewById(R.id.main_tab_home)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.main_tab_product_list)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.main_tab_mine)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.main_tab_more)).setOnCheckedChangeListener(this);
    }

    public void addTab(String tab, String indicator, int resId) {
        mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(indicator).setContent(resId));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.main_tab_home:
                setTabByTag(isChecked, MAIN_TAB);
                break;
            case R.id.main_tab_product_list:
                setTabByTag(isChecked, LIST_TAB);
                break;
            case R.id.main_tab_mine:
                if (isChecked
                        && SharePreferenceUtil.getUserPref(HomePage.this).getToken().equals("")) {
                    startActivity(new Intent(HomePage.this, Register.class));
                    mIsJump2Person = true;
                } else {
                    setTabByTag(isChecked, MINE_TAB);
                }
                break;
            case R.id.main_tab_more:
                setTabByTag(isChecked, MORE_TAB);
                break;
        }

    }

    private void setTabByTag(boolean isChecked, String tab) {
        if (isChecked) {
            mTabHost.setCurrentTabByTag(tab);
            mCurrentTab = tab;
        }
    }
}
