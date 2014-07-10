package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.SearchInterface;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchConnectionMenuItemListener implements OnItemClickListener {

	private List<String> idlist;
	private Bundle queryInfo;
	private HashMap<String, String> targets;
	private HashMap<String, String> sourceIdTable;
	
	public SearchConnectionMenuItemListener(List<String> idlist, Bundle queryInfo, HashMap<String, String> targets, HashMap<String, String> sourceIdTable) {
		super();
		this.idlist = idlist;
		this.queryInfo = queryInfo;
		this.targets = targets;
		this.sourceIdTable = sourceIdTable;
	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Context c = parent.getContext();
		Intent i = new Intent(c, SearchInterface.class);
		i.putExtra(Keys.ACCESS_PATTERN_ID.toString(), targets.get(idlist.get(position)));
		TextView martNamel = (TextView)((LinearLayout)v).getChildAt(0);
		Bundle queryInfo = new Bundle();
		
		String apid = targets.get(idlist.get(position));
		queryInfo.putString(Keys.MART_ID.toString(), sourceIdTable.get(apid));
		queryInfo.putString(Keys.ACCESS_PATTERN_ID.toString(), apid);
		queryInfo.putString(Keys.SELECTED_AP_NAME.toString(), (String) martNamel.getText());
		queryInfo.putString(Keys.CONNECTION_ID.toString(), idlist.get(position));
		queryInfo.putBoolean(Keys.IS_CONNECTION.toString(), true);
		i.putExtra(Keys.QUERY_INFO.toString(), queryInfo);
		
		c.startActivity(i);

	}

}
