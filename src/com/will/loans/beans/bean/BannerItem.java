
package com.will.loans.beans.bean;

public class BannerItem extends BaseBeans {
    public String value;//: "http://app.longyinglicai.com/activity/jmh/jm.html",

    public String type;//: "ACTIVITY",

    public String url;//: "http://app.longyinglicai.com/activity/jmh/images/yyjm_banner_ios.png",

    public int orderNum;//: 0

    public int id;//":1,

    public String bannerName;//":"banner图片名称",

    public String bannerDesc;//":"banner图片描述",

    public Long createTime;//":1411315200000,

    public String bannerUrl;//"":"img/banner.jpg",

    public int status;//":0,

    public String contUrl;//":"test/banner.html"

    public BannerItem(String value, String type, String url, int orderNum) {
        super();
        this.value = value;
        this.type = type;
        this.url = url;
        this.orderNum = orderNum;
    }

}
