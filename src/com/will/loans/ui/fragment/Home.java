
package com.will.loans.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.will.loans.R;
import com.will.loans.beans.bean.BannerItem;
import com.will.loans.beans.json.BannerInfo;
import com.will.loans.constant.ServerInfo;
import com.will.loans.ui.activity.LoansDetail;
import com.will.loans.ui.activity.Register;
import com.will.loans.ui.activity.WebBrowser;
import com.will.loans.utils.ScreenProperties;
import com.will.loans.utils.SharePreferenceUtil;
import com.will.loans.weight.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Home extends BaseFragment implements OnClickListener {

    private PullToRefreshScrollView homePRSV;

    private ProgressWheel pwTwo;

    private ViewPager viewPager;

    private RadioGroup groupPoint;

    private TextView mLeftBtn, title, persent, month, limit, pay;

    private Button enterBtn;

    private List<BannerItem> wheel;

    boolean wheelRunning;

    int wheelProgress = 0, pieProgress = 0;

    private int currentPage;

    private AQuery aq;

    private final int MESSAGE_WHAT = 0;

    private final long FLING_PAGE_INTERVAL = 3000;

    List<JSONObject> products = new ArrayList<JSONObject>();

    private View view;

    private int mProgress = 0;

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (currentPage == wheel.size() + 2) {
                currentPage = 2;
            }
            viewPager.setCurrentItem(currentPage);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SharePreferenceUtil.getUserPref(getActivity()).getToken().equals("")) {
            mLeftBtn.setText("刷新");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aq = new AQuery(getActivity(), view);
        this.view = view;
        setTitleText(view, R.string.daidaitong, R.string.login, R.string.tab_home);
        mLeftBtn = (TextView) view.findViewById(R.id.title_btn_right);
        setTitleVisible(view, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
        ((Button) view.findViewById(R.id.title_btn_right)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.title_btn_left)).setOnClickListener(this);
        enterBtn = ((Button) view.findViewById(R.id.enterBtn));
        enterBtn.setOnClickListener(this);
        pwTwo = (ProgressWheel) view.findViewById(R.id.progress_bar_two);
        groupPoint = (RadioGroup) view.findViewById(R.id.rg_points);

        homePRSV = (PullToRefreshScrollView) view.findViewById(R.id.homePRSV);
        viewPager = (ViewPager) view.findViewById(R.id.vp_ads);

        initRefreshView();

        enterBtn.setEnabled(false);
        getDate(true);
        getBanner();
    }

    private void getBanner() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.GETBANNER, params, BannerInfo.class, new AjaxCallback<BannerInfo>() {
            @Override
            public void callback(String url, BannerInfo json, AjaxStatus status) {
                if (json == null) {
                    return;
                }
                wheel = json.banners;
                initViewPager();
                homePRSV.onRefreshComplete();
            }

        });

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            wheelRunning = true;
            while (wheelProgress < mProgress) {
                pwTwo.incrementProgress();
                wheelProgress++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            wheelRunning = false;
        }
    };

    private void getDate(final boolean isRefresh) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", System.currentTimeMillis());
            jo.put("pageNum", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.PROLIST, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json == null) {
                    return;
                }
                homePRSV.onRefreshComplete();
                Log.e("11", json.toString());
                enterBtn.setEnabled(true);
                JSONArray ja = null;
                ja = json.optJSONArray("proList");
                for (int i = 0; i < ja.length(); i++) {
                    products.add(ja.optJSONObject(i));
                }
                updateView();
            }

        });

    }

    private void updateView() {
        JSONObject jo = products.get(0);
        ((TextView) view.findViewById(R.id.tv_title)).setText(jo.optString("proName") + "");
        ((TextView) view.findViewById(R.id.home_year_num)).setText(jo.optInt("percent") + "%");
        ((TextView) view.findViewById(R.id.home_tv_month)).setText(jo.optInt("timeLimit") + "");
        ((TextView) view.findViewById(R.id.home_tv_limit)).setText(jo.optInt("startBuy") + "");
        ((TextView) view.findViewById(R.id.percentTV)).setText(jo.optInt("percent") + "");
        ((TextView) view.findViewById(R.id.home_year_num)).setText(jo.optDouble("nhsy") + "");
        // setTextView(R.id.nameTV, jo.optString("proName") + "", "");
        // setTextView(R.id.home_year_num, jo.optInt("percent") + "", "");
        // setTextView(R.id.home_tv_month, jo.optInt("timeLimit") + "", "");
        // setTextView(R.id.home_tv_limit, jo.optInt("startBuy") + "", "");
        // setTextView(R.id.percentTV, jo.optInt("percent") + "", "");
        // setTextView(R.id.home_year_num, jo.optDouble("nhsy") + "", "");
        // pwTwo.setProgress((int) (jo.optInt("percent") * 3.6));
        wheelProgress = 0;
        pwTwo.setProgress(0);
        mProgress = (int) (jo.optInt("percent") * 3.6);
        new Thread(r).start();
    }

    /**
     * 通过id设置text
     * <p>
     * 若text为null或"",则使用or
     * 
     * @param resId
     * @param text
     * @param or
     */
    private void setTextView(final int resId, String text, String or) {
        final String content;
        if (TextUtils.isEmpty(text)) {
            content = or;
        } else {
            content = text;
        }
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ((TextView) view.findViewById(resId)).setText(content);
            }
        });
    }

    /**
     * 初始化下拉刷新監聽器
     */
    private void initRefreshView() {
        homePRSV.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDate(true);
            }
        });
    }

    private void initViewPager() {
        viewPager = (ViewPager) getView().findViewById(R.id.vp_ads);

        initViewPagerBound();

        // wheel.add(new
        // BannerItem("http://app.longyinglicai.com/activity/jmh/jm.html",
        // "ACTIVITY",
        // "http://app.longyinglicai.com/activity/jmh/images/yyjm_banner_ios.png",
        // 0));
        // wheel.add(new
        // BannerItem("http://www.yingyinglicai.com/front/xxnj.htm", "ACTIVITY",
        // "http://app.longyinglicai.com/banner/8new-ios.jpg", 1));
        // wheel.add(new
        // BannerItem("http://app.longyinglicai.com/activity/ajia.html",
        // "ACTIVITY",
        // "http://app.longyinglicai.com/banner/APlus-android.png", 0));
        // wheel.add(new
        // BannerItem("http://app.longyinglicai.com/activity/jgxy1.html",
        // "ACTIVITY",
        // "http://app.longyinglicai.com/banner/jgxy1-ios.png", 0));
        if (wheel == null) {
            return;
        }

        LinkedList<View> pageViews = new LinkedList<View>();
        addPageViews(pageViews);
        addPointView(pageViews.size() - 2);

        viewPager.setAdapter(new WheelPagerAdapter(getActivity(), pageViews, wheel));
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // renderBrief(arg0);
                adjustWheel(arg0);
                adjustPoint(arg0);
                sendMessageForNextPage(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        viewPager.setCurrentItem(1);

        currentPage = 2;
        handler.sendEmptyMessageDelayed(MESSAGE_WHAT, FLING_PAGE_INTERVAL);

    }

    private void initViewPagerBound() {
        // 先算出图片长在屏幕中占多少英寸
        float xInch = (ScreenProperties.getScreenWidth() / ScreenProperties.getXdpi());
        // Log.e("xInch", xInch + "Inch");
        // 根据图片宽长比例算出高应该占多少英寸
        double yInch = xInch * (15 / 32.0);
        // Log.e("yInch", yInch + "Inch");
        // 再根据Y轴方向上每英寸多少像素算出图片高应该有多少像素
        int viewPagerHeight = (int) (ScreenProperties.getXdpi() * yInch);
        // Log.e("viewPagerWidth", ScreenProperties.getScreenWidth() + "");
        // Log.e("viewPagerHeight", viewPagerHeight + "");
        RelativeLayout viewPagerBound = (RelativeLayout) getView()
                .findViewById(R.id.rlyt_viewpager);
        viewPagerBound.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                viewPagerHeight));
    }

    private void addPageViews(LinkedList<View> pageViews) {
        for (BannerItem videoItem : wheel) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ScaleType.CENTER_CROP);
            aq.id(imageView).image(videoItem.bannerUrl, true, true);
            pageViews.add(imageView);
        }

        for (int i = 0; i < wheel.size(); i++) {
            if (i == 0) {
                ImageView imageView = extraImageView(i);
                pageViews.addLast(imageView);
            }

            if (i == wheel.size() - 1) {
                ImageView imageView = extraImageView(i);
                pageViews.addFirst(imageView);
            }
        }
    }

    private ImageView extraImageView(int i) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ScaleType.CENTER_CROP);
        aq.id(imageView).image(wheel.get(i).bannerUrl, true, true);

        return imageView;

    }

    private void addPointView(int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                RadioButton radBtn = new RadioButton(getActivity());
                radBtn.setClickable(false);
                int width = (int) ScreenProperties.getScreenDensity() * 25;
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(width,
                        LayoutParams.WRAP_CONTENT);
                radBtn.setButtonDrawable(R.drawable.page_control_sel);
                groupPoint.addView(radBtn, params);
            }
        }

        View v = groupPoint.getChildAt(0);
        RadioButton radioBtn = (RadioButton) v;
        radioBtn.setChecked(true);
    }

    private void adjustWheel(int currentPage) {
        if (currentPage == wheel.size() + 1) {
            viewPager.setCurrentItem(1, false);
        }

        if (currentPage == 0) {
            viewPager.setCurrentItem(wheel.size(), false);
        }
    }

    private void adjustPoint(int pageIndex) {
        if (pageIndex == 0 || pageIndex == wheel.size() + 1) {
            return;
        }

        View child = groupPoint.getChildAt(pageIndex - 1);
        if (child instanceof RadioButton) {
            RadioButton radBtn = (RadioButton) child;
            radBtn.setChecked(true);
        }
    }

    private void sendMessageForNextPage(int nowPage) {
        if (handler.hasMessages(MESSAGE_WHAT)) {
            handler.removeMessages(MESSAGE_WHAT);
        }

        currentPage = nowPage + 1;
        handler.sendEmptyMessageDelayed(MESSAGE_WHAT, FLING_PAGE_INTERVAL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_btn_right:
                if (SharePreferenceUtil.getUserPref(getActivity()).getToken().equals("")) {
                    startActivity(new Intent(getActivity(), Register.class));
                } else {
                    homePRSV.setRefreshing();
                }
                break;
            case R.id.title_btn_left:
                getActivity().startActivity(
                        new Intent().setClass(getActivity(), WebBrowser.class).putExtra(
                                WebBrowser.URL_STRING, "http://www.baidu.com"));
                break;

            case R.id.enterBtn:
                LoansDetail.pro = products.get(0);
                jump2Activity(new LoansDetail());
                break;
            default:
                break;
        }
    }
}
