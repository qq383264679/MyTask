package com.example.moodlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.moodlog.dbutil.DBdiarysmanager;
import com.example.moodlog.service.DiaryService;
import com.example.moodlog.util.ToastUtil;

public class DiarysActivity extends Activity implements OnItemClickListener{
	private ListView listView;
	private DiaryService diaryService;
	private Bundle bundle;
	private String username;
	private int d_id;
	private String[] from = { "title", "content", "date" };
	private int to[] = { R.id.title, R.id.content, R.id.time };
	private List<Map<String, ?>> listDate;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			bundle.putString("username", username);
			bundle.putInt("diary_id", msg.arg2);
			if((Boolean) msg.obj) {
				Intent intent = new Intent();
				intent.setClass(DiarysActivity.this, DiarysActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				DiarysActivity.this.finish();
			}
			if(msg.arg1 == 1) {
				Intent intent = new Intent();
				intent.setClass(DiarysActivity.this, UpdateDiaryActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				DiarysActivity.this.finish();
			}
			super.handleMessage(msg);
		}
		
	};
	// private String[]
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diarys);
		init();
	}

	public void init() {

		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username"); // 根据用户名 判断diarys中是否有日志
		listView = (ListView) findViewById(R.id.diarys);
		// 注册上下午
		this.registerForContextMenu(listView);
		diaryService = new DiaryService(this);
		d_id = diaryService.getDiaryID(username);
		int i = getCount();
		if (i != 0) {
			listDate = getResource();
			SimpleAdapter adapter = new SimpleAdapter(this, listDate,
					R.layout.activity_diarys_list, from, to);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		} else {
			int xml = R.layout.toast_customer;
			ViewGroup root = (ViewGroup) findViewById(R.id.toast_layout_root);
			ToastUtil util = new ToastUtil(this, xml, root); //
			util.showCustomerToast(android.R.drawable.ic_delete, "没记录日志");
		}

	}

	public List<Map<String, ?>> getResource() {
		// 根据username对应的id 获取 日志信息
		DBdiarysmanager manager = new DBdiarysmanager(this);
		return manager.getAllDiarys(String.valueOf(d_id));

	}

	public int getCount() {
		bundle = getIntent().getExtras();
		String username = bundle.getString("username"); // 根据用户名 判断diarys中是否有日志
		return diaryService.getCount(username);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu_diarys, menu);
		return true;
	}

	// Menu事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case R.id.btnAddDiary:
			intent = new Intent();
			intent.setClass(DiarysActivity.this, AddDiaryActivity.class);
			bundle.putString("username", username);
			bundle.putInt("d_id", d_id);
			intent.putExtras(bundle);
			startActivity(intent);
			this.finish();
			break;
		case R.id.di_return:
			intent = new Intent();
			intent.setClass(this, LognActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.exit:
			this.finish();
			break;
		case R.id.watch_weather:
			intent = new Intent();
			intent.setClass(this, WeatherInfo.class);
			startActivity(intent);
			break;
		case R.id.watch_music:
			intent = new Intent();
			intent.setClass(this, MusicActivity.class);
			bundle.putString("username", username);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 创建上下午菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menu.setHeaderTitle("请操作");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.diarys_set, menu);

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	//长按上下文菜单
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// 得到当前被选中的item信息
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Map<String, ?> map;
		Message msg;
		int diary_id;
		switch (item.getItemId()) {
		case R.id.update:
			map = listDate.get(menuInfo.position);
			diary_id = (Integer) map.get("diary_id");
			msg = Message.obtain();
			msg.arg1 = 1;
			msg.arg2 = diary_id;
			msg.obj = false;
			handler.sendMessage(msg);
			break;
		case R.id.delete:
			map = listDate.get(menuInfo.position);
			diary_id = (Integer) map.get("diary_id");
			DBdiarysmanager manager = new DBdiarysmanager(this);
			boolean b = manager.deleteDiarys(diary_id);
			if(b) {
				msg = Message.obtain();
				msg.obj = b;
				handler.sendMessage(msg);
				Toast.makeText(this, "删除成功", 1).show();
			} else {
				Toast.makeText(this, "删除失败", 1).show();
			}
			break;
		default:
			break;
		}

		
		
		return super.onContextItemSelected(item);
	}

	//listView事件监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//根据位置获取集合里面的数据
		Map<String, ?> map = listDate.get(position);
		int diary_id = (Integer) map.get("diary_id");
		//Toast.makeText(this, "" + map.get("title") + ":" + map.get("diary_id"), 1).show();
		Intent intent = new Intent();
		intent.setClass(DiarysActivity.this, WatchDiaryActivity.class);
		bundle.putString("username", username);
		bundle.putInt("diary_id", diary_id);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}

}
