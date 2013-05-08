package cn.kli.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.kli.utils.CalendarUtils;
import cn.kli.weather.engine.Weather;

public class WeatherPreviewView extends LinearLayout {
	private Context mContext;
	private View mViewRoot;
	public WeatherPreviewView(Context context) {
		super(context);
		mContext = context;
		LayoutInflater inflator = LayoutInflater.from(mContext);
		mViewRoot = inflator.inflate(R.layout.weather_preview, this);
		initView();
	}
	
	private void initView(){
		LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
		params.weight = 1;
		this.setLayoutParams(params);
		
		this.setBackgroundResource(android.R.drawable.dialog_frame);
		this.setAlpha(Config.getWeatherDisplayAlpha());
	}

	public void setWeather(Weather weather){
		TextView tvWeek = (TextView)findViewById(R.id.tv_prv_week);
		TextView tvDate = (TextView)findViewById(R.id.tv_prv_date);
		TextView tvWeather = (TextView)findViewById(R.id.tv_prv_weather);
		TextView tvMaxTemp = (TextView)findViewById(R.id.tv_prv_maxtemp);
		TextView tvMinTemp = (TextView)findViewById(R.id.tv_prv_mintemp);
		ImageView ivIcon = (ImageView)findViewById(R.id.iv_prv_pic);

		String degree = mContext.getString(R.string.sheshidu);
		SimpleDateFormat sdf = new SimpleDateFormat(mContext.getString(R.string.date_format));
		
		String day = null;
		if(CalendarUtils.isSameDay(weather.calendar, Calendar.getInstance())){
			day = mContext.getString(R.string.today);
		}else{
			int i = weather.calendar.get(Calendar.DAY_OF_WEEK);
			String[] days = mContext.getResources().getStringArray(R.array.weekday);
			day = days[i-1];
		}
		tvWeek.setText(day);
		
		tvDate.setText(sdf.format(weather.calendar.getTimeInMillis()));
		
		tvWeather.setText(WeatherUtils.getWeather(mContext, weather.weather));
		tvMaxTemp.setText(weather.maxTemp + degree);
		tvMinTemp.setText(weather.minTemp + degree);
		ivIcon.setImageResource(WeatherUtils.getHDrawable(weather.weather));
	}
}
