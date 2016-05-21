package com.example.jiajule.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.R.bool;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.example.jiajule.MyTabHostFive;
import com.example.jiajule.jiajulemainActivity;

public class ActivtyUtil {

	  public static void showAlert(Context context,CharSequence title,CharSequence message,CharSequence btnTitle){
	    	new AlertDialog.Builder(context).setTitle(title)
	    	.setMessage(message).setPositiveButton(btnTitle, new DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					
				}
	    		
	    	}).show();
	    }
	  public static void MenuSlideMenu(){
		  if (MyTabHostFive.slideMenu.isMainScreenShowing()) {
				 MyTabHostFive.slideMenu.openMenu();
				} else {
					MyTabHostFive.slideMenu.closeMenu();
				}
	  }
	    public static void openToast(Context context,String str){
	    	Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	    }
	    /**
	     * Activity之间的跳转
	     * @param a
	     * @param b
	     */
	    public static void JumpActivity(Context a,Context b){

	    }
	    public static  String GetUsernameSharedPre(Context cxt){
	    	SharedPreferences settings =cxt.getSharedPreferences(jiajulemainActivity.SETTING_INFOS,0);
	    	String result=settings.getString("username","unkwen");
	    	Log.e("wangzhibo","share取出的username："+result);
	    	return result;
	    }
	    public static String GetNowTime(){
	    	SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");     
			String date =   sDateFormat.format(new   java.util.Date()); 
			return date;
	    }
	    public static void PutUsernameSharedPre(Context cxt,String input){
	    	Editor sharedata=cxt.getSharedPreferences(jiajulemainActivity.SETTING_INFOS, 0).edit();
	    	sharedata.putString("username",input);
	    	Log.e("wangzhibo","往username写成"+input);
			sharedata.commit();
	    }
	    public static  String GetSharedPreByName(Context cxt,String name){
	    	SharedPreferences settings =cxt.getSharedPreferences(jiajulemainActivity.SETTING_INFOS,0);
	    	String result=settings.getString(name,"false");
	    	return result;
	    }
	    public static void PutSharedPre(Context cxt,String input,String key){
	    	Editor sharedata=cxt.getSharedPreferences(jiajulemainActivity.SETTING_INFOS, 0).edit();
	    	sharedata.putString(key,input);
	    	Log.e("wangzhibo","往"+key+"写成"+input);
			sharedata.commit();
	    } 
	    public static boolean GetYuyinSwitcher(Context cxt){
	    	return cxt.getSharedPreferences(jiajulemainActivity.SETTING_INFOS, 0).getBoolean("voiceSwitcher", true);
	    }
	    public static ProgressDialog CreateDialog(String info,Context context){
	    	ProgressDialog prodia=new ProgressDialog(context);
	    	prodia.setMessage(info);
	    	prodia.setCancelable(false);
	    	return prodia;
	    }
}
