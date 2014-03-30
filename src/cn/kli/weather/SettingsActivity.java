package cn.kli.weather;

import cn.kli.utils.klilog;
import cn.kli.weather.engine.WeatherEngine;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.text.TextUtils;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {
	private final static String KEY_SELECT_CITY = "select_city";
	private final static String KEY_UPDATE_FREQUENCY = "update_frequency";
	
	private ListPreference mUpdateFreqPref;
	private Preference mSelectCityPref;
	private WeatherEngine mEngine;
	private UpdateAlarmManager mAlarmManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		mSelectCityPref = (Preference)findPreference(KEY_SELECT_CITY);
		mUpdateFreqPref = (ListPreference)findPreference(KEY_UPDATE_FREQUENCY);
		mUpdateFreqPref.setOnPreferenceChangeListener(this);
		mEngine = WeatherEngine.getInstance(this);
		mAlarmManager = UpdateAlarmManager.getInstance(this);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mSelectCityPref.setSummary(getString(R.string.current_city, mEngine.getMarkCity().get(0).name));
		mUpdateFreqPref.setSummary(getString(R.string.current_update_frequency, 
		        mAlarmManager.getUpdateDuring()));
	}


	@Override
	public boolean onPreferenceChange(Preference pref, Object obj) {
		if(pref.getKey().equals(KEY_UPDATE_FREQUENCY)){
			int hour = Integer.valueOf(String.valueOf(obj));
			klilog.info("select hour:"+hour);
			if(hour != 0){
			    mAlarmManager.setUpdateDuring(hour);
				mUpdateFreqPref.setSummary(getString(R.string.current_update_frequency,
				        mAlarmManager.getUpdateDuring()));
			}
		}
		return true;
	}

}
