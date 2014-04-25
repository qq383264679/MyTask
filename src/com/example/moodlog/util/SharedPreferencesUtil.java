package com.example.moodlog.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPreferencesUtil {
	private Context context;

	public SharedPreferencesUtil(Context context) {
		this.context = context;
	}

	//覆盖原来的
	public boolean saveMessage(String fileName, Map<String, Object> map) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// 遍历map集合
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();

			if (object instanceof Boolean) {
				Boolean new_name = (Boolean) object;
				editor.putBoolean(key, new_name);
			} else if (object instanceof Integer) {
				Integer integer = (Integer) object;
				editor.putInt(key, integer);
			} else if (object instanceof Float) {
				Float f = (Float) object;
				editor.putFloat(key, f);
			} else if (object instanceof Long) {
				Long l = (Long) object;
				editor.putLong(key, l);
			} else if (object instanceof String) {
				String s = (String) object;
				editor.putString(key, s);
			}
			Log.i("TAG", key + "::" + object);
		}
		
		boolean flag = editor.commit();
		return flag;
	}
	public Map<String, ?> getAllMessage(String fileName) {
		SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Map<String, ?> map = preferences.getAll();
		return map;
	}
	//在原文件中追加
/*	public boolean saveMessage_(String fileName, Map<String, Object> map) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_APPEND);
		Editor editor = preferences.edit();
		// 遍历map集合
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();

			if (object instanceof Boolean) {
				Boolean new_name = (Boolean) object;
				editor.putBoolean(key, new_name);
			} else if (object instanceof Integer) {
				Integer integer = (Integer) object;
				editor.putInt(key, integer);
			} else if (object instanceof Float) {
				Float f = (Float) object;
				editor.putFloat(key, f);
			} else if (object instanceof Long) {
				Long l = (Long) object;
				editor.putLong(key, l);
			} else if (object instanceof String) {
				String s = (String) object;
				editor.putString(key, s);
			}
			Log.i("TAG", key + "::" + object);
		}
		
		boolean flag = editor.commit();
		return flag;
	}*/
	
}
