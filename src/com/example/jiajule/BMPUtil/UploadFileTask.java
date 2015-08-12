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
    *  可变长的输入参数，与AsyncTask.exucute()对应http://localhost:8080/ServerForPicture/FileUploadServlet
    */
   private  ProgressDialog pdialog;
   private  Activity context=null;
    public UploadFileTask(Activity ctx){
    	this.context=ctx;
    	pdialog=ProgressDialog.show(context, "正在加载...", "系统正在处理您的请求");  
    }
    @Override
    protected void onPostExecute(String result) {
        // 返回HTML页面的内容
        pdialog.dismiss(); 
        if(UploadUtils.SUCCESS.equalsIgnoreCase(result)){
        	Toast.makeText(context, "上传成功!",Toast.LENGTH_LONG ).show();
        	Log.e("图片上传","成功");
        }else{
        	Toast.makeText(context, "上传失败!",Toast.LENGTH_LONG ).show();
        	Log.e("图片上传","失败");
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