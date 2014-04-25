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
	private ProgressDialog dialog;  //�Ի���
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
			music_count.setText(i + "�׸���");
		} else {
			music_count.setText("0�׸���");
			Toast.makeText(MusicActivity.this, "û�и���", 1).show();
		}
	}
	
	public void init() {		
		native_songs = (ImageView) findViewById(R.id.native_songs);
		native_songs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//���������б�
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
			dialog.setMessage("����ɨ�裬���Ժ󡣡���");
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
			//��������share preference��
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
				Toast.makeText(MusicActivity.this, "����ɨ��ɹ�", 1).show();
				music_count = (TextView) findViewById(R.id.music_count);
				m_Util = new MusicUtil(MusicActivity.this);
				music_count = (TextView) findViewById(R.id.music_count);
				int i = m_Util.getMusicCount();
				if(i > 0) {
					music_count.setText(i + "�׸���");
				} else {
					music_count.setText("0�׸���");
					Toast.makeText(MusicActivity.this, "û�и���", 1).show();
				}		
			} else {
				dialog.dismiss();
				Toast.makeText(MusicActivity.this, "����ɨ��ʧ��", 1).show();
			}
		}
		
	}


}
