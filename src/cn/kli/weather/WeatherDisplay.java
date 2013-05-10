package cn.kli.weather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.kli.weather.EngineManager.DataChangedListener;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.Weather;

public class WeatherDisplay extends Activity implements OnClickListener,
 			DataChangedListener{
	
	private final static int MSG_SHOW_WEATHER = 1;
	private final static int MSG_FRESH_ANIM_START = 2;
	private final static int MSG_FRESH_ANIM_STOP = 3;
	
	private EngineManager mEngine;
	private ImageButton mIbFresh;
	private ProgressBar mPbFreshing;

	private Animation mFreshAnim;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_SHOW_WEATHER:
				City city = (City)msg.obj;
				displayWeather(city);
				break;
			case MSG_FRESH_ANIM_START:
				mIbFresh.setVisibility(View.GONE);
				mPbFreshing.setVisibility(View.VISIBLE);
				break;
			case MSG_FRESH_ANIM_STOP:
				mIbFresh.setVisibility(View.VISIBLE);
				mPbFreshing.setVisibility(View.GONE);
				break;
			}
		}
		
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.weather_details);

	    mFreshAnim = AnimationUtils.loadAnimation(this, R.anim.fresh_anim);
	    mFreshAnim.setRepeatCount(Animation.INFINITE);
	    mPbFreshing = (ProgressBar)findViewById(R.id.pb_freshing);

	    mIbFresh = (ImageButton)findViewById(R.id.ib_fresh);
	    mIbFresh.setOnClickListener(this);
	    
	    //prepared
	    mEngine = EngineManager.getInstance(this);
	    mEngine.register(this);
	    
		findViewById(R.id.ib_settings).setOnClickListener(this);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
	    //get weather
	    final City city = mEngine.getDefaultMarkCity();
	    if(city.weather != null){
	    	Message msg = mHandler.obtainMessage(MSG_SHOW_WEATHER);
	    	msg.obj = city;
	    	msg.sendToTarget();
	    }else if(!mEngine.isRequesting()){
	    	mEngine.getWeatherByIndex(city.index);
	    }
	    if(getIntent().getBooleanExtra("notification", false)){
		    mEngine.clearWeahterNotif();
	    }
	}



	private void displayWeather(City city) {
		if (city == null || city.weather == null) {
			Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
			return;
		}
		
		String degree = this.getString(R.string.sheshidu);
		
		Weather firstWeather = city.weather.get(0);
		
		//current weather
		TextView cityName = (TextView)findViewById(R.id.tv_city_name);
		cityName.setText(city.name);
		
		TextView currentTemp = (TextView)findViewById(R.id.current_temp);
		currentTemp.setText(firstWeather.currentTemp + degree);
		currentTemp.setShadowLayer(2F, 2F,1F, Color.BLACK);
		
		TextView currentWeather = (TextView)findViewById(R.id.current_weather);
		currentWeather.setText(firstWeather.getWeatherName(this));
		currentWeather.setShadowLayer(2F, 2F,1F, Color.BLACK);

		TextView currentWind = (TextView)findViewById(R.id.current_wind);
		currentWind.setText(firstWeather.wind);
		currentWind.setShadowLayer(2F, 2F,1F, Color.BLACK);
		
		LinearLayout prvContainer = (LinearLayout)findViewById(R.id.ll_preview_container);
		prvContainer.removeAllViews();
		for(int i = 0; i < Config.getPrevCount(); i++){
			WeatherPreviewView preview = new WeatherPreviewView(this);
			preview.setWeather(city.weather.get(i));
			prvContainer.addView(preview);
		}
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ib_fresh:
		    City city = mEngine.getDefaultMarkCity();
	    	mEngine.getWeatherByIndex(city.index);
			break;
		case R.id.ib_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	private void startFreshAnim(){
		mHandler.sendEmptyMessage(MSG_FRESH_ANIM_START);
	}
	
	private void stopFreshAnim(){
		mHandler.sendEmptyMessage(MSG_FRESH_ANIM_STOP);
	}

	@Override
	public void onWeatherChanged(City city) {
    	Message msg = mHandler.obtainMessage(MSG_SHOW_WEATHER);
    	msg.obj = city;
    	msg.sendToTarget();
	}

	@Override
	public void onStateChanged(boolean isRequesting) {
		if(isRequesting){
			startFreshAnim();
		}else{
			stopFreshAnim();
		}
	}
}
