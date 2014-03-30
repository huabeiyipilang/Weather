package cn.kli.weather;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.kli.utils.klilog;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.WeatherEngine;

public class CitySelectActivity extends Activity implements OnItemClickListener {
	private final static int MSG_ENGINE_CHANGED = 1;
    private final static int MSG_UPDATE_LIST = 2;

	//views
	private TextView mTvCityNav;
	private ListView mLvCityList;
	private Spinner mSpSourceList;
	
	private LinkedList<City> mCityTree = new LinkedList<City>();
	private List<City> mCurrentList;
	
	private WeatherEngine mEngine;
	
	private boolean mFromSetting;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_ENGINE_CHANGED:
				mCityTree = new LinkedList<City>();
				mTvCityNav.setText("");
				initFinished();
				break;
			case MSG_UPDATE_LIST:        
			    mCurrentList = (List<City>)msg.obj;
			    City city = mCityTree.peek();
		        if(mCurrentList.size() == 0 && city != null){
		            //this means the city unit
		            mEngine.markCity(city);
		            if(!mFromSetting){
		                Intent intent = new Intent(CitySelectActivity.this, MainActivity.class);
		                startActivity(intent);
		            }
		            finish();
		        }

		        //fresh list view
		        klilog.info("mCurrentList.size() = "+mCurrentList.size());
		        CityListAdapter adapter = new CityListAdapter(CitySelectActivity.this, mCurrentList);
		        mLvCityList.setAdapter(adapter);
		        
		        //fresh navigation view
		        String nav = "";
		        if(mCityTree.size() >= 1){
		            for(City _city : mCityTree){
		                nav += "/"+_city.name;
		            }
		        }
		        mTvCityNav.setText(nav);
		        
			    break;
			}
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.acitvity_city_select);
		mFromSetting = getIntent().getBooleanExtra("fromSetting", true);
		mEngine = WeatherEngine.getInstance(this);
		mTvCityNav = (TextView)findViewById(R.id.tv_city_navigation);
		mLvCityList = (ListView)findViewById(R.id.lv_city_list);
		mLvCityList.setOnItemClickListener(this);
		/*mSpSourceList = (Spinner)findViewById(R.id.sp_source_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mEngine.getSourceList());
		mSpSourceList.setAdapter(adapter);
		mSpSourceList.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				mEngine.changeEngineSource(mEngine.getSourceList()[pos], mHandler.obtainMessage(MSG_ENGINE_CHANGED));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});*/
		initFinished();
	}


	private void initFinished(){
		freshListByCity(null);
	}
	
	private void freshListByCity(City city){
		//get data
		mEngine.requestCityListByCity(city, mHandler.obtainMessage(MSG_UPDATE_LIST));
	}
	
	private class CityListAdapter extends BaseAdapter{
		List<City> list;
		LayoutInflater inflater;
		CityListAdapter(Context context, List<City> cityList){
			inflater = LayoutInflater.from(context);
			list = cityList;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int pos) {
			return list.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			View view = null;
			if(convertView == null){
				view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			}else{
				view = convertView;
			}
			
			TextView tv = (TextView)view;
			tv.setText(list.get(pos).name);
			return tv;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		klilog.info("onItemClick pos = " + pos);
		City city = mCurrentList.get(pos);
		mCityTree.push(city);
		freshListByCity(city);
	}
	
	private void backToUpLevel(){
		City toCity = mCityTree.pollFirst();
		freshListByCity(toCity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && mCityTree.size() != 0){
			backToUpLevel();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	

}
