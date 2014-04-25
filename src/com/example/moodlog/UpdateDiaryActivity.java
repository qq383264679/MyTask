package com.example.moodlog;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.moodlog.dbutil.DBdiarysmanager;
import com.example.moodlog.domain.Diarys;
import com.example.moodlog.service.DiaryService;

public class UpdateDiaryActivity extends Activity {
	private EditText update_txtTitle;
	private EditText update_content;
	private DiaryService dService;
	private ProgressDialog progressDialog;  //进度条
	private Integer diary_id;
	private String username;
	private Bundle bundle;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if((Boolean) msg.obj) {
				Toast.makeText(UpdateDiaryActivity.this, "修改成功", 1).show();
				Intent intent = new Intent();
				intent.setClass(UpdateDiaryActivity.this, DiarysActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				UpdateDiaryActivity.this.finish();
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_diarys);
		
		init();
	}
	public void init() {
		update_txtTitle = (EditText) findViewById(R.id.update_txtTitle);
		update_content = (EditText) findViewById(R.id.update_content);
		
		bundle = getIntent().getExtras();
		username = bundle.getString("username");
		diary_id = bundle.getInt("diary_id");
		dService = new DiaryService(this);
		Diarys d = dService.getDiarys(diary_id);
		update_txtTitle.setText(d.getTitle());
		update_content.setText(d.getContent());
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("请稍后");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.actionbar_menu_update, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.btnUpdate:
			new MyTask().execute();
			break;
		case R.id.up_return:
			Intent intent = new Intent();
			intent.setClass(UpdateDiaryActivity.this, DiarysActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			UpdateDiaryActivity.this.finish();
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
			DBdiarysmanager manager = new DBdiarysmanager(UpdateDiaryActivity.this);
			Diarys d = new Diarys();
			d.setContent(update_content.getText().toString().trim());
			d.setTitle(update_txtTitle.getText().toString().trim());
			boolean b = manager.updateDiarys(diary_id, d);
		
			Message msg = Message.obtain();
			msg.obj = b;
			handler.sendMessage(msg);
			manager.dbClose();
			return true;
		}
		
	}
	
}
