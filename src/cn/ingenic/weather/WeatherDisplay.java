package cn.ingenic.weather;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.Weather;

public class WeatherDisplay extends Activity implements OnClickListener {
	
	private final static int MSG_SHOW_WEATHER = 1;
	
	//views
	private TextView mTvDetails;
	private Button mBtSelectCity;
	
	private EngineManager mEngine;

	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_SHOW_WEATHER:
				City city = (City)msg.obj;
				displayWeather(city);
				break;
			}
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.weather_details);
	    
	    //prepared
	    mEngine = EngineManager.getInstance(this);
	    mTvDetails = (TextView)findViewById(R.id.test);
	    mBtSelectCity = (Button)findViewById(R.id.bt_select_city);
	    mBtSelectCity.setOnClickListener(this);
	    
	    //get weather
	    final City city = mEngine.getDefaultMarkCity();
    	Message msg = mHandler.obtainMessage(MSG_SHOW_WEATHER);
	    if(city.weather != null){
	    	msg.obj = city;
	    	msg.sendToTarget();
	    }else{
	    	mEngine.getWeatherByIndex(city.index, msg);
	    }
	}

	private void displayWeather(City city) {
		if (city == null || city.weather == null) {
			mTvDetails.setText("error!");
			Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
			return;
		}
		
		String degree = this.getString(R.string.sheshidu);
		
		mBtSelectCity.setText(city.name);

		Weather firstWeather = city.weather.get(0);
		Weather secondWeather = city.weather.get(1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM‘¬dd»’");
		
		//current weather
		TextView cityName = (TextView)findViewById(R.id.tv_city_name);
		cityName.setText(city.name);
		
		TextView currentTemp = (TextView)findViewById(R.id.current_temp);
		currentTemp.setText(firstWeather.currentTemp + degree);
		
		TextView currentWeather = (TextView)findViewById(R.id.current_weather);
		currentWeather.setText(WeatherUtils.getWeather(this, firstWeather.weather));

		TextView currentWind = (TextView)findViewById(R.id.current_wind);
		currentWind.setText(firstWeather.wind);
		
		//1st weather
		TextView fstDate = (TextView)findViewById(R.id.tv_prv_date);
		fstDate.setText(sdf.format(firstWeather.calendar.getTime()));
		
		TextView fstWeather = (TextView)findViewById(R.id.tv_prv_weather);
		fstWeather.setText(WeatherUtils.getWeather(this, firstWeather.weather));
		
		ImageView fstImg = (ImageView)findViewById(R.id.iv_prv_pic);
		fstImg.setImageResource(WeatherUtils.getHDrawable(firstWeather.weather));

		TextView fstMaxTemp = (TextView)findViewById(R.id.tv_prv_maxtemp);
		fstMaxTemp.setText(firstWeather.maxTemp + degree);

		TextView fstMinTemp = (TextView)findViewById(R.id.tv_prv_mintemp);
		fstMinTemp.setText(firstWeather.minTemp + degree);
		
		//2st weather
		TextView fstDate2 = (TextView)findViewById(R.id.tv_prv_date2);
		fstDate2.setText(sdf.format(secondWeather.calendar.getTime()));
		
		TextView fstWeather2 = (TextView)findViewById(R.id.tv_prv_weather2);
		fstWeather2.setText(WeatherUtils.getWeather(this, secondWeather.weather));
		
		ImageView fstImg2 = (ImageView)findViewById(R.id.iv_prv_pic2);
		fstImg2.setImageResource(WeatherUtils.getHDrawable(secondWeather.weather));

		TextView fstMaxTemp2 = (TextView)findViewById(R.id.tv_prv_maxtemp2);
		fstMaxTemp2.setText(secondWeather.maxTemp + degree);

		TextView fstMinTemp2 = (TextView)findViewById(R.id.tv_prv_mintemp2);
		fstMinTemp2.setText(secondWeather.minTemp + degree);
		
		//for test start
		StringBuilder sb = new StringBuilder();
		sb.append(city.toString() + "\n");
		sb.append("\n");
		for (Weather weather : city.weather) {
			sb.append(weather + "  "+ WeatherUtils.getWeather(this, weather.weather) +"\n");
			sb.append("\n");
		}

		mTvDetails.setText(sb);
		//for test end
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.bt_select_city:
			this.startActivity(new Intent(this, CitySelectActivity.class));
			finish();
			break;
		}
	}
}
