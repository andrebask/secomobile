package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.Services;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SearchMart extends Search {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        ((TextView)findViewById(R.id.SearchMenuSubtitle)).setText("What you are looking for?");
        
        if (isOnline())
        	new SeCoLoader().execute(Services.MART);
        else{
        	Toast.makeText(this, "Server not found.\nCheck your connection", 200).show();
        	((TextView)findViewById(R.id.SearchMenuTitle)).setText("Server not found.");
        	((TextView)findViewById(R.id.SearchMenuSubtitle)).setText("Network error");
        }
    }
}