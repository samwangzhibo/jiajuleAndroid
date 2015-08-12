package com.other;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import com.example.jiajule.OnViewChangeListener;
import com.example.jiajule.R;
import com.example.jiajule.warning;
//import com.xmobileapp.cammonitor.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;


public class lampcontrol extends Activity {
	 
	private static final String TAG = "THINBTCLIENT";

	private static final boolean D = true;

	private BluetoothAdapter mBluetoothAdapter = null;

	private BluetoothSocket btSocket = null;

	private OutputStream outStream = null;

	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private static String address = "22:13:02:01:09:08"; // <==要连接的蓝牙设备MAC地址
	private  Context mContext=this;
	private static int requestCode;
	
	/** Called when the activity is first created. */


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.lampcontrol);
        setTitle("打开蓝牙");
        warning.mProgressDialog.dismiss();


		
        
        ToggleButton notify1,notify2,notify3;
        
      //第一个：廊灯
    	notify1 = (ToggleButton)findViewById(R.id.ToggleButton1);
    //廊灯开关的事件监听
    	notify1.setOnCheckedChangeListener(new OnCheckedChangeListener() { //监听OnCheckedChangeListener
    		@Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
            	String message;
				byte[] msgBuffer;
				
                 if (isChecked) { //当ToggleButton状态改变为“开”的时候执行的操作
                	 try {
                		 Toast.makeText(mContext, "廊灯开启", 3000).show();
 						outStream = btSocket.getOutputStream();

 					} catch (IOException e) {
 						Log.e(TAG, "ON RESUME: Output stream creation failed.",
 								e);
 					}

 					message = "2";

 					msgBuffer = message.getBytes();

 					try {
 						outStream.write(msgBuffer);

 					} catch (IOException e) {
 						Log.e(TAG, "ON RESUME: Exception during write.", e);
 					}
 				
                } else {//当ToggleButton状态改变为“关”的时候执行的操作
                	try {
                		Toast.makeText(mContext, "廊灯关闭", 3000).show();
						outStream = btSocket.getOutputStream();

					} catch (IOException e) {
						Log.e(TAG, "ON RESUME: Output stream creation failed.",
								e);
					}

					message = "4";

					msgBuffer = message.getBytes();

					try {
						outStream.write(msgBuffer);

					} catch (IOException e) {
						Log.e(TAG, "ON RESUME: Exception during write.", e);
					}
				
                }
            }
        }); 

    	//第二个：厕灯
    	notify2 = (ToggleButton)findViewById(R.id.ToggleButton2);
    //厕灯开关的事件监听
    	notify2.setOnCheckedChangeListener(new OnCheckedChangeListener() { //监听OnCheckedChangeListener
    		@Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
            	String message;
				byte[] msgBuffer;
				
                 if (isChecked) { //当ToggleButton状态改变为“开”的时候执行的操作
                	 try {
                		Toast.makeText(mContext, "厕灯开启", 3000).show();
 						outStream = btSocket.getOutputStream();

 					} catch (IOException e) {
 						Log.e(TAG, "ON RESUME: Output stream creation failed.",
 								e);
 					}

 					message = "1";

 					msgBuffer = message.getBytes();

 					try {
 						Toast.makeText(mContext, "厕灯关闭", 3000).show();
 						outStream.write(msgBuffer);

 					} catch (IOException e) {
 						Log.e(TAG, "ON RESUME: Exception during write.", e);
 					}
 				
                } else {//当ToggleButton状态改变为“关”的时候执行的操作
                	try {
						outStream = btSocket.getOutputStream();

					} catch (IOException e) {
						Log.e(TAG, "ON RESUME: Output stream creation failed.",
								e);
					}

					message = "3";

					msgBuffer = message.getBytes();

					try {
						outStream.write(msgBuffer);

					} catch (IOException e) {
						Log.e(TAG, "ON RESUME: Exception during write.", e);
					}
				
                }
            }
        }); 
    	notify3=(ToggleButton) findViewById(R.id.toggleButton3);
    	notify3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					Toast.makeText(mContext, "起夜自动检测功能开启", 3000);
				}
				else{
					Toast.makeText(mContext, "起夜自动检测功能开启", 3000);
				}
			}
		});
