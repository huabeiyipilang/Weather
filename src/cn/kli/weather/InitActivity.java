package cn.kli.weather;

import cn.kli.utils.klilog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class InitActivity extends Activity {
	private final static int MSG_INIT = 0;
	klilog log = new klilog(InitActivity.class);
	
	private EngineManager mManager;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_INIT:
				if(mManager.hasDefaultCity()){
					translateTo(MainActivity.class, null);
				}else{
					Bundle bundle = new Bundle();
					bundle.putBoolean("fromSetting", false);
					translateTo(CitySelectActivity.class, bundle);
				}
				break;
			}
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
		mManager = EngineManager.getInstance(this);
		mManager.init(mHandler.obtainMessage(MSG_INIT));
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

}