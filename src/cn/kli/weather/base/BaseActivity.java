package cn.kli.weather.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import cn.kli.weather.R;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity extends SherlockFragmentActivity {

	public void translateToFragment(final String to) {
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.fl_main, Fragment.instantiate(BaseActivity.this, to));
		tx.commit();
	}
}
