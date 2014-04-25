package com.example.moodlog.service;

import android.content.Context;

import com.example.moodlog.dbutil.DBUsermanager;
import com.example.moodlog.dbutil.DBdiarysmanager;
import com.example.moodlog.domain.Diarys;

public class DiaryService {
	private DBdiarysmanager db;
	private Context context;
	public DiaryService(Context context) {
		this.context = context;
	}
	public int getDiaryID(String username) {
		DBUsermanager users = new DBUsermanager(context);
		int i = users.getUserId(username);
		return i;
	}
	public int getCount(String username) {
		DBUsermanager users = new DBUsermanager(context);
		int i = users.getUserId(username);
		DBdiarysmanager diaryUtil = new DBdiarysmanager(context);
		return diaryUtil.getCount(String.valueOf(i));
		
	}
	public Diarys getDiarys(int diary_id) {
		DBdiarysmanager diaryUtil = new DBdiarysmanager(context);
		Diarys d = diaryUtil.getDiarysByDiaryId(diary_id);
		diaryUtil.dbClose();
		return d;
	}
}
