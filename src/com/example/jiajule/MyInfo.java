package com.example.jiajule;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiajule.BMPUtil.UploadUtils;
import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;


public class MyInfo extends Activity{
	TextView tv_username,tv_userid,tv_phone,tv_homeid;
	Button bt_myinfo_select_unlockway;
	ImageView im_back,myinfo_icon;
	ProgressDialog mprogressdialog;
	String result;
	URL murl;
	String username;
	HttpURLConnection con;
	JSONArray array;
	String tag="wangzhibo";
	Button bt_logout;
	ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		InitView();
		InitWebRequest();
		
		//设置用户图像
		Bitmap bm = UploadUtils.GetBitmapIfFileEixt(ActivtyUtil.GetUsernameSharedPre(MyInfo.this), MyInfo.this);
		if(bm != null)
		myinfo_icon.setImageBitmap(bm);
		
		im_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(MyInfo.this,MyTabHostFive.class);
				startActivity(it);
				finish();
			}
		});
		bt_logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ActivtyUtil.PutUsernameSharedPre(MyInfo.this, "nobody");
				Intent it=new Intent(MyInfo.this,login.class);
				startActivity(it);
				finish();
			}
		});
		registerForContextMenu(bt_myinfo_select_unlockway);
		
	}
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
        case R.id.contextitem1:
        	ActivtyUtil.PutSharedPre(MyInfo.this,"jiugongge","unlockmode");
            Toast.makeText(this, "当前解锁模式为九宫格解锁", Toast.LENGTH_SHORT).show();
            break;
        case R.id.contextitem2:
        	ActivtyUtil.PutSharedPre(MyInfo.this,"mima","unlockmode");
            Toast.makeText(this, "当前解锁模式为密码解锁", Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
		
		
	
	private void InitWebRequest() {
		// TODO Auto-generated method stub
		
		GetWEBTask gt=new GetWEBTask();
		gt.execute("");
	}

	private void InitView() {
		// TODO Auto-generated method stub
		tv_username=(TextView)findViewById(R.id.myinfo_username);
		tv_userid=(TextView)findViewById(R.id.myinfo_userid);
		tv_homeid=(TextView)findViewById(R.id.myinfo_homeid);
		tv_phone=(TextView)findViewById(R.id.myinfo_phone);
		
		bt_logout=(Button)findViewById(R.id.logout);
		
		bt_myinfo_select_unlockway=(Button) findViewById(R.id.myinfo_select_unlockway);
		
		myinfo_icon = (ImageView) findViewById(R.id.login_im_icon);
		im_back=(ImageView) findViewById(R.id.myinfo_back);
	}
	class GetWEBTask extends AsyncTask<String,Integer,String> {//锟教筹拷AsyncTask 
        @Override 
       
        protected String doInBackground(String... params) {
        	 	username=ActivtyUtil.GetUsernameSharedPre(MyInfo.this);
             	String path=URLAPI.GETMYINFO()+"?username="+username;	
        	try {
				murl=new URL(path);
				try {
					if(!NetWork.NetWorkpanduan(MyInfo.this)){
						
						result=NetWork.UNCONNET_NETWORK;
						return result;
					}
					con=(HttpURLConnection) murl.openConnection();
					con.setDoOutput(true);  
					con.setDoInput(true);  
					con.setConnectTimeout(10000);  //设置连接超时为10s  
					con.setReadTimeout(10000);     //读取数据超时也是10s  
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
        		 Toast.makeText(MyInfo.this,NetWork.UNCONNET_NETWORK, 3000).show();
        	 }
        	 else if(result.equals(NetWork.ERROR_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(MyInfo.this,NetWork.ERROR_NETWORK, 3000).show(); 
        	 }
        	 else if(result.equals("lose")){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(MyInfo.this,"获取信息失败", 3000).show(); 
        	 }
        	 else{
        		 try {
        		 array=new JSONArray(result.toString());
        		 Log.e(tag,array.toString());
				//JSONObject json=new JSONObject(result);
				//name=json.getString("name");       			
        			for(int i=0;i<array.length();i++){
        				HashMap<String, String> mHashMap=new HashMap<String, String>();
        				Log.e(tag, "第"+(i+1)+"加入ArrayList");
        				mHashMap.put("userid", array.getJSONObject(i).getString("userid").toString());
        				mHashMap.put("username",array.getJSONObject(i).getString("username").toString());
        				 Log.e(tag, array.getJSONObject(i).getString("username").toString());
        				mHashMap.put("home_id", array.getJSONObject(i).getString("home_id").toString());
        				Log.e(tag, array.getJSONObject(i).getString("home_id").toString());
        				mHashMap.put("phone", array.getJSONObject(i).getString("phone").toString());
        				data.add(mHashMap);
        				Log.e(tag, "第"+(i+1)+"加入ArrayList完成");
        			}
        			 mprogressdialog.dismiss();
        			 HashMap<String, String> MHashMap=new HashMap<String, String>();
        			 MHashMap=data.get(0);
        			 Log.e(tag, MHashMap.isEmpty()+"");
        			 if(MHashMap != null){
        			 tv_username.setText(MHashMap.get("username"));
        			 tv_userid.setText(MHashMap.get("userid"));
        			 tv_homeid.setText(MHashMap.get("home_id"));
        			 tv_phone.setText(MHashMap.get("phone"));
        			 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Toast.makeText(MyInfo.this,"json解析出错", 3000).show();
			}
        		 
    			 
      }
          } 
          
        protected void onPreExecute () {
        	//锟斤拷 doInBackground(Params...)之前锟斤拷锟斤拷锟矫ｏ拷锟斤拷ui锟竭筹拷执锟斤拷        
			mprogressdialog=new ProgressDialog(MyInfo.this);
			mprogressdialog.setMessage("服务器通信中。。。");
			mprogressdialog.show();
			//mprogressdialog.setCancelable(false);
         } 
	}

}
