
package com.will.loans.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class TranslateAnimationFactory {

    private int primaryPage;

    private int offset;

    /**
     * @param primaryPage 原来的页面索引
     * @param offset 每个页面的偏移量
     */
    public TranslateAnimationFactory(int primaryPage, int offset) {
        this.primaryPage = primaryPage;
        this.offset = offset;
    }

    public Animation createTranslateAnimation(int targetPage) {
        Animation animation = null;
        int one = offset; //一倍滑动距离，既切换相邻页面所需滑动的距离
        int two = offset * 2;

        switch (targetPage) {
            case 0:
                if (primaryPage == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (primaryPage == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (primaryPage == 0) {
                    animation = new TranslateAnimation(0, one, 0, 0);
                } else if (primaryPage == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (primaryPage == 0) {
                    animation = new TranslateAnimation(0, two, 0, 0);
                } else if (primaryPage == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
        }

        return animation;
    }

}
