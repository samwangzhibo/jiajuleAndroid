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
//设置九宫格解锁
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
				//case MotionEvent.ACTION_DOWN:// 灞忓箷鎸変笅
					//Log.e("wangzhibo", "鎸変笅鍘讳簡");
					//break;
				case MotionEvent.ACTION_UP:
					if(passWord.getOrbitString() == null){
						Toast.makeText(MyCustomViewActivity2.this,"设置失败", 3000).show();
					}
					mypass=passWord.getOrbitString();
					//System.out.println(passWord.getOrbitString());
					/*Log.e("wangzhibo", passWord.getOrbitString());
					Editor sharedata = getSharedPreferences("data", 0).edit();
					sharedata.putString("pws_jiugongge",passWord.getOrbitString());
					sharedata.commit();*/
					
					connect();
					Toast.makeText(MyCustomViewActivity2.this, "设置成功", 2000).show();
					Intent it=new Intent(MyCustomViewActivity2.this,MyTabHostFive.class);
					startActivity(it);
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
		GetWEBTask get=new GetWEBTask();
		get.execute("");
	}
	public class GetWEBTask extends AsyncTask<String, Integer, String>{
		@Override 
        protected String doInBackground(String... params) {
        	//String... params表示的是可变参数列表，也就是说，这样的方法能够接受的参数个数是可变的，但不论多少，必须都是String类型的。
        	//比如doInBackground("param1","param2","param3") ，或是doInBackground() 。
        	//处理后台执行的任务，在后台线程执行 
        	          
			try {
				//判断有无网络
				if(!NetWork.NetWorkpanduan(MyCustomViewActivity2.this)){
					
					result=NetWork.UNCONNET_NETWORK;
					return result;
				}
				String path=URLAPI.UPDATE_MY_HOMEPASS+"?home_pass="+mypass+"&&username="+ActivtyUtil.GetUsernameSharedPre(MyCustomViewActivity2.this);
				url=new URL(path);
				try {
					HttpURLConnection con=(HttpURLConnection) url.openConnection();
					con.setDoOutput(true);  
					con.setDoInput(true);  
					con.setConnectTimeout(10000);  //设置连接超时为10s  
					con.setReadTimeout(10000);     //读取数据超时也是10s  
					con.setRequestMethod("GET");  
					con.setUseCaches(false);  
					con.connect();
					
					 is=con.getInputStream();
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
          
         protected void onProgressUpdate(Integer... progress) {//在调用publishProgress之后被调用，在ui线程执行 

          } 
  
         protected void onPostExecute(String result) {//后台任务执行完之后被调用，在ui线程执行 
        	 NetWork.NetResultChuli(MyCustomViewActivity2.this, result, mprogressdialog);
        	 
        	 if(result.equals("success")){
				 //取消弹框
				mprogressdialog.dismiss();
				Toast.makeText(MyCustomViewActivity2.this, "解锁成功", 3000).show();
				Intent it=new Intent(MyCustomViewActivity2.this,MyTabHostFive.class);			
				startActivity(it);
				finish();
			 }else if(result.equals("lose")){
				 mprogressdialog.dismiss();
				 Toast.makeText(MyCustomViewActivity2.this, "解锁失败", 3000).show();
			 }
			 else if(result.equals("error")){
				 mprogressdialog.dismiss();
				 Toast.makeText(MyCustomViewActivity2.this, "服务器出错。。。。", 3000).show();
				 } 
			 else{
				 mprogressdialog.dismiss();
				 Toast.makeText(MyCustomViewActivity2.this, "unknowen error", 3000).show();
			 }		 
          } 
          
        protected void onPreExecute () {
        	//在 doInBackground(Params...)之前被调用，在ui线程执行        
			mprogressdialog=new ProgressDialog(MyCustomViewActivity2.this);
			mprogressdialog.setMessage("服务器通信中。。。。");
			mprogressdialog.show();
			mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//在ui线程执行 
          
          } 
		
	}
}