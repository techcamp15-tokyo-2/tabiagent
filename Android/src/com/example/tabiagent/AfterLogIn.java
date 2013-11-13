package com.example.tabiagent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Parser;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AfterLogIn extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    
    class DesplayImageService implements  Runnable
	{
		@Override
		public void run() {
			EditText inputEmail = (EditText) findViewById(R.id.login_email);
			EditText inputPwd = (EditText) findViewById(R.id.login_password);
			String email = inputEmail.getText().toString().trim();
			String pwd = inputPwd.getText().toString().trim();
			//Log.i("in the login");
			//connect to server
			String connectURL = "http://172.19.210.81:8888/login";
			
			//Send the mail and password to server.
			boolean isLoginSucceed = toLogin(email, pwd, connectURL);
			//if login succeed
				Intent intent = new Intent();
				if(isLoginSucceed){
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}else{
				//Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
				//System.out.println("");
//				intent.setClass(getApplicationContext(), Register.class);
//				startActivity(intent);
				
			}
		}
	}
    
    private boolean toLogin(String inputEmail, String inputPwd,String connectUrl) {
		String result = null; //
		boolean isLoginSucceed = false;
		//test
		System.out.println("email:" + inputEmail);
		System.out.println("password:" + inputPwd);
		//Send Http Post request
		String url = connectUrl + "?email=" + inputEmail + "&pwd=" + inputPwd;
		System.out.println(url);
	//	HttpPost httpRequest = new HttpPost(url);
		HttpGet httpRequest = new HttpGet(url);
		//use NameValuePair[] to store the params which need to be sent.
	//	List params = new ArrayList();
	//	params.add(new BasicNameValuePair("mail",userMail));
	//	params.add(new BasicNameValuePair("pwd",password));
		try{
			//Send HTTP request
		//	httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//get HTTP response
			HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
			// if succeed
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//get the string comes from server.
				result = EntityUtils.toString(httpResponse.getEntity());
				JSONArray re = new JSONArray(result);
				JSONObject item1 = re.getJSONObject(0);
				String state = item1.getString("state");
				
				System.out.println("result= "+result);
				System.out.println("state= "+state);
				
			//	if (ret == false) 
					System.out.println("Login failed!");
	//			else 
					System.out.println("Login succeed!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(result.equals("login succeed")){
			isLoginSucceed = true;
		}
		return isLoginSucceed;
		}

    
    /*CBW--Function of Login*/
    public void displayImage (View view) {
   
    	DesplayImageService login = new DesplayImageService(); 
    		new Thread(login).start();
    }
    	
		  
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_log_in);
        //display images after created.
        String url = "http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690";
        //得到可用的图片
        Bitmap bitmap = getHttpBitmap(url);
        imageView = (ImageView)this.findViewById(R.id.imageView1);
        //显示
        imageView.setImageBitmap(bitmap);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pagesdr);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    
    public static Bitmap getHttpBitmap(String url){
    	URL myFileURL;
    	Bitmap bitmap=null;
    	try{
    		myFileURL = new URL(url);
    		//获得连接
    		HttpURLConnection conn=(HttpURLConnection) myFileURL.openConnection();
    		//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
    		conn.setConnectTimeout(6000);    		
    		conn.connect();
    		//得到数据流
    		InputStream is = conn.getInputStream();
    		//解析得到图片
    		bitmap = BitmapFactory.decodeStream(is);
    		//关闭数据流
    		is.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
		return bitmap;
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1);//.toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section3);
                case 3:
                	return getString(R.string.title_section4);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText("人生は旅です！");
        //    dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
