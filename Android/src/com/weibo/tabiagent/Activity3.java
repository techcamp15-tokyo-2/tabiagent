package com.weibo.tabiagent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.weibo.tabiagent.MainActivity.LoginService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity3 extends Activity{

	private final static String TAG = "Activity3";

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_layout);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	//v.setLayoutParams(new GridView.LayoutParams(500, 500));
                //Toast.makeText(Activity3.this, "hello" + position, Toast.LENGTH_SHORT).show();
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
	            	URL url;
	            	if(Tool.search_Urls.size()==0){
	            		url = new URL((String)Tool.read_image_Url());
	            	}else{
	            		url = new URL((String)Tool.read_search_Urls());
	            	}
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
	
	class LoginService implements  Runnable
	{
		@Override
		public void run() {
			EditText inputSearch = (EditText) findViewById(R.id.input_search);
			String search = inputSearch.getText().toString().trim();		
			//Log.i("in the login");
			//connect to server
			String connectURL = Tool.ip+"search";
			
			//Send the mail and password to server.
			boolean isLoginSucceed = toSearch(search, connectURL);
			//if login succeed				
//				if(!isLoginSucceed){
//				Intent intent = new Intent();
//				intent.setClass(getApplicationContext(), RegisterActivity.class);
//				startActivity(intent);
//			}else{
				if(isLoginSucceed){
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Activity3.class);
				startActivity(intent);
				
			}
		}
	}
		
		private boolean toSearch(String inputSearch,String connectUrl) {
			String result = null; 
			boolean isLoginSucceed = false;

			String url = connectUrl + "?keywords=" + inputSearch;
			HttpGet httpRequest = new HttpGet(url);
			try{
				//Send HTTP request
			//	httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				//get HTTP response
				HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				// if succeed
				if(httpResponse.getStatusLine().getStatusCode()==200){
					//get the string comes from server.
					result = EntityUtils.toString(httpResponse.getEntity());
					JSONArray re2 = new JSONArray(result);
					JSONObject item_home = re2.getJSONObject(0);
					int state = item_home.getInt("state");
					String data = item_home.getString("data");
					JSONArray re_re2 = new JSONArray(data);
					for (int i=0; i<re_re2.length(); i++){
						JSONObject item_home_real = re_re2.getJSONObject(i);
						Tool.search_uploadtime.add(item_home_real.getString("uploadtime")); 
						Tool.search_Urls.add(item_home_real.getString("url")); 
						Tool.search_place.add(item_home_real.getString("place")); 
						Tool.search_nickname.add(item_home_real.getString("nickname")); 
					}
					
					if (state == 0){ 
						System.out.println("Login failed!");
						return isLoginSucceed;
					}
					else {
						System.out.println("Login succeed!");
						isLoginSucceed = true;

					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return isLoginSucceed;
		}
	
	public void clickToSearch(View view){
		LoginService login = new LoginService(); 
		new Thread(login).start();
	}
}

