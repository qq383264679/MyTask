package com.example.moodlog;

import com.example.moodlog.domain.Diarys;
import com.example.moodlog.domain.Users;
import com.example.moodlog.service.DiaryService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class WatchDiaryActivity extends Activity {
	private Bundle bundle;
	private String username;
	private TextView watch_txtTitle;
	private TextView watch_content;
	private int diary_id;
	private DiaryService dService;
	private ProgressDialog progressDialog;  //进度条
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String s[] = (String[]) msg.obj;
			watch_txtTitle.setText(s[0]);
			watch_content.setText(s[1]);
			super.handleMessage(msg);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch_diarys);

		init();
	}

	public void init() {
		bundle = getIntent().getExtras();
		username = bundle.getString("username");
		diary_id = bundle.getInt("diary_id");
		watch_txtTitle = (TextView) findViewById(R.id.watch_txtTitle);
		watch_content = (TextView) findViewById(R.id.watch_content);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在加载中");
		
		new MyTask().execute();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.actionbar_menu_sign, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.return_logn:
			intent.setClass(this, DiarysActivity.class);
			bundle.putString("username", username);
			intent.putExtras(bundle);
			startActivity(intent);
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * 异步加载
	 * @author fengchao
	 *
	 */
	public class MyTask extends AsyncTask<Void, Integer, Boolean> {
		//启动进度条
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if(result) {
				progressDialog.dismiss();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			dService = new DiaryService(WatchDiaryActivity.this);
			Diarys d = dService.getDiarys(diary_id);
			Message msg = Message.obtain();
			msg.obj = new String[]{d.getTitle(), d.getContent()};
			
			handler.sendMessage(msg);
			
			return true;
		}
		
	}

}
