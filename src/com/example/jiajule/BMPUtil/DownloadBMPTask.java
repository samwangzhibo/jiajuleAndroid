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
 * ����ͼƬ���غ��ļ����飬����ļ����ھͲ�����ֱ�Ӷ�ȡ
 * 
 */
public class DownloadBMPTask extends AsyncTask<String, Void, Bitmap>{
	//public static final String requestURL="http://192.168.1.102:8080/ServerForPicture/FileUploadServlet";
	public static final String requestURL="http://192.168.1.102:8080/MyServlet/servlet/UploadBMP?username=";
	String TAG="wangzhibo";
  /**
    *  �ɱ䳤�������������AsyncTask.exucute()��Ӧhttp://localhost:8080/ServerForPicture/FileUploadServlet
    */
   private  ProgressDialog pdialog;
   private  Activity context=null;
   private  ImageView im=null;
    public DownloadBMPTask(Activity ctx,ImageView im){
    	this.context=ctx;
    	this.im=im;
    	pdialog=ProgressDialog.show(context, "���ڼ���...", "��ȡ�û���Ϣ��");  
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // ����HTMLҳ�������
        pdialog.dismiss(); 
        Log.e(TAG, "����imageView");
        
       if(result!=null){
        	im.setImageBitmap(result);
        }else
        	Toast.makeText(context, "�������", 1000).show();
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
				//�ļ��Ƿ���ڣ����ھ�ֱ�Ӷ�ȡ
				if(UploadUtils.hasSdcard()){
					 File  file1 = new File(Environment.getExternalStorageDirectory(),"jiajule");
			          if(!file1.exists())
			        	  file1.mkdirs();  
			          File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+params[0]+".jpg");
			          if(file.exists()){
			        	  	//�����ļ�Ϊbitmap
				           bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+params[0]+".jpg");
				           return bm;
				        }
			          else file.createNewFile();
				}
				bm= UploadUtils.GetBitmapByUsername(params[0]);
				//������󱣴���SD����
				if(bm != null){
				 UploadUtils.saveMyBitmap(context,params[0],bm);
				}else 
					Log.e(TAG, "bitmapΪ�գ�����ͼƬʧ��");
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