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
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.jiajule.util.MyWebTask;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.PictureChoose;
import com.example.jiajule.util.TaskAdapter;
import com.example.jiajule.util.URLAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OldRenWuTie extends Activity {
	//private DatabaseHelper mdatabasehelper;
	//private SQLiteDatabase db;
	//Cursor cursor;
	String id[]={"1","2","3"};
	String username[]={"wang1","zhang2","li3"};
	String msg[]={"msg1","msg2","msg3"};
	String time[]={"2014.10.13","2014.10.13","2014.10.13"};
	String DEFAULT_TIME="2014.10.13";
	URL murl;
	HttpURLConnection con;
	ProgressDialog mprogressdialog;
	String result;
	JSONArray array;
	int UPDATE=0;
	String json_test;
	ListView lv;
	public static String AddTask="addtask";//设置来指定操作类型
	public static String UpdateTask="updatetask";
	ArrayList<HashMap<String, Object>> data2=new ArrayList<HashMap<String,Object>>();
	
	Button bt_refresh;
	String tag="wangzhibo";
	public static String MSG="msg";
	public static String RENWUTIE_ID="id";
	public static final int ACTIVITY_CREATE = 0;
	public static final int ACTIVITY_EDIT = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasktie);
		Button bt=(Button) findViewById(R.id.tasktie_addtask);
		
		
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(OldRenWuTie.this,addtask.class);
				it.putExtra("op", AddTask);
				
				startActivity(it);
				
			}
		});
		
		bt_refresh=(Button)findViewById(R.id.tasktie_refresh);
		bt_refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Log.e("touched", "touched");
				InitGetJsonTask();
			}
		});
		
		InitGetJsonTask();	
/*		for(int i=0;i<id.length;i++){
			HashMap<String, Object> mHp=new HashMap<String, Object>();
			mHp.put("id", id[i]);
			mHp.put("username", username[i]);
			mHp.put("msg", msg[i]);
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");     
			String date =   sDateFormat.format(new   java.util.Date()); 
			mHp.put("time", time[i]);
			Log.e(date,"第"+i+1+"加入");
			data2.add(mHp);
		}
		Toast.makeText(this, data2.size()+"", 1000).show();
		SimpleAdapter mSimpleAdapter=new SimpleAdapter(OldRenWuTie.this, data2, R.layout.renwutie_list, new String[]{"msg","time","id","username"}, new int[]{R.id.renwutie_item_msg,R.id.renwutie_item_time,R.id.renwutie_item_id,R.id.renwutie_item_username});
		lv.setAdapter(mSimpleAdapter);*/
		
