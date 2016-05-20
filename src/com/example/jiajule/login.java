package com.example.jiajule;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.jiajule.BMPUtil.DownloadBMPTask;
import com.example.jiajule.util.ActivtyUtil;
import com.example.jiajule.util.LogUtil;
import com.example.jiajule.util.NetWork;
import com.example.jiajule.util.URLAPI;
import com.jiajule.adapter.UserInfoAdapter;
import com.jiajule.db.DBHelper;
import com.jiajule.db.DBInfo.DB;
import com.jiajule.db.DBOpreate;
import com.jiajule.javabean.UserInfo;

public class login extends Activity {
	private Button login_bt;
	private EditText name_et;
	private EditText pass_et;
	private Button register_bt;
	private Button test_bt;
	ImageView im_username_delete;
	ImageView im_user_icon;
	URL url;
	InputStream is;
    ProgressDialog mprogressdialog;
    String result;
    String NET_WORK="";
    String name;
    String tag="wangzhibo";
    String flag;
    Button btn_user_select;
    List<UserInfo> users;
    SQLiteDatabase db;
    String pass;
    UserInfo user;
    
    long lastClick;
    int DELAY_TIME = 200;
    int clickNum = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		System.out.println("----by wzb");
		 SharedPreferences settings = getSharedPreferences(jiajulemainActivity.SETTING_INFOS,0);
		flag=settings.getString("username","nobody");
		Log.e(tag,"login取得的值"+flag);
		if(!flag.matches("nobody")){
			Intent it=new Intent(login.this,MyTabHostFive.class);
			Log.e(tag, flag+"跳转去主页面");
			startActivity(it);
			finish();
		}
		else{
		initview();
		setListenner();
		}
	}
	/**
	 * 初始化界面
	 */
	private void initview() {
		// TODO Auto-generated method stub
		name_et=(EditText) findViewById(R.id.name_et);
		pass_et=(EditText) findViewById(R.id.pass_et);
		login_bt=(Button) findViewById(R.id.login);
		register_bt=(Button) findViewById(R.id.register);
		test_bt=(Button) findViewById(R.id.test);
		btn_user_select=(Button) findViewById(R.id.btn_user_selected_d);
		im_username_delete=(ImageView) findViewById(R.id.login_username_delete);
		im_user_icon=(ImageView) findViewById(R.id.login_im_icon);
	}

	/**
	 * 设置监听器事件
	 */
	private void setListenner() {
		name_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(name_et.getText().toString() == ""){
					im_username_delete.setVisibility(View.INVISIBLE);
				}else{
					im_username_delete.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//数据库操作初始化
	 	DBHelper dbhelper=new DBHelper(login.this);
	 		db=dbhelper.getReadableDatabase();

		
		login_bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				//执行AsyncTask
				connect();
			}
	});
		register_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(login.this,register.class);
				startActivity(it);
				
			}
		});
			test_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ActivtyUtil.PutUsernameSharedPre(login.this,"wzb");
				Intent it=new Intent(login.this,MyTabHostFive.class);
				startActivity(it);
				finish();
			}
		});
			im_username_delete.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					name_et.setText(null);
				}
			});
			btn_user_select.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//得到所有用户
					users=DBOpreate.GetAllUserInfo(db);
					if(users == null){
					Toast.makeText(login.this, "暂时还没有人登录过，请登录", 2000).show();	
					}
					//创建View
					View viewDlg = View.inflate(login.this, R.layout.user_selected_dialog, null);
					
					final Dialog dialog = new Dialog(login.this, R.style.user_selected_dialog);
					dialog.setContentView(viewDlg);
					
					dialog.show();
					
					
					ListView listView = (ListView) viewDlg.findViewById(R.id.lv_user_list);
					
					UserInfoAdapter adapter = new UserInfoAdapter(login.this, users,dialog);
					
					listView.setAdapter(adapter);
					
					
					
					listView.setOnItemClickListener(new OnItemClickListener()
					{

						public void onItemClick(AdapterView<?> arg0, View view, int position,
								long id)
						{
							TextView tv43=(TextView) view.findViewById(R.id.txt_show_name);
							Log.e(tag, "item的值"+tv43.getText().toString());
							UserInfo myuser=DBOpreate.GetUserInfoByUserName(tv43.getText().toString(), db);
							name_et.setText(myuser.getUsername());
							pass_et.setText(myuser.getUserpass());
							//网络获取用户信息
			        		DownloadBMPTask dsa=new DownloadBMPTask(login.this, im_user_icon);
			        		dsa.execute(myuser.getUsername().toString());
							
						/*
							ImageView imagUserHead=(ImageView) view.findViewById(R.id.img_user_head_temp);
							TextView txtUserName=(TextView) view.findViewById(R.id.txt_show_name);
							
							
							imge_user_head.setImageDrawable(imagUserHead.getDrawable());
							txt_login_show_name.setText(txtUserName.getText());*/
							
							dialog.dismiss();
							
						}
					});	
				
				}
			});
	}
	protected void connect() {
		// TODO Auto-generated method stub
		
		GetWEBTask get=new GetWEBTask();
		get.execute("");
	}
	//doInBackground接受的参数，第二个为显示进度的参数，第第三个为doInBackground返回和onPostExecute传入的参 数。
	class GetWEBTask extends AsyncTask<String,Integer,String> {//继承AsyncTask 
        @Override 
        protected String doInBackground(String... params) {
        	//String... params表示的是可变参数列表，也就是说，这样的方法能够接受的参数个数是可变的，但不论多少，必须都是String类型的。
        	//比如doInBackground("param1","param2","param3") ，或是doInBackground() 。
        	//处理后台执行的任务，在后台线程执行 
        	
        	name=name_et.getText().toString();
			pass=pass_et.getText().toString();
			String path=URLAPI.getLoginUrl()+"?username="+name+"&password="+pass;
			LogUtil.log(path);
			try {
				//判断有无网络
				if(!NetWork.NetWorkpanduan(login.this)){
					
					result=NetWork.UNCONNET_NETWORK;
					return result;
				}
				
				url=new URL(path);
				try {
					HttpURLConnection con=(HttpURLConnection) url.openConnection();
					LogUtil.log(path);
					con.setDoOutput(true);  
					con.setDoInput(true);  
					con.setConnectTimeout(10000);  //设置连接超时为10s  
					con.setReadTimeout(10000);     //读取数据超时也是10s  
					con.setRequestMethod("GET");  
					con.setUseCaches(false);  
					con.connect();
					
					 is=con.getInputStream();
					 DataInputStream dis=new DataInputStream(is);
					 result=dis.readLine();
					 System.out.println(result);
					 
					 dis.close();
					 is.close();
					 con.disconnect();  
/*					 if(con.getResponseCode() != 200){
							result=NetWork.ERROR_NETWORK;
							return result;
						}*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result=NetWork.ERROR_NETWORK;
					return result;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             return result; 
         } 
          
         protected void onProgressUpdate(Integer... progress) {//在调用publishProgress之后被调用，在ui线程执行 

          } 
  
         protected void onPostExecute(String result) {//后台任务执行完之后被调用，在ui线程执行 
        	 		
        	 NetWork.NetResultChuli(login.this, result, mprogressdialog);
        	 
        	 if(result.equals("success")){
        		
        		 
        		//网络获取用户信息
        		DownloadBMPTask dsa=new DownloadBMPTask(login.this, im_user_icon);
        		dsa.execute(name_et.getText().toString());
        		
      	 		user=new UserInfo();
     			user.setUsername(name);
     			user.setUserpass(pass);
    						
    				if(DBOpreate.UserIsExist(name,db)){
    					//更新用户信息
    					DBOpreate.UpdateUserInfo(user, db);
    				}else{
    				//加入数据库
    				DBOpreate.InsertUser(user, db);
    				
    				Log.e(tag,"insert");
    				}
    				
				 //取消弹框
				mprogressdialog.dismiss();
				Toast.makeText(login.this, "登陆成功", 3000).show();
				//把用户名加入share
				Editor sharedata = getSharedPreferences(jiajulemainActivity.SETTING_INFOS, 0).edit();
				sharedata.putString("username",name);
				Log.e(tag,"存入sharedPreference"+name);
				sharedata.commit();
				
				
				Intent it=new Intent(login.this,MyTabHostFive.class);			
				startActivity(it);
				finish();
			 }else if(result.equals("lose")){
				 mprogressdialog.dismiss();
				 Toast.makeText(login.this, "登陆失败", 3000).show();
			 }
			 else if(result.equals("error")){
				 mprogressdialog.dismiss();
				 Toast.makeText(login.this, "服务器出错。。。。", 3000).show();
				 }
			 else{
				 mprogressdialog.dismiss();
				 Toast.makeText(login.this, "unknowen error", 3000).show();
			 }		 
          } 
          
        protected void onPreExecute () {
        	//在 doInBackground(Params...)之前被调用，在ui线程执行        
			mprogressdialog=new ProgressDialog(login.this);
			mprogressdialog.setMessage("服务器通信中。。。。");
			mprogressdialog.show();
			mprogressdialog.setCancelable(false);
         } 
          
          protected void onCancelled () {//在ui线程执行 
          
          } 
          
     }; 
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if(System.currentTimeMillis() - lastClick < DELAY_TIME){
    		clickNum++;
    	}else{
    		clickNum = 0;
    	}
    	lastClick = System.currentTimeMillis();
    	if(clickNum == 7 ){
    		//填写框
    		final Dialog mDialog= new Dialog(login.this, R.style.Common_Dialog);
    		mDialog.setCanceledOnTouchOutside(false);
    		mDialog.setContentView(R.layout.choose_net_dialog);
    		final EditText et =  (EditText) mDialog.findViewById(R.id.choose_net_dialog_et);
    		Button defaultIpBtn = (Button) mDialog.findViewById(R.id.default_ip);
    		defaultIpBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					URLAPI.IP = URLAPI.HOME_IP;
					Toast.makeText(login.this, "采用家庭默认IP", 100).show();
					mDialog.dismiss();
				}
			});
    		
    		CheckBox mcCheckBox = (CheckBox) mDialog.findViewById(R.id.choose_net_dialog_checkbox);
    		mDialog.findViewById(R.id.choose_net_dialog_btn).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					URLAPI.IP = et.getText().toString();
					mDialog.dismiss();
				}
			});
    		Window window = mDialog.getWindow();
    		LayoutParams layoutParams = window.getAttributes();
    		layoutParams.width = LayoutParams.MATCH_PARENT;
    		mDialog.show();
    		clickNum = 0;
    	}
    	return super.onTouchEvent(event);
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.e(tag, "点击了返回键");
			Intent home = new Intent(Intent.ACTION_MAIN);  

			home.addCategory(Intent.CATEGORY_HOME);   

			startActivity(home); 
	
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
