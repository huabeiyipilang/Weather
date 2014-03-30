package cn.kli.weather.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import cn.kli.weather.R;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.Weather;
import cn.kli.weather.engine.WeatherEngine;
import cn.kli.weather.engine.WeatherUtils;

public class WidgetManager {
    public final static String ACTION_UPDATE_TIME = "cn.kli.weatherwidget.update_time";
    public final static String ACTION_UPDATE_SKIN = "cn.kli.weatherwidget.update_skin";
    public final static String ACTION_FRESH_WIDGET_TIME = "cn.indroid.action.weather.freshwidgettime";
    public final static String ACTION_FRESH_WIDGET = "cn.indroid.action.weather.freshwidget";
    
    private static WidgetManager sInstance;
    
    private Context mContext;
    private WeatherEngine mEngine;
    
    private WidgetManager(Context context){
        mContext = context;
        mEngine = WeatherEngine.getInstance(context);
    }
    
    public static WidgetManager getInstance(Context context){
        if(sInstance == null){
            sInstance = new WidgetManager(context);
        }
        return sInstance;
    }
    
    public void updateWidget(){
        List<City> cities = mEngine.getMarkCity();
        if(cities != null && cities.size() > 0){
            updateWidget(cities.get(0));
        }
    }
    
    private void updateWidget(City city){
        Intent intent = new Intent(ACTION_FRESH_WIDGET);
        if(city != null && city.weathers != null){
            String sheshidu = mContext.getString(R.string.sheshidu);
            Weather weather = city.weathers.get(0);
            intent.putExtra("city", city.name);
            intent.putExtra("weather", WeatherUtils.getWeather(mContext, weather.weather));
            intent.putExtra("weather", weather.getWeatherName(mContext));
            intent.putExtra("current_temp", weather.currentTemp+sheshidu);
            intent.putExtra("max_temp", weather.maxTemp+sheshidu);
            intent.putExtra("min_temp", weather.minTemp+sheshidu);
            intent.putExtra("icon", weather.weather[0]);
        }else{
//          intent.putExtra("city", mContext.getString(R.string.unknown));
//          intent.putExtra("weather", mContext.getString(R.string.unknown));
//          intent.putExtra("current_temp", mContext.getString(R.string.unknown));
//          intent.putExtra("max_temp", mContext.getString(R.string.unknown));
//          intent.putExtra("min_temp", mContext.getString(R.string.unknown));
//          intent.putExtra("icon", R.drawable.l_nothing);
        }
        mContext.sendBroadcast(intent);
    }
}
