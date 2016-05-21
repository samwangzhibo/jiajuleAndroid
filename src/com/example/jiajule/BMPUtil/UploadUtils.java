package com.example.jiajule.BMPUtil;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.jiajule.util.URLAPI;

/**
 * 
 * 
 */
@SuppressLint("NewApi")
public class UploadUtils {
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10*10000000;   
	private static final String CHARSET = "utf-8"; 
	public static final String SUCCESS="1";
	public static final String FAILURE="0";
	
	 /* 上传文件至Server的方法 */
	public static String uploadFile(File uploadFile,String RequestURL)
    {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try
        {
            URL url = new URL(RequestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
            con.setDoOutput(true);
          /* Read from the connection. Default is true.*/
            con.setDoInput(true);
          /* Post cannot use caches */
            con.setUseCaches(false);
          /* Set the post method. Default is GET*/
            con.setRequestMethod("POST");
          /* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
          /*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*/
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
          /* 设置DataOutputStream，getOutputStream中默认调用connect()*/
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
            ds.writeBytes(twoHyphens + boundary + end);
            //--*****\r\n
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"file\";filename=\"" +
                    uploadFile.getName() + "\"" + end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
          /* 设置每次写入8192bytes */
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];   //8k
            int length = -1;
          /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1)
            {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            //--*****--
          /* 关闭流，写入的东西自动生成Http正文*/
            fStream.close();
          /* 关闭DataOutputStream */
            ds.close();
          /* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
          /* 显示网页响应内容 */
            return b.toString();
        } catch (Exception e)
        {
            /* 显示异常信息 */
           return FAILURE;
        }
    }
	
	public static String uploadFile2(File file,String RequestURL)
	{
		String  BOUNDARY =  UUID.randomUUID().toString();  
		String PREFIX = "--" , LINE_END = "\r\n"; 
		String CONTENT_TYPE = "multipart/form-data";   
		
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); 
			conn.setDoOutput(true);
			conn.setUseCaches(false); 
			conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Charset", CHARSET);  
			conn.setRequestProperty("connection", "keep-alive");   
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
			if(file!=null)
			{
				/**
				 * 锟斤拷锟侥硷拷锟斤拷为锟秸ｏ拷锟斤拷锟侥硷拷锟斤拷装锟斤拷锟斤拷锟较达拷
				 */
				OutputStream outputSteam=conn.getOutputStream();
				
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				//String PREFIX = "--" , LINE_END = "\r\n"; 
				//String CONTENT_TYPE = "multipart/form-data";  
				
				
				sb.append("Content-Disposition: form-data; name=\"upload\"; filename=\""+file.getName()+"\""+LINE_END); 
				sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while((len=is.read(bytes))!=-1)
				{
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();
				
				
				  InputStream iss = conn.getInputStream();  
			      InputStreamReader isr = new InputStreamReader(iss, "utf-8");  
			      BufferedReader br = new BufferedReader(isr);  
			      String result = br.readLine();  
			      return result;
				
				/*int res = conn.getResponseCode();  
				Log.e(TAG, "response code:"+res);
				if(res==200)
				{
			     return SUCCESS;
				}*/
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	public static Bitmap GetBitmapIfFileEixt(String username,Context context){
		Bitmap bm;
		try {
			//锟侥硷拷锟角凤拷锟斤拷冢锟斤拷锟斤拷诰锟街憋拷佣锟饺�
			if(UploadUtils.hasSdcard()){
				 File  file1 = new File(Environment.getExternalStorageDirectory(),"jiajule");
		          if(!file1.exists())
		        	  file1.mkdirs();  
		          File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+username+".jpg");
		          if(file.exists()){
		        	  	//锟斤拷锟斤拷锟侥硷拷为bitmap
			           bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+username+".jpg");
			           return bm;
			        }
		          else file.createNewFile();
			}
			bm= UploadUtils.GetBitmapByUsername(username);
			//锟斤拷锟斤拷锟斤拷蟊４锟斤拷锟絊D锟斤拷锟斤拷
			if(bm != null){
			 UploadUtils.saveMyBitmap(context,username,bm);
			}else 
				Log.e(TAG, "bitmap");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bm=null;
			return bm;
		}
		return bm;
	}
	
	
	public static Bitmap GetBitmapByUsername(String username) throws Exception{
		String path=URLAPI.GetPicture()+username+".jpg";
		
		Log.e(TAG, "DownloadBmpTask path = "+path);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(1000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			InputStream inStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inStream);
			return bitmap;
		}
		return null;	
}
	public static void saveMyBitmap(Context context,String bitName,Bitmap mBitmap){
		   File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+bitName+ ".jpg");
		   try {
		    f.createNewFile();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    Toast.makeText(context,"", 1000);
		   }
		   FileOutputStream fOut = null;
		   try {
		    fOut = new FileOutputStream(f);
		   } catch (FileNotFoundException e) {
		    e.printStackTrace();
		   }
		   //锟斤拷锟斤拷锟斤拷
		   mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		   try {
		    fOut.flush();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		   try {
		    fOut.close();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		  }
	public static boolean hasSdcard() {
		// TODO Auto-generated method stub
    	if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
           return true;
        }
        else
        {
        	return false;
        }
		
	}
    	
}