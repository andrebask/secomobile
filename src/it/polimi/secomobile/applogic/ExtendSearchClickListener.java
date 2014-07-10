package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.SeCoState;
import it.polimi.secomobile.ui.TabTupleDetail;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ExtendSearchClickListener implements OnClickListener {

	private Context context;
	private ArrayList<String> celltexts;
	private Bundle queryInfo;
	
	public ExtendSearchClickListener(Context context, ArrayList<String> celltexts, Bundle queryInfo) {
		this.context = context;
		this.celltexts = celltexts;
		this.queryInfo = queryInfo;
	}

	public void onClick(View v) {
		Intent i = new Intent(context, TabTupleDetail.class);
    	HashMap<String, Object> tabInfo = new HashMap<String, Object>();
    	
		if (celltexts != null && queryInfo != null){
    		tabInfo.put("celltexts", celltexts);
    		tabInfo.put(Keys.QUERY_INFO.toString(), queryInfo.getString(Keys.SELECTED_INTERFACE_NAME.toString()));
    		tabInfo.put(Keys.ACCESS_PATTERN_ID.toString(), queryInfo.getString(Keys.ACCESS_PATTERN_ID.toString()));
    		tabInfo.put(Keys.SELECTED_TUPLE.toString(), queryInfo.getString(Keys.SELECTED_TUPLE.toString()));
    		tabInfo.put(Keys.SELECTED_INTERFACE_NAME.toString(), queryInfo.getString(Keys.SELECTED_INTERFACE_NAME.toString()));
    		((SeCoState)context.getApplicationContext()).addTab(tabInfo);
		}
		context.startActivity(i);
		
	}
}
