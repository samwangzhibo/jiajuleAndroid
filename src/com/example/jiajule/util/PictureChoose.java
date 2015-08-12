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
		// ����ϵͳͼ�⣬ѡ��һ��ͼƬ
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_GALLERY
        return intent;

	}
	/*
     * �������ȡ
     */
    public static Intent camera() {
    	Log.e(TAG, "���������");
        // �������
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // �жϴ洢���Ƿ�����ã����ý��д洢
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            // ���ļ��д���uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CAREMA
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
     * ����ͼƬ
     */
    public static Intent crop(Uri uri) {
    	if(tempFile == null)
    	Log.e(TAG, "tempFile�ǿյ�");
    	 Log.e(TAG, "���м���");
        // �ü�ͼƬ��ͼ
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // �ü���ı�����1��1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // �ü������ͼƬ�ĳߴ��С
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
 
        intent.putExtra("outputFormat", "JPEG");// ͼƬ��ʽ
        intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
        intent.putExtra("return-data", true);
        // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
        return intent;
    }
}
