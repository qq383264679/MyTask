package com.example.moodlog.service;

import java.util.List;

import android.content.Context;

import com.example.moodlog.dbutil.DBUsermanager;
import com.example.moodlog.domain.Users;

public class UsersLogn {
	/**
	 * 核对用户名是否正确
	 * @param u
	 * @return
	 */
	private DBUsermanager db;
	private Context context;
	public UsersLogn(Context context) {
		this.context = context;
	}
	public boolean checkUser(Users u) {
		db = new DBUsermanager(context);
		List<Users> list = db.getAllUsers();
		for(Users user : list) {
			String username = user.getUsername();
			String password = user.getPassword();
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return true;
			}
			
		}
		db.dbClose();
		return false;
	}
}
