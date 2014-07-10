package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.HistoryData;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryItemListener implements OnItemClickListener {

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Context c = parent.getContext();
		Intent i = HistoryData.getIntentAtPos(position);
		
		c.startActivity(i);

	}

}
