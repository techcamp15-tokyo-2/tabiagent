package com.example.tabiagent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class TastTVlist extends AsyncTask<Integer, Integer, Integer>{

	
		@Override
		public Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			HttpPost request	= new HttpPost(GlobalData.strDefaultURL);
			JSONObject param	= new JSONObject();
			String strResult	= null;
			int applySuccess = 0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String nowTime 		= df.format(new Date());// new Date()为获取当前系统时间
			try {
				param.put("command", 501);
				param.put("classid", params[0]);
				param.put("userid", GlobalData.userID);
				param.put("datetime",nowTime);
				//param.put("Json2", strJsonValue);
				// 绑定到请求 Entry  
				StringEntity se = new StringEntity(param.toString());   
				request.setEntity(se);  
				// 发送请求  
				DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
				//请求超时 
				defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
				HttpResponse httpResponse = defaultHttpClient.execute(request); 
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据  
				String retSrc = EntityUtils.toString(httpResponse.getEntity());  
				// 生成 JSON 对象  
				JSONObject result = new JSONObject( retSrc);  
				int state = result.getInt("respond");
				if(state != 0){
					Log.i("New User", "error!");
					applySuccess = 0;
					return applySuccess;
				}
				
				int programNum = result.getInt("programnum");
				GlobalData.loginInfoList = new Vector<ComInfo>();
				for(int i = 1; i < (programNum +1) ; i++){
					String loginEmail = String.valueOf(i)+"_Email";
					String passWord = String.valueOf(i)+"_passWord";
				//	String datetimeName = String.valueOf(i)+"_datetime";
			//		String programidName = String.valueOf(i) + "_programid";
			//		String imageName = String.valueOf(i) + "_image";
					//
					//
					
					ComInfo tmp = new ComInfo();
					tmp.loginEmail = result.getString(loginEmail);
					tmp.passWord = result.getString(passWord);
					//tmp.tvName = new String(tmp.tvName.getBytes("iso-8859-1") , "UTF-8");
					//tmp.tvName = result.getString(tvName) + result.getString(tvcolName);
		//			tmp.datetimeName = result.getString(datetimeName);
			//		GlobalData.tvInfoList.add(tmp);
				}
				//Log.i("New User", String.valueOf(GlobalData.userID));
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.v("HttpPost Error", "test");
				applySuccess = 0;
				return applySuccess;
			}
			applySuccess = 1;
			return applySuccess;
		}
		//onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			if(applySuccess == false){
				useLocalData(classId);
				return;
			}
        	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        	for(int i = 0; i < GlobalData.tvInfoList.size() ; i++){
        		Map<String, Object> map = new HashMap<String, Object>();
                map.put("Title", GlobalData.tvInfoList.get(i).tvName);       
                map.put("Time",  GlobalData.tvInfoList.get(i).datetimeName);
                map.put("Channel", R.drawable.cctv6);
                map.put("Name", R.drawable.xiyou);
                list.add(map);
        	}
        	SimpleAdapter adapter = new SimpleAdapter(TVprogramActivity.this,list,R.layout.list_item,
                    new String[]{"Channel","Name","Title","Time"},
                    new int[]{R.id.ivChannel,R.id.ivProgram,R.id.tvTitle,R.id.tvTime});
    		lvTvprogram.setAdapter(adapter);
			//super.onPreExecute();
			super.onPostExecute(result);
		}  */
		 
	 }