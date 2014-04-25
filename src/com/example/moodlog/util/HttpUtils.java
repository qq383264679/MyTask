package com.example.moodlog.util;



import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class HttpUtils {
	private Context context;
	public HttpUtils(Context context) {
		this.context = context;
	}
	/**
	 * 
	 * @param path  url  http://localhost:8080/PersonIntroduce/servlet/Logn?username=xxx&password=123
	 * @param encoding 编码方式
	 * @return
	 */
	public  String sendPostMethod(String path, String encoding, String city) {
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(path);
			
		    List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
	        params.add(new BasicNameValuePair("theCityName", city));  //设置参数1
	        post.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中  	     
	        HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), encoding);
			}
		} catch (Exception e) { 
			// TODO: handle exception
			((Activity) context).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(context, "网络连接异常。。。", 1).show();
				}
				
			});
			Log.i("TAG", "网络连接异常。。。");
			e.printStackTrace();
			
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}
}
