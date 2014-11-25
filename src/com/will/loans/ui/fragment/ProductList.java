
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.utils.TranslateAnimationFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends BaseFragment implements OnPageChangeListener,
        OnCheckedChangeListener, OnClickListener {
    private AQuery mAQuery = new AQuery(getActivity());

    private List<Fragment> list;

    private ImageView glideBar;

    private int offset;

    private int currentPage;

    private ViewPager mViewPager;

    private ListPagerAdapter mListPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAQuery = new AQuery(getActivity(), view);
        initView(view);
    }

    private void initView(View view) {
        offset = calculateGlideOffset();
        list = new ArrayList<Fragment>();
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        glideBar = (ImageView) view.findViewById(R.id.iv_blue_glide);
        list.add(new ProductFirst());
//        list.add(new ProductListCoins());
        		list.add(new ProductListOthers());
        mListPagerAdapter = new ListPagerAdapter(getFragmentManager());
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(mListPagerAdapter);

        setTitleText(view, R.string.refresh, R.string.refresh, R.string.tab_produce_list);
        setTitleVisible(view, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
        ((Button) view.findViewById(R.id.title_btn_right)).setOnClickListener(this);

        ((RadioButton) view.findViewById(R.id.btn_radio_left)).setOnCheckedChangeListener(this);
        ((RadioButton) view.findViewById(R.id.btn_radio_center)).setOnCheckedChangeListener(this);
        ((RadioButton) view.findViewById(R.id.btn_radio_right)).setOnCheckedChangeListener(this);
    }

    private class ListPagerAdapter extends FragmentPagerAdapter {
        public ListPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

    }

    private void glideToTab(int currentTab) {
        if (currentPage == currentTab) {
            return;
        }

        TranslateAnimationFactory animationFactory = new TranslateAnimationFactory(currentPage,
                offset);
        Animation animation = animationFactory.createTranslateAnimation(currentTab);
        currentPage = currentTab;
        if (animation == null) {
            // Logger.e(this, "animation is null. primaryIndex:" + currentPage +
            // "  targetTab:" + currentTab);
            // Toaster.showShort(this, "animation is null. primaryIndex:" +
            // currentPage + "  targetTab:" + currentTab);
            return;
        }

        animation.setFillAfter(true);
        animation.setDuration(150);
        glideBar.startAnimation(animation);
    }

    /**
     * 计算切换页面时，上面黑色条形需要滑动的距离
     */
    private int calculateGlideOffset() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth / 2;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        glideToTab(arg0);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btn_radio_left:
                if (isChecked) {
                    changeStatus(0);
                }
                break;
            case R.id.btn_radio_center:
                if (isChecked) {
                    changeStatus(1);
                }
                break;
            case R.id.btn_radio_right:
                if (isChecked) {
                    changeStatus(2);
                }
                break;

            default:
                break;
        }

    }

    private void changeStatus(int index) {
        mViewPager.setCurrentItem(index);
        glideToTab(index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_btn_right:
                if (mViewPager.getCurrentItem() == 0) {
                    ((ProductFirst) mListPagerAdapter.getItem(mViewPager.getCurrentItem()))
                            .onRefresh();
                } else {
                    ((ProductListOthers) mListPagerAdapter.getItem(mViewPager.getCurrentItem()))
                            .onRefresh();
                }
                break;

            default:
                break;
        }

    }

}
