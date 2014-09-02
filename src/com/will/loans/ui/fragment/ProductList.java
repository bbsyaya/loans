
package com.will.loans.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.will.loans.R;
import com.will.loans.weight.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends BaseFragment {
    private AQuery mAQuery = new AQuery(getActivity());

    private PagerSlidingTabStrip mSlidingTabStrip;

    private List<Fragment> list;

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
        initView(view);
    }

    private void initView(View view) {
        list = new ArrayList<Fragment>();
        mSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.pager_slid);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        list.add(new ProductFirst());
        list.add(new ProductListCoins());
        list.add(new ProductListOthers());
        mListPagerAdapter = new ListPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mListPagerAdapter);
        mSlidingTabStrip.setViewPager(mViewPager);
        mAQuery.id(R.id.title_tv).text("产品列表");
        mAQuery.id(R.id.title_btn_left).text("登录");
        mAQuery.id(R.id.title_btn_right).text("刷新");
    }

    private class ListPagerAdapter extends FragmentPagerAdapter {
        String[] titles = {
                "稳盈贷", "货币基金", "其他产品"
        };

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

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
}