/*		mdatabasehelper=new DatabaseHelper(renwutie.this, "renwu.db", null, 1);
		db=mdatabasehelper.getReadableDatabase();
		cursor=db.query("renwu", new String[]{"_id","neirong"}, null,null , null, null, null);
		
		if(cursor.getCount()>0){
			tv.setVisibility(View.GONE);
		}
		SimpleCursorAdapter sca=new SimpleCursorAdapter(renwutie.this, R.layout.renwutie_list, cursor, new String[]{"_id","neirong"}, new int[]{R.id.renwutielist1,R.id.renwutielist2});
		lv.setAdapter(sca);*/
		
	
	}
	
	void InitGetJsonTask(){
		GetWEBTask gt=new GetWEBTask();
		gt.execute("");		
	}
	void InitListView(){
		//lv=(ListView)findViewById(R.id.renwutie_list);	
		//SimpleAdapter mSimpleAdapter=new SimpleAdapter(OldRenWuTie.this, data, R.layout.renwutie_list, new String[]{"id","username","msg","time"}, new int[]{R.id.renwutie_item_id,R.id.renwutie_item_username,R.id.renwutie_item_msg,R.id.renwutie_item_time});  	 
		//lv.setAdapter(mSimpleAdapter);	
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
//			Intent i = new Intent(Intent.ACTION_MAIN);
//			   i.setFlags(Intent.FL AG_ACTIVITY_NEW_TASK);
//			   i.addCategory(Intent.CATEGORY_HOME);
//			   startActivity(i);
//					}
//		 
//		 return super.onKeyDown(keyCode, event);
//		}

	// 需要对position和id进行一个很好的区分
	// position指的是点击的这个ViewItem在当前ListView中的位置
	// 每一个和ViewItem绑定的数据，肯定都有一个id，通过这个id可以找到那条数据。
/*	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
		Intent i = new Intent(this, addtask.class);
		i.putExtra("MSG", data.get(position).get("msg"));
		i.putExtra(RENWUTIE_ID, data.get(position).get("id"));
		startActivityForResult(i, ACTIVITY_EDIT);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		InitGetJsonTask();
		InitListView();
	}	*/
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		InitGetJsonTask();
	}
	void CreateDialog(final String ids,final String msg){
		CharSequence[] items = {"更改", "删除"};    
		    new AlertDialog.Builder(this)  
		     .setTitle("以下操作将会推送至家庭成员")  
		     .setItems(items, new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int which) {  
		             if( which == UPDATE ){  
		               //更改任务帖
		            	Intent it=new Intent(OldRenWuTie.this,addtask.class);
		            	it.putExtra("op", UpdateTask);
		            	it.putExtra(RENWUTIE_ID, ids);
		            	it.putExtra("msg", msg);
		            	startActivity(it);
	            }else{  	  
	            	//删除任务帖
	            	//需要刷新ListView
	            	MyWebTask mwt = new MyWebTask(OldRenWuTie.this);
	            	mwt.execute(URLAPI.DELETE_TASK()+"?id="+ids);
	            	InitGetJsonTask();
		             }  
		         }  
		    })  
		     .create().show();

	}
	class GetWEBTask extends AsyncTask<String,Integer,String> {//锟教筹拷AsyncTask 
        @Override 
        protected String doInBackground(String... params) {
        	
        	try {
				murl=new URL(URLAPI.GET_TASK_MSG());
				try {
					if(!NetWork.NetWorkpanduan(OldRenWuTie.this)){
						
						result=NetWork.UNCONNET_NETWORK;
						return result;
					}
					con=(HttpURLConnection) murl.openConnection();
					con.setDoOutput(true);  
					con.setDoInput(true);  
					con.setConnectTimeout(5000);  //设置连接超时为10s  
					con.setReadTimeout(5000);     //读取数据超时也是10s  
					con.setRequestMethod("GET");  
					con.setUseCaches(false);  
					con.connect();
					
					InputStream in=con.getInputStream();
					DataInputStream dis=new DataInputStream(in);
					result=dis.readLine();					
					in.close();
					dis.close();
					//tv.setText(result);
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
          
         protected void onProgressUpdate(Integer... progress) {//锟节碉拷锟斤拷publishProgress之锟襟被碉拷锟矫ｏ拷锟斤拷ui锟竭筹拷执锟斤拷 

          } 
  
         protected void onPostExecute(String result) {
        	 if(result.equals(NetWork.UNCONNET_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(OldRenWuTie.this,NetWork.UNCONNET_NETWORK, 3000).show();
        	 }
        	 else if(result.equals(NetWork.ERROR_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(OldRenWuTie.this,NetWork.ERROR_NETWORK, 3000).show(); 
        	 }
        	 else{
        		 try {
        			 mprogressdialog.dismiss();
        		 array=new JSONArray(result.toString());
        		 final ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
				//JSONObject json=new JSONObject(result);
				//name=json.getString("name");       			
        			for(int i=0;i<array.length();i++){
        				HashMap<String, String> mHashMap=new HashMap<String, String>();
        				Log.e(tag, "第"+(i+1)+"加入ArrayList");
        				mHashMap.put("id", array.getJSONObject(i).getString("id").toString());
        				//json_test=array.getJSONObject(i).getString("id").toString();
        				mHashMap.put("username",array.getJSONObject(i).getString("usename").toString());
        				 Log.e(tag, array.getJSONObject(i).getString("usename").toString());
        				mHashMap.put("msg", array.getJSONObject(i).getString("msg").toString());
        				Log.e(tag, array.getJSONObject(i).getString("msg").toString());
        				mHashMap.put("time", array.getJSONObject(i).getString("time").toString());
        				data.add(mHashMap);
        			}
        			
        			if(data.size() == 0)
        				Toast.makeText(OldRenWuTie.this,"data为空", 3000).show();
        			
                	 //tv.setText(json_test);
                	 
                	 
             	/*	if(data.size() > 0){
            			tv.setVisibility(View.GONE);
            			//bt_refresh.setVisibility(View.GONE);
            		}*/
             		Log.e(tag, "设置adapter");
        			lv=(ListView)findViewById(R.id.tasktie_lv);
        			SimpleAdapter mySimpleAdapter=new SimpleAdapter(OldRenWuTie.this, data, R.layout.renwutie_list, new String[]{"id","username","msg","time"}, new int[]{R.id.renwutie_item_id,R.id.renwutie_item_username,R.id.renwutie_item_msg,R.id.renwutie_item_time});  	
        			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							
							CreateDialog(data.get(position).get("id"),data.get(position).get("msg"));
						}
        				
					});
             		lv.setAdapter(mySimpleAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Toast.makeText(OldRenWuTie.this,"json解析出错", 3000).show();
			}
        	
      }
          } 
          
        protected void onPreExecute () {
        	//锟斤拷 doInBackground(Params...)之前锟斤拷锟斤拷锟矫ｏ拷锟斤拷ui锟竭筹拷执锟斤拷        
			mprogressdialog=new ProgressDialog(OldRenWuTie.this);
			mprogressdialog.setMessage("服务器通信中。。。");
			mprogressdialog.show();
			//mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//锟斤拷ui锟竭筹拷执锟斤拷 
          
          } 
          
     }; 
	/*private static class DatabaseHelper extends SQLiteOpenHelper {
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
	
	}*/

}
