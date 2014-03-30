package cn.kli.weather;

import java.util.List;

import weathersource.weathercomcn.SourceWeatherComCn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.kli.utils.klilog;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.EngineListener;
import cn.kli.weather.engine.WeatherEngine;

public class InitActivity extends Activity {
	private final static int MSG_INIT = 0;
	klilog log = new klilog(InitActivity.class);
	
	private WeatherEngine mManager;
	
	private EngineListener mListener = new EngineListener(){

        @Override
        protected void onInited() {
            List<City> markCities = mManager.getMarkCity();
            if(markCities != null && markCities.size() > 0){
                translateTo(MainActivity.class, null);
            }else{
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromSetting", false);
                translateTo(CitySelectActivity.class, bundle);
            }
        }

        @Override
        protected void onError(int errorCode) {
            klilog.error("weather engine init error:"+errorCode);
        }
	    
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		log.i("test klilog");
	}



	@Override
	protected void onResume() {
		super.onResume();
		klilog.info("onResume");
		mManager = WeatherEngine.getInstance(this);
		mManager.addListener(mListener);
		mManager.init(new SourceWeatherComCn(getApplicationContext()));
	}

	private void translateTo(Class<? extends Activity> cls, Bundle bundle){
		klilog.info("translateTo "+cls.getName());
		overridePendingTransition(0, 0);
		Intent intent = new Intent(this, cls);
		if(bundle != null){
			intent.putExtras(bundle);
		}
		this.startActivity(intent);
		finish();
	}

    @Override
    protected void onPause() {
        super.onPause();
        mManager.removeListener(mListener);
    }

}
