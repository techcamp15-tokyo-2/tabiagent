package com.weibo.tabiagent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity5 extends Activity{

	private final static String TAG = "Activity5";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity5_layout);
        Button button = (Button) findViewById(R.id.button1);
    	button.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    			
    			startActivityForResult(intent, 1);
    		}
    	});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == Activity.RESULT_OK) {
    		String sdStatus = Environment.getExternalStorageState();
    		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
    			Log.i("TestFile",
    					"SD card is not avaiable/writeable right now.");
    			return;
    		}
    		String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";	
    		Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    		Bundle bundle = data.getExtras();
    		Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
    	   
    		((ImageView) findViewById(R.id.imageView1)).setImageBitmap(bitmap);// 将图片显示在ImageView里
    	}
    }
    }