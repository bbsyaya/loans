package com.will.loans.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.will.loans.R;
import com.will.loans.constant.ServerInfo;
import com.will.loans.utils.Toaster;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 11/28/14.
 */
public class AppealActivity extends BaseTextActivity {
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appeal_activyt);

        aq = new AQuery(this);
        ((TextView) findViewById(R.id.title_tv)).setText("账户申诉");
        findViewById(R.id.title_tv).setOnClickListener(this);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.getphoto).setOnClickListener(this);
    }

    private Object obj;

    private void postData() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("timeStamp", System.currentTimeMillis());
            jo.put("userName", ((EditText) findViewById(R.id.usename)).getText().toString());
            jo.put("represCont", ((EditText) findViewById(R.id.represCont)).getText().toString());
            jo.put("photoData", bitmapToBase64(photo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("jsonData", jo.toString());
        aq.ajax(ServerInfo.REPRESENTATION, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json == null) {
                    return;
                }
                String resultflag = json.optString("resultflag");
                if (resultflag.equals("0")){
                    Toaster.showShort(AppealActivity.this,"申诉成功！");
                }else{
                    Toaster.showShort(AppealActivity.this,"申诉提交失败，请重新提交申诉！");
                }
            }
        });

    }

    private Bitmap photo;

    private String picPath;

    private void destoryBimap()
    {
        if (photo != null && ! photo.isRecycled()) {
            photo.recycle();
            photo = null;
        }
    }

    public void takePhoto()
    {
        destoryBimap();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(AppealActivity.this, "没有SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Uri uri = data.getData();
        if (uri != null) {
            this.photo = BitmapFactory.decodeFile(uri.getPath());
        }
        if (this.photo == null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                this.photo = (Bitmap) bundle.get("data");
            } else {
                Toast.makeText(AppealActivity.this, "拍照失败", Toast.LENGTH_LONG).show();
                return;
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            // 获取 SD 卡根目录
            String saveDir = Environment.getExternalStorageDirectory() + "/will/photo";
            // 新建目录
            File dir = new File(saveDir);
            if (! dir.exists()) dir.mkdir();
            // 生成文件名
            SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
            String filename = "MT" + (t.format(new Date())) + ".jpg";
            // 新建文件
            File file = new File(saveDir, filename);
            // 打开文件输出流
            fileOutputStream = new FileOutputStream(file);
            // 生成图片文件
            this.photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            // 相片的完整路径
            this.picPath = file.getPath();
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(this.photo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        int id = view.getId();
        if (id == R.id.getphoto) {
            takePhoto();
        } else if (id == R.id.submit) {
            if (((EditText) findViewById(R.id.usename)).getText().toString().equals("") || ((EditText) findViewById(R.id.represCont)).getText().toString().equals("") || obj == null) {
                Toast.makeText(AppealActivity.this,"请填充完整所需要的信息",Toast.LENGTH_SHORT).show();
            } else
                postData();
        }
    }
}
