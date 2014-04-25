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
	 * ��ȡ������   ��·��     ��map���� 
	 * @param f
	 */
	public void getMusic(File f) {
		
		File[] files = f.listFiles();
		for(File fs : files) {
			String fileName = fs.getName();
			//�ж�f�Ƿ���һ��Ŀ¼
			//f.isFile()  �ж��Ƿ���һ����׼�ļ�
			//f.isDirectory() �ж��Ƿ���һ���ļ���
			if(fs.isDirectory() && !fileName.startsWith(".")) {
				getMusic(fs);
			} 
			if(fileName.endsWith(".mp3")) {
				map.put(fileName, fs.getAbsolutePath());
					//Log.i("TAG", fileName + "�ļ�·��:" + fs.getAbsolutePath());
			}	
		}

		
	}
	/**
	 * ��ȡ����������
	 * @return
	 */
	public int getMusicCount() {
		SharedPreferencesUtil p_util = new SharedPreferencesUtil(context);
		Map<String, ?> map = p_util.getAllMessage("music");
		return map.size();
	}
	/**
	 * ��ȡmap ����
	 * ���� + ����·��
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
