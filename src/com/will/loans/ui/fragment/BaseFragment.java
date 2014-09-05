
package com.will.loans.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.will.loans.R;

public class BaseFragment extends Fragment {

    public void pushToFragment(BaseFragment targetFragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, targetFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public void jump2Activity(Activity targetActivity) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), targetActivity.getClass());
        getActivity().startActivity(intent);
    }

    protected void finish() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    protected void clearBackStack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        while (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
        }
    }

    protected void hideSystemKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    protected void setTitleText(View view, String leftBtnText, String rightBtnText,
            String centerText) {
        ((TextView) view.findViewById(R.id.title_tv)).setText(centerText);
        ((Button) view.findViewById(R.id.title_btn_left)).setText(leftBtnText);
        ((Button) view.findViewById(R.id.title_btn_right)).setText(rightBtnText);
    }

    protected void setTitleText(View view, int leftBtnText, int rightBtnText, int centerText) {
        ((TextView) view.findViewById(R.id.title_tv)).setText(centerText);
        ((Button) view.findViewById(R.id.title_btn_left)).setText(leftBtnText);
        ((Button) view.findViewById(R.id.title_btn_right)).setText(rightBtnText);
    }

    protected void setTitleVisible(View view, int leftBtnText, int rightBtnText, int centerText) {
        ((TextView) view.findViewById(R.id.title_tv)).setVisibility(centerText);
        ((Button) view.findViewById(R.id.title_btn_left)).setVisibility(leftBtnText);
        ((Button) view.findViewById(R.id.title_btn_right)).setVisibility(rightBtnText);
    }
}
