package cn.kli.weather;

import android.os.Bundle;
import cn.kli.weather.base.BaseActivity;
import cn.kli.weather.display.WeatherDisplayFragment;

public class MainActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		
		translateToFragment(WeatherDisplayFragment.class.getName());
	}


}
