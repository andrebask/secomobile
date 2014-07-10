package it.polimi.secomobile.ui;

import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.secomobile.applogic.SubjectClickListener;
import it.polimi.secomobile.data.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;

public class Subjects extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects);
        
        final Button businessButton = (Button) findViewById(R.id.BusinessButton);
        final Button freeeTimeButton = (Button) findViewById(R.id.FreeTimeButton);
        final Button educationButton = (Button) findViewById(R.id.EducationButton);
        final Button scienceButton = (Button) findViewById(R.id.ScienceButton);
        
        businessButton.setOnClickListener(new SubjectClickListener());
        freeeTimeButton.setOnClickListener(new SubjectClickListener());
        educationButton.setOnClickListener(new SubjectClickListener());
        scienceButton.setOnClickListener(new SubjectClickListener());
    }
    
	@Override
	public void onBackPressed() {
		if (!Utils.started)
			super.onBackPressed();
		else
			return;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		((SeCoState)getApplicationContext()).resetSavedTabs();
		Utils.started = true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (Utils.started)
			menu.add("History").setIcon(android.R.drawable.ic_menu_recent_history).setIntent(new Intent(this, History.class));
		
		return super.onCreateOptionsMenu(menu);
	}
}