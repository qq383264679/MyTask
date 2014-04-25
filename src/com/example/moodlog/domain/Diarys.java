package com.example.moodlog.domain;

import java.util.Date;

public class Diarys {
	private int d_id;
	private String title;
	private String content;
	private Date diarydate;
	public Diarys() {};
	public Diarys(int d_id, String title, String content, Date diarydate) {
		this.d_id = d_id;
		this.title = title;
		this.content = content;
		this.diarydate = diarydate;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDiarydate() {
		return diarydate;
	}
	public void setDiarydate(Date diarydate) {
		this.diarydate = diarydate;
	}
}
