
package com.will.loans.getsurepassword.pattern;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.will.loans.R;
import com.will.loans.application.AppContext;
import com.will.loans.getsurepassword.view.LockPatternUtils;
import com.will.loans.getsurepassword.view.LockPatternView;
import com.will.loans.getsurepassword.view.LockPatternView.Cell;
import com.will.loans.ui.activity.HomePage;

import java.util.List;

public class UnlockGesturePasswordActivity extends Activity {
    private LockPatternView mLockPatternView;

    private int mFailedPatternAttemptsSinceLastTimeout = 0;

    private CountDownTimer mCountdownTimer = null;

    private Handler mHandler = new Handler();

    private TextView mHeadTextView;

    private Animation mShakeAnim;

    private Toast mToast;

    private void showToast(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }

        mToast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturepassword_unlock);

        mLockPatternView = (LockPatternView) findViewById(R.id.gesturepwd_unlock_lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AppContext.getInstance().getLockPatternUtils(this).savedPatternExists()) {
            startActivity(new Intent(this, GuideGesturePasswordActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null) {
            mCountdownTimer.cancel();
        }
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        @Override
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        @Override
        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        @Override
        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null) {
                return;
            }
            if (AppContext.getInstance().getLockPatternUtils(UnlockGesturePasswordActivity.this).checkPattern(pattern)) {
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                Intent intent = new Intent();
                intent.setClass(UnlockGesturePasswordActivity.this, HomePage.class);
                startActivity(intent);
                finish();
//                showToast("解锁成功");
                finish();
            } else {
                mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0) {
                            showToast("您已5次输错密码，请30秒后再试");
                        }
                        mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
                        mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);
                    }

                } else {
                    showToast("输入长度不够，请重试");
                }

                if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                    mHandler.postDelayed(attemptLockout, 2000);
                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                }
            }
        }

        @Override
        public void onPatternCellAdded(List<Cell> pattern) {

        }

        private void patternInProgress() {
        }
    };

    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1,
                    1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        mHeadTextView.setText(secondsRemaining + " 秒后重试");
                    } else {
                        mHeadTextView.setText("请绘制手势密码");
                        mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };

}
