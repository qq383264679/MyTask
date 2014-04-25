package com.example.moodlog.domain;

public class Weather {
	private String city;
	private String date; //明天时间
	private String pic_new; //明天当前的天气图片
	private String temparture; //temparture
	private String air_quality; 
	private String ultraviolet; //紫外线
	private String weather; //明天天气情况  
	private String humidity;  //湿度
	private String wind_direction;
//date	pic_new temparture air_quality  ultraviolet  weather  humidity   wind_direction
	
	private String tomorrow_time; //后天
	private String tomorrow_one; //在后天时间
	private String update_time; //天气跟新的时间
	

	private String tomorrow_time_view;
	private String tomorrow_one_view;
	
	private String today_tem;
	private String tomorrow_time_tem;
	private String tomorrow_one_tem;
//后天
	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPic_new() {
		return pic_new;
	}
	public void setPic_new(String pic_new) {
		this.pic_new = pic_new;
	}
	public String getTemparture() {
		return temparture;
	}
	public void setTemparture(String temparture) {
		this.temparture = temparture;
	}
	public String getAir_quality() {
		return air_quality;
	}
	public void setAir_quality(String air_quality) {
		this.air_quality = air_quality;
	}
	public String getUltraviolet() {
		return ultraviolet;
	}
	public void setUltraviolet(String ultraviolet) {
		this.ultraviolet = ultraviolet;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}

	public String getTomorrow_time() {
		return tomorrow_time;
	}
	public void setTomorrow_time(String tomorrow_time) {
		this.tomorrow_time = tomorrow_time;
	}
	public String getTomorrow_one() {
		return tomorrow_one;
	}
	public void setTomorrow_one(String tomorrow_one) {
		this.tomorrow_one = tomorrow_one;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getTomorrow_time_view() {
		return tomorrow_time_view;
	}
	public void setTomorrow_time_view(String tomorrow_time_view) {
		this.tomorrow_time_view = tomorrow_time_view;
	}
	public String getTomorrow_one_view() {
		return tomorrow_one_view;
	}
	public void setTomorrow_one_view(String tomorrow_one_view) {
		this.tomorrow_one_view = tomorrow_one_view;
	}
	public String getToday_tem() {
		return today_tem;
	}
	public void setToday_tem(String today_tem) {
		this.today_tem = today_tem;
	}
	public String getTomorrow_time_tem() {
		return tomorrow_time_tem;
	}
	public void setTomorrow_time_tem(String tomorrow_time_tem) {
		this.tomorrow_time_tem = tomorrow_time_tem;
	}
	public String getTomorrow_one_tem() {
		return tomorrow_one_tem;
	}
	public void setTomorrow_one_tem(String tomorrow_one_tem) {
		this.tomorrow_one_tem = tomorrow_one_tem;
	}
	
	
	
	

}
//
