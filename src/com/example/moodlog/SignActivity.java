package com.example.moodlog;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.moodlog.dbutil.DBUsermanager;
import com.example.moodlog.domain.Users;
import com.example.moodlog.util.SharedPreferencesUtil;
import com.example.moodlog.util.ToastUtil;



public class SignActivity extends Activity implements OnClickListener{
	private Button sign;
	private Button btnUpdate;
	private ProgressDialog progressDialog;  //������
	private	EditText username;
	private EditText password;
	private EditText re_password;
	private DBUsermanager db;

	private AlertDialog alertDialog; //�Զ���Ի���
	AlertDialog.Builder builder;
	
	private EditText up_username;
	private EditText up_password;
	private EditText up_re_password;
//	ActionBar�¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.return_logn:
			Intent intent = new Intent();
			intent.setClass(SignActivity.this, LognActivity.class);
			startActivity(intent);
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int xml = R.layout.toast_customer;
			ViewGroup root = (ViewGroup) findViewById(R.id.toast_layout_root);
			ToastUtil toastUtil = new ToastUtil(SignActivity.this, xml, root); //
			if((Boolean) msg.obj) {
				SharedPreferencesUtil util = new SharedPreferencesUtil(SignActivity.this);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", username.getText().toString().trim());
				map.put("is_ture", false);
				util.saveMessage("logn", map);
				
				//�Զ�����˾
				toastUtil.showCustomerToast(android.R.drawable.checkbox_on_background, "ע��ɹ�");
				
				
				Intent intent = new Intent();
				intent.setClass(SignActivity.this, LognActivity.class);
				startActivity(intent);
				SignActivity.this.finish();
				
			} else {
				toastUtil.showCustomerToast(android.R.drawable.ic_delete, "ע��ʧ��");
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		
		init();
		
	}
	public void init() {
		sign = (Button) findViewById(R.id.btnAdd);
		sign.setOnClickListener(this);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this);
		
		username = (EditText) findViewById(R.id.txtRegAccount);
		password = (EditText) findViewById(R.id.txtRegPassword);
		re_password = (EditText) findViewById(R.id.txtReRegPassword);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("���Ե�");
	    progressDialog.setMessage("����ע��");
	    db = new DBUsermanager(this);
	    
	    //�Զ���Ի���
	    //�޸����봰��
		builder = new Builder(SignActivity.this);
		View view = LayoutInflater.from(SignActivity.this).
				inflate(R.layout.activity_update_users, null);
		builder.setView(view);
		up_username = (EditText) view.findViewById(R.id.update_username);
		up_password = (EditText) view.findViewById(R.id.update_password);
		up_re_password = (EditText) view.findViewById(R.id.update_RePassword);
		confirm();
		alertDialog = builder.create();
		alertDialog.setTitle("����������Ϣ");
		//���öԻ�����������ܲ���ʧ
		alertDialog.setCanceledOnTouchOutside(false);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnAdd:			
			boolean b = check(username.getText().toString().trim(), password.getText().toString().trim(), re_password.getText().toString().trim());
			if(b) {
				Users user = new Users();
				user.setUsername(username.getText().toString().trim());
				user.setPassword(password.getText().toString().trim());
				
				//��û�����ж�
				new MyTask().execute(user);
			} 
			break;
		case R.id.btnUpdate:
			
			alertDialog.show();
			//���ݹ���
			SharedPreferencesUtil util = new SharedPreferencesUtil(SignActivity.this);
			Map<String, ?> map = util.getAllMessage("logn");
			String username1 = (String) map.get("username");
			up_username.setText(username1);
			break;
		default:
			break;
		}
	}
	
	/**
	 * �첽ע��
	 * @author fengchao
	 *
	 */
	public class MyTask extends AsyncTask<Users, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Users... params) {
			// TODO Auto-generated method stub
			Users user = params[0];
			boolean b = db.insertUsers(user);
		
			return b;
		}

		//����������
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
			
				//���û������ݵ���½����
				Message msg = Message.obtain();
				msg.obj = result;
				handler.sendMessage(msg);
			} else {
				Log.i("TAG", "ע��ʧ�ܡ�������");
			}
			progressDialog.dismiss();
		}
		
	}
	@SuppressLint("NewApi")
	public boolean check(String u, String p, String re_p) {
		int xml = R.layout.toast_customer;
		ViewGroup root = (ViewGroup) findViewById(R.id.toast_layout_root);
		ToastUtil util = new ToastUtil(this, xml, root); //
		if(!p.equals(re_p)) {
			util.showCustomerToast(android.R.drawable.ic_delete, "���벻һ��");
			return false;
		}
		if(u.isEmpty() || p.isEmpty()) {
			util.showCustomerToast(android.R.drawable.ic_delete, "�˺Ż������벻��Ϊ��");
			return false;
		} 
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_menu_sign, menu);
		return true;
	}
	//�Ƿ��޸Ļ���ȡ��
	public void confirm() {
		builder.setPositiveButton("�޸�", new DialogInterface.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//http://10.84.197.246:8080/PersonIntroduce/servlet/Logn?username=xxx&password=123
				String username = up_username.getText().toString().trim();
				String password = up_password.getText().toString().trim();
				String re_password = up_re_password.getText().toString().trim();
				Log.i("TAG", password + "::" + re_password);
				if(!password.equals(re_password)) {
					Toast.makeText(SignActivity.this, "���벻һ��", 1).show();
					return;
				}
				if(password.isEmpty()) {
					Toast.makeText(SignActivity.this, "���벻��Ϊ��", 1).show();
					return;
				}
				Users user = new Users(username, password);
				DBUsermanager util = new DBUsermanager(SignActivity.this);
				util.updateUsers(user);
				Toast.makeText(SignActivity.this, "�޸ĳɹ�", 1).show();
			}
			
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
			
		});
		
	}
}
