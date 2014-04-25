package com.example.moodlog;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayMusicActivity extends Activity implements OnSeekBarChangeListener{
	private MediaPlayer mediaPlay;
	private Button play_pause;
	private String name;  //��������
	private String path; //����·��
	private boolean flag = false; //������ͣ�л�
	private SeekBar seekBar;// ������
	private TextView seek_time; //��ɵ�ʱ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_music);

		init();
	}
	
	/**
	 * activity �˳�ʱ�� ���ٶ���
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mediaPlay != null) {
			mediaPlay.release();
			mediaPlay = null;
		}
	}
	public void init() {
		Bundle bundle = this.getIntent().getExtras();
		name = (String) bundle.get("musicName");  //������
		path = (String) bundle.get("musicPath");  //��������
		
		
		play_pause = (Button) findViewById(R.id.play_pause);
		play_pause.setOnClickListener(new myClickListener());
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		seek_time = (TextView) findViewById(R.id.seek_time);
		
		mediaPlay = new MediaPlayer();  //ʵ����������
		
	}
	class myClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//������ź���ͣ
			try {
				if(mediaPlay.isPlaying() && !flag) {
					//play_pause.setText("��ͣ");
					mediaPlay.pause();
					play_pause.setBackgroundResource(R.anim.pause);
					flag = true;
					return;
				}
				if(flag) {
					//play_pause.setText("����");
					mediaPlay.start();
					play_pause.setBackgroundResource(R.anim.start);
					flag = false;
					return;
				}
				mediaPlay.setDataSource(path);		
				mediaPlay.prepare();
				//������ɺ���ø÷���
				mediaPlay.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						// TODOAuto-generated method stub //����
						Toast.makeText(PlayMusicActivity.this, "���ſ�ʼ", 1).show();
						seekBar.setMax(mediaPlay.getDuration());  //Ϊ������������󳤶�
						mediaPlay.start();
					}
				});
				
				//������ɺ��ͷ� 
				mediaPlay.setOnCompletionListener(new OnCompletionListener(){
	                @Override
	                public void onCompletion(MediaPlayer mp) {
	                    mp.release();
	                    mediaPlay = new MediaPlayer();  //ʵ����������
	                    seekBar.setMax(mediaPlay.getDuration());  //Ϊ������������󳤶�
	                }
	             });
				
		
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@SuppressLint("NewApi")
	//�Զ���bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 //��Ӳ˵���  
        MenuItem return_music = menu.add(0,0,0,"����");    
        //�󶨵�ActionBar    
        return_music.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);  
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == 0) {
			Intent intent = new Intent();
			intent.setClass(PlayMusicActivity.this, MusicActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	
	}

	
	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		//����ʱ��
		if(fromUser) {
			mediaPlay.seekTo(seekBar.getProgress());
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
