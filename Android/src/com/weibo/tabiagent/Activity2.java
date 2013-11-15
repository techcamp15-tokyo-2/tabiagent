package com.weibo.tabiagent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.weibo.tabiagent.Activity1.ImageAdapter;
import com.weibo.tabiagent.Activity1.ImageAdapter.Asynctest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity2 extends Activity{

	private final static String TAG = "Activity2";

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_layout);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        ImageAdapter adapter = new ImageAdapter(Tool.friend_picurl,Activity2.this);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	//v.setLayoutParams(new GridView.LayoutParams(500, 500));
  
                Toast.makeText(Activity2.this, "This is taken by " + Tool.read_friend_nicknameString(position), Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, "=============>onCreate");
    }
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;

	    private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	    private List<String> urls = new ArrayList<String>();
	    
	    public ImageAdapter(List<String> urls,Context mContext){
	    	this.mContext = mContext;
	    	this.urls = urls;
	    }
	   
	    public int getCount() {
	        return urls.size();//mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return urls.get(position);
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
//	        ImageView imageView;
	    	View rowV = rowViews.get(position);
	        if (convertView == null) {
	        	// if it's not recycled, initialize some attributes
	        	LayoutInflater layout = LayoutInflater.from(mContext);
	        	rowV = layout.inflate(R.layout.image, null);
	        	ImageView imageView = (ImageView)rowV.findViewById(R.id.imageView);
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        	Asynctest testAsynctest = new Asynctest(imageView);
		        testAsynctest.execute();
		        rowViews.put(position, rowV);
	        }
//	            imageView = new ImageView(mContext);

//	        } else {
//	            imageView = (ImageView) convertView;
//	        }
	        //imageView = (ImageView) findViewById(R.id.imageView);
	        
	        //imageView.setImageResource(mThumbIds[position]);
	        return rowV;
	    }

		class Asynctest extends AsyncTask<String, Integer, Bitmap>{
			private ImageView imageView;
	        //private LinearLayout linearLayout;
	        
	        public Asynctest(ImageView imageView){
	            super();
	            this.imageView = imageView;
	            //this.linearLayout = linearLayout;
	        }
	        
	        @Override
	        protected Bitmap doInBackground(String... param){
	            Bitmap bitmap;
	            
	            try {
	                //URL url = new URL("http", "suzuki.toshinari.jp", "/images/thumbnail_codaholic.org.jpg");
	                URL url = new URL((String)Tool.read_friend_picurl());
	                HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
	                urlcon.setConnectTimeout(10000000);
	                urlcon.setDoInput(true);
	                urlcon.connect();
	                InputStream inputStream = urlcon.getInputStream();
	                //InputStream inputStream = url.openStream();
	                bitmap = BitmapFactory.decodeStream(inputStream);
	                
	                return bitmap;
	            } catch (IOException ex) {
	                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
	            }
	            
	            return null;
	        }
	        
	        @Override
	        protected void onPostExecute(Bitmap result){
	            
	            /*
	            this.imageView.setImageBitmap(result);
	            this.linearLayout.addView(this.imageView);
	            */
	            imageView.setImageBitmap(result);

	        }
		}

	    // references to our images
//	    private Integer[] mThumbIds = {
//	            R.drawable.sample_2, R.drawable.sample_3,
//	            R.drawable.sample_4, R.drawable.sample_5,
//	            R.drawable.sample_6, R.drawable.sample_7,
//	            R.drawable.sample_0, R.drawable.sample_1,
//	            R.drawable.sample_2, R.drawable.sample_3,
//	    };
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

