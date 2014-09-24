
package com.will.loans.beans.json;

import com.will.loans.beans.bean.UserMsg;

public class UserInfoJson extends BaseJson {
    public UserMsg userMsg;

    @Override
    public String toString() {
        return "UserInfoJson [userMsg=" + userMsg + ", resultflag=" + resultflag + ", resultMsg="
                + resultMsg + "]";
    }

}
