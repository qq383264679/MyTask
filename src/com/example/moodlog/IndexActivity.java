package com.example.moodlog;

import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

import com.example.moodlog.util.TimeDelay;

public class IndexActivity extends Activity {
	private Timer timer;
	private TimeDelay td;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int index = msg.arg1;
			
			if(index > 2) {
				td.cancelTime();
			
				Intent intent = new Intent();
				intent.setClass(IndexActivity.this, LognActivity.class);
				startActivity(intent);
				IndexActivity.this.finish();
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //È¥µôtitle
		setContentView(R.layout.activity_index);
		
		init();
	
	}
	public void init() {
		timer = new Timer();
		td = new TimeDelay(timer);
		//Log.i("TAG", "index + >>>>" + td.index);
		td.startTime(handler);
		//if(td.index > 15) {
		//}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

}
