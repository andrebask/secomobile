package it.polimi.secomobile.ui;

import it.polimi.secomobile.applogic.HistoryItemListener;
import it.polimi.secomobile.data.HistoryData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class History extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        
        String[] columns = new String[] {"label", "datetime"};
        int[] renderTo = new int[] {R.id.name, R.id.description};
    	
    	((ListView)findViewById(R.id.HistoryList)).setAdapter(
    			new SimpleAdapter(
    					this, HistoryData.getHistoryTable(),
    					R.layout.two_text_view, columns, renderTo));
    	((ListView)findViewById(R.id.HistoryList)).setOnItemClickListener(
    			new HistoryItemListener());
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("Main Menu").setIcon(R.drawable.ic_menu_home).setIntent(new Intent(this, Subjects.class));
		
		return super.onCreateOptionsMenu(menu);
	}
}
