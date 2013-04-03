package cn.ingenic.weather;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.WeatherEngine;

public class EngineManager {
	//shared prefs
	private final static String ENGINE_MANAGER = "engine_manager";
	private final static String KEY_ENGINE_SOURCE = "engine_source";
	private final static String KEY_DEFAULT_CITY = "default_city";
	
	private static EngineManager sInstance;
	private Context mContext;
	private WeatherEngine mEngine;
	private CacheManager mCache;
	
	private EngineManager(Context context){
		mContext = context;
		mCache = new CacheManager(mContext);
		mEngine = WeatherEngine.getInstance(mContext);
	}
	
	public static EngineManager getInstance(Context context){
		if(sInstance == null){
			sInstance = new EngineManager(context);
		}
		return sInstance;
	}
	
	public void init(final Message callback){
		AsyncTask.execute(new Runnable(){
			@Override
			public void run() {
				mEngine.init(getEngineSource());
				callback.sendToTarget();
			}
		});
	}
	
	public void changeEngineSource(final String source, final Message callback){
		AsyncTask.execute(new Runnable(){

			@Override
			public void run() {
				callback.arg1 = mEngine.init(source);
				setEngineSource(source);
				callback.sendToTarget();
			}
		});
	}
	
	public String[] getSourceList(){
		return mEngine.getSourceList();
	}
	
	/**
	 * Must be called in thread.
	 * Get city weather from internet.
	 */
	public void getWeatherByIndex(final String index, final Message callback){
		AsyncTask.execute(new Runnable(){
			@Override
			public void run() {
				City city = mEngine.getWeatherByIndex(index);
				if(city != null && city.weather != null){
					//cache weather
					mCache.cacheWeather(city);
				}
				callback.obj = city;
				callback.sendToTarget();
			}
		});
	}
	
	public List<City> getCityList(City city){
		return mEngine.getCityList(city);
	}

	public void setDefaultMarkCity(City city){
		mCache.markDefaultCity(city, getEngineSource());
//		SharedPreferences prefs = mContext.getSharedPreferences(ENGINE_MANAGER, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = prefs.edit();
//		editor.putString(KEY_DEFAULT_CITY, city.index);
//		editor.commit();
	}
	
	public boolean hasDefaultCity(){
		return mCache.hasDefaultCity();
	}
	
	public City getDefaultMarkCity(){
//		SharedPreferences prefs = mContext.getSharedPreferences(ENGINE_MANAGER, Context.MODE_PRIVATE);
//		return prefs.getString(KEY_DEFAULT_CITY, null);
		
		try {
			return mCache.getDefaultCity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	private void setEngineSource(String source){
		SharedPreferences prefs = mContext.getSharedPreferences(ENGINE_MANAGER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_ENGINE_SOURCE, source);
		editor.commit();
	}
	
	private String getEngineSource(){
		SharedPreferences prefs = mContext.getSharedPreferences(ENGINE_MANAGER, Context.MODE_PRIVATE);
		return prefs.getString(KEY_ENGINE_SOURCE, null);
	}
}
