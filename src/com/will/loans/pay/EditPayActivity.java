package com.will.loans.pay;

import android.app.Activity;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class EditPayActivity extends BasePayActivity {

	@Override
	public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
		UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null,
				tn, mode);
	}

}
