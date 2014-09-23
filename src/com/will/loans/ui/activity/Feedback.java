
package com.will.loans.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.utils.Toaster;

public class Feedback extends BaseTextActivity {
    private EditText mFeedback;

    private Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_tv)).setText(R.string.more_feedback);

        mFeedback = (EditText) findViewById(R.id.feedback_content);
        mFeedback.addTextChangedListener(this);
        mSend = (Button) findViewById(R.id.feedback_send);
        mSend.setOnClickListener(this);
        findViewById(R.id.feedback_phone).setOnClickListener(this);

    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_send:
                finish();
                Toaster.showShort(this, "谢谢您的反馈！");
                break;
            case R.id.feedback_phone:

                break;
            default:
                break;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mFeedback.getText().toString().length() > 0) {
            mSend.setEnabled(true);
        } else {
            mSend.setEnabled(false);
        }
    }

}
