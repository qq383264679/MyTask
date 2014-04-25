package com.example.moodlog.junit;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.moodlog.dbutil.DBUsermanager;
import com.example.moodlog.domain.Users;
import com.example.moodlog.service.UsersLogn;
import com.example.moodlog.util.SharedPreferencesUtil;

public class Test extends AndroidTestCase {
	public void getAllMap() {
		SharedPreferencesUtil util = new SharedPreferencesUtil(getContext());
		Map<String, ?> map = util.getAllMessage("logn");
		
		//±éÀú
		for(Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();
			
			Log.i("TAG", "-->" + object);
		}
	}
	public void test() {
		Users u = new Users();
		u.setUsername("yang");
		u.setPassword("12345");
		UsersLogn logn = new UsersLogn(getContext());
		boolean b = logn.checkUser(u);
		Log.i("TAG", b + ">>>|");
	}
	@SuppressLint("NewApi")
	public void check() {
		String u = "";
		String pass = "";
		String p = "";
		
		if(!pass.equals(p) && !u.isEmpty() && !pass.isEmpty()) {
			Log.i("TAG", "OK");
		} else {
			Log.i("TAG", "failure");
		}
	}
	public void updateUser() {
		Users u = new Users("123", "123456");
		DBUsermanager db = new DBUsermanager(getContext());
		db.updatePassword(u);
		List<Users> list = db.getAllUsers();
		
		for(Users uu : list) {
			Log.i("TAG", uu.getUsername() + ":::" + uu.getPassword());
		}
		//Log.i("TAG", db.updatePassword(u) + "-->");
	}
}
