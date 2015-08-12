package com.example.jiajule;

//import com.xmobileapp.cammonitor.R;

import com.other.lampcontrol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class warning extends Activity {
	BluetoothAdapter mBluetoothAdapter;
	public static ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiadian);
	
//		Log.e("wangzhibo", "flag="+flag);
//		
	
		ImageView im=(ImageView) findViewById(R.id.jiadian_goto_home);
		im.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				mProgressDialog=new ProgressDialog(warning.this);
				mProgressDialog.setMessage("please wait a minite");
				mProgressDialog.show();
						Intent it=new Intent(warning.this,lampcontrol.class);
						startActivity(it);					
					}
				});
		
		ImageView im2=(ImageView) findViewById(R.id.jiadian_bluetooth);
		im2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				if(!mBluetoothAdapter.isEnabled()){
					Toast.makeText(warning.this, "蓝牙未打开，请打开", 300).show();
					Intent enableIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				    startActivityForResult(enableIntent, 300);					
				}
				else if(mBluetoothAdapter.isEnabled()){
					Toast.makeText(warning.this, "蓝牙已成功开启，请使用", 300).show();
				}
						
					}
				});
										

	}
//跳到主页
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
//			Intent i = new Intent(Intent.ACTION_MAIN);
//			   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			   i.addCategory(Intent.CATEGORY_HOME);
//			   startActivity(i);
//					}
//		 
//		 return super.onKeyDown(keyCode, event);
//		}
	

}
