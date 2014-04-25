package com.example.moodlog.dbutil;

import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.moodlog.domain.Diarys;

public class DBdiarysmanager {
	private Context context;
	private SQLiteDBUtil dbHelp;
	private SQLiteDatabase db;

	public DBdiarysmanager(Context context) {
		this.context = context;
		dbHelp = new SQLiteDBUtil(context);
		db = dbHelp.getWritableDatabase(); // 若没有创建数据库则创建 有则打开数据库
	}

	/**
	 * 插入日志
	 * 
	 * @param diary
	 * @return
	 */
	public boolean insertDiarys(Diarys diary) {
		String sql = "insert into diarys values(null, ?, ?, ?,?)";
		try {
			db.execSQL(sql, new Object[] { diary.getD_id(), diary.getTitle(),
					diary.getContent(), diary.getDiarydate() });

		} catch (SQLException e) {
			return false;
		}
		return true;

	}

	/**
	 * 
	 * @param d_id
	 * @return
	 */
	public List<Diarys> allDiarys(String d_id) {
		List<Diarys> diarys = new ArrayList<Diarys>();
		String sql = "select * from diarys where d_id = ? order by diarydate desc;";
		try {
			Cursor c = db.rawQuery(sql, new String[] { d_id });
			while (c.moveToNext()) {
				Diarys d = new Diarys();
				d.setD_id(c.getInt(c.getColumnIndex("d_id")));
				d.setTitle(c.getString(c.getColumnIndex("title")));
				d.setContent(c.getString(c.getColumnIndex("content")));

				String date = c.getString(c.getColumnIndex("diarydate"));

				java.util.Date diarydate;
				try {
					if (date != null) {
						diarydate = new SimpleDateFormat("yyyy-MM-dd",
								Locale.CHINA).parse(date);
						d.setDiarydate(diarydate);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				diarys.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return diarys;
	};

	/**
	 * 返回日志
	 * 
	 * @param d_id
	 * @return
	 */
	public List<Map<String, ?>> getAllDiarys(String d_id) {
		List<Map<String, ?>> diarys = new ArrayList<Map<String, ?>>();
		String sql = "select * from diarys where d_id = ? order by diarydate desc;";
		try {
			Cursor c = db.rawQuery(sql, new String[] { d_id });
			while (c.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int diary_id = c.getInt(c.getColumnIndex("diary_id"));
				String title = c.getString(c.getColumnIndex("title"));
				String content = c.getString(c.getColumnIndex("content"));
				content = "   " + (String) content.subSequence(0, 5) + "...";
				String date = c.getString(c.getColumnIndex("diarydate"));

				java.util.Date diarydate;
				if (date != null) {
					// diarydate = new
					// SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).parse(date);
					map.put("diary_id", diary_id);
					map.put("title", title);
					map.put("content", content);
					map.put("date", date);
				}
				diarys.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return diarys;
	};

	/**
	 * 根据用户的id 获取日志的条数
	 * 
	 * @param d_id
	 * @return
	 */
	public int getCount(String d_id) {
		int count = 0;
		String sql = "select count(*) from diarys where d_id = ?";
		Cursor c = db.rawQuery(sql, new String[] { d_id });
		if (c.moveToNext()) {
			count = c.getInt(0);
		}
		return count;
	}

	public Diarys getDiarysByDiaryId(int diary_id) {
		String sql = "select * from diarys where diary_id = ?;";
		Diarys d = null;
		try {
			Cursor c = db.rawQuery(sql, new String[]{String.valueOf(diary_id)});
			if(c.moveToLast()) {
				d = new Diarys();
				d.setD_id(c.getInt(c.getColumnIndex("d_id")));
				d.setTitle(c.getString(c.getColumnIndex("title")));
				d.setContent("          " + c.getString(c.getColumnIndex("content")));
				
				String date = c.getString(c.getColumnIndex("diarydate"));
			
			    java.util.Date diarydate;
				try {
					if(date !=null) {
						diarydate = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).parse(date);
						d.setDiarydate(diarydate); 
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		return d;
		
	}
	/**
	 * 删除日志
	 * @param diary_id
	 * @return
	 */
	public boolean deleteDiarys(int diary_id) {
		boolean b = false;
		String sql = "delete from diarys where diary_id = ?;";
		try {
			db.execSQL(sql, new Integer[]{diary_id});
			b = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	public boolean updateDiarys(int diary_id, Diarys d) {
		boolean b = false;
		String sql = "update diarys set title = ?, content = ? where diary_id = ?;";
		try {
			db.execSQL(sql, new Object[]{d.getTitle(), d.getContent(), diary_id});
			b = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	public void dbClose() {
		db.close();
	}

}
