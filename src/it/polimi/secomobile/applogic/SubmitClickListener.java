package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.HistoryData;
import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.Results;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubmitClickListener implements OnClickListener {

	private LinearLayout view;
	private ArrayList<HashMap<String, String>> attributes;
	private Bundle queryInfo;
	
	public SubmitClickListener(LinearLayout ll, ArrayList<HashMap<String, String>> attributes, Bundle queryInfo) {
		this.view = ll;
		this.attributes = attributes;
		this.queryInfo = queryInfo;
	}

	public void onClick(View v) {
		
		for (int i = 0; i < view.getChildCount(); i++) {
			
			LinearLayout l = (LinearLayout) view.getChildAt(i);
			RelativeLayout rl = (RelativeLayout) l.getChildAt(0);
			String value;
			try{
				TextView et = (TextView) rl.getChildAt(1);
				value = et.getText().toString();
			} catch (ClassCastException e){
				try{
					DatePicker dp = (DatePicker) rl.getChildAt(1);
					String monthHeader = "";
					for (int j = 0; j < (Integer.toString(dp.getMonth()).length() % 2); j++) {
						monthHeader = "0";
					}
					String dayHeader = "";
					for (int j = 0; j < (Integer.toString(dp.getDayOfMonth()).length() % 2); j++) {
						dayHeader = "0";
					}
					value = Integer.toString(dp.getYear())
							+ monthHeader + Integer.toString(dp.getMonth() + 1)
							+ dayHeader + Integer.toString(dp.getDayOfMonth());
				} catch (ClassCastException ee){
					CheckBox cb = (CheckBox) rl.getChildAt(1);
					value = Boolean.toString(cb.isChecked());
				} 
			} 
			
			attributes.get(i).put(Keys.ATTRIBUTE_VALUE.toString(), value);
			//Log.i("Interface", "VALUE: " + value);
		}
		
		Context c = v.getContext();
		Intent i = new Intent(c, Results.class);
		i.putExtra(Keys.QUERY_INFO.toString(), queryInfo);
		i.putExtra(Keys.ATTRIBUTE_LIST.toString(), attributes);
		
		HistoryData.addSearchInHistory(i);
		
		c.startActivity(i);
		
	}

}
