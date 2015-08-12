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

	private static String address = "22:13:02:01:09:08"; // <==Ҫ���ӵ������豸MAC��ַ
	private  Context mContext=this;
	private static int requestCode;
	
	/** Called when the activity is first created. */


	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.lampcontrol);
        setTitle("������");
        warning.mProgressDialog.dismiss();


		
        
        ToggleButton notify1,notify2,notify3;
        
      //��һ�����ȵ�
    	notify1 = (ToggleButton)findViewById(R.id.ToggleButton1);
    //�ȵƿ��ص��¼�����
    	notify1.setOnCheckedChangeListener(new OnCheckedChangeListener() { //����OnCheckedChangeListener
    		@Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
            	String message;
				byte[] msgBuffer;
				
                 if (isChecked) { //��ToggleButton״̬�ı�Ϊ��������ʱ��ִ�еĲ���
                	 try {
                		 Toast.makeText(mContext, "�ȵƿ���", 3000).show();
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
 				
                } else {//��ToggleButton״̬�ı�Ϊ���ء���ʱ��ִ�еĲ���
                	try {
                		Toast.makeText(mContext, "�ȵƹر�", 3000).show();
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

    	//�ڶ������޵�
    	notify2 = (ToggleButton)findViewById(R.id.ToggleButton2);
    //�޵ƿ��ص��¼�����
    	notify2.setOnCheckedChangeListener(new OnCheckedChangeListener() { //����OnCheckedChangeListener
    		@Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
            	String message;
				byte[] msgBuffer;
				
                 if (isChecked) { //��ToggleButton״̬�ı�Ϊ��������ʱ��ִ�еĲ���
                	 try {
                		Toast.makeText(mContext, "�޵ƿ���", 3000).show();
 						outStream = btSocket.getOutputStream();

 					} catch (IOException e) {
 						Log.e(TAG, "ON RESUME: Output stream creation failed.",
 								e);
 					}

 					message = "1";

 					msgBuffer = message.getBytes();

 					try {
 						Toast.makeText(mContext, "�޵ƹر�", 3000).show();
 						outStream.write(msgBuffer);

 					} catch (IOException e) {
 						Log.e(TAG, "ON RESUME: Exception during write.", e);
 					}
 				
                } else {//��ToggleButton״̬�ı�Ϊ���ء���ʱ��ִ�еĲ���
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
					Toast.makeText(mContext, "��ҹ�Զ���⹦�ܿ���", 3000);
				}
				else{
					Toast.makeText(mContext, "��ҹ�Զ���⹦�ܿ���", 3000);
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
	//start֮�󣬿ɽ���״̬
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
//			 setTitle("�ƹ����");
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
//	      //��һ�����ȵ�
//	    	notify1 = (ToggleButton)findViewById(R.id.ToggleButton1);
//	    //�ȵƿ��ص��¼�����
//	    	notify1.setOnCheckedChangeListener(new OnCheckedChangeListener() { //����OnCheckedChangeListener
//	    		@Override
//	            public void onCheckedChanged(CompoundButton buttonView,
//	                    boolean isChecked) {
//	            	String message;
//					byte[] msgBuffer;
//					
//	                 if (isChecked) { //��ToggleButton״̬�ı�Ϊ��������ʱ��ִ�еĲ���
//	                	 try {
//	                		 Toast.makeText(mContext, "�ȵƿ���", 3000).show();
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
//	                } else {//��ToggleButton״̬�ı�Ϊ���ء���ʱ��ִ�еĲ���
//	                	try {
//	                		Toast.makeText(mContext, "�ȵƹر�", 3000).show();
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
//	    	//�ڶ������޵�
//	    	notify2 = (ToggleButton)findViewById(R.id.ToggleButton2);
//	    //�޵ƿ��ص��¼�����
//	    	notify2.setOnCheckedChangeListener(new OnCheckedChangeListener() { //����OnCheckedChangeListener
//	    		@Override
//	            public void onCheckedChanged(CompoundButton buttonView,
//	                    boolean isChecked) {
//	            	String message;
//					byte[] msgBuffer;
//					
//	                 if (isChecked) { //��ToggleButton״̬�ı�Ϊ��������ʱ��ִ�еĲ���
//	                	 try {
//	                		Toast.makeText(mContext, "�޵ƿ���", 3000).show();
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
//	 						Toast.makeText(mContext, "�޵ƹر�", 3000).show();
//	 						outStream.write(msgBuffer);
//
//	 					} catch (IOException e) {
//	 						Log.e(TAG, "ON RESUME: Exception during write.", e);
//	 					}
//	 				
//	                } else {//��ToggleButton״̬�ı�Ϊ���ء���ʱ��ִ�еĲ���
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
//						Toast.makeText(mContext, "��ҹ�Զ���⹦�ܿ���", 3000);
//					}
//					else{
//						Toast.makeText(mContext, "��ҹ�Զ���⹦�ܿ���", 3000);
//					}
//				}
//			});
//		}
//	}

}
