package cn.kli.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import cn.kli.weather.base.BaseActivity;
import cn.kli.weather.display.WeatherDisplayFragment;

public class MainActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		translateToFragment(WeatherDisplayFragment.class.getName());
	}

	private void translateToFragment(final String to) {
//        new Thread(){
//                @Override
//                public void run() {
//                        super.run();
//                }
//        }.start();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fl_main, Fragment.instantiate(MainActivity.this, to));
        tx.commit();
	}
}
