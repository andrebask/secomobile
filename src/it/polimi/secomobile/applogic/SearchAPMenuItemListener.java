package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.SearchInterface;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchAPMenuItemListener implements OnItemClickListener {

	private List<String> idlist;
	private Bundle queryInfo;
	
	public SearchAPMenuItemListener(List<String> idlist, Bundle queryInfo) {
		super();
		this.idlist = idlist;
		this.queryInfo = queryInfo;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Context c = parent.getContext();
		Intent i = new Intent(c, SearchInterface.class);
		i.putExtra(Keys.ACCESS_PATTERN_ID.toString(), idlist.get(position));
		TextView martNamel = (TextView)((LinearLayout)v).getChildAt(0);
		
		queryInfo.putString(Keys.ACCESS_PATTERN_ID.toString(), idlist.get(position));
		queryInfo.putString(Keys.SELECTED_AP_NAME.toString(), (String) martNamel.getText());
		i.putExtra(Keys.QUERY_INFO.toString(), queryInfo);
		
		c.startActivity(i);
	}

}

