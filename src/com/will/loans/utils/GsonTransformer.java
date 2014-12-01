
package com.will.loans.utils;

import android.util.Log;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson转化工具
 * 
 * @author yushan.peng
 * @since 2013-12-25
 */
public class GsonTransformer implements Transformer {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public <T> T transform(String url, Class<T> type, String encoding, byte[] data,
            AjaxStatus status) {
        try {
            return gson.fromJson(new String(data, encoding), type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
