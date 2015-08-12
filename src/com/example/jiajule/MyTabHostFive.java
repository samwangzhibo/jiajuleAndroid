 package com.example.jiajule;


import cn.jpush.android.api.JPushInterface;


import com.example.jiajule.BMPUtil.UploadUtils;
import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.view.SlideMenu;
import com.example.jiajule.view.unlock_new;
import com.jiajule.floating.FloatView;

import com.other.lampcontrol;
//import com.xmobileapp.cammonitor.R;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class MyTabHostFive extends TabActivity {
	/**
	 * TabHost鎺т欢
	 */
	private TabHost mTabHost;
	private static final int DIALOG1_KEY=0;//dialog
	private static final int FIRST_MENU_ID=0;//menu1
	public static final int SECOND_MENU_ID = Menu.FIRST + 1;//menu2
	public static final int THIRD_MENU_ID = Menu.FIRST + 2;//menu3
	public static final int FOUR_MENU_ID = Menu.FIRST + 3;
	private static final String TAG = "wangzhibo";
	private TextView slide_menu1,slide_menu2,slide_menu3,slide_menu4;
	public static SlideMenu  slideMenu;
	/**
	 * TabWidget
	 */
	private TabWidget mTabWidget;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_new);
		SetListener();
		MediaPlayer player=MediaPlayer.create(MyTabHostFive.this, R.raw.xiaomi);
		mTabHost = this.getTabHost();
		
		mTabHost.setPadding(mTabHost.getPaddingLeft(),
				mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
				mTabHost.getPaddingBottom() - 5);
		
		Resources rs = getResources();

		
		InitSlideMenu();
		Intent layout1intent = new Intent();
		layout1intent.setClass(this, unlock.class);
		TabHost.TabSpec layout1spec = mTabHost.newTabSpec("layout1");
		layout1spec.setIndicator("",
				rs.getDrawable(R.drawable.yunsuo));
		layout1spec.setContent(layout1intent);
		mTabHost.addTab(layout1spec);
		
		Log.e(TAG,"加载第二个TabSpec");
		Intent layout2intent = new Intent();
		layout2intent.setClass(this, CamMonitorClient.class);
		TabHost.TabSpec layout2spec = mTabHost.newTabSpec("layout2");
		layout2spec.setIndicator("",
				rs.getDrawable(R.drawable.jiankong));
		layout2spec.setContent(layout2intent);
		mTabHost.addTab(layout2spec);


		
		Intent layout3intent = new Intent();
		layout3intent.setClass(this, mainactivity.class);
		//setClass()指明方向
	    //形成TabSpec
		TabHost.TabSpec layout3spec = mTabHost.newTabSpec("layout3");
		//setIndicator设置
		layout3spec.setIndicator("",
				rs.getDrawable(R.drawable.yuyin_));
		//设置Intent
		layout3spec.setContent(layout3intent);
		//TabHost添加TabSpec
		mTabHost.addTab(layout3spec);
		
		Intent layout4intent = new Intent();
		layout4intent.setClass(this, warning.class);
		TabHost.TabSpec layout4spec = mTabHost.newTabSpec("layout4");
		layout4spec.setIndicator("",
				rs.getDrawable(R.drawable.jiandian));
		layout4spec.setContent(layout4intent);
		mTabHost.addTab(layout4spec);

		
		Intent layout5intent = new Intent();
		layout5intent.setClass(this,OldRenWuTie.class);
		TabHost.TabSpec layout5spec = mTabHost.newTabSpec("layout5");
		layout5spec.setIndicator("",
				rs.getDrawable(R.drawable.renwutie));
		layout5spec.setContent(layout5intent);
		mTabHost.addTab(layout5spec);
		
		//设置TabHost的背景颜色      
		//mTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));     
		//设置TabHost的背景图片资源      
	    //mTabHost.setBackgroundResource(R.drawable.color);     

		
		ImageView im2=(ImageView) findViewById(R.id.unlock_menu);
		if(im2 == null){
			Log.e(TAG, "im2 == null");
		} 
		else{
		im2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e(TAG, "MYtab点击了Im");
				ActivtyUtil.MenuSlideMenu();
			}
		});
		}
		
		mTabWidget = mTabHost.getTabWidget();
		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			
			View view = mTabWidget.getChildAt(i);
			
			if (mTabHost.getCurrentTab() == i) {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_bg_pressed));
				
				
			} else {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.color));
			}
			
			// tabWidget.setBackgroundColor(Color.WHITE);
			
			// tabWidget.setBackgroundResource(R.drawable.icon);
			
			
			
		//mTabWidget.getChildAt(i).getLayoutParams().height = 150;
			//TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
				//	android.R.id.title);
			
			//tv.setTextColor(Color.rgb(49, 116, 171));/* 璁剧疆tab鍐呭瓧浣撶殑棰滆�?*/
		}

		
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < mTabWidget.getChildCount(); i++) {
					View view = mTabWidget.getChildAt(i);
					if (mTabHost.getCurrentTab() == i) {
						view.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.number_bg_pressed));
					} else {
						view.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.color));
					}
				}
			}
		});
		
		
	}
	private void InitSlideMenu() {
		// TODO Auto-generated method stub
		ImageView mytab_icon = (ImageView) findViewById(R.id.mytab_icon);
		Bitmap bm = UploadUtils.GetBitmapIfFileEixt(ActivtyUtil.GetUsernameSharedPre(MyTabHostFive.this), MyTabHostFive.this);
		if(bm != null)
		mytab_icon.setImageBitmap(bm);
		mytab_icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MyTabHostFive.this,MyInfo.class);
				startActivity(it);	
			}
		});
		
		slide_menu1=(TextView) findViewById(R.id.slide_menu1);
		slide_menu1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MyTabHostFive.this,MyInfo.class);
				startActivity(it);
			}
		});
		slide_menu2=(TextView) findViewById(R.id.slide_menu2);
		slide_menu3=(TextView) findViewById(R.id.slide_menu3);
		slide_menu4=(TextView) findViewById(R.id.slide_menu4);
		slide_menu2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MyTabHostFive.this,setting.class);
				startActivity(it);
			}
		});
		slide_menu3.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		ActivtyUtil.PutUsernameSharedPre(MyTabHostFive.this, "nobody");
		Intent it=new Intent(MyTabHostFive.this,login.class);
		startActivity(it);
		finish();
	}
});
		slide_menu4.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}
});
		
	}
	private void SetListener() {
		// TODO Auto-generated method stub
		slideMenu=(SlideMenu) findViewById(R.id.slide_menu);
		if(slideMenu != null)
			Log.e(TAG, "slidemenu不为空");
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	public void onDestroy() {
		super.onDestroy();

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle("加载提示");
			dialog.setMessage("请等待，正在Loading...");
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
	}
	
/*	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, FIRST_MENU_ID, 0, R.string.menu1);
		menu.add(0, SECOND_MENU_ID, 0, R.string.menu2);
		menu.add(0,THIRD_MENU_ID,0,R.string.menu3);
		menu.add(0, FOUR_MENU_ID, 0, R.string.menu4);
//		menu.add(0, RED_MENU_ID, 0, R.string.menu1).setIcon(R.drawable.redimage);
//		menu.add(0, GREEN_MENU_ID, 0, R.string.menu2).setIcon(R.drawable.yellowimage);
//		menu.add(0, BLUE_MENU_ID, 0, R.string.menu3).setIcon(R.drawable.blueimage);
		return true;
	}*/
	//menu选择
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case FIRST_MENU_ID:
			Intent it=new Intent(MyTabHostFive.this,setting.class);
			startActivity(it);
			return true;
		case SECOND_MENU_ID:
			Intent intent=new Intent();
			intent.setClass(MyTabHostFive.this, MyInfo.class);
			startActivity(intent);
			intent.setAction("com.example.jiajule.broadcast");
			intent.putExtra("content", "陌生人闯入");
			sendBroadcast(intent);
			return true;
		case FOUR_MENU_ID:
			ActivtyUtil.PutUsernameSharedPre(MyTabHostFive.this, "nobody");
			Intent jumptoLogin=new Intent(MyTabHostFive.this,login.class);
			startActivity(jumptoLogin);
			finish();
		case THIRD_MENU_ID:
			System.exit(0);
		}
		return super.onOptionsItemSelected(item);
		
	}*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e(TAG, "Tab点击了按键");
		if(keyCode == KeyEvent.KEYCODE_MENU){
			 if(slideMenu == null)Log.e(TAG, "slidemenu == null");
			 else Log.e(TAG, "slidemenu != null");
			 
			 if (slideMenu.isMainScreenShowing()) {
					slideMenu.openMenu();
				} else {
					slideMenu.closeMenu();
				}
		 }
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){						
		/*AlertDialog dlg = new AlertDialog.Builder(MyTabHostFive.this)
				.setTitle("家居乐").setMessage("是否退出")
				.setPositiveButton(
				R.string.confirm,
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
				int whichButton) {
					 左键事件 		
					System.exit(0);						
						}
						}).setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
							
															 右键事件 

														}
													}).create();
				 
			 dlg.show();	*/	
			Intent it=new Intent(Intent.ACTION_MAIN);
			it.addCategory(Intent.CATEGORY_HOME);   

			startActivity(it); 
		}
		
		 return super.onKeyDown(keyCode, event);
		}

}

