package com.example.tabiagent;


import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    
    class LoginService implements  Runnable
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
				if(!isLoginSucceed){
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
			}else{
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AfterLogIn.class);
				startActivity(intent);
				
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
				int state = item1.getInt("state");
				
				System.out.println("result = "+result);
				System.out.println("state = "+state);
				
				if (state == 0){ 
					System.out.println("Login failed!");
					return isLoginSucceed;
				}
				else {
					System.out.println("Login succeed!");
					isLoginSucceed = true;
					return isLoginSucceed;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return isLoginSucceed;
				
		}

    
    /*CBW--Function of Login*/
    public void clickToLogin (View view) {
   
    	 LoginService login = new LoginService(); 
    		new Thread(login).start();
    }
    
    public void clickToRegisterPage(View view) {
    	Intent intent = new Intent();
		intent.setClass(getApplicationContext(), RegisterActivity.class);
		startActivity(intent);
    }
		  
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            dummyTextView.setText("�������äǤ���");
        //    dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}