//    	
//    	
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");
		
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available.",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		if(!mBluetoothAdapter.isEnabled()){
			Toast.makeText(this, "Bluetooth is not open,please open",
					Toast.LENGTH_LONG).show();
			Intent enableIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableIntent, requestCode);
			finish();
		}



		if (D)
			Log.e(TAG, "+++ DONE IN ON CREATE, GOT LOCAL BT ADAPTER +++");

        
	}
	@Override
	public void onStart() {

		super.onStart();

		
		if (D)
			Log.e(TAG, "++ ON START ++");
	}

	@Override
	//start之后，可交互状态
	public void onResume() {

		super.onResume();
		if (D) {
			Log.e(TAG, "+ ON RESUME +");
			Log.e(TAG, "+ ABOUT TO ATTEMPT CLIENT CONNECT +");

	        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

			try {

				btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

			} catch (IOException e) {

				Log.e(TAG, "ON Create: Socket creation failed.", e);

			}
			//mBluetoothAdapter.cancelDiscovery();
			try {

				btSocket.connect();

				Log.e(TAG,
						"ON Create: BT connection established, data transfer link open.");

			} catch (IOException e) {

				try {
					btSocket.close();

				} catch (IOException e2) {

					Log.e(TAG,
							"ON Create: Unable to close socket during connection failure",
							e2);
				}

			}
		}

		

		// Create a data stream so we can talk to server.

		if (D)
			Log.e(TAG, "+ ABOUT TO SAY SOMETHING TO SERVER +");
		/*
		 * try { outStream = btSocket.getOutputStream();
		 * 
		 * } catch (IOException e) { Log.e(TAG,
		 * "ON RESUME: Output stream creation failed.", e); }
		 * 
		 * 
		 * String message = "1";
		 * 
		 * byte[] msgBuffer = message.getBytes();
		 * 
		 * try { outStream.write(msgBuffer);
		 * 
		 * } catch (IOException e) { Log.e(TAG,
		 * "ON RESUME: Exception during write.", e); }
		 */

	}

	@Override
	public void onPause() {

		super.onPause();

		if (D)
			Log.e(TAG, "- ON PAUSE -");
		if (outStream != null) {
			try {
				outStream.flush();
			} catch (IOException e) {
				Log.e(TAG, "ON PAUSE: Couldn't flush output stream.", e);
			}

		}

		try {
			btSocket.close();
		} catch (IOException e2) {
			Log.e(TAG, "ON PAUSE: Unable to close socket.", e2);
		}

	}

	@Override
	public void onStop() {

		super.onStop();

		if (D)
			Log.e(TAG, "-- ON STOP --");

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		if (D)
			Log.e(TAG, "--- ON DESTROY ---");

	}
