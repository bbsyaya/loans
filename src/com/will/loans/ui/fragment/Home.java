
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.will.loans.R;

public class Home extends BaseFragment implements OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitleText(view, R.string.tab_mine, R.string.refresh, R.string.tab_home);
        setTitleVisible(view, View.VISIBLE, View.VISIBLE, View.VISIBLE);
        ((Button) view.findViewById(R.id.title_btn_right)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.title_btn_left)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
