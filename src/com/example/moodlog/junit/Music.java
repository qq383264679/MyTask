package com.example.moodlog.junit;

import java.io.File;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.moodlog.util.MusicUtil;
import com.example.moodlog.util.SharedPreferencesUtil;

public class Music extends AndroidTestCase {
	public void test() {
		MusicUtil m_util = new MusicUtil(getContext());
		SharedPreferencesUtil p_util = new SharedPreferencesUtil(getContext());
		
		File root = Environment.getExternalStorageDirectory();
		Log.i("TAG", root.getAbsolutePath() + "::");
		m_util.getMusic(root);
		
		
		Set set = m_util.map.keySet();
		for(Object key : set) {
			Log.i("TAG", key + ":::" + m_util.map.get(key));
		}
	/*	p_util.saveMessage("music", m_util.map);
		Log.i("TAG", m_util.map.isEmpty() + "--->" );
		
		Map<String, ?> map = p_util.getAllMessage("music");
		Set<String> set = map.keySet();
		for(Object key : set) {
			Log.i("TAG", key + "--->" + map.get(key));
		}*/
		
		//Çå¿Õshared preferenceÎÄ¼þ
		/*SharedPreferences preferences = getContext().getSharedPreferences("music",
				Context.MODE_PRIVATE);
		boolean b = preferences.edit().clear().commit();
		Log.i("TAG", b + "--->");*/
	}
	public void test1() {
		Log.i("TAG", "xxxxxxxxxxxx");
	}
}
