package com.other;




//import com.xmobileapp.cammonitor.R;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiajule.OldRenWuTie;
import com.example.jiajule.R;
import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.util.GetJsonTask;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;

public class unlock_record extends Activity implements View.OnClickListener{
	ProgressBar mProgressBar;
	Button bt_refresh;
	ListView lv;
	JSONArray jarray;
	static String unlock_time="unlock_time";
	static String username="username";
	String tag="wangzhibo";
	ProgressDialog mprogressdialog;
	String result;
	URL murl;
	HttpURLConnection con;
	JSONArray array;
	TextView tv;
	
	
	ArrayList<HashMap<String, String>> mArrayData = new ArrayList<HashMap<String,String>>();
	
	public void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.unlock_record);	
			InitView();
			GetWEBTask sdf= new GetWEBTask();
			sdf.execute("");
	}
	
	private void GetWebData() {
		// TODO Auto-generated method stub
		GetJsonTask getjson = new GetJsonTask(mProgressBar,unlock_record.this);
		getjson.execute(URLAPI.UNLOCK_RECORD()+"?username="+ActivtyUtil.GetUsernameSharedPre(unlock_record.this));
		jarray=getjson.getJsonarray();
		Log.e(tag,jarray.toString());
			try {
				if(jarray != null){
					for(int i=0;i<jarray.length();i++){
					HashMap<String, String> mHshMap = new HashMap<String, String>();
					mHshMap.put(unlock_time,jarray.getJSONObject(i).getString(unlock_time));
					mHshMap.put(username,jarray.getJSONObject(i).getString(username));
					mArrayData.add(mHshMap);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleAdapter msimpleadapter= new SimpleAdapter(unlock_record.this, mArrayData,R.layout.unlock_record_item, new String[]{unlock_time,username}, new int[]{R.id.unlock_record_item_time,R.id.unlock_record_item_username});
			lv.setAdapter(msimpleadapter);
			
		}
		
	
	public void InitView(){
		mProgressBar=(ProgressBar) findViewById(R.id.unlock_record_progressBar);
		bt_refresh=(Button) findViewById(R.id.unlock_record_refresh);
		lv=(ListView) findViewById(R.id.unlock_record_lv);
		tv = (TextView) findViewById(R.id.unlock_record_tv);
		bt_refresh.setOnClickListener(this);
	}
	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
			 if(keyCode == KeyEvent.KEYCODE_BACK ){
				 Intent it=new Intent(unlock_record.this,MyTabHostFive.class);
				 startActivity(it);
				 finish();
			 }
		 return super.onKeyDown(keyCode, event);
		}*/
	class GetWEBTask extends AsyncTask<String,Integer,String> {//ï¿½Ì³ï¿½AsyncTask 
        @Override 
        protected String doInBackground(String... params) {
        	
        	try {
				murl=new URL(URLAPI.UNLOCK_RECORD()+"?username="+ActivtyUtil.GetUsernameSharedPre(unlock_record.this));
				try {
					if(!NetWork.NetWorkpanduan(unlock_record.this)){
						
						result=NetWork.UNCONNET_NETWORK;
						return result;
					}
					con=(HttpURLConnection) murl.openConnection();
					con.setDoOutput(true);  
					con.setDoInput(true);  
					con.setConnectTimeout(5000);  //ÉèÖÃÁ¬½Ó³¬Ê±Îª10s  
					con.setReadTimeout(5000);     //¶ÁÈ¡Êý¾Ý³¬Ê±Ò²ÊÇ10s  
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
          
         protected void onProgressUpdate(Integer... progress) {//ï¿½Úµï¿½ï¿½ï¿½publishProgressÖ®ï¿½ó±»µï¿½ï¿½Ã£ï¿½ï¿½ï¿½uiï¿½ß³ï¿½Ö´ï¿½ï¿½ 

          } 
  
         protected void onPostExecute(String result) {
        	 mProgressBar.setVisibility(View.INVISIBLE);
        	 if(result.equals(NetWork.UNCONNET_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(unlock_record.this,NetWork.UNCONNET_NETWORK, 3000).show();
        	 }
        	 else if(result.equals(NetWork.ERROR_NETWORK)){
        		 mprogressdialog.dismiss();
        		 Toast.makeText(unlock_record.this,NetWork.ERROR_NETWORK, 3000).show(); 
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
        				Log.e(tag, "µÚ"+(i+1)+"¼ÓÈëArrayList");
        				mHashMap.put(unlock_time, array.getJSONObject(i).getString(unlock_time).toString());
        				
        				mHashMap.put(username,array.getJSONObject(i).getString(username).toString());
        				 Log.e(tag, array.getJSONObject(i).getString(username).toString());
        				
        				data.add(mHashMap);
        			}
        			
        			if(data.size() >0)
        				tv.setVisibility(View.INVISIBLE);
        			
                	 //tv.setText(json_test);
                	 
                	 
             	/*	if(data.size() > 0){
            			tv.setVisibility(View.GONE);
            			//bt_refresh.setVisibility(View.GONE);
            		}*/
             		Log.e(tag, "ÉèÖÃadapter");
        			lv=(ListView)findViewById(R.id.unlock_record_lv);
        			SimpleAdapter mySimpleAdapter=new SimpleAdapter(unlock_record.this, data, R.layout.unlock_record_item, new String[]{username,unlock_time}, new int[]{R.id.unlock_record_item_username,R.id.unlock_record_item_time});  	
             		lv.setAdapter(mySimpleAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Toast.makeText(unlock_record.this,"json½âÎö³ö´í", 3000).show();
			}
        	
      }
          } 
          
        protected void onPreExecute () {
        	//ï¿½ï¿½ doInBackground(Params...)Ö®Ç°ï¿½ï¿½ï¿½ï¿½ï¿½Ã£ï¿½ï¿½ï¿½uiï¿½ß³ï¿½Ö´ï¿½ï¿½        
			mprogressdialog=new ProgressDialog(unlock_record.this);
			mprogressdialog.setMessage("·þÎñÆ÷Í¨ÐÅÖÐ¡£¡£¡£");
			mprogressdialog.show();
			//mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//ï¿½ï¿½uiï¿½ß³ï¿½Ö´ï¿½ï¿½ 
          
          } 
          
     }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.unlock_record_refresh:
			GetWEBTask sdf= new GetWEBTask();
			sdf.execute("");
			break;

		default:
			break;
		}
	}; 
}

