package it.polimi.secomobile.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Intent;

public class HistoryData {
	
	private static ArrayList<HashMap<String, Object>> historyTable = new ArrayList<HashMap<String, Object>>();
	
	public static void addSearchInHistory(Intent i){
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("intent", i);
		hm.put("datetime", getDateTime());
		String key = Keys.QUERY_INFO.toString();
		String apattern = i.getBundleExtra(key).getString(Keys.SELECTED_AP_NAME.toString());
		String interf = i.getBundleExtra(key).getString(Keys.SELECTED_INTERFACE_NAME.toString());
		hm.put("label", apattern + "\n" + interf);
		historyTable.add(hm);
		if (historyTable.size() > 10)
			historyTable.remove(0);
	}
	
    public static ArrayList<HashMap<String, Object>> getHistoryTable() {
		return historyTable;
	}

    public static Intent getIntentAtPos(int pos){
    	return (Intent) historyTable.get(pos).get("intent");
    }
    
	private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
