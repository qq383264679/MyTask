package com.example.moodlog;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.moodlog.util.MusicUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NativeMusicActivity extends Activity {
	private MusicUtil m_Util;
	private Map<String, ?> map;
	private ListView list;
	private List<String> listMusicName;
	private Set set;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_native_music);
		
		init();
	}
	//初始化页面
    public void init() {
    	m_Util = new MusicUtil(NativeMusicActivity.this);
    	map = m_Util.getMusicMap();
    	//将map 转换为 list
    	set = map.keySet();	
    	list = (ListView) findViewById(R.id.nativeMusic);
    	listMusicName = new ArrayList<String>(set);
    	
    	Myadapter adapter = new Myadapter(listMusicName);
    	list.setAdapter(adapter);
    	
    	//为listView设置监听事件
    	list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String musicName = listMusicName.get(position);
				for(Object key : set) {
					if(key.equals(musicName)) {
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("musicName", key+"");
						bundle.putString("musicPath", map.get(key)+"");
						intent.putExtras(bundle);
						intent.setClass(NativeMusicActivity.this, PlayMusicActivity.class);
						startActivity(intent);
					}
				}
				
			}
		});
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		this.getMenuInflater().inflate(R.menu.actionbar_menu_native_music, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.native_music_return) {
			Intent intent = new Intent();
			intent.setClass(NativeMusicActivity.this, MusicActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * 自定义适配器
	 * @author fengchao
	 *
	 */
	class Myadapter extends BaseAdapter {
		private List<String> list;
		public Myadapter(List<String> list) {
			this.list = list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			if(convertView == null) {
				view = LayoutInflater.from(NativeMusicActivity.this).
                        inflate(R.layout.list_item_music, null);
			} else {
				view = convertView;
			}
			TextView m_Name = (TextView) view.findViewById(R.id.MusicName);
			m_Name.setText(list.get(position).split(".mp3")[0]);
			return view;
		}
		
	}
	
}
