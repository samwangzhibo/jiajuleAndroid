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

import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HomeInfo extends Activity {
	private LinearLayout ll;
	private ProgressDialog mprogressdialog;
	private String result;
	private String home_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeinfo);
		initView();
		getWebData();
	}

	private void initView() {
		ll = (LinearLayout) findViewById(R.id.home_info_ll);
		Intent it = getIntent();
		if (it != null && it.getExtras() != null) {
			home_id = it.getExtras().getString("home_id");
		}
	}
	private void getWebData(){
		GetWEBTask task = new GetWEBTask();
		task.execute(URLAPI.ShowHomePeople()+"?home_id="+home_id);
	}
	class GetWEBTask extends AsyncTask<String,Integer,String> {
        @Override 
        protected String doInBackground(String... params) {
        	
        	try {
				URL murl=new URL(params[0]);
				try {
					if(!NetWork.NetWorkpanduan(HomeInfo.this)){
						
						result=NetWork.UNCONNET_NETWORK;
						return result;
					}
					HttpURLConnection con=(HttpURLConnection) murl.openConnection();
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
          
         protected void onProgressUpdate(Integer... progress) {

         } 
  
         protected void onPostExecute(String result) {
        	 if(result.equals(NetWork.UNCONNET_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(HomeInfo.this,NetWork.UNCONNET_NETWORK, 3000).show();
        	 }
        	 else if(result.equals(NetWork.ERROR_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(HomeInfo.this,NetWork.ERROR_NETWORK, 3000).show(); 
        	 }
        	 else{
        		 try {
        			 mprogressdialog.dismiss();
        			//UI填充
         			ll.removeAllViews();
        		 JSONArray array=new JSONArray(result.toString());
        		 //final ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();  			
        			for(int i=0;i<array.length();i++){
        				/*HashMap<String, String> mHashMap=new HashMap<String, String>();
        				mHashMap.put("phone", array.getJSONObject(i).getString("phone").toString());
        				mHashMap.put("username",array.getJSONObject(i).getString("username").toString());
        				data.add(mHashMap);*/
        				View item = LayoutInflater.from(HomeInfo.this).inflate(R.layout.homeinfo_item, null);
        				TextView usernamTv = (TextView) item.findViewById(R.id.home_item_username);
        				TextView phoneTv = (TextView) item.findViewById(R.id.home_item_phone);
        				if(array.getJSONObject(i).getString("phone") != null){
        					phoneTv.setText("电话号码:"+array.getJSONObject(i).getString("phone"));
        				}
        				if (array.getJSONObject(i).getString("username") != null) {
        					usernamTv.setText("用户名:"+array.getJSONObject(i).getString("username"));
						}
        				ll.addView(item);
        				View divider = new View(HomeInfo.this);
        				divider.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20));
        				ll.addView(divider);
        			}
        			ll.removeViewAt(ll.getChildCount()-1);
        			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Toast.makeText(HomeInfo.this,"json解析出错", 3000).show();
			}
        	
      }
          } 
          
        protected void onPreExecute () {
			mprogressdialog=new ProgressDialog(HomeInfo.this);
			mprogressdialog.setMessage("服务器通信中。。。");
			mprogressdialog.show();
			//mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {
          } 
          
     }; 
}
