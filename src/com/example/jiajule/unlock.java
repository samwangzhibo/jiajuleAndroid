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
			Log.e(TAG,"unlock��ʼ����");
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
						AlertDialog dlg2 = new AlertDialog.Builder(unlock.this).setIcon(R.drawable.icon3).setTitle("Warning").setMessage("����ʱ��û�����ý�����ʽ").setNegativeButton("ȥ����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
					int whichButton) {
						Intent it=new Intent(unlock.this,MyInfo.class);
						startActivity(it);
					}}).setPositiveButton("ʹ��Ĭ�Ͻ���ģʽ", new DialogInterface.OnClickListener() {
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
		.setTitle("����������")
		.setView(textEntryView).setPositiveButton(
		R.string.confirm,
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,int whichButton) {
			/* ����¼� */		
			EditText pwd = (EditText) textEntryView.findViewById(R.id.pwd);
			
			final  String pwd2 = pwd.getText()
			.toString();
			password = pwd2;
			
			GetWEBTask mygetwt=new GetWEBTask();
			mygetwt.execute("");
			
			
				//TextView text=(TextView) findViewById(R.id.text);
				//text.setText(username+"���");		

				}
				}).setNegativeButton(R.string.close,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {}
											}).create();
		
							dlg.show();
	

			//				createView();
				
	
	
	}
	class GetWEBTask extends AsyncTask<String,Integer,String> {//�̳�AsyncTask 
        @Override 
        protected String doInBackground(String... params) {
        	//String... params��ʾ���ǿɱ�����б�Ҳ����˵�������ķ����ܹ����ܵĲ��������ǿɱ�ģ������۶��٣����붼��String���͵ġ�
        	//����doInBackground("param1","param2","param3") ������doInBackground() ��
        	//�����ִ̨�е������ں�̨�߳�ִ�� 
        	
			String path=URLAPI.PASSUNLOCK()+"?username="+ActivtyUtil.GetUsernameSharedPre(unlock.this)+"&&password="+password+"&&time="+ActivtyUtil.GetNowTime(); 
			Log.e(TAG, path);
			try {
				//�ж���������
				if(!NetWork.NetWorkpanduan(unlock.this)){
					
					result=NetWork.UNCONNET_NETWORK;
					return result;
				}
				
				url=new URL(path);
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
			Log.e(TAG, "result��ֵ��"+result);
             return result; 
         } 
          
         protected void onProgressUpdate(Integer... progress) {//�ڵ���publishProgress֮�󱻵��ã���ui�߳�ִ�� 

          } 
  
         protected void onPostExecute(String result) {//��̨����ִ����֮�󱻵��ã���ui�߳�ִ�� 
        	 

				
        	 NetWork.NetResultChuli(unlock.this, result, mprogressdialog);
        	 
        	 if(result.equals("success")){
        		
				mprogressdialog.dismiss();
				ActivtyUtil.openToast(unlock.this,"�����ɹ�");
			 }else if(result.equals("lose")){
				 mprogressdialog.dismiss();
				 Toast.makeText(unlock.this, "���벻ƥ��", 3000).show();
			 }
			 else if(result.equals("error")){
				 mprogressdialog.dismiss();
				 Toast.makeText(unlock.this, "�������쳣��������", 3000).show();
				 }
			 else{
				 mprogressdialog.dismiss();
				 Toast.makeText(unlock.this, "unknowen error", 3000).show();
			 }		 
          } 
          
        protected void onPreExecute () {
        	//�� doInBackground(Params...)֮ǰ�����ã���ui�߳�ִ��        
			mprogressdialog=new ProgressDialog(unlock.this);
			mprogressdialog.setMessage("������ͨ���С�������");
			mprogressdialog.show();
			mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//��ui�߳�ִ�� 
          
          } 
          
     }; 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e(TAG, "Unlock����˰���");
		 // ����Ƿ��ؼ�,ֱ�ӷ��ص�����

		  if(keyCode == KeyEvent.KEYCODE_BACK ){
			  /*Log.e(TAG, "Unlock����˷��ؼ�");
			  if(!MyTabHostFive.slideMenu.isMainScreenShowing()){
				  Log.e(TAG, "��SlideMenu");
				  MyTabHostFive.slideMenu.closeMenu();  
			  }
			  else{
				  Log.e(TAG, "ûSlideMenu");*/
			 //����Ի�������
			 AlertDialog dlg = new AlertDialog.Builder(unlock.this)
				.setTitle("�Ҿ���").setMessage("�Ƿ��˳�")
				.setPositiveButton(
				R.string.confirm,
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
				int whichButton) {
					/* ����¼� */		
					System.exit(0);						
						}
						}).setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
							
															/* �Ҽ��¼� */

														}
													}).create();
				 
			 dlg.show();//����Ի���չʾ
			  
								}
		  
		
		  else if(keyCode == KeyEvent.KEYCODE_MENU){
				 Log.e(TAG, "Unlock�����menu");
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
						