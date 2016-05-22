package com.example.jiajule;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.PictureChoose;
import com.example.jiajule.util.URLAPI;
import com.example.jiajule.BMPUtil.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class register extends Activity{
	private static final String TAG = "wangzhibo";
	
	Button register;//ע��
	Button add_picture;
	EditText name;
	EditText pass;
	EditText jiaID;
	EditText phone;
	URL url;
	URLConnection con;
	ProgressDialog mprodialog;
	String result;
	//���ͼƬ�Ĳ���
	ImageView my_picture;
	
	int SELECT_PICTURE=0;
	File tempFile;
	
	Bitmap bitmap;
	File cache;
	private byte[] mContent;
	protected String picPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register2);
		initview();
		setListenner();
	}
	private void setListenner() {
		// TODO Auto-generated method stub
		my_picture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CreateDialog();	
			}
		});
		
		//ע��
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				if(CheckNull(register.this, name, pass, jiaID, phone)){
				WebTask wt=new WebTask();
				wt.execute("");
				}
				
			}
		});
		//�ϴ�ͼƬ
		
		//���Ӱ�ť
		add_picture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CreateDialog();
			}
		});
		
		
		
	}
	
	void UploadPIC(){
		Log.e(TAG,"�ϴ�ͼƬ");
		try {
//			picPath ="/storage/emulated/0/lqcache/fdfc03632dba1b80667bd3f0a30b846f.png";
			if (picPath!=null) {
				UploadFileTask uploadFileTask=new UploadFileTask(register.this);
				uploadFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 
						picPath,name.getText().toString());
				//uploadFileTask.execute(picPath,name.getText().toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		};
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PictureChoose.PHOTO_REQUEST_GALLERY) {
            // ����᷵�ص�����
            if (data != null) {
                // �õ�ͼƬ��ȫ·��
            	Log.e(TAG, "��ȡ�����ͼƬ");
                Uri uri = data.getData();
               
               Intent it= PictureChoose.crop(uri);
               startActivityForResult(it, PictureChoose.PHOTO_REQUEST_CUT); 
            }
 
        } 
        
        else if (requestCode == PictureChoose.PHOTO_REQUEST_CAREMA) {
        	Log.e(TAG, "��ȡ�����ͼƬ");
            // ��������ص�����
            if (PictureChoose.hasSdcard()) {
            	Log.e(TAG, "SD����Ϊ��");
            	 tempFile = new File(Environment.getExternalStorageDirectory(),
                         PictureChoose.PHOTO_FILE_NAME);
            	//Intent it = PictureChoose.crop(Uri.fromFile(tempFile));
            	 Intent it = PictureChoose.crop(Uri.fromFile(tempFile));
            	if(it != null){
            	startActivityForResult(it, PictureChoose.PHOTO_REQUEST_CUT); 
            	Log.e(TAG, "����󷵻ص�Intent��Ϊ��");
            	}
            } else {
            	Log.e(TAG, "SD��Ϊ��");
                Toast.makeText(register.this, "δ�ҵ��洢�����޷��洢��Ƭ��", 0).show();
            }
 
        }
        
        
        else if (requestCode == PictureChoose.PHOTO_REQUEST_CUT) {
        	Log.e(TAG, "��ȡ�˼���ͼƬ");
            // �Ӽ���ͼƬ���ص�����
            if (data != null) {
                bitmap = data.getParcelableExtra("data");
                add_picture.setText("�����,��Ҫ���ģ�");
                this.my_picture.setImageBitmap(bitmap);
            }
            else{
            	Log.e(TAG, "Intent Ϊ��");
            }
            /*try {
                // ����ʱ�ļ�ɾ��
            	PictureChoose.tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			mContent = baos.toByteArray();
			cache = new File(getExternalFilesDir("cache")+"/picture.jpg");
			OutputStream os= new FileOutputStream(cache);
			os.write(mContent);
			os.close();
			picPath = getExternalFilesDir("cache")+"/picture.jpg";
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
 
        super.onActivityResult(requestCode, resultCode, data);
    }
	protected boolean CheckNull(Context cxt,EditText et1,EditText et2,EditText et3,EditText et4) {
		// TODO Auto-generated method stub
		if(et1.getText().length()<1){
			Toast.makeText(cxt, et1.toString()+"is null please reinput", 3000).show();
			return false;
		}else if(et2.getText().length()<1){
			Toast.makeText(cxt, et2.toString()+"is null please reinput", 3000).show();
			return false;
		}else if(et3.getText().length()<1){
			Toast.makeText(cxt, et3.toString()+"is null please reinput", 3000).show();
			return false;
		}else if (et4.getText().length()<1) {
			Toast.makeText(cxt, et3.toString()+"is null please reinput", 3000).show();
			return false;
		}else{
			return true;
		}
	}
	/**
	 * ��ʼ������
	 */
	private void initview() {
		// TODO Auto-generated method stub
		
		register=(Button) findViewById(R.id.register_bt);
		name=(EditText) findViewById(R.id.regi_name);
		pass=(EditText) findViewById(R.id.regi_pass);
		jiaID=(EditText) findViewById(R.id.regi_jiaID);
		phone=(EditText) findViewById(R.id.regi_phone);	
		
		add_picture =(Button) this.findViewById(R.id.register_addpicture);
		my_picture = (ImageView) this.findViewById(R.id.register_mypicture);
		
	}
	/**
	 * 
	 * @author Administrator
	 *AsyncTask��
	 */
	void CreateDialog(){
		CharSequence[] items = {"���", "���"};    
		    new AlertDialog.Builder(this)  
		     .setTitle("ѡ��ͼƬ��Դ")  
		     .setItems(items, new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int which) {  
		             if( which == SELECT_PICTURE ){  
		               Intent it= PictureChoose.gallery();
		               startActivityForResult(it, PictureChoose.PHOTO_REQUEST_GALLERY);
	            }else{  	  
	            	 Intent it= PictureChoose.camera();
	            	 startActivityForResult(it, PictureChoose.PHOTO_REQUEST_CAREMA);
		             }  
		         }  
		    })  
		     .create().show();

	}
	class WebTask extends AsyncTask<String, Integer, String>{
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path=URLAPI.getRegisteUrl()+"?username="+name.getText().toString()+"&&pass="+pass.getText().toString()+"&&home_id="+jiaID.getText().toString()+"&&phone="+phone.getText().toString();
			
			try {
				//�ж���������
				if(!NetWork.NetWorkpanduan(register.this)){
					
					result=NetWork.UNCONNET_NETWORK;
					return result;
				}
				url=new URL(path);
				try {
					HttpURLConnection con=(HttpURLConnection) url.openConnection();
					con.setDoOutput(true);  
					con.setDoInput(true);  
					con.setConnectTimeout(10000);  //�������ӳ�ʱΪ10s  
					con.setReadTimeout(10000);     //��ȡ���ݳ�ʱҲ��10s  
					con.setRequestMethod("GET");  
					con.setUseCaches(false);  
					con.connect();
					
					InputStream is=con.getInputStream();
					DataInputStream dis=new DataInputStream(is);
					result=dis.readLine();
					
					dis.close();
					is.close();
					con.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					result=NetWork.ERROR_NETWORK;
					return result;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				mprodialog.dismiss();
				Toast.makeText(register.this,"���ӷ���������", 3000).show();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			 NetWork.NetResultChuli(register.this, result, mprodialog);
			 
			if(result.equals("success")){
				mprodialog.dismiss();
				Toast.makeText(register.this,"ע��ɹ�������ȥ��¼��", 3000).show();
				UploadPIC();
				
			}else if(result.equals("lose")){
				mprodialog.dismiss();
				Toast.makeText(register.this,"������,������ע��", 3000).show();
				Intent it =new Intent(register.this,login.class);
				
				startActivity(it);
				finish();
			}else if(result.equals("username is used")){
				mprodialog.dismiss();
				Toast.makeText(register.this,"username is used", 3000).show();
			}else {
				mprodialog.dismiss();
				 Toast.makeText(register.this, "unknowen error", 3000).show();
			 }	
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mprodialog=new ProgressDialog(register.this);
			mprodialog.setMessage("������ͨ���С�����");
			mprodialog.setCancelable(false);
			mprodialog.show();
		}
		
	}

}
