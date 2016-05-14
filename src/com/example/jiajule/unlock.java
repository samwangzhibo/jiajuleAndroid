package com.example.jiajule;
//import org.demo.custon_view.MyCustomViewActivity;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.jiajule.R;
import com.example.jiajule.push.ExampleUtil;
import com.jiajule.db.DBOpreate;
import com.jiajule.floating.FloatView;
import com.jiajule.javabean.UserInfo;
import com.other.unlock_record;


//import com.xmobileapp.cammonitor.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;
import com.example.jiajule.view.SlideMenu;

public class unlock extends Activity {
	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowManagerParams = null;
	public static boolean isForeground = false;
	public static ProgressDialog mprogressdialog;
	public String password;
	String TAG="wangzhibo";
	String result;
	URL url;
	
	public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.unlock2);
			registerMessageReceiver();
			Log.e(TAG,"unlock初始化了");
			Button bt2=(Button) findViewById(R.id.unlock_password);
			Button bt3=(Button) findViewById(R.id.unlock_record);
			
			ImageView im2=(ImageView) findViewById(R.id.unlock_menu);
			im2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					ActivtyUtil.MenuSlideMenu();
				}
			});
			
			bt3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent it=new Intent(unlock.this,unlock_record.class);
					startActivity(it);
					
				}
			});			
			bt2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String unlockmode=ActivtyUtil.GetSharedPreByName(unlock.this,"unlockmode");
					if(unlockmode.equals("false")){
						AlertDialog dlg2 = new AlertDialog.Builder(unlock.this).setIcon(R.drawable.icon3).setTitle("Warning").setMessage("你暂时还没有设置解锁方式").setNegativeButton("去设置", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
					int whichButton) {
						Intent it=new Intent(unlock.this,MyInfo.class);
						startActivity(it);
					}}).setPositiveButton("使用默认解锁模式", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
								
								

															}
														}).create();
						dlg2.show();
					}else if(unlockmode.equals("mima")){
						ShowDialog();
					}
					else if(unlockmode.equals("jiugongge")){
						Intent it=new Intent(unlock.this,MyCustomViewActivity.class);
						startActivity(it);	
					}
					else
					Toast.makeText(unlock.this, "something wrong", 3000);
			}
			});
	}

	public void ShowDialog(){
		LayoutInflater factory = LayoutInflater.from(unlock.this);
		final View textEntryView = factory.inflate(R.layout.layoutdialog2, null);
		
		AlertDialog dlg = new AlertDialog.Builder(unlock.this)
		.setTitle("请输入密码")
		.setView(textEntryView).setPositiveButton(
		R.string.confirm,
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,int whichButton) {
			/* 左键事件 */		
			EditText pwd = (EditText) textEntryView.findViewById(R.id.pwd);
			
			final  String pwd2 = pwd.getText()
			.toString();
			password = pwd2;
			
			GetWEBTask mygetwt=new GetWEBTask();
			mygetwt.execute("");
			
			
				//TextView text=(TextView) findViewById(R.id.text);
				//text.setText(username+"你好");		

				}
				}).setNegativeButton(R.string.close,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {}
											}).create();
		
							dlg.show();
	

			//				createView();
				
	
	
	}
	class GetWEBTask extends AsyncTask<String,Integer,String> {//继承AsyncTask 
        @Override 
        protected String doInBackground(String... params) {
        	//String... params表示的是可变参数列表，也就是说，这样的方法能够接受的参数个数是可变的，但不论多少，必须都是String类型的。
        	//比如doInBackground("param1","param2","param3") ，或是doInBackground() 。
        	//处理后台执行的任务，在后台线程执行 
        	
			String path=URLAPI.PASSUNLOCK()+"?username="+ActivtyUtil.GetUsernameSharedPre(unlock.this)+"&&password="+password+"&&time="+ActivtyUtil.GetNowTime(); 
			Log.e(TAG, path);
			try {
				//判断有无网络
				if(!NetWork.NetWorkpanduan(unlock.this)){
					
					result=NetWork.UNCONNET_NETWORK;
					return result;
				}
				
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
			Log.e(TAG, "result的值："+result);
             return result; 
         } 
          
         protected void onProgressUpdate(Integer... progress) {//在调用publishProgress之后被调用，在ui线程执行 

          } 
  
         protected void onPostExecute(String result) {//后台任务执行完之后被调用，在ui线程执行 
        	 

				
        	 NetWork.NetResultChuli(unlock.this, result, mprogressdialog);
        	 
        	 if(result.equals("success")){
        		
				mprogressdialog.dismiss();
				ActivtyUtil.openToast(unlock.this,"解锁成功");
			 }else if(result.equals("lose")){
				 mprogressdialog.dismiss();
				 Toast.makeText(unlock.this, "密码不匹配", 3000).show();
			 }
			 else if(result.equals("error")){
				 mprogressdialog.dismiss();
				 Toast.makeText(unlock.this, "服务器异常。。。。", 3000).show();
				 }
			 else{
				 mprogressdialog.dismiss();
				 Toast.makeText(unlock.this, "unknowen error", 3000).show();
			 }		 
          } 
          
        protected void onPreExecute () {
        	//在 doInBackground(Params...)之前被调用，在ui线程执行        
			mprogressdialog=new ProgressDialog(unlock.this);
			mprogressdialog.setMessage("服务器通信中。。。。");
			mprogressdialog.show();
			mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//在ui线程执行 
          
          } 
          
     }; 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e(TAG, "Unlock点击了按键");
		 // 如果是返回键,直接返回到桌面

		  if(keyCode == KeyEvent.KEYCODE_BACK ){
			  /*Log.e(TAG, "Unlock点击了返回键");
			  if(!MyTabHostFive.slideMenu.isMainScreenShowing()){
				  Log.e(TAG, "有SlideMenu");
				  MyTabHostFive.slideMenu.closeMenu();  
			  }
			  else{
				  Log.e(TAG, "没SlideMenu");*/
			 //警告对话框配置
			 AlertDialog dlg = new AlertDialog.Builder(unlock.this)
				.setTitle("家居乐").setMessage("是否退出")
				.setPositiveButton(
				R.string.confirm,
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
				int whichButton) {
					/* 左键事件 */		
					System.exit(0);						
						}
						}).setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
							
															/* 右键事件 */

														}
													}).create();
				 
			 dlg.show();//警告对话框展示
			  
								}
		  
		
		  else if(keyCode == KeyEvent.KEYCODE_MENU){
				 Log.e(TAG, "Unlock点击了menu");
				 if (MyTabHostFive.slideMenu.isMainScreenShowing()) {
					 MyTabHostFive.slideMenu.openMenu();
					} else {
						MyTabHostFive.slideMenu.closeMenu();
					}
			 }
		 return super.onKeyDown(keyCode, event);
		}
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		 isForeground = false;
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	
	//for receive customer msg from jpush server
			private MessageReceiver mMessageReceiver;
			public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
			public static final String KEY_TITLE = "title";
			public static final String KEY_MESSAGE = "message";
			public static final String KEY_EXTRAS = "extras";
			
			public void registerMessageReceiver() {
				mMessageReceiver = new MessageReceiver();
				IntentFilter filter = new IntentFilter();
				filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
				filter.addAction(MESSAGE_RECEIVED_ACTION);
				registerReceiver(mMessageReceiver, filter);
			}

			public class MessageReceiver extends BroadcastReceiver {

				@Override
				public void onReceive(Context context, Intent intent) {
					if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
		              String messge = intent.getStringExtra(KEY_MESSAGE);
		              String extras = intent.getStringExtra(KEY_EXTRAS);
		              StringBuilder showMsg = new StringBuilder();
		              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
		              if (!ExampleUtil.isEmpty(extras)) {
		            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
		              }
		              setCostomMsg(showMsg.toString());
					}
				}
			}
			
			private void setCostomMsg(String msg){
//				 if (null != msgText) {
//					 msgText.setText(msg);
//					 msgText.setVisibility(android.view.View.VISIBLE);
//		         }
			}
			
		    
}
						