package com.weibo.tabiagent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Activity4 extends Activity{

	
	private TextView mTextView01;
	private TextView mTextView02;
	private TextView mTextView03;
	private TextView mTextView04;
	private TextView mTextView05;
	private TextView mTextView06;
	
	private final static String TAG = "Activity4";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_layout);
        
        mTextView01 = (TextView) findViewById(R.id.textView2);
        mTextView02 = (TextView) findViewById(R.id.textView3);
        mTextView03 = (TextView) findViewById(R.id.textView4);

        
        int userid = Tool.userid;
        mTextView01.setText("Your Userid = " + userid);
        mTextView02.setText("Your name = " + Tool.name);
        mTextView03.setText("Your nickname = " + Tool.nickname);


        Log.i(TAG, "=============>onCreate");
    }
	
	@Override
	protected void onResume() {
		super.onResume();	
		Log.i(TAG, "=============>onResume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "=============>onDestroy");
	}
}