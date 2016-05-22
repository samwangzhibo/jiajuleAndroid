package com.example.jiajule.util;

public class URLAPI {
	public static final String IP_HEAD="http://";
	public static boolean isPhp = true;
	public static boolean getisPhp() {
		return isPhp;
	}
	public static void setisPhp(boolean isPhp) {
		URLAPI.isPhp = isPhp;
	}

	public static String HOME_IP="192.168.31.129";
	//public static String HOME_IP="192.168.1.100";
	public static String IP="anniezhang.esy.es";
	//public static String IP = HOME_IP;
	
	//public static final String GET_TASK_MSG = IP_HEAD+IP+"/MyServlet/servlet/GetTaskServlet";
	///public static final String ADD_TASK=IP_HEAD+IP+"/MyServlet/servlet/AddTaskServlet";
	//public static final String UPDATE_TASK=IP_HEAD+IP+"/MyServlet/servlet/UpdateTaskServlet";
	//public static final String DELETE_TASK=IP_HEAD+IP+"/MyServlet/servlet/DeleteTaskServlet";
	//public String LOGIN = getLoginUrl();
	//public final String REGISTE=IP_HEAD+IP+"/MyServlet/servlet/RegisterServlet";
	//public static final String GETMYINFO=IP_HEAD+IP+"/MyServlet/servlet/GetMyInfo";
	//public static final String PASSUNLOCK=IP_HEAD+IP+"/MyServlet/servlet/UnLock";
	//public static final String UPDATE_MY_HOMEPASS=IP_HEAD+IP+"/MyServlet/servlet/UpdateHomePass";
	//public static final String GetPicture=IP_HEAD+IP+"/MyServlet/ImagesUploaded/";
	/*public static final String UNLOCK_RECORD=IP_HEAD+IP+"/MyServlet/servlet/GetUnlockMessage";
	public static final String UPLOAD_BMP=IP_HEAD+IP+"/MyServlet/servlet/UploadBMP";*/
	public static URLAPI instance = new URLAPI();
	
	public static String getLoginUrl(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/alogin.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/LoginServlet";
	}
	public static String ShowHomePeople(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/select/ShowHomePeople.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/ShowHomePeople";
	}
	public static String getRegisteUrl(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/areg.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/RegisterServlet";
	}
	public static String GET_TASK_MSG(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/select/GetTask.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/GetTaskServlet";
	}
	public static String ADD_TASK(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/AddTask.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/AddTaskServlet";
	}
	public static String UPDATE_TASK(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/update/UpdateTask.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/UpdateTaskServlet";
	}
	public static String DELETE_TASK(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/del/DeleteTask.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/DeleteTaskServlet";
	}
	public static String GETMYINFO(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/select/GetMyInfo.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/GetMyInfo";
	}
	public static String PASSUNLOCK(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/select/PassUnlock.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/UnLock";
	}
	public static String UPDATE_MY_HOMEPASS(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/update/UpdateHomePass.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/UpdateHomePass";
	}
	public static String GetPicture(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/img/";
		}
		return IP_HEAD+IP+"/MyServlet/ImagesUploaded/";
	}
	public static String UNLOCK_RECORD(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/select/UnlockRecord.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/GetUnlockMessage";
	}
	public static String UPLOAD_BMP(){
		if (isPhp) {
			return IP_HEAD+IP+"/php_login/upload.php";
		}
		return IP_HEAD+IP+"/MyServlet/servlet/UploadBMP";
	}
	
	public static URLAPI getInstance(){
		return instance;
	}
}

