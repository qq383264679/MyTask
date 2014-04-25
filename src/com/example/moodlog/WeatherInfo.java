package com.example.moodlog;



import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.moodlog.domain.Weather;
import com.example.moodlog.util.HttpUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherInfo extends Activity {
	private EditText search_city;
	private String city;
	String path = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName";
	String encoding = "utf-8";
	private ProgressDialog dialog; //增加进度条
	private Weather weather;
	//天气信息
	private String weatherXml;
	private TextView cityName;
	private TextView date; //明天时间
	private ImageView imageView1;//明天天气图片
	private TextView temparture;  //今天
	private TextView air_quality;
	private TextView ultraviolet;
	private TextView weather11; //明天天气情况
	private TextView wind_direction; //风向
	private TextView humidity; //湿度
	private TextView update_time; //
	
	private TextView tomorrow; //
	private TextView tomorrow_one; //
	private TextView tomorrow_two; 

	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	
	private ImageView imageView2;//明天天气图片
	private ImageView imageView3;//后天天气图片
	private ImageView imageView4;//在后天天气图片
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		
		
	}
	public void init() {
		search_city = (EditText) findViewById(R.id.search_city);
		city = search_city.getText().toString().trim();
		dialog = new ProgressDialog(this);
		dialog.setMessage("请稍后。。。");
		
		//城市信息初始化
		cityName = (TextView) findViewById(R.id.city);
		date = (TextView) findViewById(R.id.date);
		imageView1 = (ImageView) findViewById(R.id.imageView1); //明天图片
		temparture = (TextView) findViewById(R.id.temparture);
		air_quality = (TextView) findViewById(R.id.air_quality);
		ultraviolet = (TextView) findViewById(R.id.ultraviolet);
		weather11 = (TextView) findViewById(R.id.weather);
		wind_direction = (TextView) findViewById(R.id.wind_direction);
		humidity = (TextView) findViewById(R.id.humidity);
		update_time = (TextView) findViewById(R.id.update_time);
		
		tomorrow = (TextView) findViewById(R.id.tomorrow);
		tomorrow_one = (TextView) findViewById(R.id.tomorrow_one);
		tomorrow_two = (TextView) findViewById(R.id.tomorrow_two);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.weather_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId() == R.id.weather_confirm) {
			//点击了确定按钮
			init();
			new myTask().execute(city);
			
		}
		if(item.getItemId() == R.id.weather_return) {
//			点击了返回
			Intent intent = new Intent();
			intent.setClass(this, DiarysActivity.class);
			startActivity(intent);
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
	public String[] getWeatherInfo(String xml) throws XmlPullParserException, IOException {
		String[] message = new String[25];
		String info = "";
		
		//解析xml 字符串  
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
        factory.setNamespaceAware(true);  
        XmlPullParser parse = factory.newPullParser();  
 
        parse.setInput(new StringReader(xml));  
        int eventType = parse.getEventType(); 
        int i = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {  
	         if(eventType == XmlPullParser.TEXT) { 
	        	 if(!parse.getText().toString().trim().equals("")) {
	        		 i++;
	        		 message[i] = parse.getText();
	        	 }
	         }  
	         eventType = parse.next();  
	    }  
       /* for(int j = 1; j < message.length - 1; j++) {
        	//测试
        	Log.i("TAG", j + ":" + message[j]);
        	info += message[j] + "\n";
        }*/
        return message;
	}
	public Weather getWeather(String weatherInfo[]) {
		Weather info = new Weather();
		//明天 tomorrow_time	tomorrow_time_view tomorrow_time_tem
		info.setTomorrow_time(weatherInfo[14].split(" ")[0]);  //明天时间
		info.setTomorrow_time_view(weatherInfo[16]);
		info.setTomorrow_time_tem(weatherInfo[13]);
		//后天tomorrow_one tomorrow_one_view tomorrow_one_tem
		info.setTomorrow_one(weatherInfo[19].split(" ")[0]); //后天时间
		info.setTomorrow_one_view(weatherInfo[21]); //
		info.setTomorrow_one_tem(weatherInfo[18]); //

		//今天参数date	pic_new temparture air_quality  ultraviolet  weather  humidity   wind_direction
		//info.setDate(date);
		info.setPic_new(weatherInfo[9]);

		//String s = "今日天气实况：气温：8℃；风向/风力：南风 1级；湿度：87%；空气质量：暂无；紫外线强度：弱";
		String[] test = weatherInfo[11].split("：");
		String tem = test[2].split("；")[0];
		String wind_deriction = test[3].split("；")[0];
		String shidu = test[4].split("；")[0];
		String air_quality = test[5].split("；")[0];
		String ziwaixianqiangdu = test[6];

		/*	Log.i("TAG", tem + "-->");
					Log.i("TAG", wind_deriction + "-->");
					Log.i("TAG", shidu + "-->");
					Log.i("TAG", air_quality + "-->");
					Log.i("TAG", ziwaixianqiangdu + "-->");*/		
		info.setTemparture(tem);
		info.setAir_quality(air_quality);
		info.setUltraviolet(ziwaixianqiangdu);
		info.setHumidity(shidu);
		info.setWind_direction(wind_deriction);

		info.setCity(weatherInfo[2]);
		String timetest = weatherInfo[7];
		info.setDate(timetest.split(" ")[0]);
		info.setWeather(timetest.split(" ")[1]);

		info.setToday_tem(weatherInfo[6]);
		info.setUpdate_time(weatherInfo[5]);
		//Log.i("TAG", timetest.split(" ")[0] + "-->" + timetest.split(" ")[1]);
		//Log.i("TAG", info.getTomorrow_one() + ":::" + info.getTomorrow_one_tem());
		return info;
		
	}
	//
	class myTask extends AsyncTask<String, Integer, Boolean> {

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
				Toast.makeText(WeatherInfo.this, "跟新成功", 1).show();
				//天气信息跟新UI
				cityName.setText(weather.getCity());
				date.setText(weather.getDate());
				imageView1.setBackgroundResource(getPicture(weather.getPic_new()));
				temparture.setText(weather.getTemparture() + "(今天)");
				air_quality.setText("空气质量:" + weather.getAir_quality() + "(今天)");
				ultraviolet.setText("紫外线强度:" + weather.getUltraviolet() + "(今天)");
				weather11.setText(weather.getWeather());
				wind_direction.setText(weather.getWind_direction() + "(今天)");
				humidity.setText("湿度:" + weather.getHumidity() + "(今天)");
				update_time.setText(weather.getUpdate_time());
				
				tomorrow.setText(weather.getDate()); 
				tomorrow_one.setText(weather.getTomorrow_time());   
				tomorrow_two.setText(weather.getTomorrow_one());    
				
				textView1.setText(weather.getToday_tem());;
				textView2.setText(weather.getTomorrow_time_tem());;
				textView3.setText(weather.getTomorrow_one_tem());;
				
				imageView2.setBackgroundResource(getPicture(weather.getPic_new()));
				imageView3.setBackgroundResource(getPicture(weather.getTomorrow_time_view()));
				imageView4.setBackgroundResource(getPicture(weather.getTomorrow_one_view()));
			} else {
				dialog.dismiss();
			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String city = params[0];
			HttpUtils utils = new HttpUtils(WeatherInfo.this);
			boolean b = false;
			try {
				weatherXml = utils.sendPostMethod(path, encoding, city);
			
				String[] weatherInfo = getWeatherInfo(weatherXml);
				weather =  getWeather(weatherInfo);
				
				b = true;
				//Log.i("TAG",weather.getCity() + "-->" );
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return b;
		}
		
	}
	public int getPicture(String pic_name) {
		if(pic_name.equals("0.gif")) {
			return R.drawable.a_0;
		}
		if(pic_name.equals("1.gif")) {
			return R.drawable.a_1;
		}
		if(pic_name.equals("2.gif")) {
			return R.drawable.a_2;
		}
		if(pic_name.equals("3.gif")) {
			return R.drawable.a_3;
		}
		if(pic_name.equals("4.gif")) {
			return R.drawable.a_4;
		}
		if(pic_name.equals("5.gif")) {
			return R.drawable.a_5;
		}
		if(pic_name.equals("6.gif")) {
			return R.drawable.a_6;
		}
		if(pic_name.equals("7.gif")) {
			return R.drawable.a_7;
		}
		if(pic_name.equals("8.gif")) {
			return R.drawable.a_8;
		}
		if(pic_name.equals("9.gif")) {
			return R.drawable.a_9;
		}
		
		if(pic_name.equals("10.gif")) {
			return R.drawable.a_10;
		}
		if(pic_name.equals("11.gif")) {
			return R.drawable.a_11;
		}
		if(pic_name.equals("12.gif")) {
			return R.drawable.a_12;
		}
		if(pic_name.equals("13.gif")) {
			return R.drawable.a_13;
		}
		if(pic_name.equals("14.gif")) {
			return R.drawable.a_14;
		}
		if(pic_name.equals("15.gif")) {
			return R.drawable.a_15;
		}
		if(pic_name.equals("16.gif")) {
			return R.drawable.a_16;
		}
		if(pic_name.equals("17.gif")) {
			return R.drawable.a_17;
		}
		if(pic_name.equals("18.gif")) {
			return R.drawable.a_18;
		}
		if(pic_name.equals("19.gif")) {
			return R.drawable.a_19;
		}
		if(pic_name.equals("20.gif")) {
			return R.drawable.a_20;
		}
		if(pic_name.equals("21.gif")) {
			return R.drawable.a_21;
		}
		if(pic_name.equals("22.gif")) {
			return R.drawable.a_22;
		}
		if(pic_name.equals("23.gif")) {
			return R.drawable.a_23;
		}
		if(pic_name.equals("24.gif")) {
			return R.drawable.a_24;
		}
		if(pic_name.equals("25.gif")) {
			return R.drawable.a_25;
		}
		if(pic_name.equals("26.gif")) {
			return R.drawable.a_26;
		}
		if(pic_name.equals("27.gif")) {
			return R.drawable.a_27;
		}
		if(pic_name.equals("28.gif")) {
			return R.drawable.a_28;
		}
		if(pic_name.equals("29.gif")) {
			return R.drawable.a_29;
		}
		if(pic_name.equals("30.gif")) {
			return R.drawable.a_30;
		}
		if(pic_name.equals("31.gif")) {
			return R.drawable.a_31;
		}
		return -1;
	}

}
