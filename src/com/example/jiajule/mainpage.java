package com.example.jiajule;




//import com.xmobileapp.cammonitor.R;
import com.other.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mainpage extends Activity {
	private static final int DIALOG1_KEY=0;
	private static final int FIRST_MENU_ID=0;
	public static final int SECOND_MENU_ID = Menu.FIRST + 1;
	public static final int THIRD_MENU_ID = Menu.FIRST + 2;
	DatabaseHelper mOpenHelper;

	private static final String DATABASE_NAME = "user.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "unlockrecord";
	private static final String ID="_id";
	private static final String TITLE = "user";
	private static final String BODY = "password";
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql = "CREATE TABLE " + TABLE_NAME + " (" + TITLE
					+ " text not null, " + BODY + " text not null," + ID+" text not null AUTO_INCREMENT "+");";
			Log.i("wangzhibo:createDB=", sql);
			db.execSQL(sql);

		}
		@Override
		public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
			
		}
	
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);

			setContentView(R.layout.mainpage);
			mOpenHelper = new DatabaseHelper(this);
			//CreateTable();//创建数据库
			
			//TabHost tabHost = getTabHost();
			//LayoutInflater.from(this).inflate(R.layout.mainpage,
					//tabHost.getTabContentView(), true);
			//tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("设置").setContent(new Intent(this, setting.class)));	
			//tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("登陆").setContent(R.id.TextView01));
					
			//tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("我的位置")
					//.setContent(
						//	new Intent(Intent.ACTION_VIEW, Uri.parse("geo:39.904667,116.408198"))
							//	.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
			
	 
			Button btn=(Button) findViewById(R.id.changemodel);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//进度框
					showDialog(DIALOG1_KEY);
					//跳转
					Intent it=new Intent(mainpage.this,mainactivity.class);
					startActivity(it);
				}
			});
			Button btn2=(Button) findViewById(R.id.lampcontrol);
			btn2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
					Intent intent = new Intent();
					intent.setClass(mainpage.this,lampcontrol.class);
					mainpage.this.startActivity(intent);
					
				}
			});
			Button btn3=(Button) findViewById(R.id.unlock);
			btn3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					
					Intent it=new Intent(mainpage.this,unlock.class);
					startActivity(it);
					
				}
			});
			Button btn4=(Button) findViewById(R.id.tag_bt);
			btn4.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent it=new Intent(mainpage.this,CamMonitorClient.class);
					startActivity(it);
				}
			});
			Button btn5=(Button) findViewById(R.id.myplace);
			btn5.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showDialog(DIALOG1_KEY);
					// 打开地图
					Uri uri = Uri.parse("geo:30.907499443727346,103.8959187269206"
);
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					mainpage.this.startActivity(it);
				}
			});
			 Button login = (Button) findViewById(R.id.login);
			 login.setOnClickListener(new View.OnClickListener() {
			          public void onClick(View v) {
		          
			  //do something
		LayoutInflater factory = LayoutInflater.from(mainpage.this);
		final View textEntryView = factory.inflate(R.layout.layoutdialog, null);
		AlertDialog dlg = new AlertDialog.Builder(mainpage.this)
		.setIcon(R.drawable.loginimage).setTitle("请登录")
		.setView(textEntryView).setPositiveButton(
		R.string.confirm,
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,
		int whichButton) {
			/* 左键事件 */		
			
			EditText user = (EditText) textEntryView
			.findViewById(R.id.username);
			EditText pass = (EditText) textEntryView
			.findViewById(R.id.password);
			final  String username = user.getText()
			.toString();
			final String password = pass.getText()
			.toString();
			
			//insertItem(username,password);//插入数据
			Toast.makeText(
				mainpage.this,
				username + " 登录了"
				,
				Toast.LENGTH_SHORT).show();
			
				//TextView text=(TextView) findViewById(R.id.text);
				//text.setText(username+"你好");		

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
			 Button register=(Button) findViewById(R.id.register);
				register.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(DIALOG1_KEY);
						// 注册
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse("http://dsagf.html"));
						startActivity(i);
					}
				});
	}
	private void CreateTable() {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + TITLE
				+ " text not null, " + BODY + " text not null ," +ID+" text not null AUTO_INCREMENT "+");";
		Log.i("wangzhibo:createDB=", sql);

		try {
			db.execSQL("DROP TABLE IF EXISTS diary");
			db.execSQL(sql);
			setTitle("数据表成功重建");
		} catch (SQLException e) {
			setTitle("数据表重建错误");
		}
	}
	private void insertItem(String a,String b) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		//EditText ed=(EditText) findViewById(R.id.username);
		String sql1 = "insert into " + TABLE_NAME + " (" + TITLE + ", " + BODY
				+ ") values("+a+", "+b+");";
		//String sql2 = "insert into " + TABLE_NAME + " (" + TITLE + ", " + BODY
				//+ ") values('icesky', 'android的发展真是迅速啊');";
		try {
			Log.i("wangzhibo:sql1=", sql1);
			//Log.i("haiyang:sql2=", sql2);
			db.execSQL(sql1);
			//db.execSQL(sql2);
			setTitle("输入成功");
		} catch (SQLException e) {
			setTitle("输入失败");
		}
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, FIRST_MENU_ID, 0, R.string.menu1);
		menu.add(0, SECOND_MENU_ID, 0, R.string.menu2);
		menu.add(0,THIRD_MENU_ID,0,R.string.menu3);
//		menu.add(0, RED_MENU_ID, 0, R.string.menu1).setIcon(R.drawable.redimage);
//		menu.add(0, GREEN_MENU_ID, 0, R.string.menu2).setIcon(R.drawable.yellowimage);
//		menu.add(0, BLUE_MENU_ID, 0, R.string.menu3).setIcon(R.drawable.blueimage);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case FIRST_MENU_ID:
			//mTextView.setBackgroundColor(Color.RED);
			System.exit(0);
			return true;
		case SECOND_MENU_ID:
			//mTextView.setBackgroundColor(Color.RED);
			Intent it=new Intent(mainpage.this,setting.class);
			startActivity(it);
			return true;	
	
		
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 // 如果是返回键,直接返回到桌面
		 if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
			 AlertDialog dlg = new AlertDialog.Builder(mainpage.this)
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
									dlg.show();
								}
		 
		 return super.onKeyDown(keyCode, event);
		}
}
