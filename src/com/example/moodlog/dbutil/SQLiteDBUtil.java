package com.example.moodlog.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDBUtil extends SQLiteOpenHelper {
//	private static int VERSION = 1;
	private static int VERSION = 2;
	private static String DB_NAME = "moodlog";  //写成moodlog.db也可以
	/**
	 * 
	 * @param context
	 * @param name 数据库名
	 * @param factory  记录游标工厂
	 * @param version  版本号
	 */
	public SQLiteDBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SQLiteDBUtil(Context context) {
		super(context, DB_NAME, null, VERSION);
		//没有就创建数据库 有就打开数据库
		
	}

	/**
	 * 初次使用时 生产数据库表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String usersTableSQL = "create table users("
				+ "userid integer primary key autoincrement, "
				+ "username varchar(30), "
				+ "password varchar(30))";
		String  diarysTableSQL = 
		"create table diarys(diary_id integer primary key autoincrement,d_id integer,title varchar(100), content varchar(200),diarydate date,FOREIGN KEY(d_id) REFERENCES users(userid));";		
				

		db.execSQL(usersTableSQL);
		db.execSQL(diarysTableSQL);		
	}

	/**
	 * 升级软件时 更新数据库表结构
	 * 例如当version为2就执行下面的函数
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		onCreate(db);

	}

}
