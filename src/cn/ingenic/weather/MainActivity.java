package cn.ingenic.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

public class MainActivity extends Activity {
	private final static int MSG_INIT = 0;
	
	private EngineManager mManager;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_INIT:
				if(mManager.hasDefaultCity()){
					translateTo(WeatherDisplay.class);
				}else{
					translateTo(CitySelectActivity.class);
				}
				break;
			}
		}
		
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}



	@Override
	protected void onResume() {
		super.onResume();
		mManager = EngineManager.getInstance(this);
		mManager.init(mHandler.obtainMessage(MSG_INIT));
	}



	private void translateTo(Class<? extends Activity> cls){
		overridePendingTransition(0, 0);
		this.startActivity(new Intent(this, cls));
		finish();
	}
	
}
