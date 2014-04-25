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
	private String name;  //歌曲名字
	private String path; //歌曲路径
	private boolean flag = false; //播放暂停切换
	private SeekBar seekBar;// 进度条
	private TextView seek_time; //完成的时间
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_music);

		init();
	}
	
	/**
	 * activity 退出时候 销毁对象
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
		name = (String) bundle.get("musicName");  //歌曲名
		path = (String) bundle.get("musicPath");  //歌曲姓名
		
		
		play_pause = (Button) findViewById(R.id.play_pause);
		play_pause.setOnClickListener(new myClickListener());
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		seek_time = (TextView) findViewById(R.id.seek_time);
		
		mediaPlay = new MediaPlayer();  //实例化音乐类
		
	}
	class myClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//点击播放盒暂停
			try {
				if(mediaPlay.isPlaying() && !flag) {
					//play_pause.setText("暂停");
					mediaPlay.pause();
					play_pause.setBackgroundResource(R.anim.pause);
					flag = true;
					return;
				}
				if(flag) {
					//play_pause.setText("播放");
					mediaPlay.start();
					play_pause.setBackgroundResource(R.anim.start);
					flag = false;
					return;
				}
				mediaPlay.setDataSource(path);		
				mediaPlay.prepare();
				//缓冲完成后调用该方法
				mediaPlay.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						// TODOAuto-generated method stub //播放
						Toast.makeText(PlayMusicActivity.this, "播放开始", 1).show();
						seekBar.setMax(mediaPlay.getDuration());  //为滚动条设置最大长度
						mediaPlay.start();
					}
				});
				
				//歌曲完成后释放 
				mediaPlay.setOnCompletionListener(new OnCompletionListener(){
	                @Override
	                public void onCompletion(MediaPlayer mp) {
	                    mp.release();
	                    mediaPlay = new MediaPlayer();  //实例化音乐类
	                    seekBar.setMax(mediaPlay.getDuration());  //为滚动条设置最大长度
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
	//自定义bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 //添加菜单项  
        MenuItem return_music = menu.add(0,0,0,"返回");    
        //绑定到ActionBar    
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
		//滑动时候
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
