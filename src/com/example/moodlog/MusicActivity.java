package com.example.moodlog;
import java.io.File;
import java.util.Map;

import com.example.moodlog.util.MusicUtil;
import com.example.moodlog.util.SharedPreferencesUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
public class MusicActivity extends Activity {
	private ImageView native_songs;
	private ViewFlipper flipper;
	private MusicUtil m_Util;
	private TextView music_count;
	private ProgressDialog dialog;  //对话框
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		checkMusic();
		
		init();
	}
	public void checkMusic() {
		m_Util = new MusicUtil(MusicActivity.this);
		music_count = (TextView) findViewById(R.id.music_count);
		int i = m_Util.getMusicCount();
		if(i > 0) {
			music_count.setText(i + "首歌曲");
		} else {
			music_count.setText("0首歌曲");
			Toast.makeText(MusicActivity.this, "没有歌曲", 1).show();
		}
	}
	
	public void init() {		
		native_songs = (ImageView) findViewById(R.id.native_songs);
		native_songs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//进入乐曲列表
				Intent intent = new Intent();
				bundle = getIntent().getExtras();
				intent.putExtras(bundle);
				intent.setClass(MusicActivity.this, NativeMusicActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		this.getMenuInflater().inflate(R.menu.actionbar_menu_music, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case R.id.music_scan:
			dialog = new ProgressDialog(MusicActivity.this);
			dialog.setMessage("正在扫描，请稍后。。。");
			new MyTask().execute();
			break;
		case R.id.music_return:
			intent = new Intent();
			bundle = getIntent().getExtras();
			Log.i("TAG", bundle.getString("username"));
			intent.setClass(MusicActivity.this, DiarysActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * @author fengchao
	 *
	 */
	class MyTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			m_Util = new MusicUtil(MusicActivity.this);
			
			File root = Environment.getExternalStorageDirectory();
			m_Util.getMusic(root);
			//存数据在share preference中
			m_Util.saveMusic("music", m_Util.map);
			return true;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result) {
				dialog.dismiss();
				Toast.makeText(MusicActivity.this, "歌曲扫描成功", 1).show();
				music_count = (TextView) findViewById(R.id.music_count);
				m_Util = new MusicUtil(MusicActivity.this);
				music_count = (TextView) findViewById(R.id.music_count);
				int i = m_Util.getMusicCount();
				if(i > 0) {
					music_count.setText(i + "首歌曲");
				} else {
					music_count.setText("0首歌曲");
					Toast.makeText(MusicActivity.this, "没有歌曲", 1).show();
				}		
			} else {
				dialog.dismiss();
				Toast.makeText(MusicActivity.this, "歌曲扫描失败", 1).show();
			}
		}
		
	}


}
