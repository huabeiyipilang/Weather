package cn.kli.weather.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.kli.weather.R;
import cn.kli.weather.base.BaseActivity;
import cn.kli.weather.base.BaseFragment;
import cn.kli.weather.display.WeatherDisplayFragment;
import cn.kli.weather.settings.SettingsFragment;

public class MenuView extends ListView implements android.widget.AdapterView.OnItemClickListener {
	private MenuAdapter mAdapter;

	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mAdapter = new MenuAdapter();
		fillData();
		
		ListView menuList = (ListView)findViewById(R.id.lv_menu);
		menuList.setAdapter(mAdapter);
		menuList.setOnItemClickListener(this);
	}
	
	private void fillData(){
		List<Menu> list = new ArrayList<Menu>();
		
		Menu weather = new Menu();
		weather.setTitle(R.string.menu_weather);
		weather.setFragment(WeatherDisplayFragment.class);
		list.add(weather);
		
		Menu setting = new Menu();
		setting.setTitle(R.string.menu_setting);
		setting.setFragment(SettingsFragment.class);
		list.add(setting);
		
		mAdapter.setMenuList(list);
	}
	
	private class MenuAdapter extends BaseAdapter{

		private List<Menu> menuList;
		
		public void setMenuList(List<Menu> list){
			menuList = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return menuList.size();
		}

		@Override
		public Menu getItem(int arg0) {
			return menuList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			return getItem(arg0).getView();
		}
		
	}

	private class Menu{
		String title;
		String fragment;
		
		void setTitle(int res){
			title = getContext().getString(res);
		}
		
		void setFragment(Class<? extends BaseFragment> f){
			fragment = f.getName();
		}
		
		View getView(){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.menu_item, null);
			((TextView)view.findViewById(R.id.tv_menu)).setText(title);
			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Menu menu = mAdapter.getItem(arg2);
		BaseActivity activity = (BaseActivity)getContext();
		activity.translateToFragment(menu.fragment);
	}
	
}
