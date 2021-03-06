package com.weibo.tabiagent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity1 extends Activity{

	private final static String TAG = "Activity1";

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_layout);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	//v.setLayoutParams(new GridView.LayoutParams(500, 500));
                //Toast.makeText(Activity1.this, "This is taken by " + Tool.read_image_nickname(position), Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, "=============>onCreate");
    }
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;

	    public ImageAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return 20;//mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        //imageView = (ImageView) findViewById(R.id.imageView);
	        Asynctest testAsynctest = new Asynctest(imageView);
	        testAsynctest.execute();
	        //imageView.setImageResource(mThumbIds[position]);
	        return imageView;
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
	                URL url = new URL((String)Tool.read_image_Url());
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

