package com.example.jiajule.BMPUtil;

import java.io.File;

import com.example.jiajule.util.URLAPI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * 
 */
public class UploadFileTask extends AsyncTask<String, Void, String>{
	//public static final String requestURL="http://192.168.1.102:8080/ServerForPicture/FileUploadServlet";
	public static final String requestURL=URLAPI.UPLOAD_BMP+"?username=";
			//"http://192.168.1.108:8080/MyServlet/servlet/UploadBMP?username=";
   /**
    *  �ɱ䳤�������������AsyncTask.exucute()��Ӧhttp://localhost:8080/ServerForPicture/FileUploadServlet
    */
   private  ProgressDialog pdialog;
   private  Activity context=null;
    public UploadFileTask(Activity ctx){
    	this.context=ctx;
    	pdialog=ProgressDialog.show(context, "���ڼ���...", "ϵͳ���ڴ�����������");  
    }
    @Override
    protected void onPostExecute(String result) {
        // ����HTMLҳ�������
        pdialog.dismiss(); 
        if(UploadUtils.SUCCESS.equalsIgnoreCase(result)){
        	Toast.makeText(context, "�ϴ��ɹ�!",Toast.LENGTH_LONG ).show();
        	Log.e("ͼƬ�ϴ�","�ɹ�");
        }else{
        	Toast.makeText(context, "�ϴ�ʧ��!",Toast.LENGTH_LONG ).show();
        	Log.e("ͼƬ�ϴ�","ʧ��");
        }
    }

	  @Override
	  protected void onPreExecute() {
	  }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

		@Override
		protected String doInBackground(String... params) {
			File file=new File(params[0]);
			Log.e("UploadFileTask filePath", params[0]);
			return UploadUtils.uploadFile( file, requestURL+params[1]);
		}
	       @Override
	       protected void onProgressUpdate(Void... values) {
	       }

	
}