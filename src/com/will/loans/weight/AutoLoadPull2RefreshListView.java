
package com.will.loans.weight;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.will.loans.R;
import com.will.loans.utils.SharePreferenceUtil;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA. User: ys.peng Date: 13-12-18 Time: 下午5:07 To
 * change this template use File | Settings | File Templates.
 */
public class AutoLoadPull2RefreshListView extends ListView implements OnScrollListener,
        ICheckIsNeededRefresh {

    /**
     * 实际的padding的距离与界面上偏移距离的比例
     */
    private final static int RATIO = 3;

    //===========================以下4个常量为 下拉刷新的状态标识===============================
    /**
     * 松开刷新
     */
    private final static int RELEASE_TO_REFRESH = 0;

    /**
     * 下拉刷新
     */
    private final static int PULL_TO_REFRESH = 1;

    /**
     * 正在刷新
     */
    private final static int REFRESHING = 2;

    /**
     * 刷新完成 or 什么都没做，恢复原状态。
     */
    private final static int DONE = 3;

    //===========================以下3个常量为 加载更多的状态标识===============================
    /**
     * 加载中
     */
    private final static int ENDINT_LOADING = 1;

    /**
     * 手动完成刷新
     */
    private final static int ENDINT_MANUAL_LOAD_DONE = 2;

    /**
     * 自动完成刷新
     */
    private final static int ENDINT_AUTO_LOAD_DONE = 3;

    /**
     * <strong>下拉刷新HeadView的实时状态flag</strong>
     * <p/>
     * <p>
     * 0 : RELEASE_TO_REFRESH;
     * <p>
     * 1 : PULL_To_REFRESH;
     * <p>
     * 2 : REFRESHING;
     * <p>
     * 3 : DONE;
     */
    private int mHeadState;

    /**
     * <strong>加载更多FootView（EndView）的实时状态flag</strong>
     * <p/>
     * <p>
     * 0 : 完成/等待刷新 ;
     * <p>
     * 1 : 加载中
     */
    private int mEndState;

    // ================================= 功能设置Flag ================================

    /**
     * 可以加载更多？
     */
    private boolean mCanLoadMore = false;

    /**
     * 可以下拉刷新？
     */
    private boolean mCanRefresh = false;

    /**
     * 可以自动加载更多吗？（注意，先判断是否有加载更多，如果没有，这个flag也没有意义）
     */
    private boolean mIsAutoLoadMore = false;

    /**
     * 下拉刷新后是否显示第一条Item
     * <p>
     * <strong>注意 : </strong> <br>
     * 这个flag在构造函数里要用到，由于实例化listView的构造函数要早于set方法， <br>
     * 所以这个flag最好在这个就指定好。
     */
    private boolean mIsMoveToFirstItemAfterRefresh = false;

    /**
     * 当该ListView所在的控件显示到屏幕上时，是否直接显示正在刷新...
     */
    private boolean mIsDoRefreshOnUIChanged = false;

    public boolean isCanLoadMore() {
        return mCanLoadMore;
    }

    public void setCanLoadMore(boolean pCanLoadMore) {
        mCanLoadMore = pCanLoadMore;
        if (mCanLoadMore && getFooterViewsCount() == 0) {
            addFooterView();
        }
    }

    public boolean isCanRefresh() {
        return mCanRefresh;
    }

    public void setCanRefresh(boolean pCanRefresh) {
        mCanRefresh = pCanRefresh;
    }

    public boolean isAutoLoadMore() {
        return mIsAutoLoadMore;
    }

    public void setAutoLoadMore(boolean pIsAutoLoadMore) {
        mIsAutoLoadMore = pIsAutoLoadMore;
    }

    public boolean isMoveToFirstItemAfterRefresh() {
        return mIsMoveToFirstItemAfterRefresh;
    }

    public void setMoveToFirstItemAfterRefresh(boolean pIsMoveToFirstItemAfterRefresh) {
        mIsMoveToFirstItemAfterRefresh = pIsMoveToFirstItemAfterRefresh;
    }

    public boolean isDoRefreshOnUIChanged() {
        return mIsDoRefreshOnUIChanged;
    }

    public void setDoRefreshOnUIChanged(boolean pIsDoRefreshOnWindowFocused) {
        mIsDoRefreshOnUIChanged = pIsDoRefreshOnWindowFocused;
    }

    // ============================================================================

    /**
     * 为了标记当前是第几页的ListView (没有实际用处)
     */
    private String mLabel;

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String pLabel) {
        mLabel = pLabel;
    }

    private LayoutInflater mInflater;

    private LinearLayout mHeadRootView;

    private TextView mTipsTextView;

    private TextView mLastUpdatedTextView;

    private ImageView mArrowImageView;

    private ProgressBar mProgressBar;

    private View mEndRootView;

    private ProgressBar mEndLoadProgressBar;

    private TextView mEndLoadTipsTextView;

    /**
     * headView动画
     */
    private RotateAnimation mArrowAnim;

    /**
     * headView反转动画
     */
    private RotateAnimation mArrowReverseAnim;

    /**
     * 用于保证startY的值在一个完整的touch事件中只被记录一次
     */
    private boolean mIsRecored;

    private int mHeadViewWidth;

    private int mHeadViewHeight;

    private int mStartY;

    private boolean mIsBack;

    private int mFirstItemIndex;

    private int mLastItemIndex;

    private int mCount;

    @SuppressWarnings("unused")
    private boolean mEnoughCount;//足够数量充满屏幕？

    private OnRefreshListener mRefreshListener;

    private OnLoadMoreListener mLoadMoreListener;

    /**
     * 更新间隔 30分钟 转换为毫秒
     */
    private static final int mUpdateIntervalMillis = 30 * 60 * 1000;

    /**
     * 上次更新的时间戳
     */
    private long mLastUpdateTimeMillis = 0;

    /**
     * 上次检查更新时记录的时间段，如果小于 mUpdateIntervalMillis， <br>
     * 说明上次没有达到更新条件，那么在启动线程去更新的时候， <br>
     * 延时的时间就要缩短为 (mUpdateIntervalMillis - mLastComparedDiffMillis)
     * <p/>
     * <br>
     * 该变量单位为毫秒
     */
    private long mLastComparedDiffMillis = 0;

    private static final String LAST_SAVE_TIME = "lastSaveTime";

    /**
     * 是否已经准备好刷新了？仅针对自动下拉刷新
     */
    private boolean mIsReadyToRefresh = false;

    public boolean isIsReadyToRefresh() {
        return mIsReadyToRefresh;
    }

    /**
     * 保存上次更新时间的标识
     */
    private String mSharedPrefName = "refreshTime";

    public void setRefreshName(String str) {
        mSharedPrefName = str;
    }

    /**
     * 相当于一个启动的线程
     */
    private Runnable mLoadCheckRunnable = new Runnable() {

        @Override
        public void run() {
            if (mLastComparedDiffMillis < mUpdateIntervalMillis) {
                AutoLoadPull2RefreshListView.this.postDelayed(mCheckRefRunnable,
                        mUpdateIntervalMillis - mLastComparedDiffMillis);
                System.out.println(mLabel + "——延时检查时间长度为 "
                        + (mUpdateIntervalMillis - mLastComparedDiffMillis));
            } else {
                AutoLoadPull2RefreshListView.this.postDelayed(mCheckRefRunnable,
                        mUpdateIntervalMillis);
            }

        }
    };

    /**
     * 不断地去检查是否达到自动更新的条件
     */
    private Runnable mCheckRefRunnable = new Runnable() {

        @Override
        public void run() {
            if (!mIsReadyToRefresh) {
                checkRefresh();
            }
            if (!mIsReadyToRefresh) {
                AutoLoadPull2RefreshListView.this.post(mLoadCheckRunnable);
            }
        }
    };

    public AutoLoadPull2RefreshListView(Context pContext, String title) {
        super(pContext);
        setRefreshName(title);
        init(pContext);
    }

    public AutoLoadPull2RefreshListView(Context pContext, AttributeSet pAttrs) {
        super(pContext, pAttrs);
        init(pContext);
    }

    public AutoLoadPull2RefreshListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
        super(pContext, pAttrs, pDefStyle);
        init(pContext);
    }

    /**
     * 初始化操作
     */
    private void init(Context pContext) {
        mSharedPrefName = this.getClass().toString();

        SharePreferenceUtil sharePreferenceUtil = new SharePreferenceUtil(getContext(),
                mSharedPrefName);
        mLastUpdateTimeMillis = sharePreferenceUtil.getLong(LAST_SAVE_TIME, -1);

        setCacheColorHint(pContext.getResources().getColor(R.color.transparent));
        setOnLongClickListener(null);
        mInflater = LayoutInflater.from(pContext);

        addHeadView();

        setOnScrollListener(this);

        initPullImageAnimation(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        saveRefreshTime();
        removeCallbacks(mLoadCheckRunnable);
        removeCallbacks(mCheckRefRunnable);
        mIsReadyToRefresh = false;
    }

    public void saveRefreshTime() {
        SharePreferenceUtil _SPrefUtil = new SharePreferenceUtil(getContext(), mSharedPrefName);
        _SPrefUtil.putLong(LAST_SAVE_TIME, mLastUpdateTimeMillis);
        _SPrefUtil.commit();
    }

    /**
     * 如果onFocusChanged方法正在刷新的话， onWindowFocusChanged就不用去重复刷新了。
     */
    private boolean mIsFocusedRefDone = true;

    @Override
    protected void onFocusChanged(boolean pGainFocus, int pDirection, Rect pPreviouslyFocusedRect) {
        super.onFocusChanged(pGainFocus, pDirection, pPreviouslyFocusedRect);

        if (!mIsDoRefreshOnUIChanged) {
            return;
        }

        if (pGainFocus) {
            if (mIsReadyToRefresh) {
                mIsFocusedRefDone = false;
                pull2RefreshManually();
            }
            removeCallbacks(mLoadCheckRunnable);
            removeCallbacks(mCheckRefRunnable);

        } else {
            if (!mIsReadyToRefresh) {
                checkRefresh();
            }
            if (!mIsReadyToRefresh) {
                AutoLoadPull2RefreshListView.this.post(mLoadCheckRunnable);
            }
        }

    }

    @Override
    public void onWindowFocusChanged(boolean pHasWindowFocus) {
        super.onWindowFocusChanged(pHasWindowFocus);
        if (mIsDoRefreshOnUIChanged && pHasWindowFocus && mIsReadyToRefresh && mIsFocusedRefDone
                && isFocused()) {
            pull2RefreshManually();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!mIsReadyToRefresh) {
            checkRefresh();
        }

        if (mIsDoRefreshOnUIChanged && !mIsReadyToRefresh) {
            AutoLoadPull2RefreshListView.this.post(mLoadCheckRunnable);
        }
    }

    /**
     * 添加下拉刷新的HeadView
     */
    private void addHeadView() {
        mHeadRootView = (LinearLayout) mInflater.inflate(R.layout.pull_to_refresh_head, null);

        mArrowImageView = (ImageView) mHeadRootView.findViewById(R.id.head_arrowImageView);
        mArrowImageView.setMinimumWidth(70);
        mArrowImageView.setMinimumHeight(50);
        mProgressBar = (ProgressBar) mHeadRootView.findViewById(R.id.head_progressBar);
        mTipsTextView = (TextView) mHeadRootView.findViewById(R.id.head_tipsTextView);
        mLastUpdatedTextView = (TextView) mHeadRootView.findViewById(R.id.head_lastUpdatedTextView);

        measureView(mHeadRootView);
        mHeadViewHeight = mHeadRootView.getMeasuredHeight();
        mHeadViewWidth = mHeadRootView.getMeasuredWidth();

        addHeaderView(mHeadRootView, null, false);
    }

    private String getLastUpdatedText(final long pTimeDiffSeconds) {
        String _Text = "从未更新";

        if (pTimeDiffSeconds < 60) {
            // 小于一分钟
            _Text = pTimeDiffSeconds + "秒前更新";
        } else if (pTimeDiffSeconds >= (1 * 60) && pTimeDiffSeconds < (60 * 60)) {
            // 大于等于1分钟，小于一小时。
            long _MinNum = pTimeDiffSeconds / 60;
            _Text = _MinNum + "分钟前更新";
        } else if (pTimeDiffSeconds >= (60 * 60) && pTimeDiffSeconds < (24 * 60 * 60)) {
            // 大于等于一小时
            long _HourNum = pTimeDiffSeconds / (60 * 60);
            _Text = _HourNum + "小时前更新";
        } else if (pTimeDiffSeconds >= (24 * 60 * 60)) {
            // 大于等于一小时
            long _DayNum = pTimeDiffSeconds / (24 * 60 * 60);
            _Text = _DayNum + "天前更新";
        } else if (pTimeDiffSeconds >= 3 * 24 * 60 * 60) {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            _Text = "最近一次更新于: " + smf.format(pTimeDiffSeconds);
        }
        return _Text;
    }

    /**
     * 添加加载更多FootView
     */
    private void addFooterView() {
        mEndRootView = mInflater.inflate(R.layout.pull_to_refresh_load_more, null);
        mEndRootView.setVisibility(View.VISIBLE);
        mEndLoadProgressBar = (ProgressBar) mEndRootView
                .findViewById(R.id.pull_to_refresh_progress);
        mEndLoadTipsTextView = (TextView) mEndRootView.findViewById(R.id.load_more);
        mEndRootView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCanLoadMore) {
                    if (mCanRefresh) {
                        // 当可以下拉刷新时，如果FootView没有正在加载，并且HeadView没有正在刷新，才可以点击加载更多。
                        if (mEndState != ENDINT_LOADING && mHeadState != REFRESHING) {
                            mEndState = ENDINT_LOADING;
                            onLoadMore();
                        }
                    } else if (mEndState != ENDINT_LOADING) {
                        // 当不能下拉刷新时，FootView不正在加载时，才可以点击加载更多。
                        mEndState = ENDINT_LOADING;
                        onLoadMore();
                    }
                }
            }
        });

        addFooterView(mEndRootView);

        if (mIsAutoLoadMore) {
            mEndState = ENDINT_AUTO_LOAD_DONE;
        } else {
            mEndState = ENDINT_MANUAL_LOAD_DONE;
        }
    }

    /**
     * 实例化下拉刷新的箭头的动画效果
     * 
     * @param pAnimDuration 动画运行时长
     */
    private void initPullImageAnimation(final int pAnimDuration) {

        int _Duration;

        if (pAnimDuration > 0) {
            _Duration = pAnimDuration;
        } else {
            _Duration = 250;
        }

        Interpolator _Interpolator = new LinearInterpolator();

        mArrowAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mArrowAnim.setInterpolator(_Interpolator);
        mArrowAnim.setDuration(_Duration);
        mArrowAnim.setFillAfter(true);

        mArrowReverseAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mArrowReverseAnim.setInterpolator(_Interpolator);
        mArrowReverseAnim.setDuration(_Duration);
        mArrowReverseAnim.setFillAfter(true);
    }

    /**
     * 测量HeadView宽高
     */
    private void measureView(View pChild) {
        ViewGroup.LayoutParams p = pChild.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;

        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        pChild.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 为了判断滑动到ListView底部没
     */
    @Override
    public void onScroll(AbsListView pView, int pFirstVisibleItem, int pVisibleItemCount,
            int pTotalItemCount) {
        mFirstItemIndex = pFirstVisibleItem;
        mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
        mCount = pTotalItemCount - 2;
        if (pTotalItemCount > pVisibleItemCount) {
            mEnoughCount = true;
        } else {
            mEnoughCount = false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView pView, int pScrollState) {
        if (mCanLoadMore) {// 存在加载更多功能
            if (mLastItemIndex == mCount && pScrollState == SCROLL_STATE_IDLE) {
                //SCROLL_STATE_IDLE=0，滑动停止
                if (mEndState != ENDINT_LOADING) {
                    if (mIsAutoLoadMore) {// 自动加载更多，我们让FootView显示 “更多”
                        if (mCanRefresh) {
                            // 存在下拉刷新并且HeadView没有正在刷新时，FootView可以自动加载更多。
                            if (mHeadState != REFRESHING) {
                                // FootView显示 : 更    多  ---> 加载中...
                                mEndState = ENDINT_LOADING;
                                onLoadMore();
                                changeEndViewByState();
                            }
                        } else {// 没有下拉刷新，我们直接进行加载更多。
                            // FootView显示 : 更    多  ---> 加载中...
                            mEndState = ENDINT_LOADING;
                            onLoadMore();
                            changeEndViewByState();
                        }
                    } else {// 不是自动加载更多，我们让FootView显示 “点击加载”
                        // FootView显示 : 点击加载  ---> 加载中...
                        mEndState = ENDINT_MANUAL_LOAD_DONE;
                        changeEndViewByState();
                    }
                }
            }
        } else if (mEndRootView != null && mEndRootView.getVisibility() == VISIBLE) {
            // 突然关闭加载更多功能之后，我们要移除FootView。
            mEndRootView.setVisibility(View.GONE);
            removeFooterView(mEndRootView);
        }
    }

    /**
     * 改变加载更多状态
     */
    private void changeEndViewByState() {
        if (mCanLoadMore) {
            //允许加载更多
            switch (mEndState) {
                case ENDINT_LOADING://刷新中

                    // 加载中...
                    if (mEndLoadTipsTextView.getText().equals(R.string.p2refresh_doing_end_refresh)) {
                        break;
                    }
                    mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
                    mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                    mEndLoadProgressBar.setVisibility(View.VISIBLE);
                    break;
                case ENDINT_MANUAL_LOAD_DONE:// 手动刷新完成

                    // 点击加载
                    mEndLoadTipsTextView.setText(R.string.p2refresh_end_click_load_more);
                    mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                    mEndLoadProgressBar.setVisibility(View.GONE);

                    mEndRootView.setVisibility(View.VISIBLE);
                    break;
                case ENDINT_AUTO_LOAD_DONE:// 自动刷新完成

                    // 更    多
                    mEndLoadTipsTextView.setText(R.string.p2refresh_end_load_more);
                    mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                    mEndLoadProgressBar.setVisibility(View.GONE);

                    mEndRootView.setVisibility(View.VISIBLE);
                    break;
                default:

                    break;
            }
        }
    }

    /**
     * 真正地回调正在刷新方法...
     */
    public void pull2RefreshManually() {
        if (mHeadState == REFRESHING) {
            onRefresh();

            mIsRecored = false;
            mIsBack = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mCanRefresh) {
            if (mCanLoadMore && mEndState == ENDINT_LOADING) {
                // 如果存在加载更多功能，并且当前正在加载中，默认不允许下拉刷新，必须加载完毕后下拉刷新才能使用。
                return super.onTouchEvent(event);
            }

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    if (mFirstItemIndex == 0 && !mIsRecored) {
                        mIsRecored = true;
                        mStartY = (int) event.getY();
                    } else if (mFirstItemIndex == 0 && mIsRecored) {
                        mStartY = (int) event.getY();
                    }

                    break;

                case MotionEvent.ACTION_UP:

                    if (mHeadState != REFRESHING) {

                        if (mHeadState == DONE) {

                        }
                        if (mHeadState == PULL_TO_REFRESH) {
                            // 在松手的时候，如果HeadView显示下拉刷新，那就恢复原状态。
                            mHeadState = DONE;
                            changeHeadViewByState();
                        }
                        if (mHeadState == RELEASE_TO_REFRESH) {
                            // 在松手的时候，如果HeadView显示松开刷新，那就显示正在刷新。
                            mHeadState = REFRESHING;
                            changeHeadViewByState();
                            onRefresh();
                        }
                    }

                    mIsRecored = false;
                    mIsBack = false;

                    break;

                case MotionEvent.ACTION_MOVE:

                    int _TempY = (int) event.getY();

                    if (!mIsRecored && mFirstItemIndex == 0) {
                        mIsRecored = true;
                        mStartY = _TempY;
                    }

                    if (mHeadState != REFRESHING && mIsRecored) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，
                        // 否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                        // 可以松手去刷新了
                        if (mHeadState == RELEASE_TO_REFRESH) {

                            setSelection(0);

                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((_TempY - mStartY) / RATIO < mHeadViewHeight)
                                    && (_TempY - mStartY) > 0) {
                                mHeadState = PULL_TO_REFRESH;
                                changeHeadViewByState();
                            }
                            // 一下子推到顶了
                            else if (_TempY - mStartY <= 0) {
                                mHeadState = DONE;
                                changeHeadViewByState();
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (mHeadState == PULL_TO_REFRESH) {

                            setSelection(0);

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((_TempY - mStartY) / RATIO >= mHeadViewHeight) {
                                mHeadState = RELEASE_TO_REFRESH;
                                mIsBack = true;
                                changeHeadViewByState();
                            } else if (_TempY - mStartY <= 0) {
                                mHeadState = DONE;
                                changeHeadViewByState();
                            }
                        }

                        if (mHeadState == DONE) {
                            if (_TempY - mStartY > 0) {
                                mHeadState = PULL_TO_REFRESH;
                                changeHeadViewByState();
                            }
                        }

                        if (mHeadState == PULL_TO_REFRESH) {
                            mHeadRootView.setPadding(0, -1 * mHeadViewHeight + (_TempY - mStartY)
                                    / RATIO, 0, 0);

                        }

                        if (mHeadState == RELEASE_TO_REFRESH) {
                            mHeadRootView.setPadding(0, (_TempY - mStartY) / RATIO
                                    - mHeadViewHeight, 0, 0);
                        }
                    }
                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当HeadView状态改变时候，调用该方法，以更新界面
     */
    private void changeHeadViewByState() {
        switch (mHeadState) {
            case RELEASE_TO_REFRESH:
                //			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  RELEASE_TO_REFRESH");
                mArrowImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTipsTextView.setVisibility(View.VISIBLE);
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(mArrowAnim);
                // 松开刷新
                mTipsTextView.setText(R.string.p2refresh_release_refresh);

                break;
            case PULL_TO_REFRESH:
                //			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  PULL_TO_REFRESH");
                mProgressBar.setVisibility(View.GONE);
                mTipsTextView.setVisibility(View.VISIBLE);
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                if (mLastUpdateTimeMillis == -1) {
                    mLastUpdatedTextView.setText("从未更新过");
                } else {
                    long _TimeDiffSeconds = (System.currentTimeMillis() - mLastUpdateTimeMillis) / 1000;
                    mLastUpdatedTextView.setText(getLastUpdatedText(_TimeDiffSeconds));
                }

                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (mIsBack) {
                    mIsBack = false;
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mArrowReverseAnim);
                    // 下拉刷新
                    mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
                } else {
                    // 下拉刷新
                    mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
                }
                break;

            case REFRESHING:
                //			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  REFRESHING");

                changeHeaderViewRefreshState();
                break;
            case DONE:
                //			MyLogger.showLogWithLineNum(3, "changeHeaderViewByState ===>  DONE");

                mHeadRootView.setPadding(0, -1 * mHeadViewHeight, 0, 0);

                mProgressBar.setVisibility(View.GONE);
                mArrowImageView.clearAnimation();
                mArrowImageView.setImageResource(R.drawable.arrow);
                // 下拉刷新
                mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                break;
        }
    }

    /**
     * 改变HeadView在刷新状态下的显示
     */
    private void changeHeaderViewRefreshState() {
        mHeadRootView.setPadding(0, 0, 0, 0);

        // 华生的建议： 实际上这个的setPadding可以用动画来代替。我没有试，但是我见过。其实有的人也用Scroller可以实现这个效果，
        // 我没时间研究了，后期再扩展，这个工作交给小伙伴你们啦~ 如果改进了记得发到我邮箱噢~
        // 本人邮箱： xxzhaofeng5412@gmail.com

        mProgressBar.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.GONE);
        // 正在刷新...
        mTipsTextView.setText(R.string.p2refresh_doing_head_refresh);
        mLastUpdatedTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 下拉刷新监听接口
     */
    public interface OnRefreshListener {
        public void onRefresh();
    }

    /**
     * 加载更多监听接口
     */
    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
        if (pRefreshListener != null) {
            mRefreshListener = pRefreshListener;
            mCanRefresh = true;
        }
    }

    public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
        if (pLoadMoreListener != null) {
            mLoadMoreListener = pLoadMoreListener;
            mCanLoadMore = true;
            if (mCanLoadMore && getFooterViewsCount() == 0) {
                addFooterView();
            }
        }
    }

    /**
     * 正在下拉刷新
     */
    private void onRefresh() {
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    /**
     * 下拉刷新完成
     */
    public void onRefreshComplete() {

        mLastUpdateTimeMillis = System.currentTimeMillis();
        saveRefreshTime();
        mHeadState = DONE;
        // XXX前更新
        mLastUpdatedTextView.setText("1分钟前更新");
        changeHeadViewByState();

        mIsReadyToRefresh = false;

        // 下拉刷新后是否显示第一条Item
        if (mIsMoveToFirstItemAfterRefresh) {
            mFirstItemIndex = 0;
            setSelection(0);
        }

        mIsFocusedRefDone = true;
    }

    /**
     * 正在加载更多，FootView显示 ： 加载中...
     */
    private void onLoadMore() {
        if (mLoadMoreListener != null) {
            // 加载中...
            mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
            mEndLoadTipsTextView.setVisibility(View.VISIBLE);
            mEndLoadProgressBar.setVisibility(View.VISIBLE);

            mLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 加载更多完成
     */
    public void onLoadMoreComplete() {
        if (mIsAutoLoadMore) {
            mEndState = ENDINT_AUTO_LOAD_DONE;
        } else {
            mEndState = ENDINT_MANUAL_LOAD_DONE;
        }
        changeEndViewByState();
    }

    @Override
    public void checkRefresh() {

        long _LastSaveTimeMillis = mLastUpdateTimeMillis;

        //		System.out.println(mLabel + "mLastUpdateTimeMillis = "+mLastUpdateTimeMillis);

        if (mIsDoRefreshOnUIChanged) {

            mProgressBar.setVisibility(View.VISIBLE);
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.GONE);
            // 正在刷新...
            mTipsTextView.setText(R.string.p2refresh_doing_head_refresh);

            mLastComparedDiffMillis = System.currentTimeMillis() - _LastSaveTimeMillis;
            long _TimeDiffSeconds = mLastComparedDiffMillis / 1000;
            //			System.out.println(mLabel + "___TimeDiffSeconds = "+_TimeDiffSeconds);

            if (_LastSaveTimeMillis == -1) {
                //				System.out.println(mLabel + "__第一次进入应用,显示正在刷新。 _LastSaveTime == -1........");
                // 第一次进入应用,显示正在刷新。
                mLastUpdatedTextView.setText("N天前更新");
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                mHeadRootView.setPadding(0, 0, 0, 0);
                mHeadRootView.invalidate();
                mHeadState = REFRESHING;
                mIsReadyToRefresh = true;
                setSelection(0);
            } else if (_TimeDiffSeconds >= (mUpdateIntervalMillis / 1000)) {
                //				System.out.println(mLabel + "__直接显示正在刷新 _LastSaveTime >= mUpdateIntervalMillis ........");
                // 直接显示正在刷新
                mLastUpdatedTextView.setText(getLastUpdatedText(_TimeDiffSeconds));
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                mHeadRootView.setPadding(0, 0, 0, 0);
                mHeadRootView.invalidate();
                mHeadState = REFRESHING;
                mIsReadyToRefresh = true;
                setSelection(0);
            } else {
                //				System.out.println(mLabel + " 不显示正在刷新 __TimeDiffSeconds < mUpdateIntervalMillis ........");
                // 不显示正在刷新
                mLastUpdatedTextView.setText(getLastUpdatedText(_TimeDiffSeconds));
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                mHeadRootView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
                mHeadRootView.invalidate();

                mHeadState = DONE;
                changeHeadViewByState();
            }
        } else {
            //			System.out.println(mLabel + " 没有开启自动下拉刷新........");
            mHeadRootView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
            mHeadRootView.invalidate();

            mHeadState = DONE;
            changeHeadViewByState();
        }
    }

}
