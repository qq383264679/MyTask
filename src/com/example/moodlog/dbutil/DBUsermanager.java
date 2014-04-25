package com.example.moodlog.dbutil;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.moodlog.domain.Users;

public class DBUsermanager {
	private Context context;
	private SQLiteDBUtil dbHelp;
	private SQLiteDatabase db;
	public DBUsermanager(Context context) {
		this.context = context;
		dbHelp = new SQLiteDBUtil(context);
		db = dbHelp.getWritableDatabase();  //若没有创建数据库则创建 有则打开数据库
	}
	
	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 */
	public boolean insertUsers(List<Users> list) {
		String sql = "insert into users values(null, ?, ?)";
		db.beginTransaction();  //开启事物
	    try {  
            for (Users user : list) {  
                db.execSQL(sql, new Object[]{user.getUsername(), user.getPassword()});  
            }  
            db.setTransactionSuccessful();  //设置事务成功完成  
        } catch(SQLException e){
        	return false;
        }finally {  
            db.endTransaction();    //结束事务  
        }  
		return true;
	}
	/**
	 * 单个像数据库中添加数据 
	 * @param user
	 * @return
	 */
	public boolean insertUsers(Users user) {
		String sql = "insert into users values(null, ?, ?)";
		db.beginTransaction();  //开启事物
	    try {              
            db.execSQL(sql, new Object[]{user.getUsername(), user.getPassword()});  
            db.setTransactionSuccessful();  //设置事务成功完成  
        } catch(SQLException e) {
        	return false;
        }finally {  
            db.endTransaction();    //结束事务  
            db.close();
        }  
		return true;
	}
	/**
	 * 跟新用户密码
	 * @param user
	 */
	public void updateUsers(Users user) {
		String sql = "insert into users values(null, ?, ?)";
		ContentValues cv = new ContentValues();
		cv.put("password", user.getPassword());
		db.update("users", cv, "username = ?", new String[]{user.getUsername()});
	
	}
	public List<Users> getAllUsers() {
		String sql = "select * from users";
		List<Users> list = new ArrayList<Users>();
		Cursor c = db.rawQuery(sql, null);
		while(c.moveToNext()) {
			String username = c.getString(c.getColumnIndex("username"));
			String password = c.getString(c.getColumnIndex("password"));
			Users user = new Users(username, password);
			list.add(user);
		}

		return list;
	}
	/**
	 * 根据用户名获取 userid
	 * @param username
	 * @return
	 */
	public int getUserId(String username) {
		String sql = "select * from users where username = ?";
		int i = -1;
		try {
			Cursor c = db.rawQuery(sql, new String[]{username});
			if(c.moveToNext()) {
				i = c.getInt(c.getColumnIndex("userid"));
			}
			return i;
		} catch(SQLException e){
			e.printStackTrace();
		} 
		return -1;
	}
	public void dbClose() {
		db.close();
	}
	public boolean updatePassword(Users u) {
		boolean b = false;
		String sql = "update users set password = ? where username = ?;";
		try {
			db.execSQL(sql, new Object[]{u.getPassword(), u.getUsername()});
			b = true;
			return b;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
}
