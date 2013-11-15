
package com.weibo.tabiagent;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import com.weibo.tabiagent.R.string;

import android.R.bool;
import android.R.integer;
import android.net.NetworkInfo.State;

/**
 * Some simple test data to use for this sample app.
 */
public class Tool {
	public static String ip = "http://172.19.208.96:8888/";
	public static int num = 20;
	/**
	 * all picture url info
	 * */
	public static int index = 0;
    public static List<String> image_Urls = new ArrayList<String>();
    public static ArrayList image_uploadtime = new ArrayList();
    public static ArrayList image_place = new ArrayList();
    public static ArrayList image_nickname = new ArrayList();
//    {
//        "http://192.168.3.68:8888/static/hikaru.jpg",
//        "http://192.168.3.68:8888/static/liwei.jpg",
//        "http://192.168.3.68:8888/static/chenbowen.jpg"
//    };

    public static Object read_image_Url(){
    	if(index == image_Urls.size())	index = 0;
        return image_Urls.get(index++);
    }
    public static void write_image_Url(ArrayList a){
    	image_Urls = null;
    	image_Urls = a;
    }
    public static Object read_image_nickname(int k){
    	if(friend_index == image_nickname.size())	friend_index = 0;
        return image_nickname.get(k);
    }
    public static void write_image_nickname(ArrayList a){
    	image_nickname = null;
    	image_nickname = a;
    }
    /**
     * personal info
     * */
    public static int state = 0;
    public static int userid = 0;
    public static String name = "";
    public static String nickname = "";
    
    /**
     * search info
     * */
	public static int search_index = 0;
    public static List<String> search_Urls = new ArrayList<String>();
    public static ArrayList search_uploadtime = new ArrayList();
    public static ArrayList search_place = new ArrayList();
    public static ArrayList search_nickname = new ArrayList();
    public static Object read_search_Urls(){
    	if(index == search_Urls.size())	search_index = 0;
        return search_Urls.get(search_index++);
    }
    public static void write_search_Urls(ArrayList a){
    	search_Urls = null;
    	search_Urls = a;
    }
    /**
     * info of friends 
     * 
     * */
    public static int friend_index = 0;
    public static ArrayList friend_id = new ArrayList();
    public static ArrayList friend_nicknameString = new ArrayList();
    public static ArrayList friend_picid =new ArrayList();
    public static List<String> friend_picurl =new ArrayList<String>();
    public static ArrayList friend_uploadtime =new ArrayList();
    public static ArrayList friend_place =new ArrayList();
    
    public static Object read_friend_id(){
    	if(friend_index == friend_id.size())	friend_index = 0;
        return friend_id.get(friend_index++);
    }
    public static void write_friend_id(ArrayList a){
    	friend_id = null;
    	friend_id = a;
    }
    
    public static Object read_friend_nicknameString(int k){
    	if(friend_index == friend_nicknameString.size())	friend_index = 0;
        return friend_nicknameString.get(k);
    }
    public static void write_friend_nicknameString(ArrayList a){
    	friend_nicknameString = null;
    	friend_nicknameString = a;
    }
    
    public static Object read_friend_picid(){
    	if(friend_index == friend_picid.size())	friend_index = 0;
        return friend_picid.get(friend_index++);
    }
    public static void write_friend_picid(ArrayList a){
    	friend_picid = null;
    	friend_picid = a;
    }
    
    public static Object read_friend_picurl(){
    	if(friend_index == friend_picurl.size())	friend_index = 0;
        return friend_picurl.get(friend_index++);
    }
    public static void write_friend_picurl(ArrayList a){
    	friend_picurl = null;
    	friend_picurl = a;
    }
    
    public static Object read_friend_uploadtime(){
    	if(friend_index == friend_uploadtime.size())	friend_index = 0;
        return friend_uploadtime.get(friend_index++);
    }
    public static void write_friend_uploadtime(ArrayList a){
    	friend_uploadtime = null;
    	friend_uploadtime = a;
    }
 
    public static Object read_friend_place(){
    	if(friend_index == friend_place.size())	friend_index = 0;
        return friend_place.get(friend_index++);
    }
    public static void write_friend_place(ArrayList a){
    	friend_place = null;
    	friend_place = a;
    }
    
}
