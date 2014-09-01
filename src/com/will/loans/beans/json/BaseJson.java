
package com.will.loans.beans.json;

import com.will.loans.beans.bean.BaseBeans;

/**
 * Created with IntelliJ IDEA. User: ys.peng Date: 14-1-2 Time: 下午2:59 To change
 * this template use File | Settings | File Templates.
 */
public class BaseJson extends BaseBeans {
    protected String message;

    protected int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