//	@Override
//	protected void onActivityResult(int request, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(request==requestCode&&resultCode==Activity.RESULT_OK){
//			 setTitle("灯光控制");
//			BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//			try {
//
//				btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
//
//			} catch (IOException e) {
//
//				Log.e(TAG, "ON Create: Socket creation failed.", e);
//
//			}
//			//mBluetoothAdapter.cancelDiscovery();
//			try {
//
//				btSocket.connect();
//
//				Log.e(TAG,
//						"ON Create: BT connection established, data transfer link open.");
//
//			} catch (IOException e) {
//
//				try {
//					btSocket.close();
//
//				} catch (IOException e2) {
//
//					Log.e(TAG,
//							"ON Create: Unable to close socket during connection failure",
//							e2);
//				}
//
//			}
//	        
//	        ToggleButton notify1,notify2,notify3;
//	        
//	      //第一个：廊灯
//	    	notify1 = (ToggleButton)findViewById(R.id.ToggleButton1);
//	    //廊灯开关的事件监听
//	    	notify1.setOnCheckedChangeListener(new OnCheckedChangeListener() { //监听OnCheckedChangeListener
//	    		@Override
//	            public void onCheckedChanged(CompoundButton buttonView,
//	                    boolean isChecked) {
//	            	String message;
//					byte[] msgBuffer;
//					
//	                 if (isChecked) { //当ToggleButton状态改变为“开”的时候执行的操作
//	                	 try {
//	                		 Toast.makeText(mContext, "廊灯开启", 3000).show();
//	 						outStream = btSocket.getOutputStream();
//
//	 					} catch (IOException e) {
//	 						Log.e(TAG, "ON RESUME: Output stream creation failed.",
//	 								e);
//	 					}
//
//	 					message = "2";
//
//	 					msgBuffer = message.getBytes();
//
//	 					try {
//	 						outStream.write(msgBuffer);
//
//	 					} catch (IOException e) {
//	 						Log.e(TAG, "ON RESUME: Exception during write.", e);
//	 					}
//	 				
//	                } else {//当ToggleButton状态改变为“关”的时候执行的操作
//	                	try {
//	                		Toast.makeText(mContext, "廊灯关闭", 3000).show();
//							outStream = btSocket.getOutputStream();
//
//						} catch (IOException e) {
//							Log.e(TAG, "ON RESUME: Output stream creation failed.",
//									e);
//						}
//
//						message = "0";
//
//						msgBuffer = message.getBytes();
//
//						try {
//							outStream.write(msgBuffer);
//
//						} catch (IOException e) {
//							Log.e(TAG, "ON RESUME: Exception during write.", e);
//						}
//					
//	                }
//	            }
//	        }); 
//
//	    	//第二个：厕灯
//	    	notify2 = (ToggleButton)findViewById(R.id.ToggleButton2);
//	    //厕灯开关的事件监听
//	    	notify2.setOnCheckedChangeListener(new OnCheckedChangeListener() { //监听OnCheckedChangeListener
//	    		@Override
//	            public void onCheckedChanged(CompoundButton buttonView,
//	                    boolean isChecked) {
//	            	String message;
//					byte[] msgBuffer;
//					
//	                 if (isChecked) { //当ToggleButton状态改变为“开”的时候执行的操作
//	                	 try {
//	                		Toast.makeText(mContext, "厕灯开启", 3000).show();
//	 						outStream = btSocket.getOutputStream();
//
//	 					} catch (IOException e) {
//	 						Log.e(TAG, "ON RESUME: Output stream creation failed.",
//	 								e);
//	 					}
//
//	 					message = "1";
//
//	 					msgBuffer = message.getBytes();
//
//	 					try {
//	 						Toast.makeText(mContext, "厕灯关闭", 3000).show();
//	 						outStream.write(msgBuffer);
//
//	 					} catch (IOException e) {
//	 						Log.e(TAG, "ON RESUME: Exception during write.", e);
//	 					}
//	 				
//	                } else {//当ToggleButton状态改变为“关”的时候执行的操作
//	                	try {
//							outStream = btSocket.getOutputStream();
//
//						} catch (IOException e) {
//							Log.e(TAG, "ON RESUME: Output stream creation failed.",
//									e);
//						}
//
//						message = "0";
//
//						msgBuffer = message.getBytes();
//
//						try {
//							outStream.write(msgBuffer);
//
//						} catch (IOException e) {
//							Log.e(TAG, "ON RESUME: Exception during write.", e);
//						}
//					
//	                }
//	            }
//	        }); 
//	    	notify3=(ToggleButton) findViewById(R.id.toggleButton3);
//	    	notify3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					// TODO Auto-generated method stub
//					if(isChecked){
//						Toast.makeText(mContext, "起夜自动检测功能开启", 3000);
//					}
//					else{
//						Toast.makeText(mContext, "起夜自动检测功能开启", 3000);
//					}
//				}
//			});
//		}
//	}

}
