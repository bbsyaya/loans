
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.will.loans.R;

public class More extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_more, null);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	((TextView) view.findViewById(R.id.title_tv)).setText(R.string.tab_more);
        ((Button) view.findViewById(R.id.title_btn_left)).setText(R.string.login);
        ((Button) view.findViewById(R.id.title_btn_right)).setText(R.string.refresh);
    }
}
