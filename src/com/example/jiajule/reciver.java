package com.example.jiajule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class reciver extends BroadcastReceiver {

	/**
	 * @param args
	 */
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context=context;		
		shownotification(intent);
	}

	private void shownotification(Intent intent) {
		// TODO Auto-generated method stub
		//实例化manage
		NotificationManager nm=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		Notification notification=new Notification(R.drawable.icon3,intent.getExtras().getString("content"),System.currentTimeMillis());
		//跳转页面
		PendingIntent  pandingintent=PendingIntent.getActivity(context,0,new Intent(context,MyTabHostFive.class), 0);
		//设置参数
		notification.setLatestEventInfo(context,intent.getExtras().getString("content"), null, pandingintent);
		nm.notify(R.layout.unlock2, notification);
	}
	

}
