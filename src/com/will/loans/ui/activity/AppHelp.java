package com.will.loans.ui.activity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.will.loans.R;
import com.will.loans.utils.Toaster;

public class AppHelp extends BaseActivity {
    ClipboardManager cmb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//        cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        init();

    }

    private void init() {
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView)findViewById(R.id.title_back)).setText("返回");
        ((TextView)findViewById(R.id.title_tv)).setText("客服中心");
        findViewById(R.id.call_phone).setOnClickListener(this);
        findViewById(R.id.help_jijin).setOnClickListener(this);
        findViewById(R.id.help_calcul).setOnClickListener(this);
        findViewById(R.id.help_calcul_method).setOnClickListener(this);
        findViewById(R.id.help_point).setOnClickListener(this);
        findViewById(R.id.about_morehelp).setOnClickListener(this);
        findViewById(R.id.about_wx).setOnClickListener(this);
        findViewById(R.id.help_qq).setOnClickListener(this);
        findViewById(R.id.help_weibo).setOnClickListener(this);
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()){
            case R.id.call_phone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:40087009888"));
                startActivity(intent);
                break;
            case R.id.help_jijin:
                Intent jijin = new Intent(AppHelp.this,WebBrowser.class);
                jijin.putExtra(WebBrowser.URL_STRING,"");
                AppHelp.this.startActivity(jijin);
                break;
            case R.id.help_calcul:
                Intent calcul = new Intent(AppHelp.this,WebBrowser.class);
                calcul.putExtra(WebBrowser.URL_STRING,"");
                AppHelp.this.startActivity(calcul);
                break;
            case R.id.help_calcul_method:
                Intent method = new Intent(AppHelp.this,WebBrowser.class);
                method.putExtra(WebBrowser.URL_STRING,"");
                AppHelp.this.startActivity(method);
                break;
            case R.id.help_point:
                Intent point = new Intent(AppHelp.this,WebBrowser.class);
                point.putExtra(WebBrowser.URL_STRING,"");
                AppHelp.this.startActivity(point);
                break;
            case R.id.about_morehelp:
                Intent morehelp = new Intent(AppHelp.this,WebBrowser.class);
                morehelp.putExtra(WebBrowser.URL_STRING,"");
                AppHelp.this.startActivity(morehelp);
                break;
            case R.id.about_wx:
//                ClipData clip = ClipData.newPlainText("txt","贷贷通");
//                cmb.setPrimaryClip(clip);
                Toaster.showShort(AppHelp.this,"已复制到剪切板！");
                break;
            case R.id.help_qq:
                Toaster.showShort(AppHelp.this,"已复制到剪切板！");
//                ClipData clip1 = ClipData.newPlainText("txt","88888888");
//                cmb.setPrimaryClip(clip1);
                break;
            case R.id.help_weibo:
                Toaster.showShort(AppHelp.this,"已复制到剪切板！");
//                ClipData clip2 = ClipData.newPlainText("txt","贷贷通");
//                cmb.setPrimaryClip(clip2);
                break;
        }
    }
}
