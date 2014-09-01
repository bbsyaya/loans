
package com.will.loans.utils;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA. User: ys.peng Date: 14-1-2 Time: 上午11:06 To
 * change this template use File | Settings | File Templates.
 */
public class DateUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getLastUpdatedText(long pTimeDiffSeconds) {
        String _Text = "N天前";
        pTimeDiffSeconds = (System.currentTimeMillis() - pTimeDiffSeconds) / 1000;
        if (pTimeDiffSeconds < 60) {
            // 小于一分钟
            //			_Text = "1分钟前更新";
            _Text = pTimeDiffSeconds + "秒前";
        } else if (pTimeDiffSeconds >= (1 * 60) && pTimeDiffSeconds < (60 * 60)) {
            // 大于等于1分钟，小于一小时。
            long _MinNum = pTimeDiffSeconds / 60;
            _Text = _MinNum + "分钟前";
        } else if (pTimeDiffSeconds >= (60 * 60) && pTimeDiffSeconds < (24 * 60 * 60)) {
            // 大于等于一小时
            long _HourNum = pTimeDiffSeconds / (60 * 60);
            _Text = _HourNum + "小时前";
        } else if (pTimeDiffSeconds >= (24 * 60 * 60)) {
            // 大于等于一小时
            long _DayNum = pTimeDiffSeconds / (24 * 60 * 60);
            _Text = _DayNum + "天前";
        }
        return _Text;
    }

    public static Long getDate() {
        Long nowDate = System.currentTimeMillis();
        return nowDate;
    }
}
