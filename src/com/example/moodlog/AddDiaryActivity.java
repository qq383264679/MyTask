package com.example.moodlog;

import java.sql.Date;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.moodlog.dbutil.DBdiarysmanager;
import com.example.moodlog.domain.Diarys;
import com.example.moodlog.domain.Users;
import com.example.moodlog.service.DiaryService;
import com.example.moodlog.util.ToastUtil;

public class AddDiaryActivity extends Activity {
	private Bundle bundle;
	private String username;
	private int d_id;
	private EditText txtTitle;//文章标题
	private EditText txtContent; //内容
	private String title;
	private String content;
	private ProgressDialog progressDialog;  //进度条
	int xml;
	ViewGroup root;
	ToastUtil util;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diarys);
		
		bundle = getIntent().getExtras();
		username = (String) bundle.get("username");
		d_id = bundle.getInt("d_id");

	}
    public void init() {
		txtTitle = (EditText) findViewById(R.id.txtTitle);
		title = txtTitle.getText().toString().trim();
		txtContent = (EditText) findViewById(R.id.txtContent);
		content = txtContent.getText().toString().trim();

		xml = R.layout.toast_customer;
		root = (ViewGroup) findViewById(R.id.toast_layout_root);
		util = new ToastUtil(AddDiaryActivity.this, xml, root); //
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在添加中");
    }
    public boolean addContent(int d_id, String title, String content) {
    	//获取手机系统自带的时间
		Calendar cal=Calendar.getInstance();
		DBdiarysmanager manager = new DBdiarysmanager(this);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String time = year+"-" + month + "-" + day;
		
		Diarys d = new Diarys(d_id, title, content, Date.valueOf(time));
		return manager.insertDiarys(d);
    }
	// 添加子菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu_add, menu);
		return true;
	}

	// 菜单 事件
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.btnFinish:
			//Toast.makeText(this, "点击了完成", 1).show();
			init();
			if(title.isEmpty() || content.isEmpty()) {
				util.showCustomerToast(android.R.drawable.ic_delete, "标题和内容不能为空");
				break;
			} 
			if(content.length() <= 5) {
				util.showCustomerToast(android.R.drawable.ic_delete, "内容的字数必须大于5");
				break;
			}
			new MyTask().execute();
		
			break;
		case R.id.bt_return:
			Intent intent = new Intent();
			intent.setClass(this, DiarysActivity.class);
			bundle.putString("username", username);
			intent.putExtras(bundle);
			startActivity(intent);
			this.finish();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	/**
	 * 异步加载
	 * @author fengchao
	 *
	 */
	public class MyTask extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean b = addContent(d_id, title, content);
			return b;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result) {
				util.showCustomerToast(android.R.drawable.checkbox_on_background, "添加成功");	
				Intent intent = new Intent();
				intent.setClass(AddDiaryActivity.this, DiarysActivity.class);
				bundle.putString("username", username);
				intent.putExtras(bundle);
				startActivity(intent);
				AddDiaryActivity.this.finish();
			} else {
				util.showCustomerToast(android.R.drawable.ic_delete, "添加失败");
			}
		}

	
		
	}

}
