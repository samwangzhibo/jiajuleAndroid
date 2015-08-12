package com.jiajule.db;

public class DBInfo
{

	
	public static class DB
	{
		
		//数据库名字
		public static  final String DB_NAME="renwutie_user"; 
		
		//版本
		public static final int  VERSION=1;
		
	}
	
	
	public static class Table
	{
		
		public static final String USER_INFO_TB_NAME="UserInfo";
		public static final String USER_INFO_CREATE="CREATE TABLE IF NOT EXISTS  " + USER_INFO_TB_NAME + "(_id INTEGER PRIMARY KEY autoincrement,username TEXT not null,password TEXT not null)";
		public static final String USER_INFO_DROP="DROP TABLE " + USER_INFO_TB_NAME; 
		
		
	}
	
	
	
}
