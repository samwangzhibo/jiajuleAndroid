package com.example.jiajule;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;

public class renwutie extends Activity{
	ListView lv;
	Button bt_refresh;
	Button bt_addtask;
	TextView tv;
	String result;
	URL murl;
	URLConnection con;
	ProgressDialog mProgressDialog;
	String handresult;
	String tag="wangzhibo";
	JSONArray array;
	SimpleAdapter mSimpleAdapter;
	ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			handresult=(String) msg.obj;
			if(handresult.equals(NetWork.UNCONNET_NETWORK)){
				mProgressDialog.dismiss();
       		 Toast.makeText(renwutie.this,NetWork.UNCONNET_NETWORK, 3000).show();
			}else{
       	 try {
       		 array=new JSONArray(handresult.toString());
				//JSONObject json=new JSONObject(result);
				//name=json.getString("name");       			
       			for(int i=0;i<array.length();i++){
       				HashMap<String, String> mHashMap=new HashMap<String, String>();
       				Log.e(tag, "第"+(i+1)+"加入ArrayList");
       				mHashMap.put("id", array.getJSONObject(i).getString("id").toString());
       				Log.e(tag, array.getJSONObject(i).getString("usename").toString());
       				mHashMap.put("username",array.getJSONObject(i).getString("usename").toString());
       				mHashMap.put("msg", array.getJSONObject(i).getString("msg").toString());
       				mHashMap.put("time", array.getJSONObject(i).getString("time").toString());
       				data.add(mHashMap);
       			}
       			if(data.size() == 0)
       				Log.e(tag,"data为空");
       			if(data.size()>0){
    				tv.setVisibility(View.GONE);
    				//bt_refresh.setVisibility(View.GONE);
    			}
    			InitListView();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Toast.makeText(renwutie.this,"json解析出错", 3000).show();
			}
			      }
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasktie);	
		InitView();
		SetListener();
	}
	void InitListView(){		
		mSimpleAdapter=new SimpleAdapter(renwutie.this, data, R.layout.renwutie_list, new String[]{"id","username","msg","time"}, new int[]{R.id.renwutie_item_id,R.id.renwutie_item_username,R.id.renwutie_item_msg,R.id.renwutie_item_time});  	 
		lv.setAdapter(mSimpleAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int id,
					long position) {
				// TODO Auto-generated method stub
				Toast.makeText(renwutie.this,id+"" , 1000).show();
				Log.e(tag, "Touch item"+id+"");
			}
		});
	}
	private void SetListener() {
		// TODO Auto-generated method stub
		bt_addtask.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(renwutie.this,addtask.class);
				startActivity(i);
			}
		});
		bt_refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mProgressDialog=new ProgressDialog(renwutie.this);
				mProgressDialog.setMessage("服务器通信中...");
				mProgressDialog.show();
				
				GetJsonTaskThread gjtt=new GetJsonTaskThread();
				gjtt.start();
			}
		});
	}

	private void InitView(){
		lv=(ListView)findViewById(R.id.tasktie_lv);
		bt_refresh=(Button)findViewById(R.id.tasktie_refresh);
		bt_addtask=(Button)findViewById(R.id.tasktie_addtask);
	
	}
	class GetJsonTaskThread extends Thread{
		public void run(){
			if(!NetWork.NetWorkpanduan(renwutie.this)){
				
				result=NetWork.UNCONNET_NETWORK;
				
		}else{
        	
				try {
					murl=new URL(URLAPI.GET_TASK_MSG());
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				try {
					
					con=murl.openConnection();
					InputStream in=con.getInputStream();
					DataInputStream dis=new DataInputStream(in);
					result=dis.readLine();
					//tv.setText(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
        	
		}
        	Message msg = Message.obtain();
			msg.obj = result;
			handler.sendMessage(msg);       		
        	
		};
}
}