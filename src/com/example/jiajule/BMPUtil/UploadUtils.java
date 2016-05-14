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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.jiajule.util.URLAPI;

/**
 * 
 * 
 */
public class UploadUtils {
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10*10000000;   //超时时间
	private static final String CHARSET = "utf-8"; //设置编码
	public static final String SUCCESS="1";
	public static final String FAILURE="0";
	/**
	 * 
	 */
	public static String uploadFile(File file,String RequestURL)
	{
		String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
		String PREFIX = "--" , LINE_END = "\r\n"; 
		String CONTENT_TYPE = "multipart/form-data";   //内容类型
		
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true);  //允许输入流
			conn.setDoOutput(true); //允许输出流
			conn.setUseCaches(false);  //不允许使用缓存
			conn.setRequestMethod("POST");  //请求方式
			conn.setRequestProperty("Charset", CHARSET);  //设置编码
			conn.setRequestProperty("connection", "keep-alive");   
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
			if(file!=null)
			{
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				OutputStream outputSteam=conn.getOutputStream();
				
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				//String PREFIX = "--" , LINE_END = "\r\n"; 
				//String CONTENT_TYPE = "multipart/form-data";  内容类型
				/**
				 * 这里重点注意：
				 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的   比如:abc.png  
				 */
				
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
				/**
				 * 获取响应码  200=成功
				 * 当响应成功，获取响应的流  
				 */
				
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
			//文件是否存在，存在就直接读取
			if(UploadUtils.hasSdcard()){
				 File  file1 = new File(Environment.getExternalStorageDirectory(),"jiajule");
		          if(!file1.exists())
		        	  file1.mkdirs();  
		          File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+username+".jpg");
		          if(file.exists()){
		        	  	//解析文件为bitmap
			           bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiajule/"+username+".jpg");
			           return bm;
			        }
		          else file.createNewFile();
			}
			bm= UploadUtils.GetBitmapByUsername(username);
			//下载完后保存在SD卡中
			if(bm != null){
			 UploadUtils.saveMyBitmap(context,username,bm);
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
	
	
	public static Bitmap GetBitmapByUsername(String username) throws Exception{
		String path=URLAPI.GetPicture()+username+".jpg";
		
		Log.e(TAG, "path = "+path);
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
		   //主方法
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