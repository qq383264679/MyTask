package com.example.moodlog;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moodlog.domain.Users;
import com.example.moodlog.service.UsersLogn;
import com.example.moodlog.util.SharedPreferencesUtil;
import com.example.moodlog.util.ToastUtil;

public class LognActivity extends Activity {
	private EditText username;
	private EditText password;
	private CheckBox u_p;
	private Button logn;
	private Button sign;
	SharedPreferencesUtil preferenceUtil;
	Map<String, ?> map;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String username1 = (String) map.get("username");
			if(msg.obj.equals("ok")) {
				String password1 = (String) map.get("password");
				username.setText(username1);
				password.setText(password1);
			} else {
				username.setText(username1);
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ��title
		setContentView(R.layout.activity_logn);
		
		init();
		setNum();		
	}
	public void setNum() {
		map = preferenceUtil.getAllMessage("logn");
		if(map !=null && !map.isEmpty()) {
			boolean b = (Boolean) map.get("is_ture");
			Message msg = Message.obtain();
			if(b) {
				msg.obj = "ok";
				
				handler.sendMessage(msg);
				
			} else {
				msg.obj = "not_ok";
				handler.sendMessage(msg);
			}
		}
	}
	public void init() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		u_p = (CheckBox) findViewById(R.id.save_u_p);
		
		logn = (Button) findViewById(R.id.logn);
		sign = (Button) findViewById(R.id.sign);
		preferenceUtil = new SharedPreferencesUtil(this);
		
		
		
		
		//��½��ť ��������û�  
		logn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String u = username.getText().toString().trim();//�˺�
				String p = password.getText().toString().trim();//����
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", u);
				map.put("password", p);
				map.put("is_ture", u_p.isChecked());
				boolean b = preferenceUtil.saveMessage("logn", map);
				if(b) {
					Users user = new Users(u, p);
					UsersLogn logn = new UsersLogn(LognActivity.this);
					boolean sign = logn.checkUser(user);  //b�ɹ��͵�½
					if(sign) {
						Intent intent = new Intent();
						intent.setClass(LognActivity.this, DiarysActivity.class);
						//��User��userid��Ϣ���ݹ�ȥ  
						Bundle bundle = new Bundle();
						bundle.putString("username", u);
						intent.putExtras(bundle);
						startActivity(intent);
						LognActivity.this.finish();
					} else {		
						int xml = R.layout.toast_customer;
						ViewGroup root = (ViewGroup) findViewById(R.id.toast_layout_root);
						ToastUtil util = new ToastUtil(LognActivity.this, xml, root); //
						util.showCustomerToast(android.R.drawable.ic_delete, "�˺���������");
					}
				}
			}
		});
		//ע�ᰴť �����ת��ע�����
		sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LognActivity.this, SignActivity.class);
				startActivity(intent);
				LognActivity.this.finish();
			}
		});
	}

}
