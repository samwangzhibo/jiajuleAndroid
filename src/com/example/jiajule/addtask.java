package com.example.jiajule;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addtask extends Activity {
	private DatabaseHelper mdatabasehelper;
	private SQLiteDatabase db;
	private EditText et;
	private ProgressDialog TaskProgressDialog;
	URL url;
	String   date ;
	URLConnection mURLConnection;
	public static String DEFAULT_TIME="2014/10/9";
	String path;
	String id;
	String msg;
	static String op;
	String result;
	String TAG="wangzhibo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtask);
		//获得操作类型
		op=getIntent().getExtras().getString("op");
		Log.e(TAG, "op is "+op);
		if(op.equals(OldRenWuTie.UpdateTask)){
		id=getIntent().getExtras().getString(OldRenWuTie.RENWUTIE_ID);
		Log.e("wangzhibo", id);
		msg=getIntent().getExtras().getString("msg");
		}
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");     
		date =   sDateFormat.format(new   java.util.Date());  
		 
		//mdatabasehelper=new DatabaseHelper(addtask.this,"renwu.db",null,1);
		//db=mdatabasehelper.getReadableDatabase();
		Button bt=(Button) findViewById(R.id.addtask_submit);
	    et=(EditText) findViewById(R.id.addtask_et);
	    et.setText(msg);
		//String value=et.getText().toString();
		//Log.e("wangzhibo", value);
	    
	    //Intent i=getIntent();
	  /*  Bundle extras = getIntent().getExtras();
	    if(extras != null){
	    id=extras.getString(OldRenWuTie.RENWUTIE_ID);
	    msg=extras.getString(OldRenWuTie.MSG);
	    et.setText(msg);
	    ACTIOIN_MODE=OldRenWuTie.ACTIVITY_EDIT;*/
	    
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				SendTaskMsg mSendTaskMsg=new SendTaskMsg();
				mSendTaskMsg.execute("");
				/*
				String sql="insert into renwu(_id,neirong) values("+"null,'"+et.getText().toString()+"')";
				db.execSQL(sql);
				TaskProgressDialog.dismiss();
				Toast.makeText(addtask.this, "锟斤拷锟斤拷晒锟�, 3000).show();
				Intent intent=new Intent();
				intent.setAction("com.example.jiajule.broadcast");
				intent.putExtra("content", "锟斤拷锟铰硷拷庭锟斤拷锟斤拷");
				sendBroadcast(intent);
				Intent it=new Intent(addtask.this,renwutie.class);
				startActivity(it);
				finish();
				*/
			}
		});

	}class SendTaskMsg extends AsyncTask<String,Integer,String>{
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Log.e(TAG, op);
		if(op.equals(OldRenWuTie.AddTask)){
			Log.e(TAG, "op is add");
		path=URLAPI.ADD_TASK+"?username="+ActivtyUtil.GetUsernameSharedPre(addtask.this)+"&&msg="+et.getText().toString().trim()+"&&time="+date;
		}
		else {
			Log.e(TAG,"op is update");
		path=URLAPI.UPDATE_TASK+"?msg="+et.getText().toString().trim()+"&&id="+id;
		}
		Log.e(TAG, "addtask path:"+path);
		
		
		//String path2="http://192.168.66.112:8080/MyServlet/servlet/AddTaskServlet?username=wzb&msg=qwert&time=2014.10.1912:22:22";
		//"http://192.168.66.112:8080/MyServlet/servlet/AddTaskServlet?username=wzb&msg="+et.getText().toString()+"&time=2014.10.1912:22:22";
		//Toast.makeText(addtask.this, "It's error:"+path, 3000).show();
		try {
			//判断有无网络
			if(!NetWork.NetWorkpanduan(addtask.this)){
				
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
	
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if(result.equals(NetWork.UNCONNET_NETWORK)){
			TaskProgressDialog.dismiss();
			Toast.makeText(addtask.this,NetWork.UNCONNET_NETWORK, 3000).show();
		}
		if(result.equals("success")){
			TaskProgressDialog.dismiss();
			Toast.makeText(addtask.this,"添加成功", 3000).show();
/*			Intent it=new Intent(addtask.this,MyTabHostFive.class);
			startActivity(it);*/
			finish();
		}else if(result.equals("false")){
			TaskProgressDialog.dismiss();
			Toast.makeText(addtask.this,"添加失败，请重试", 3000).show();
		}else{
			TaskProgressDialog.dismiss();
			 Toast.makeText(addtask.this, "unknowen error", 3000).show();
		 }			
	}
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		TaskProgressDialog = new ProgressDialog(addtask.this);
		TaskProgressDialog.setMessage("服务器通信中。。。");
		TaskProgressDialog.show();
	}
	}
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context,String name,CursorFactory factory,int version) {
			super(context, "renwu.db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql = "CREATE TABLE renwu"  + " (" 
					+"_id integer primary key autoincrement,"+ "neirong text not null"  +")";
			Log.i("wangzhibo:createDB=", sql);
			db.execSQL(sql);

		}
		@Override
		public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
			
		}
	
	
	}
}
