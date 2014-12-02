package com.will.loans.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.will.loans.R;
import org.w3c.dom.Text;

public class SelectBank extends BaseTextActivity implements AdapterView.OnItemClickListener{

    public static int REQUEST_CODE_BANK_ID = 1;
    public static String BANK_TITLE = "com.will.loans.select_bank";

    private String[] bank = {"兴业银行","招商银行","中国银行","农业银行","光大银行","工商银行","建设银行","平安银行","中国邮政储蓄银行"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);

        findViewById(R.id.title_tv).setOnClickListener(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.title_back)).setText(R.string.back);
        ((TextView) findViewById(R.id.title_tv)).setText("选择银行");
        ListView lv = (ListView) findViewById(R.id.select_bank_lv);

        MyAdapeter adapeter = new MyAdapeter();
        lv.setAdapter(adapeter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        setResult(REQUEST_CODE_BANK_ID,new Intent().putExtra(BANK_TITLE,bank[i]));
        finish();
    }

    class MyAdapeter extends BaseAdapter{

        @Override
        public int getCount() {
            return bank.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_text, null);
            }
            ((TextView)view.findViewById(R.id.tvbank_name)).setText(bank[i]);
            return view;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.title_back:
                setResult(REQUEST_CODE_BANK_ID,new Intent().putExtra(BANK_TITLE,bank[0]));
                finish();
                break;

        }
    }
}
