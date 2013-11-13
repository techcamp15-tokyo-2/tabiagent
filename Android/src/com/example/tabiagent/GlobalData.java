package com.example.tabiagent;

import java.util.Vector;


public class GlobalData {
		public static int userID ;
		public static String strDefaultURL = "http://172.19.208.96:8888";
		public static Boolean isNetConnected = null;
		public static Vector<ComInfo> loginInfoList = null;
		public static Vector<ComInfo> registerInfoList = null;
		
		
	}

class ComInfo {
	String loginEmail;
	String passWord;
	//Wait to add more
}