package com.example.jiajule.util;

import com.example.jiajule.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetWork {

	private static final String TAG = "wangzhibo";
	
	 public static final String UNCONNET_NETWORK="unconnet network";
	 public static final String ERROR_NETWORK="network error";
	/**
	 * @param  判断网络
	 */
	public static boolean NetWorkpanduan(Context context){
		ConnectivityManager connectMgr = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) { 
		Log.i(TAG, "unconnect"); 
		// unconnect network 
		/*pg.dismiss();
		Toast.makeText(context, "unconnect network", 3000).show();*/
		return false;
		}
		else { 
		return true;
		} 

	}
	public static ProgressDialog NewProgressDialog(Context context){
		ProgressDialog mproDialog=new ProgressDialog(context);
		mproDialog.setMessage("服务器通信中。。。");
		mproDialog.show(); 
		return mproDialog;
	}
	public static void NetResultChuli(Context context,String result,ProgressDialog mprogressdialog){
		 if(result.equals(NetWork.UNCONNET_NETWORK)){
     			mprogressdialog.dismiss();
				Toast.makeText(context, NetWork.UNCONNET_NETWORK, 3000).show();
     	 }
     	 else if(result.equals(NetWork.ERROR_NETWORK)){
      			mprogressdialog.dismiss();
				Toast.makeText(context, NetWork.ERROR_NETWORK, 3000).show();
      	 }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
