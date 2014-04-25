package com.example.moodlog.junit;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.moodlog.dbutil.DBUsermanager;
import com.example.moodlog.dbutil.DBdiarysmanager;
import com.example.moodlog.dbutil.SQLiteDBUtil;
import com.example.moodlog.domain.Diarys;

public class DBtest extends AndroidTestCase{
	public void dbCreate() {
		SQLiteDBUtil util = new SQLiteDBUtil(getContext());
		util.getWritableDatabase();   //生产数据库
	}
	public void dbAdd() throws ParseException {
		DBdiarysmanager manager = new DBdiarysmanager(getContext());
		Diarys d = new Diarys();
		d.setContent("qsasddsfdsfdsf");
		d.setTitle("123");
		manager.updateDiarys(5, d);
		//Diarys d = manager.getDiarysByDiaryId(2);
		//Log.i("TAG", d.getTitle() + ":" + d.getContent() + ":::" + d.getDiarydate()  );
		/*		//获取手机系统自带的时间
				Calendar cal=Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DATE);
				String time = year+"-" + month + "-" + day;
				
		Diarys d = new Diarys(6, "qaxxxz", "sfnskjxsjkb", Date.valueOf(time));
		manager.insertDiarys(d);*/
		
		/*List<Map<String, ?>> list = manager.getAllDiarys("1");  //根据id查找`
		for(Map<String,?> map : list) {
				String title = (String) map.get("title");
				String content = (String) map.get("content");
				String date = (String) map.get("date");
				int diary_id = (Integer) map.get("diary_id");
				//String date = diary.getDiarydate().toString();
				//将英文时间 转换为中文时间
				 //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				 
				
				Log.i("TAG", title + ":" + content + ":::" + date + ":::id:" + diary_id );
		
			
		}*/
		manager.dbClose();
	}
	public void timeTest() throws ParseException {
		Diarys diary = new Diarys();
		
		//获取手机系统自带的时间
		Calendar cal=Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String time = year+"-" + month + "-" + day;
		
		java.util.Date diarydate = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).parse(time);
		
		diary.setDiarydate(diarydate);
		Log.i("TAG", diary.getDiarydate().toString());
		
	} 
	public void UserTest() {
		DBUsermanager db = new DBUsermanager(getContext());
		int i = db.getUserId("sssssss0");
		Log.i("TAG", i + "--->");
	}
	public void getCount() {
		DBdiarysmanager manager = new DBdiarysmanager(getContext());
		int i = manager.getCount("1");
		
		Log.i("TAG", i + "--->");
	}
	//根据用户名获取id
	public void getUsernameID() {
		DBUsermanager db = new DBUsermanager(getContext());
		int id = db.getUserId("qqq");
		Log.i("TAG", id + "--->");
		
	}
	
}
