package com.jiajule.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jiajule.util.DatabaseHelper;
import com.jiajule.db.DBInfo.DB;
import com.jiajule.javabean.UserInfo;

public class DBOpreate {
	static String[] columns={"username","password"};
	static String tag="wangzhibo";
	
	public static SQLiteDatabase GetDB(Context context){
		DBHelper dbhelper=new DBHelper(context);
		SQLiteDatabase db=dbhelper.getReadableDatabase();
		return db;
	}
	
	public static void closeDB(SQLiteDatabase db){
		if(db != null){
			db.close();
		}
	}
	
	public static void InsertUser(UserInfo user,SQLiteDatabase db){
		String insert="insert into "+DBInfo.Table.USER_INFO_TB_NAME+"(_id,username,password) values(null,'"+user.getUsername()+"','"+user.getUserpass()+"')";
		db.execSQL(insert);
		Log.e("wangzhibo", insert);
		
	}
	/**
	 * 查询所有用户信息
	 * @param db
	 * @return
	 */
	public static List<UserInfo> GetAllUserInfo(SQLiteDatabase db){
		Cursor cursor=db.query(DBInfo.Table.USER_INFO_TB_NAME,new String[]{"username","password"}, null, null, null, null, null);
		List<UserInfo> list=null;
		if(null != cursor && cursor.getCount()>0){
			list=new ArrayList<UserInfo>();
			UserInfo userInfo = null;
			
			while(cursor.moveToNext()){
			userInfo=new UserInfo();
			String username=cursor.getString(cursor.getColumnIndex("username"));
			userInfo.setUsername(username);
			String password=cursor.getString(cursor.getColumnIndex("password"));
			userInfo.setUserpass(password);
			list.add(userInfo);
			}
		}
		return list;
	}
	public static UserInfo GetUserInfoByUserName(String username,SQLiteDatabase db){
		Cursor cursor=db.query(DBInfo.Table.USER_INFO_TB_NAME, columns,"username=?", new String[]{username}, null, null, null);
		UserInfo myuser=new UserInfo();
		String password = null;
		Log.e(tag, "GetUserInfoByUserName的cursorCount "+cursor.getCount());
			if(cursor.getCount()>0){
				while(cursor.moveToNext()){
				password=cursor.getString(cursor.getColumnIndex("password"));
				Log.e(tag, "GetUserInfoByUserName的pass "+password);
				}
			}
			myuser.setUsername(username);
			myuser.setUserpass(password);		
		return myuser;
	}
	
	public static boolean UserIsExist(String username,SQLiteDatabase db){
		Cursor cursor=db.query(DBInfo.Table.USER_INFO_TB_NAME,columns,"username=?", new String[]{username}, null, null, null);
		if(cursor.getCount()>0){
			return true;
		}
		return false;
	}
	
	public static void UpdateUserInfo(UserInfo user,SQLiteDatabase db){
		/*ContentValues cv = new ContentValues(); 
		cv.put("password", user.password);  
		db.update(DBInfo.Table.USER_INFO_TB_NAME, cv, "username = ?", new String[]{user.username}); */
		String sql="update "+DBInfo.Table.USER_INFO_TB_NAME+" set password='"+user.getUserpass()+"' where username='"+user.getUsername()+"'";
		
		db.execSQL(sql);
		Log.e(tag, sql);
	}
	
	public static void DeleteUserInfoByUsername(UserInfo user,SQLiteDatabase db){
		String sql="delete from "+DBInfo.Table.USER_INFO_TB_NAME+" where username='"+user.getUsername()+"'";
		Log.e(tag, sql);
		db.execSQL(sql);
	}
}
