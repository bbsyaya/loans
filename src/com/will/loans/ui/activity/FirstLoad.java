
package com.will.loans.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.will.loans.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: ys.peng Date: 13-11-8 Time: 下午2:44 To
 * change this template use File | Settings | File Templates.
 */
public class FirstLoad extends BaseActivity {
    private ViewPager pager;

    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_firstload);
        layoutInflater = LayoutInflater.from(this);
        init();
    }

    private void init() {
        int[] image = {
                R.drawable.splash_new_0, R.drawable.splash_new_1, R.drawable.splash_new_2,
                R.drawable.splash_new_3
        };
        List<View> list = new ArrayList<View>();
        for (int i = 0; i < image.length; i++) {
            View view = layoutInflater.inflate(R.layout.item_firstloading, null);
            ((ImageView) view.findViewById(R.id.loading_iv)).setImageResource(image[i]);
            if (i == image.length - 1) {
                ((Button) view.findViewById(R.id.loading_btn)).setVisibility(View.VISIBLE);
                ((Button) view.findViewById(R.id.loading_btn))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(FirstLoad.this,
                                        HomePage.class);
                                FirstLoad.this.startActivity(intent);
                                FirstLoad.this.finish();
                            }
                        });
            }

            list.add(view);
        }
        FirstLoadingPager firstLoadingPager = new FirstLoadingPager(list, this);
        pager = (ViewPager) findViewById(R.id.vp_help);
        pager.setAdapter(firstLoadingPager);
    }

    public class FirstLoadingPager extends PagerAdapter {
        private List<View> pageView;

        private Context context;

        public FirstLoadingPager(List<View> pageView, Context context) {
            this.pageView = pageView;
            this.context = context;
        }

        @Override
        public int getCount() {
            return pageView.size(); //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o; //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pageView.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pageView.get(position);
            container.addView(view);
            return view; //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
