package com.example.jiajule.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.jiajule.OldRenWuTie;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GetJsonTask extends AsyncTask<String,Integer,String>{
	Context context;
	ProgressBar mpro;
	String result;
	private JSONArray jsonarray;
	public GetJsonTask(ProgressBar mpro,Context context){
		this.context=context;
		this.mpro=mpro;
	}
	public JSONArray getJsonarray() {
		
		return jsonarray;
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			URL murl = new URL(params[0]);
			HttpURLConnection con;
			try {
				con = (HttpURLConnection) murl.openConnection();
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setConnectTimeout(10000);
				con.setReadTimeout(10000);
				con.setRequestMethod("GET");
				con.setUseCaches(false);  
				con.connect();
				
				InputStream in=con.getInputStream();
				DataInputStream dis=new DataInputStream(in);
				result=dis.readLine();
				
				dis.close();
				in.close();
				con.disconnect();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=NetWork.ERROR_NETWORK;
			return result;
		}
		return result;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		mpro.setVisibility(View.INVISIBLE);
		 if(result.equals(NetWork.UNCONNET_NETWORK)){
    		 Toast.makeText(context,NetWork.UNCONNET_NETWORK, 3000).show();
    	 }
		 else{
		try {
			jsonarray=new JSONArray(result);		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
	
	}

}
