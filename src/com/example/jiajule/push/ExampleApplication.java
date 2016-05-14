package com.example.jiajule.push;

import android.app.Application;
import android.util.Log;
import android.view.WindowManager;
import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * 
 */
public class ExampleApplication extends Application {
    private static final String TAG = "JPush";
    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getWindowParams() {
    	return windowParams;
    } 
    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();
        // JPushInterface.init(this);  
        // JPushInterface.setDebugMode(true); 	
         //设置调试模式
            		
         //JPush

     	
    }
    

}
