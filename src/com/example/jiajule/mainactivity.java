package com.example.jiajule;
import java.util.ArrayList;
import java.util.List;


import com.example.jiajule.R.raw;
import com.example.jiajule.util.ActivtyUtil;
import com.other.lampcontrol;
//import com.xmobileapp.cammonitor.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class mainactivity extends Activity implements OnClickListener, OnItemClickListener {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 22;
	private static Button btnSearch;
	private EditText keyword;
	private ListView lvCandidate;
	private String path="http://image.baidu.com/i?ct=201326592&&word=";
	private Handler handler;
	private String flag="0";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice);
		if(ActivtyUtil.GetYuyinSwitcher(this)){
			MediaPlayer player=MediaPlayer.create(mainactivity.this, R.raw.xiaomi);
			player.start();
		}
//		handler=new Handler(){
//			@Override
//			public void handleMessage(Message msg){
//				switch(msg.what){
//				case 0:
//					Intent it=new Intent(mainactivity.this,CamMonitorClient.class);
//					startActivity(it);
//				case 1:
//					Intent it2=new Intent(mainactivity.this,unlock.class);
//					startActivity(it2);
//				}
//					
//			}
			
//		};
		
		btnSearch = (Button) this.findViewById(R.id.btnSearch);
		keyword = (EditText) this.findViewById(R.id.keyword);
		lvCandidate = (ListView) this.findViewById(R.id.lvCandidate);
		

		btnSearch.setOnClickListener(this);
		lvCandidate.setOnItemClickListener(this);
	}

	public void onClick(View v) {
		Toast.makeText(this, "searching ..... ", 0).show(); 
		// do search
		String vlaue=keyword.getText().toString().trim();
//		if(keyword.getText().toString().trim().getBytes().length<3){
//			Toast.makeText(mainactivity.this,"too short,please reinput", 3000).show();
//		}
//		else 
		if(keyword.getText().toString().matches("监控")){
//			Intent it=new Intent(mainactivity.this,CamMonitorClient.class);
//			startActivity(it);
			myThread mythread=new myThread();
			mythread.start();
//			mythread.sendmsg(0);
//			Message msg=new Message();
//			msg.what=0;
//			handler.sendMessage(msg);
			try {
				mythread.sleep(1000);
				Intent it=new Intent(mainactivity.this,CamMonitorClient.class);
				startActivity(it);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyword.getText().toString().contains("解锁")){
//			myThread mythread=new myThread();
//			mythread.start();
//			if(flag.matches("1")){
			myThread mythread=new myThread();
			mythread.start();

			try {
				mythread.sleep(1000);
				Intent it2=new Intent(mainactivity.this,unlock.class);
				startActivity(it2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(keyword.getText().toString().contains("家电")){
			
			Intent it=new Intent(mainactivity.this,warning.class);
			startActivity(it);
		}else if(keyword.getText().toString().substring(0, 3).matches("我要看")){
			String str=keyword.getText().toString();
			String str2=str.substring(3);
			System.out.println(str2);
			Log.i("wangzhibo",str2);
			//path=path+str2;
			Log.i("wangzhibo",path+str2);
			Intent it=new Intent(Intent.ACTION_VIEW);
			it.setData(Uri.parse(path.concat(str2)));
			startActivity(it);	
		}
		else if(keyword.getText().toString().contains("听音乐")){
			Intent it=new Intent(mainactivity.this,mymusic.class);
			startActivity(it);
		}
		else if(keyword.getText().toString().substring(0, 3).matches("我要看")){
			Log.e("wangzhibo","mainactivity_woyaoting");
			//Intent it=new Intent(mainactivity.this,TabMusicActivity.class);
			//startActivity(it);
			mainactivity.this.finish();
			
		}
		else if(keyword.getText().toString().contains("爸爸打电话")){
			Intent intent = new Intent();
		    intent.setAction("android.intent.action.CALL");
		    intent.setData(Uri.parse("tel:13568918106"));
		    startActivity(intent);
		}else if(keyword.getText().toString().contains("给爸爸发短信")){
			 Intent intent2 = new Intent();

		        intent2.setAction(Intent.ACTION_SENDTO);

		        intent2.setData(Uri.parse("smsto:13888888888"));

		        intent2.putExtra("sms_body", "你好，我是家居乐语音助手。");

		        startActivity(intent2); 
		}
		else{
			Toast.makeText(mainactivity.this,"please reinput", 3000).show();
		}

	}

	public void speak(View view) {
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm
				.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) { 
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("本地不存在Voice Search，立即 下载？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setData(Uri
							.parse("http://img.yingyonghui.com/apk/1494/com.google.android.voicesearch.1310128057965.apk"));
					startActivity(intent);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (matches != null) {
				//btnSearch.setClickable(true);
				lvCandidate.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
				//keyword.setText(matches.get(0));
				keyword.setText(matches.get(0));
				if(matches.get(0).contains("监控")){
					Intent it=new Intent(mainactivity.this,CamMonitorClient.class);
					startActivity(it);
				}
				else if(matches.get(0).contains("解锁")){
					Intent it=new Intent(mainactivity.this,unlock.class);
					startActivity(it);
				}
				else if(matches.get(0).contains("家电")){
					Intent it=new Intent(mainactivity.this,warning.class);
					MediaPlayer player=MediaPlayer.create(mainactivity.this, R.raw.loading);
					player.start();
					startActivity(it);
				}else if(matches.get(0).substring(0, 3).matches("我要")){
					String str=matches.get(0);
					String str2=str.substring(3);
					System.out.println(str2);
					Log.i("wangzhibo",str2);
					//path=path+str2;
					Log.i("wangzhibo",path+str2);
					Intent it=new Intent(Intent.ACTION_VIEW);
					it.setData(Uri.parse(path.concat(str2)));
					startActivity(it);	
				}
				else if(matches.get(0).matches("我要听")){
					MediaPlayer player=MediaPlayer.create(mainactivity.this, R.raw.loading);
					player.start();
					Intent it=new Intent(mainactivity.this,mymusic.class);
					startActivity(it);
				}
				else if(matches.get(0).substring(0, 3).matches("锟斤拷要锟斤拷")){
					/*Log.e("wangzhibo","mainactivity_woyaoting");
					//Intent it=new Intent(mainactivity.this,TabMusicActivity.class);
					MediaPlayer player=MediaPlayer.create(mainactivity.this, R.raw.loading);
					player.start();
					//startActivity(it);
					mainactivity.this.finish();			*/		
				}
				else if(keyword.getText().toString().contains("爸爸打电话")){
					Intent intent = new Intent();
				    intent.setAction("android.intent.action.CALL");
				    intent.setData(Uri.parse("tel:13568918106"));
				    startActivity(intent);
				}
				else if(keyword.getText().toString().contains("张三发短信")){
					 Intent intent2 = new Intent();

				        intent2.setAction(Intent.ACTION_SENDTO);

				        intent2.setData(Uri.parse("smsto:13888888888"));

				        intent2.putExtra("sms_body", "你好，我是家居乐语音助手。");

				        startActivity(intent2); 
				}

			}
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String item = (String) parent.getItemAtPosition(position);
		keyword.setText(item);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
			Intent i = new Intent(Intent.ACTION_MAIN);
			   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   i.addCategory(Intent.CATEGORY_HOME);
			   startActivity(i);
			}
		 
		 return super.onKeyDown(keyCode, event);
		}

	class myThread extends Thread{
		public void run(){
			MediaPlayer player=MediaPlayer.create(mainactivity.this, R.raw.loading);
			player.start();
		}
//		public void sendmsg(int a){
//			Message msg=new Message();
//			msg.what=a;
//			handler.sendMessage(msg);
//		}
	}
}