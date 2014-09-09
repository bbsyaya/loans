
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.ui.activity.ActionCenter;
import com.will.loans.ui.activity.HelpCenter;
import com.will.loans.ui.activity.MessageCenter;

public class More extends BaseFragment implements OnClickListener{
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

		view.findViewById(R.id.more_user_center).setOnClickListener(this);
		view.findViewById(R.id.more_about).setOnClickListener(this);
		view.findViewById(R.id.more_message_center).setOnClickListener(this);
		view.findViewById(R.id.more_help_center).setOnClickListener(this);
		view.findViewById(R.id.more_attention).setOnClickListener(this);
		view.findViewById(R.id.more_exit).setOnClickListener(this);
		view.findViewById(R.id.more_rate).setOnClickListener(this);
		view.findViewById(R.id.more_share).setOnClickListener(this);
		view.findViewById(R.id.more_feedback).setOnClickListener(this);
		view.findViewById(R.id.check_update_llyt).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_user_center:
			jump2Activity(new ActionCenter());
			break;
		case R.id.more_about:

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

			break;
		case R.id.more_rate:

			break;
		case R.id.more_share:

			break;
		case R.id.more_feedback:

			break;
		case R.id.check_update_llyt:

			break;
		default:
			break;
		}

	}
}
