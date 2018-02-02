package com.example.jiajule;

//import com.xmobileapp.cammonitor.R;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.jiajule.MyCustomViewActivity2.GetWEBTask;
import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Toast;
//���þŹ������
public class MyCustomViewActivity2 extends Activity {
	/** Called when the activity is first created. */
	SquaredPassWord passWord;
	ProgressDialog mprogressdialog;
	String result;
	URL url;
	InputStream is;
	String mypass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jiugonggemain);
		passWord = (SquaredPassWord) this.findViewById(R.id.wp_ninelock);
		passWord.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				//case MotionEvent.ACTION_DOWN:// 屏幕按下
					//Log.e("wangzhibo", "按下去了");
					//break;
				case MotionEvent.ACTION_UP:
					mypass=passWord.getOrbitString();
					Toast.makeText(MyCustomViewActivity2.this, mypass, 3000).show();
					//System.out.println(passWord.getOrbitString());
					/*Log.e("wangzhibo", passWord.getOrbitString());
					Editor sharedata = getSharedPreferences("data", 0).edit();
					sharedata.putString("pws_jiugongge",passWord.getOrbitString());
					sharedata.commit();*/
//					connect();
					break;

				default:
					
					break;
				}
				return false;
			}
		});

	}
	
	protected void connect() {
		// TODO Auto-generated method stub
		GetWEBTask get=new GetWEBTask(MyCustomViewActivity2.this, mprogressdialog, result, mypass);
		get.execute("");
	}
	
	public static class GetWEBTask extends AsyncTask<String, Integer, String>{
		private Context context;
		private ProgressDialog mprogressdialog;
		private String result;
		private String mypass;
		public GetWEBTask(Context context, ProgressDialog mprogressdialog, String result, String mypass){
			this.context = context;
			this.mprogressdialog = mprogressdialog;
			this.result = result;
			this.mypass = mypass;
		}
		@Override 
        protected String doInBackground(String... params) {
        	//String... params��ʾ���ǿɱ�����б�Ҳ����˵�������ķ����ܹ����ܵĲ��������ǿɱ�ģ������۶��٣����붼��String���͵ġ�
        	//����doInBackground("param1","param2","param3") ������doInBackground() ��
        	//�����ִ̨�е������ں�̨�߳�ִ�� 
        	          
			try {
				//�ж���������
				if(!NetWork.NetWorkpanduan(context)){
					
					result=NetWork.UNCONNET_NETWORK;
					return result;
				}
				String path=URLAPI.UPDATE_MY_HOMEPASS()+"?home_pass="+mypass+"&&username="+ActivtyUtil.GetUsernameSharedPre(context);
				URL url=new URL(path);
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
          
         protected void onProgressUpdate(Integer... progress) {//�ڵ���publishProgress֮�󱻵��ã���ui�߳�ִ�� 

          } 
  
         protected void onPostExecute(String result) {//��̨����ִ����֮�󱻵��ã���ui�߳�ִ�� 
        	 //NetWork.NetResultChuli(context, result, mprogressdialog);
        	 mprogressdialog.dismiss();
        	 
        	 if(result.equals("success")){
				 //ȡ������
				Toast.makeText(context, "���óɹ�", 2000).show();
				((Activity) context).finish();
			 }else if(result.equals("lose")){
				 Toast.makeText(context, "����ʧ��", 3000).show();
			 }
			 else if(result.equals("error")){
				 Toast.makeText(context, "����������������", 3000).show();
				 } 
			 else{
				 Toast.makeText(context, "unknowen error", 3000).show();
			 }		 
          } 
          
        protected void onPreExecute () {
        	//�� doInBackground(Params...)֮ǰ�����ã���ui�߳�ִ��        
			mprogressdialog=new ProgressDialog(context);
			mprogressdialog.setMessage("������ͨ���С�������");
			mprogressdialog.show();
			mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//��ui�߳�ִ�� 
          
          } 
		
	}
}