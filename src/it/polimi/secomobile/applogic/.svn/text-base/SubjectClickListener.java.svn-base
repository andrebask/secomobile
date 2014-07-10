package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.SearchMart;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class SubjectClickListener implements OnClickListener {

	public void onClick(View v) {
		Context c = v.getContext();
		Intent i = new Intent(c, SearchMart.class);
		i.putExtra(Keys.SUBJECT_ID.toString(), v.getId());
		c.startActivity(i);
	}
}
