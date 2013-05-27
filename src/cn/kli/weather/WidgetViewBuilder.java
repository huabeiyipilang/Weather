package cn.kli.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class WidgetViewBuilder {
	private final static boolean HOUR_24 = false;
	
	private final static int[] numRes = {R.drawable.n0, R.drawable.n1, R.drawable.n2, 
			R.drawable.n3, R.drawable.n4, R.drawable.n5, R.drawable.n6, 
			R.drawable.n7, R.drawable.n8, R.drawable.n9, 
			R.drawable.am, R.drawable.pm};
	
	private Context mContext;
	private RemoteViews mRv;
	private Bundle mWeatherData;
	private boolean mUpdateTime;
	private Skin mSkin;
	
	WidgetViewBuilder(Context context, Skin skin){
		mContext = context;
		mSkin = skin;
	}
	
	public void updateTime(boolean update){
		mUpdateTime = update;
	}
	
	public void updateWeather(Bundle data){
		mWeatherData = data;
	}
	
	public RemoteViews build(){
		if(mSkin == null){
			buildFromDefault();
		}else{
			buildFromRemote();
		}
		
		return mRv;
	}
	
	private int getId(String resName){
		try {
			return mSkin.getId(resName);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getNumId(int i){
		return getId(Skin.NUMBERS[i]);
	}
	
	private void buildFromRemote(){

		mRv = new RemoteViews(mSkin.getPackageName(), getId(Skin.layout.widget_layout));
		if(mWeatherData != null){
			mRv.setTextViewText(getId(Skin.id.tv_city), mWeatherData.getString("city"));
			mRv.setTextViewText(getId(Skin.id.tv_weather), mWeatherData.getString("weather"));
			mRv.setTextViewText(getId(Skin.id.tv_current), mWeatherData.getString("current_temp"));
			mRv.setTextViewText(getId(Skin.id.tv_min), mWeatherData.getString("min_temp"));
			mRv.setTextViewText(getId(Skin.id.tv_max), mWeatherData.getString("max_temp"));
			int icon = mWeatherData.getInt("icon", 0);
			if(icon != 0){
				BitmapDrawable bd = (BitmapDrawable)mContext.getResources().getDrawable(icon);
				mRv.setImageViewBitmap(getId(Skin.id.iv_icon), bd.getBitmap());
			}
		}
		
		if(mUpdateTime){
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minite = c.get(Calendar.MINUTE);
			if(HOUR_24){
				mRv.setImageViewResource(getId(Skin.id.iv_time_1), getNumId(hour/10));
				mRv.setImageViewResource(getId(Skin.id.iv_time_2), getNumId(hour%10));
				mRv.setViewVisibility(getId(Skin.id.iv_time_apm), View.GONE);
			}else{
				mRv.setImageViewResource(getId(Skin.id.iv_time_1), hour > 12 ? getNumId((hour - 12)/10) : getNumId(hour/10));
				mRv.setImageViewResource(getId(Skin.id.iv_time_2), hour > 12 ? getNumId((hour - 12)%10) : getNumId(hour%10));
				mRv.setViewVisibility(getId(Skin.id.iv_time_apm), View.VISIBLE);
				mRv.setImageViewResource(getId(Skin.id.iv_time_apm), hour > 12 ? getNumId(11) : getNumId(10));
			}
			mRv.setImageViewResource(getId(Skin.id.iv_time_3), getNumId(minite/10));
			mRv.setImageViewResource(getId(Skin.id.iv_time_4), getNumId(minite%10));
			
			int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			String[] days = mContext.getResources().getStringArray(R.array.weekday);
			mRv.setTextViewText(getId(Skin.id.tv_week), days[i-1]);

			String dateFormat = mContext.getString(R.string.date_format);
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			mRv.setTextViewText(getId(Skin.id.tv_date), sdf.format(new Date()));
			
		}
	}
	
	private void buildFromDefault(){
		
		mRv = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
		
		if(mWeatherData != null){
			mRv.setTextViewText(R.id.tv_city, mWeatherData.getString("city"));
			mRv.setTextViewText(R.id.tv_weather, mWeatherData.getString("weather"));
			mRv.setTextViewText(R.id.tv_current, mWeatherData.getString("current_temp"));
			mRv.setTextViewText(R.id.tv_min, mWeatherData.getString("min_temp"));
			mRv.setTextViewText(R.id.tv_max, mWeatherData.getString("max_temp"));
			int icon = mWeatherData.getInt("icon", 0);
			if(icon != 0){
				mRv.setImageViewResource(R.id.iv_icon, icon);
			}
		}
		
		if(mUpdateTime){
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minite = c.get(Calendar.MINUTE);
			if(HOUR_24){
				mRv.setImageViewResource(R.id.iv_time_1, numRes[hour/10]);
				mRv.setImageViewResource(R.id.iv_time_2, numRes[hour%10]);
				mRv.setViewVisibility(R.id.iv_time_apm, View.GONE);
			}else{
				mRv.setImageViewResource(R.id.iv_time_1, hour > 12 ? numRes[(hour - 12)/10] : numRes[hour/10]);
				mRv.setImageViewResource(R.id.iv_time_2, hour > 12 ? numRes[(hour - 12)%10] : numRes[hour%10]);
				mRv.setViewVisibility(R.id.iv_time_apm, View.VISIBLE);
				mRv.setImageViewResource(R.id.iv_time_apm, hour > 12 ? numRes[11] : numRes[10]);
			}
			mRv.setImageViewResource(R.id.iv_time_3, numRes[minite/10]);
			mRv.setImageViewResource(R.id.iv_time_4, numRes[minite%10]);
			
			int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			String[] days = mContext.getResources().getStringArray(R.array.weekday);
			mRv.setTextViewText(R.id.tv_week, days[i-1]);

			String dateFormat = mContext.getString(R.string.date_format);
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			mRv.setTextViewText(R.id.tv_date, sdf.format(new Date()));
			
		}
	}

	
	
}
