package com.example.jiajule.BMPUtil;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

import com.example.jiajule.login;
import com.example.jiajule.util.NetWork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 包括图片下载和文件检验，如果文件存在就不下载直接读取
 * 
 */
public class DownloadBMPTask extends AsyncTask<String, Void, Bitmap>{
	//public static final String requestURL="http://192.168.1.102:8080/ServerForPicture/FileUploadServlet";
	public static final String requestURL="http://192.168.1.102:8080/MyServlet/servlet/UploadBMP?username=";
	String TAG="wangzhibo";
  /**
    *  可变长的输入参数，与AsyncTask.exucute()对应http://localhost:8080/ServerForPicture/FileUploadServlet
    */
   private  ProgressDialog pdialog;
   private  Activity context=null;
   private  ImageView im=null;
    public DownloadBMPTask(Activity ctx,ImageView im){
    	this.context=ctx;
    	this.im=im;
    	pdialog=ProgressDialog.show(context, "正在加载...", "获取用户信息中");  
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // 返回HTML页面的内容
        pdialog.dismiss(); 
        Log.e(TAG, "设置imageView");
        
       if(result!=null){
        	im.setImageBitmap(result);
        }else
        	Toast.makeText(context, "网络错误", 1000).show();
    }

	  @Override
	  protected void onPreExecute() {
	  }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bm = null;
			if(!NetWork.NetWorkpanduan(context)){
				bm=null;
				return bm;
			}
			try {
				//文件是否存在，存在就直接读取
				if(UploadUtils.hasSdcard()){
					 File  file1 = new File(Environment.getExternalStorageDirectory(),"jiajule");
			          if(!file1.exists())
			        	  file1.mkdirs();  
			          File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+params[0]+".jpg");
			          if(file.exists()){
			        	  	//解析文件为bitmap
				           bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+params[0]+".jpg");
				           return bm;
				        }
			          else file.createNewFile();
				}
				bm= UploadUtils.GetBitmapByUsername(params[0]);
				//下载完后保存在SD卡中
				if(bm != null){
				 UploadUtils.saveMyBitmap(context,params[0],bm);
				}else 
					Log.e(TAG, "bitmap为空，保存图片失败");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bm=null;
				return bm;
			}
			return bm;
		}
	       @Override
	       protected void onProgressUpdate(Void... values) {
	       }

	
}