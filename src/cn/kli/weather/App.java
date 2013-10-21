package cn.kli.weather;

import cn.kli.utils.KliUtils;
import android.app.Application;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		KliUtils.init(this);
	}

}
