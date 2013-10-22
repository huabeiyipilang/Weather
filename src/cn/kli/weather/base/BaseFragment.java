package cn.kli.weather.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class BaseFragment extends SherlockFragment {
	private View mRoot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRoot = inflater.inflate(onContentInflate(), null);
		return mRoot;
	}
	
	protected int onContentInflate(){
		return 0;
	}
	
	protected View findViewById(int res){
		return mRoot.findViewById(res);
	}
}
