package com.example.jiajule.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.jiajule.MyTabHostFive;
import com.example.jiajule.jiajulemainActivity;
import com.example.jiajule.login;
import com.example.jiajule.BMPUtil.DownloadBMPTask;
import com.jiajule.db.DBOpreate;
import com.jiajule.javabean.UserInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MyWebTask extends AsyncTask<String, Integer, String>{
	String result;
	Context context;
	ProgressDialog mprogressdialog;
	public MyWebTask(Context context){
		this.context=context;
		
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			//�ж���������
			if(!NetWork.NetWorkpanduan(context)){
				
				result=NetWork.UNCONNET_NETWORK;
				return result;
			}
			
			URL url=new URL(params[0]);
			try {
				HttpURLConnection con=(HttpURLConnection) url.openConnection();
				con.setDoOutput(true);  
				con.setDoInput(true);  
				con.setConnectTimeout(10000);  //�������ӳ�ʱΪ10s  
				con.setReadTimeout(10000);     //��ȡ���ݳ�ʱҲ��10s  
				con.setRequestMethod("GET");  
				con.setUseCaches(false);  
				con.connect();
				
			     InputStream is=con.getInputStream();
				 DataInputStream dis=new DataInputStream(is);
				 result=dis.readLine();
				 System.out.println(result);
				 
				 dis.close();
				 is.close();
				 con.disconnect();  
/*					 if(con.getResponseCode() != 200){
						result=NetWork.ERROR_NETWORK;
						return result;
					}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result=NetWork.ERROR_NETWORK;
				return result;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return result; 
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mprogressdialog = new ProgressDialog(context);
		mprogressdialog.setMessage("������ɾ���С���������");
		mprogressdialog.show();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		 NetWork.NetResultChuli(context, result, mprogressdialog);
   	 
    	 if(result.equals("success")){

			 //ȡ������
			mprogressdialog.dismiss();
		 }else if(result.equals("lose")){
			 mprogressdialog.dismiss();
			 Toast.makeText(context, "��½ʧ��", 3000).show();
		 }
		 else if(result.equals("error")){
			 mprogressdialog.dismiss();
			 Toast.makeText(context, "����������������", 3000).show();
			 }
		 else{
			 mprogressdialog.dismiss();
			 Toast.makeText(context, "unknowen error", 3000).show();
		 }
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
}
