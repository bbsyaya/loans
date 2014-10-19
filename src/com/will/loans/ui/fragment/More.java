
package com.will.loans.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.will.loans.R;
import com.will.loans.ui.activity.About;
import com.will.loans.ui.activity.ActionCenter;
import com.will.loans.ui.activity.Feedback;
import com.will.loans.ui.activity.HelpCenter;
import com.will.loans.ui.activity.MessageCenter;
import com.will.loans.utils.SharePreferenceUtil;

public class More extends BaseFragment implements OnClickListener {

    private View logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_more, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.title_tv)).setText(R.string.tab_more);

        view.findViewById(R.id.more_user_center).setOnClickListener(this);
        view.findViewById(R.id.more_about).setOnClickListener(this);
        view.findViewById(R.id.more_message_center).setOnClickListener(this);
        view.findViewById(R.id.more_help_center).setOnClickListener(this);
        view.findViewById(R.id.more_attention).setOnClickListener(this);
        logout = view.findViewById(R.id.more_exit);
        logout.setOnClickListener(this);
        view.findViewById(R.id.more_rate).setOnClickListener(this);
        view.findViewById(R.id.more_share).setOnClickListener(this);
        view.findViewById(R.id.more_feedback).setOnClickListener(this);
        view.findViewById(R.id.check_update_llyt).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        if (SharePreferenceUtil.getUserPref(getActivity()).getToken().equals("")) {
            logout.setVisibility(View.GONE);
        } else {
            logout.setVisibility(View.VISIBLE);
        }
    }

    private void showExitDlg(){
        new AlertDialog.Builder(getActivity()).setTitle("退出").setMessage("是否退出？").setPositiveButton("退出",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "成功退出登录", Toast.LENGTH_SHORT).show();
                SharePreferenceUtil.getUserPref(getActivity()).clear();
                updateView();
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_user_center:
                jump2Activity(new ActionCenter());
                break;
            case R.id.more_about:
                jump2Activity(new About());
                break;
            case R.id.more_message_center:
                jump2Activity(new MessageCenter());
                break;
            case R.id.more_help_center:
                jump2Activity(new HelpCenter());
                break;
            case R.id.more_attention:

                break;
            case R.id.more_exit:
                showExitDlg();
                break;
            case R.id.more_rate:

                break;
            case R.id.more_share:

                break;
            case R.id.more_feedback:
                jump2Activity(new Feedback());
                break;
            case R.id.check_update_llyt:

                break;
            default:
                break;
        }

    }
}
