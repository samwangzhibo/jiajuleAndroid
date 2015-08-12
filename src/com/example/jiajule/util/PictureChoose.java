package com.example.jiajule.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class PictureChoose {
	private static final String TAG ="wangzhibo";
	public static int PHOTO_REQUEST_GALLERY=0;
	public static int PHOTO_REQUEST_CAREMA=1;
	public static int PHOTO_REQUEST_CUT=2;
	public static File tempFile;
	public static String PHOTO_FILE_NAME="GallaryDemo";
	
	public static Intent gallery(){
		// 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        return intent;

	}
	/*
     * 从相机获取
     */
    public static Intent camera() {
    	Log.e(TAG, "相机处理中");
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        return intent;
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
    /*
     * 剪切图片
     */
    public static Intent crop(Uri uri) {
    	if(tempFile == null)
    	Log.e(TAG, "tempFile是空的");
    	 Log.e(TAG, "进行剪切");
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
 
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        return intent;
    }
}
