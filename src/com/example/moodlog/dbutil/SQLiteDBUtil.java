package com.example.moodlog.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDBUtil extends SQLiteOpenHelper {
//	private static int VERSION = 1;
	private static int VERSION = 2;
	private static String DB_NAME = "moodlog";  //д��moodlog.dbҲ����
	/**
	 * 
	 * @param context
	 * @param name ���ݿ���
	 * @param factory  ��¼�α깤��
	 * @param version  �汾��
	 */
	public SQLiteDBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SQLiteDBUtil(Context context) {
		super(context, DB_NAME, null, VERSION);
		//û�оʹ������ݿ� �оʹ����ݿ�
		
	}

	/**
	 * ����ʹ��ʱ �������ݿ��
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
	 * �������ʱ �������ݿ��ṹ
	 * ���統versionΪ2��ִ������ĺ���
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		onCreate(db);

	}

}
