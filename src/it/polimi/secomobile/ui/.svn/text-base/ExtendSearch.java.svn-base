package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.Services;

import java.util.Arrays;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

public class ExtendSearch extends Search {
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        SeCoState tabsState = (SeCoState) getApplicationContext();
		HashMap<String, Object> tab = tabsState.getSavedTabs().get(tabsState.getCurrentTab());
		Log.i("CURRENTTAB", String.valueOf(tabsState.getCurrentTab())+ "\n" +(String) tab.get(Keys.ACCESS_PATTERN_ID.toString())+ "\n");
		
        TextView tv1 = (TextView)findViewById(R.id.SearchMenuTitle);
        tv1.setText((String) tab.get(Keys.SELECTED_INTERFACE_NAME.toString()));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
        TextView tv2 = (TextView)findViewById(R.id.SearchMenuSubtitle);
        tv2.setText("Available connections");
		String sourceAccessPatternId = (String) tab.get(Keys.ACCESS_PATTERN_ID.toString());
		serviceIdlist = Arrays.asList(sourceAccessPatternId);
		Log.i("CONNECTIONS", serviceIdlist.toString());
		if (isOnline())
        	new SeCoLoader().execute(Services.CONNECTIONS);
		else
			Toast.makeText(this, "Server not found.\nCheck your connection", 200).show();
    }
}
