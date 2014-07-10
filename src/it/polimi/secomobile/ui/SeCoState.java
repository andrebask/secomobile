package it.polimi.secomobile.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.util.Log;

public class SeCoState extends Application {
	
	private ArrayList<HashMap<String, Object>> savedTabs = new ArrayList<HashMap<String,Object>>();
	
	private int currentTab = 0;
	
	public synchronized ArrayList<HashMap<String, Object>> getSavedTabs() {
		return savedTabs;
	}

	public synchronized void addTab(HashMap<String, Object> tab){
		Log.i("ADDTAB", tab.toString());
		savedTabs.add(tab);
		if (savedTabs.size() > 5)
			savedTabs.remove(0);
	}

	public synchronized void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public synchronized int getCurrentTab() {
		return currentTab;
	}

	public void resetSavedTabs() {
		this.savedTabs = new ArrayList<HashMap<String,Object>>();
		
	}
	
}
