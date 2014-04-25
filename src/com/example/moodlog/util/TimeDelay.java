package com.example.moodlog.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TimeDelay {
	public Timer timer;
	public int index = 0;
	public TimeDelay(Timer timer) {
		this.timer = timer;	
	}
	public void startTime(final Handler handler) {
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				index++;
				Message message = Message.obtain();
				message.arg1 = index;
				handler.sendMessage(message);
				
			}
		}, 1000, 1000); //1√Î÷”∫Ûø™ º —”≥Ÿ1√Î
	}
	public void cancelTime() {
		timer.cancel();
	}
}
