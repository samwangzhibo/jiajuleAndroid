package com.example.jiajule.util;

public class URLAPI {
	public static final String IP_HEAD="http://";
	public static final String IP="192.168.43.82:8080";
	public static final String GET_TASK_MSG=IP_HEAD+IP+"/MyServlet/servlet/GetTaskServlet";
	public static final String ADD_TASK=IP_HEAD+IP+"/MyServlet/servlet/AddTaskServlet";
	public static final String UPDATE_TASK=IP_HEAD+IP+"/MyServlet/servlet/UpdateTaskServlet";
	public static final String DELETE_TASK=IP_HEAD+IP+"/MyServlet/servlet/DeleteTaskServlet";
	public static final String LOGIN=IP_HEAD+IP+"/MyServlet/servlet/LoginServlet";
	public static final String REGISTE=IP_HEAD+IP+"/MyServlet/servlet/RegisterServlet";
	public static final String GETMYINFO=IP_HEAD+IP+"/MyServlet/servlet/GetMyInfo";
	public static final String PASSUNLOCK=IP_HEAD+IP+"/MyServlet/servlet/UnLock";
	public static final String UPDATE_MY_HOMEPASS=IP_HEAD+IP+"/MyServlet/servlet/UpdateHomePass";
	public static final String GetPicture=IP_HEAD+IP+"/MyServlet/ImagesUploaded/";
	public static final String UNLOCK_RECORD=IP_HEAD+IP+"/MyServlet/servlet/GetUnlockMessage";
	public static final String UPLOAD_BMP=IP_HEAD+IP+"/MyServlet/servlet/UploadBMP";
}

