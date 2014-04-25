package com.example.moodlog.util;




import com.example.moodlog.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ToastUtil {
	private Context context;
	private int xml;
	private ViewGroup root;
	public ToastUtil(Context context, int xml, ViewGroup root) {
		this.context = context;
		this.xml = xml;
		this.root = root;
	}
	/**
	 * 自定义土司
	 * @param icon
	 * @param message
	 */
	public void showCustomerToast(final int icon, String message) {
		LayoutInflater inflater = LayoutInflater.from(context);  //获取Layoutflater		
		View layout = inflater.inflate(xml, root);
		
		ImageView toastIcon = (ImageView) layout.findViewById(R.id.toastIcon);
		toastIcon.setImageResource(icon);
		TextView toastMessage = (TextView) layout.findViewById(R.id.toastMessage);
		toastMessage.setText(message);

		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);

		toast.show();
	}
}
