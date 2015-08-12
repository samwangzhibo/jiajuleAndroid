package com.example.jiajule;

import java.io.IOException;
//import com.xmobileapp.cammonitor.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;



	public class mymusic extends Activity {
		private static final int FIRST_MENU_ID = 0;
		MediaPlayer player;
		public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.second);
				Log.e("wangzhibo","mymusic进入" );
				MediaPlayer player=MediaPlayer.create(mymusic.this, R.raw.bgmusic);
		        Log.e("wangzhibo","isplay");		       
		        player.start();		        
		        //MainActivity.isplay=1;
		}
		/*
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			player.stop();
			Log.e("wangzhibo","暂停了。。。");
			super.onPause();
		}
		*/
		
		/*
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			 // 如果是返回键,直接返回到桌面
			 if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
				 AlertDialog dlg = new AlertDialog.Builder(mymusic.this)
					.setTitle("音乐播放").setMessage("是否退出")
					.setPositiveButton(
					R.string.confirm,
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
					int whichButton) {
							
						mymusic.this.onDestroy();							

							}
							}).setNegativeButton(R.string.close,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
								
																

															}
														}).create();
										dlg.show();
									}
			 
			 return super.onKeyDown(keyCode, event);
			}
			*/
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			menu.add(0, FIRST_MENU_ID, 0, R.string.menu4);
			return super.onCreateOptionsMenu(menu);
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch(item.getItemId()){
			case FIRST_MENU_ID:
				Log.e("wangzhibo","mymusic_menu_click");
				player.stop();
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
		
}
