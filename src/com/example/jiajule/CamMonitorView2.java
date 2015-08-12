package com.example.jiajule;

import com.example.jiajule.config.CamMonitorParameter;
import com.example.jiajule.core.CameraSource;
import com.example.jiajule.core.SocketCamera;
import com.example.jiajule.util.ActivtyUtil;

import android.R.bool;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CamMonitorView2 extends SurfaceView implements SurfaceHolder.Callback{
	private CamMonitorParameter cmpara;
	private CamMonitorThread thread;
	private final String  TAG = "CamMonitorView2";
	public CamMonitorView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//register our interest in hearing about changes to our surface  
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		thread = new CamMonitorThread(holder);
		Log.e(TAG,"@@@ done create CamMonitorView");
		
	}

	public void setRunning(boolean b){
		this.thread.setRunning(b);
	}
	
	public CamMonitorThread getThread(){
		return thread;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		thread.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO bAuto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		thread.closeCameraSourse();
		boolean retry = true;
		while(retry){
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public CamMonitorParameter getCmpara() {
		return cmpara;
	}

	public void setCmpara(CamMonitorParameter cmpara) {
		this.cmpara = cmpara;
	}

	
	public class CamMonitorThread extends Thread{
		

		/** Handle to the surface manager object we interact with*/
		private SurfaceHolder msurfaceHolder;
		 /** Indicate whether the surface has been created & is ready to draw */
		private boolean mRun = false;
		private Canvas c = null;
		private CameraSource cs;
		/**
		 * current height of the canvas/surface
		 * @see #setSurfaceSize
		 */
		private int mCanvasHeight = 1;
		
		private int mCanvasWidth = 1;
		public CamMonitorThread(SurfaceHolder surfaceHolder){
			this.msurfaceHolder = surfaceHolder;
		}
		
		public void setRunning(boolean b){
		mRun = b;
		}
		/**
		 * the heard of the work
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			//while running do the work in a loop
			while(mRun){
				try {
					c = msurfaceHolder.lockCanvas(null);
					captureImage(cmpara.getIp(), cmpara.getPort(), mCanvasWidth, mCanvasHeight);
				} catch (Exception e) {
					// TODO: handle exception
				}
				finally{
					if(c != null){
						msurfaceHolder.unlockCanvasAndPost(c);
						c = null;
					}
				}
			}
		}
		
		public void setSurfaceSize(int weight,int height){
			synchronized (msurfaceHolder) {
				mCanvasHeight = height;
				mCanvasWidth = weight;
			}
			
		}
		
		public boolean captureImage(String ip,int port,int width,int height){
			cs = new SocketCamera(ip, port, width, height, true);
			if(!cs.open()){
				ActivtyUtil.showAlert(CamMonitorView2.this.getContext(),"ERROR","不能连接远端服务器", "确定");
				return false;
			}
			cs.capture(c);
			return true;
		}
		
		public boolean saveImage(){
			String now = String.valueOf(System.currentTimeMillis());
			if(cs == null){
				return false;
			}
			cs.saveImage(cmpara.getLocal_dir(), now+".JPG");
			return true;
		}
		
		 public void closeCameraSourse(){
			cs.close(); 
		 }
		
		 public CameraSource getCameraSourse(){
			 return cs;
		 }
	}

}
