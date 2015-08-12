package com.jiajule.db;

import com.jiajule.db.DBInfo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	public DBHelper(Context context) 
	{
		///����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����,����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����

		super(context, DBInfo.DB.DB_NAME, null, DB.VERSION);
	}
	//���ڳ���ʹ�����ʱ�������ݿ��
		//������SQLiteOpenHelper��getWritableDatabase()����getReadableDatabase()������ȡ���ڲ������ݿ��SQLiteDatabaseʵ����ʱ��
		//������ݿⲻ���ڣ�Androidϵͳ���Զ�����һ�����ݿ⣬���ŵ���onCreate()������
		//onCreate()�����ڳ����������ݿ�ʱ�Żᱻ���ã�
		//��onCreate()����������������ݿ��ṹ�����һЩӦ��ʹ�õ��ĳ�ʼ������
		public void onCreate(SQLiteDatabase db)
		{
			
			//String create_cus="CREATE TABLE IF NOT EXISTS Customer (_id integer primary key autoincrement, name varchar(20), age INTEGER)";
			db.execSQL(DBInfo.Table.USER_INFO_CREATE);
			Log.e("wangzhibo:createDB=", DBInfo.Table.USER_INFO_CREATE);
		}

		//onUpgrade()���������ݿ�İ汾�����仯ʱ�ᱻ���ã�һ�����������ʱ����ı�汾��
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			
			
			//db.execSQL("DROP TABLE IF EXISTS Customer");
	   // onCreate(db);

	    //�������������ݿ�汾ÿ�η����仯ʱ������û��ֻ��ϵ����ݿ��ɾ����Ȼ�������´�����
	    	//һ����ʵ����Ŀ���ǲ����������ģ���ȷ���������ڸ������ݿ��ṹʱ����Ҫ�����û���������ݿ��е����ݲ��ᶪʧ
	    
		}
}
