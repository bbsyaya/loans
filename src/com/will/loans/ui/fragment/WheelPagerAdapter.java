
package com.will.loans.ui.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.will.loans.beans.bean.BannerItem;
import com.will.loans.ui.activity.WebBrowser;

public class WheelPagerAdapter extends PagerAdapter {

	private Activity context;

	private List<View> pageViews;

	private List<BannerItem> wheel;

	public WheelPagerAdapter(Activity context, List<View> pageViews, List<BannerItem> wheel) {
		this.context = context;
		this.pageViews = pageViews;
		this.wheel = wheel;
	}

	@Override
	public int getCount() {
		return pageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(pageViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = pageViews.get(position);
		try {
			((ViewPager) container).addView(view);
		} catch (Exception e) {
		}

		if (position == pageViews.size() - 1) {
			setListener(view, wheel.get(0));
		} else if (position == 0) {
			setListener(view, wheel.get(wheel.size() - 1));
		} else {
			setListener(view, wheel.get(position - 1));
		}

		return view;
	}

	private void setListener(View view, final BannerItem videoItem) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				jumpTopVedioDetail(videoItem);
			}
		});
	}

	private void jumpTopVedioDetail(BannerItem videoItem) {
		Intent intent = new Intent(context, WebBrowser.class);
		intent.putExtra("value", videoItem.contUrl);
		context.startActivity(intent);
	}
}
