package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.Parameters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchInterfaceMenuItemListener implements OnItemClickListener {

	private List<String> idlist;
	private Bundle queryInfo;
	
	public SearchInterfaceMenuItemListener(List<String> idlist, Bundle queryInfo) {
		super();
		this.idlist = idlist;
		this.queryInfo = queryInfo;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Context c = parent.getContext();
		Intent i = new Intent(c, Parameters.class);
		i.putExtra(Keys.INTERFACE_ID.toString(), idlist.get(position));
		TextView martNamel = (TextView)((LinearLayout)v).getChildAt(0);
		
		queryInfo.putString(Keys.INTERFACE_ID.toString(), idlist.get(position));
		queryInfo.putString(Keys.SELECTED_INTERFACE_NAME.toString(), (String) martNamel.getText());
		i.putExtra(Keys.QUERY_INFO.toString(), queryInfo);
		
		c.startActivity(i);
	}

}
