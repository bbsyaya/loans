
package com.will.loans.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.will.loans.R;

public class WebBrowser extends BaseActivity {
    private WebView mWeb;

    public static final String URL_STRING = "url_string";
    public static final String URL_ITLE = "url_title";

    private String mUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getExtras().getString(URL_STRING);
        title = getIntent().getExtras().getString(URL_ITLE)!=null?getIntent().getExtras().getString(URL_ITLE):"信息详情";
        setContentView(R.layout.web_browser);
        ((TextView) findViewById(R.id.title_tv)).setText(title);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        mWeb = (WebView) findViewById(R.id.web_view);
        mWeb.setWebChromeClient(new MyWebChrome());
        mWeb.setWebViewClient(new MyWebViewClient());
        mWeb.loadUrl(mUrl);
    }

    class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            super.onProgressChanged(view, newProgress);
        }

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onLoadResource(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

    }

}
