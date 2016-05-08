package com.ijustyce.diancan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class MainPagerViewAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> mainFragmentLists ;
	public MainPagerViewAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public MainPagerViewAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
		super(fm);
		this.mainFragmentLists = fragments ;
	}

	@Override
	public Fragment getItem(int arg0) {
		return mainFragmentLists.get(arg0);
	}

	@Override
	public int getCount() {
		return mainFragmentLists.size();
	}

}