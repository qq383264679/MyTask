package com.example.moodlog.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;

public class MusicUtil {
	private Context context;
	public MusicUtil(Context context) {
		this.context = context;
	}
	public Map<String, Object> map = new HashMap<String, Object>();
	/**
	 * 获取歌曲名   和路径     的map集合 
	 * @param f
	 */
	public void getMusic(File f) {
		
		File[] files = f.listFiles();
		for(File fs : files) {
			String fileName = fs.getName();
			//判断f是否是一个目录
			//f.isFile()  判断是否是一个标准文件
			//f.isDirectory() 判断是否是一个文件夹
			if(fs.isDirectory() && !fileName.startsWith(".")) {
				getMusic(fs);
			} 
			if(fileName.endsWith(".mp3")) {
				map.put(fileName, fs.getAbsolutePath());
					//Log.i("TAG", fileName + "文件路径:" + fs.getAbsolutePath());
			}	
		}

		
	}
	/**
	 * 获取歌曲的数量
	 * @return
	 */
	public int getMusicCount() {
		SharedPreferencesUtil p_util = new SharedPreferencesUtil(context);
		Map<String, ?> map = p_util.getAllMessage("music");
		return map.size();
	}
	/**
	 * 获取map 集合
	 * 歌名 + 歌曲路径
	 * @return
	 */
	public Map<String, ?> getMusicMap() {
		SharedPreferencesUtil p_util = new SharedPreferencesUtil(context);
		Map<String, ?> map = p_util.getAllMessage("music");
		return map;
	}
	public void saveMusic(String fileName, Map<String, Object> map) {
		SharedPreferencesUtil p_util = new SharedPreferencesUtil(context);
		p_util.saveMessage(fileName, map);
	}
}
