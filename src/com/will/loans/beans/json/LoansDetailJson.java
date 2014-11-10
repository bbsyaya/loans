package com.will.loans.beans.json;

import com.will.loans.beans.bean.ProDetail;
import com.will.loans.beans.bean.ProFundcomp;
import com.will.loans.beans.bean.ProInfo;
import com.will.loans.beans.bean.ProItems;

import java.util.List;

/**
 * Created by will on 11/7/14.
 */
public class LoansDetailJson extends BaseJson {
    //    {"resultMsg":"获取消息列表成功","resultflag":"0",
    public ProInfo proInfo;//":{"timeLimit":3,"wfsy":50,"startBuy":1,"extraPoint":0,"syms":"预期年化收益10%","percent":0,"proType":"WYD","state":1,"endTime":1416040164000,"tip":"热售","version":0,"profitCountMethod":0,"startTime":1415176164000,"id":13,"qrsy":5,"tipColor":"RED","inAmount":0,"proName":"贷贷通003号","totalAmount":5000,"repayDate":1415376000000,"nhsy":500},
    public ProDetail detail;//":{"proId":13,"id":7,"fundCompId":1,"avgMoney":0,"benxiDesc":"第三方本息担保","iconUrl":"","buyPerNum":0,"buyNum":0,"securityTip":"账户资金安全由中信银行监管","totalMoney":0,"companyDesc":"公司稳定状态中"},
    public ProFundcomp fundcomp;//":{"wfsy":1.1243,"weekRose":0.08,"detailDesc3":"交易全程受证监会监管，申购金额进入您在基金公司开立的账户，并仅可赎回至您本人的银行卡","yearRose":5.06,"detailDesc2":"2012年年化收益超过4.0%，长期稳健","yqnh":0,"qrnh":4.26,"saleComName":"好买基金","id":1,"sixMonRose":2.36,"threeMonRose":1.13,"comName":"工银货币","fundType":0,"monthRose":0.35,"detailDesc1":"基金规模234.32亿，不受市场波动影响的无风险投资品种占比达90%以上，确保投资安全稳健","fundScale":23431999500},
    public List<ProItems> items;//":[{"proId":13,"id":12,"itemName":"稳盈贷03号","itemDesc":"项目描述","itemUrl":"","itemIcon":""}]}
}
