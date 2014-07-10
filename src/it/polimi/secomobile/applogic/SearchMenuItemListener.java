package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.SearchAccPattern;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchMenuItemListener implements OnItemClickListener {

	private List<String> idlist;
	
	public SearchMenuItemListener(List<String> idlist) {
		super();
		this.idlist = idlist;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Context c = parent.getContext();
		Intent i = new Intent(c, SearchAccPattern.class);
		i.putExtra(Keys.MART_ID.toString(), idlist.get(position));
		TextView martNamel = (TextView)((LinearLayout)v).getChildAt(0);
		
		Bundle queryInfo = new Bundle();
		queryInfo.putString(Keys.MART_ID.toString(), idlist.get(position));
		queryInfo.putString(Keys.SELECTED_MART_NAME.toString(), (String) martNamel.getText());
		i.putExtra(Keys.QUERY_INFO.toString(), queryInfo);
		
		c.startActivity(i);
	}

}
