package com.weibo.tabiagent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class RegisterActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	
	class RegisterService implements  Runnable
	{
		@Override
		public void run() {
			EditText inputEmail = (EditText) findViewById(R.id.register_email);
			EditText inputPwd = (EditText) findViewById(R.id.register_password);
			EditText inputConfirmPwd = (EditText) findViewById(R.id.register_confirmPassword);
			EditText inputName = (EditText) findViewById(R.id.register_name);
			EditText inputNickname = (EditText) findViewById(R.id.register_nickname);
			EditText inputCountry = (EditText) findViewById(R.id.register_country);
			EditText inputCity = (EditText) findViewById(R.id.register_city);
			EditText inputGender = (EditText) findViewById(R.id.register_gender);
			//EditText inputHeadPic = (EditText) findViewById(R.id.register_email);
			String email = inputEmail.getText().toString().trim();
			String pwd = inputPwd.getText().toString().trim();
			String confirmPwd = inputConfirmPwd.getText().toString().trim();
			String name = inputName.getText().toString().trim();
			String nickname = inputNickname.getText().toString().trim();
			String country = inputCountry.getText().toString().trim();
			String city = inputCity.getText().toString().trim();
			String gender = inputGender.getText().toString().trim();
			//Log.i("in the login");
			//connect to server
			String connectURL = Tool.ip + "register";
			
			//Send the mail and password to server.
			boolean isRegisterSucceed = toRegister(email, pwd, confirmPwd, name, nickname, country, city, gender, connectURL);
			//if login succeed
				Intent intent = new Intent();
				if(isRegisterSucceed){
				intent.setClass(getApplicationContext(), CustomTabActivity1.class);
				startActivity(intent);
			}else{
				//Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
				//System.out.println("");
				Log.i("Register Error", "error!");
				
			}
		}
	}
    
    private boolean toRegister(String inputEmail, String inputPwd, String inputConfirmPassword, String name, String nickname, String country, String city, String gender, String connectUrl) {
		String result = null; //
		boolean isRegisterSucceed = false;
		if (!(inputPwd.equals(inputConfirmPassword)))
			return isRegisterSucceed;
		if (inputEmail.equals("") && inputPwd.equals("") && nickname.equals(""))
			return isRegisterSucceed;
		//Send Http Post request
		
		
		String url = connectUrl + "?email=" + inputEmail + "&pwd=" + inputPwd +"&nickname=" + nickname + "&name=" + name + "&country=" + country + "&city=" + city + "&gender=" + gender;
		System.out.println("url="+url);
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
					return false;
				}
				else {
					System.out.println("Login succeed!");
					isRegisterSucceed = true;
					return isRegisterSucceed;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(result.equals("login succeed")){
			isRegisterSucceed = true;
		}
		return isRegisterSucceed;
		}

    
    /*CBW--Function of Login*/
    public void clickToRegister (View view) {
   
    	 RegisterService register = new RegisterService(); 
    		new Thread(register).start();
    }

	/**
	 * The default email to populate the email field with.
	 */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
}