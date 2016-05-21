package com.example.jiajule;

//import org.demo.custon_view.MyCustomViewActivity;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.jiajule.MyCustomViewActivity2.GetWEBTask;
import com.example.jiajule.push.ExampleUtil;
//import com.xmobileapp.cammonitor.R;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
//基本设置
public class setting extends Activity{
	EditText et;
	Button bt;
	Button bt2;
	Button tag_bt;
	EditText tag_et;
	private ToggleButton voiceSwitcher;
	private ProgressDialog mprogressdialog;
	private String result;
	public void onCreate(Bundle savedInstanceState) {	
			super.onCreate(savedInstanceState);
			setContentView(R.layout.setting);
			initView();
			//et=(EditText) findViewById(R.id.setting_pass_);
			bt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					
//					String str=et.getText().toString();
//					Editor sharedata = getSharedPreferences("data", 0).edit();
//					sharedata.putString("pws",str);
//					sharedata.commit();
//					Toast.makeText(setting.this, "设置成功", 3000).show();
//					Intent it=new Intent(setting.this,MyTabHostFive.class);
//					startActivity(it);
					LayoutInflater factory = LayoutInflater.from(setting.this);
					final View textEntryView = factory.inflate(R.layout.layoutdialog2, null);
					AlertDialog dlg = new AlertDialog.Builder(setting.this)
					.setTitle("请设置密码")
					.setView(textEntryView).setPositiveButton(
					R.string.confirm,
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
					int whichButton) {
						/* 左键事件 */		
						
						EditText pwd = (EditText) textEntryView
						.findViewById(R.id.pwd);
						
						final  String pwd2 = pwd.getText()
						.toString();
					
						/*Editor sharedata = getSharedPreferences("data", 0).edit();
						sharedata.putString("pws",pwd2);
						sharedata.commit();*/
						connect(pwd2);
						
							}
							}).setNegativeButton(R.string.close,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
								
																/* 右键事件 */

															}
														}).create();
										dlg.show();
					
				}
			});
			bt2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent it = new Intent(setting.this,MyCustomViewActivity2.class);
					startActivity(it);
					
				}
			});
			tag_bt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String tag=tag_et.getText().toString();
					 // 检查 tag 的有效性,TextUtil的优点是只判断实体，不用担心tag是否为空
					if (TextUtils.isEmpty(tag)) {
						Toast.makeText(setting.this,R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
						return;
					}
					// ","隔开的多个 转换成 Set
					String[] sArray = tag.split(",");
					Set<String> tagSet= new LinkedHashSet<String>();
					for(String sTagItem:tagSet){
						if(!ExampleUtil.isValidTagAndAlias(sTagItem)){
							Toast.makeText(setting.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
							return;
						}
						tagSet.add(sTagItem);
					}//调用JPush API设置Tag
					mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
					//JPushInterface.setAliasAndTags(getApplicationContext(), arg1, arg2, arg3);
				}
			});
			final SharedPreferences settings = getSharedPreferences(jiajulemainActivity.SETTING_INFOS,0);
			boolean flag=settings.getBoolean("voiceSwitcher", true);
			if (flag) {
				voiceSwitcher.setChecked(true);
			}else {
				voiceSwitcher.setChecked(false);
			}
			voiceSwitcher.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//当按钮第一次被点击时候响应事件
					if (voiceSwitcher.isChecked()) {
						Toast.makeText(setting.this, "语音开启", Toast.LENGTH_SHORT).show();
						Editor dataEditer = settings.edit();
						dataEditer.putBoolean("voiceSwitcher", true);
						dataEditer.commit();
					}else{
						Toast.makeText(setting.this, "语音关闭", Toast.LENGTH_SHORT).show();
						Editor dataEditer = settings.edit();
						dataEditer.putBoolean("voiceSwitcher", false);
						dataEditer.commit();
					}
				}
			});
	}
	protected void connect(String mypass) {
		// TODO Auto-generated method stub
		GetWEBTask get=new GetWEBTask(setting.this, mprogressdialog, result, mypass);
		get.execute("");
	}
	private void initView() {
		// TODO Auto-generated method stub
	    bt=(Button) findViewById(R.id.unlock_pass);
		bt2=(Button) findViewById(R.id.unlock_jiugongge);
		tag_bt=(Button) findViewById(R.id.setting_tag_bt);
		tag_et=(EditText) findViewById(R.id.setting_tag);
		voiceSwitcher = (ToggleButton) findViewById(R.id.voice_switcher);
	}
	/**
	 * alias调试 
	 */
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
            ExampleUtil.showToast(logs, getApplicationContext());
        }
	    
	};
	/**
	 * tag调试
	 */
	 private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

	        @Override
	        public void gotResult(int code, String alias, Set<String> tags) {
	            String logs ;
	            switch (code) {
	            case 0:
	                logs = "Set tag and alias success";
	                Log.i(TAG, logs);
	                break;
	                
	            case 6002:
	                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
	                Log.i(TAG, logs);
	                if (ExampleUtil.isConnected(getApplicationContext())) {
	                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
	                } else {
	                	Log.i(TAG, "No network");
	                }
	                break;
	            
	            default:
	                logs = "Failed with errorCode = " + code;
	                Log.e(TAG, logs);
	            }
	            
	            ExampleUtil.showToast(logs, getApplicationContext());
	        }
	        
	    };
	private static final int MSG_SET_TAGS = 1002;
	private static final int MSG_SET_ALIAS = 1001;
	protected static final String TAG = "wangzhibo";
	 private final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(android.os.Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what) {	
	            case MSG_SET_ALIAS:
	                Log.d(TAG, "Set alias in handler.");
	                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
	         // setAliasAndTags(Context context, String alias, Set<String> tags, TagAliasCallback callback)
	                break;
	            case MSG_SET_TAGS:
	                Log.d(TAG, "Set tags in handler.");
	                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
	                break;
	                
	            default:
	                Log.i(TAG, "Unhandled msg - " + msg.what);
	            }
	        }
	    };
	
	
}