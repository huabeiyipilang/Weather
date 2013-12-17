package cn.kli.weather;

import cn.kli.utils.klilog;
import cn.kli.weather.widget.WidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WeatherReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		klilog.info("Weather Receiver received action:"+action);
		if(Intent.ACTION_BOOT_COMPLETED.equals(action)){
			
		}/*else if(WidgetManager.ACTION_UPDATE_WEATHER.equals(action)){
		    WidgetManager.getInstance(context).refreshWeather();
		}else if(WidgetManager.ACTION_NOTIFY_WEATHER.equals(action)){
		    WidgetManager.getInstance(context).checkNotifyWeather();
		}*/else if(WidgetManager.ACTION_FRESH_WIDGET_TIME.equals(action)){
		    WidgetManager.getInstance(context).updateWidget();
		}
	}

}
