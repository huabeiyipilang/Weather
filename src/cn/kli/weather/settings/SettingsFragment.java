package cn.kli.weather.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.kli.weather.EngineManager;
import cn.kli.weather.R;
import cn.kli.weather.base.BaseFragment;

public class SettingsFragment extends BaseFragment {
	
	enum Item{
		CITY_SELECT, FRESH_DURING
	}

	private EngineManager mEngine;
	
	@Override
	protected int onContentInflate() {
		return R.layout.fragment_settings;
	}

	@Override
	protected void onInitViews() {
		mEngine = EngineManager.getInstance(this.getActivity());
		
	}
	
	private class SettingsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return Item.values().length;
		}

		@Override
		public Item getItem(int position) {
			return Item.values()[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItemView(getItemDisplay(getItem(position)));
		}
		
		private View getItemView(String[] content){
			LayoutInflater inflater = SettingsFragment.this.getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.setting_item, null);
			((TextView) view.findViewById(R.id.tv_title)).setText(content[0]);
			((TextView) view.findViewById(R.id.tv_summary)).setText(content[1]);
			return view;
		}
		
	}
	
	private String[] getItemDisplay(Item item){
		String[] res = new String[2];
		switch(item){
		case CITY_SELECT:
			res[0] = getString(R.string.settings_city_select);
			res[1] = getString(R.string.current_city, mEngine.getDefaultMarkCity().name);
			break;
		case FRESH_DURING:
			res[0] = getString(R.string.settings_update_frequency);
			res[1] = getString(R.string.current_update_frequency, 
					mEngine.getUpdateDuringHour());
			break;
		}
		return res;
	}
	
	private void onItemClick(Item item){
		switch(item){
		case CITY_SELECT:
			break;
		case FRESH_DURING:
			break;
		}
	}
	
}
