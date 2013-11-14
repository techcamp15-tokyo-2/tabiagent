package com.weibo.tabiagent;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	//定义Button按钮对象
	//private Button btn1,btn2;
	
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
				String connectURL = Tool.ip+"login";
				
				//Send the mail and password to server.
				boolean isLoginSucceed = toLogin(email, pwd, connectURL);
				//if login succeed				
//					if(!isLoginSucceed){
//					Intent intent = new Intent();
//					intent.setClass(getApplicationContext(), RegisterActivity.class);
//					startActivity(intent);
//				}else{
					if(isLoginSucceed){
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), CustomTabActivity1.class);
					startActivity(intent);
					
				}
			}
		}
			
			private boolean toLogin(String inputEmail, String inputPwd,String connectUrl) {
				String result = null; 
				boolean isLoginSucceed = false;
				/**
				 * *login?.......
				 * */
				String url = connectUrl + "?email=" + inputEmail + "&pwd=" + inputPwd;
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
						JSONArray re1 = new JSONArray(result);
						JSONObject item1 = re1.getJSONObject(0);
						int state = item1.getInt("state");
						Tool.state = state;
						Tool.userid = item1.getInt("userid");
						Tool.name = item1.getString("name");
						Tool.nickname = item1.getString("nickname");
						
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
				/**
				 * *home?.......
				 * */
				HttpGet httpRequest_home = new HttpGet(Tool.ip+"home?pageNum=0");
				try{
					//Send HTTP request
				//	httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					//get HTTP response
					HttpResponse httpResponse_home=new DefaultHttpClient().execute(httpRequest_home);
					// if succeed
					if(httpResponse_home.getStatusLine().getStatusCode()==200){
						//get the string comes from server.
						result = EntityUtils.toString(httpResponse_home.getEntity());
						JSONArray re2 = new JSONArray(result);
						JSONObject item_home = re2.getJSONObject(0);
						int state = item_home.getInt("state");
						String data = item_home.getString("data");
						JSONArray re_re2 = new JSONArray(data);
						for (int i=0; i<re_re2.length(); i++){
							JSONObject item_home_real = re_re2.getJSONObject(i);
							Tool.image_uploadtime.add(item_home_real.getString("uploadtime")); 
							Tool.image_Urls.add(item_home_real.getString("url")); 
							Tool.image_place.add(item_home_real.getString("place")); 
							Tool.image_nickname.add(item_home_real.getString("nickname")); 
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
				
				/**
				 * *friends?.......
				 * */
				HttpGet httpRequest_friend = new HttpGet(Tool.ip+"friends?userid=" + Tool.userid);
				try{
					//Send HTTP request
				//	httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					//get HTTP response
					HttpResponse httpResponse_friend = new DefaultHttpClient().execute(httpRequest_friend);
					// if succeed
					if(httpResponse_friend.getStatusLine().getStatusCode()==200){
						//get the string comes from server.
						result = EntityUtils.toString(httpResponse_friend.getEntity());
						JSONArray re3 = new JSONArray(result);
						JSONObject item_friend = re3.getJSONObject(0);
						int state = item_friend.getInt("state");
						String data = item_friend.getString("data");
						JSONArray re_re3 = new JSONArray(data);
						for (int i=0; i<re_re3.length(); i++){
							JSONObject item_friend_real = re_re3.getJSONObject(i);
							Tool.friend_uploadtime.add(item_friend_real.getString("friend_uploadtime")); 
							Tool.friend_picurl.add(item_friend_real.getString("friend_picurl")); 
							Tool.friend_place.add(item_friend_real.getString("friend_place")); 
							Tool.friend_nicknameString.add(item_friend_real.getString("friend_nickname")); 
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
			
			public void clickToLogin (View view) {
				   
		    	 LoginService login = new LoginService(); 
		    		new Thread(login).start();
		    }
		    
		    public void clickToRegisterPage(View view) {
		    	Intent intent = new Intent();
				intent.setClass(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
		    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		initView();
	}

	/**
	 * 初始化组件
	 */
//	private void initView(){
		//实例化按钮对象
//		btn1 = (Button)findViewById(R.id.button1);
//		btn2 = (Button)findViewById(R.id.button2);
//
//		//设置监听，进入CustomTab界面
//		btn1.setOnClickListener(new OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(MainActivity.this,CustomTabActivity1.class));
//			}
//		});
//		
//		//设置监听，进入RadioGroup界面
//		btn2.setOnClickListener(new OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(MainActivity.this,CustomTabActivity2.class));
//			}
////		});
//	}
}
