package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.ui.R.style;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class TabTupleDetail extends TabActivity implements android.widget.TabHost.TabContentFactory{

	private TabHost tabHost;
	private SeCoState tabsState;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabsState = ((SeCoState)getApplicationContext());
        
        this.tabHost = getTabHost();
        
        //tabHost.getTabWidget().setDividerDrawable(R.drawable.tab_separator);
        if (tabsState.getSavedTabs() != null){
	        
        	addTabListToTabHost();
        }
    }

    private void addTabListToTabHost() {
    	int count = 0;
    	for (HashMap<String, Object> tab : tabsState.getSavedTabs()) {
    		Log.i("ITERATIONSAVEDTABS", String.valueOf(count));
    		View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
    		TextView tv = (TextView) view.findViewById(R.id.IndicatorText);
    		tv.setText(((ArrayList<String>) tab.get("celltexts")).get(0));
    		tabHost.addTab(tabHost.newTabSpec(Integer.toString(count))
    							.setIndicator(view)
    							.setContent(this));
    		count++;
		}
    	
    	tabHost.setCurrentTab(count-1);
    	Log.i("SAVEDTABS", tabsState.getSavedTabs().toString());
	}
    
	@Override
	protected void onPause() {
		super.onPause();
		
		tabsState.setCurrentTab(getTabHost().getCurrentTab());
	}
	
	@Override
	public void onBackPressed() {
		try{
			int size = tabsState.getSavedTabs().size();
			tabsState.getSavedTabs().remove(size-1);
		} catch (ArrayIndexOutOfBoundsException e) {}
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("Extend Search").setIcon(android.R.drawable.ic_menu_search).setIntent(new Intent(this, ExtendSearch.class));
		
		menu.add("Map").setIcon(android.R.drawable.ic_menu_mapmode).setIntent(new Intent(this, Map.class));
		
		menu.add("Main Menu").setIcon(R.drawable.ic_menu_home).setIntent(new Intent(this, Subjects.class));
		menu.add("History").setIcon(android.R.drawable.ic_menu_recent_history).setIntent(new Intent(this, History.class));
		
		return super.onCreateOptionsMenu(menu);
	}

	public View createTabContent(String tag) {
            try {
				return createTupleView(tag);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
    }
    
	private View createTupleView(String tag) throws JSONException{
		
		LinearLayout.LayoutParams params = 
			new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 
											android.view.ViewGroup.LayoutParams.WRAP_CONTENT);		
		
		View v = LayoutInflater.from(this).inflate(R.layout.tuple_detail, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.TupleDetailLinearLayout);
		
		HashMap<String, Object> tab = tabsState.getSavedTabs().get(Integer.valueOf(tag));
		
		ArrayList<String> celltexts = (ArrayList<String>) tab.get("celltexts");
		if(celltexts != null){
			if (celltexts.size() > 0){
		        TextView titlecell = (TextView) v.findViewById(R.id.TupleDetailTitle);
		        titlecell.setMaxLines(2);
		        titlecell.setEllipsize(TruncateAt.END);
		        titlecell.setText(celltexts.get(1));
				
				for (int i = 2; i < celltexts.size(); i++) {
					try{
						String s = celltexts.get(i);
						if (!s.contains("}}") && !s.equals("null")){
					        TextView cell = new TextView( this );
							cell.setText(Html.fromHtml(s));
							cell.setTextAppearance(this, style.TupleDetailText);
							cell.setAutoLinkMask(Linkify.WEB_URLS);
							if (cell.getLinksClickable()){
								cell.setMovementMethod(LinkMovementMethod.getInstance());
								cell.setLinkTextColor(Color.WHITE);
							}
							layout.addView(cell, params); 
						}
					}catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return v;
	}
	
}
