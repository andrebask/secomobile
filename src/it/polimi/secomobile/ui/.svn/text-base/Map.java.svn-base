package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.MapMarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Map extends MapActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        new MapLoader().execute();
        ((MapView)findViewById(R.id.map)).invalidate();;
    }
    
    private GeoPoint getLocationFromTuple(String tuple){
    	
    	JSONObject json;
    	double lat;
        double lon;
        String add;
        String cit;
        String cou;
		try {
			json = new JSONObject(tuple);
	        lat = json.getDouble("latitude");
	        lon = json.getDouble("longitude");
	        return new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6));
		} catch (JSONException e) {
			try {
				json = new JSONObject(tuple);
		        add = json.getString("address");
		        cit = json.getString("city");
		        try{cou = json.getString("country");}catch(JSONException c){cou = "";}
		        Geocoder gp = new Geocoder(this);
		        Address loc = gp.getFromLocationName(add + "," + cit + "," + cou, 1).get(0);
		        lat = loc.getLatitude();
		        lon = loc.getLongitude();
		        return new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6));
			} catch (JSONException ee) {
				ee.printStackTrace();
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
		return null;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Extend Search").setIcon(android.R.drawable.ic_menu_search).setIntent(new Intent(this, ExtendSearch.class));

		menu.add("Main Menu").setIcon(R.drawable.ic_menu_home).setIntent(new Intent(this, Subjects.class));
		menu.add("History").setIcon(android.R.drawable.ic_menu_recent_history).setIntent(new Intent(this, History.class));
		
		return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	static final int PROGRESS_DIALOG_ID = 0;
	
    @Override
    protected Dialog onCreateDialog(int id) {
    	ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Searching location");
        return progressDialog;
    }
    
    
    
    
    
	
	public class MapLoader extends AsyncTask<Void, Void, ArrayList<GeoPoint>> {
    	
        private MapController mapControl;
		private MapView map;
		private String tuple;
		private String title; 
		private GeoPoint mainPoint;

		@Override
        protected ArrayList<GeoPoint> doInBackground(Void... params) {
			SeCoState tabsState = (SeCoState) getApplicationContext();
			ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
			int count = 0;
	        for (HashMap<String, Object> tab : tabsState.getSavedTabs()) {
	        	String tabTuple = (String) tab.get(Keys.SELECTED_TUPLE.toString());
	        	GeoPoint location = getLocationFromTuple(tabTuple);
	        	count ++;
		        if (location == null || count-1 == tabsState.getCurrentTab())
		        	continue;
		        points.add(location);
			}
		        
	        mainPoint = getLocationFromTuple(tuple);
	        if (mainPoint == null)
	        	return points;
	        
        	try {
            	JSONObject json = new JSONObject(tuple);
            	String t = json.getString("title");
            	this.title = t;
			} catch (JSONException e) {
	        	try {
	            	JSONObject json = new JSONObject(tuple);
	            	String t = json.getString("name");
	            	this.title = t;
				} catch (JSONException ee) {}
			}
	        
            final MapController mapCon = map.getController();
            mapCon.setZoom(15);
            this.mapControl = mapCon;
	        
			mapControl.animateTo(mainPoint);
			return points;
        }

        @Override
		protected void onCancelled() {
            Log.e("Net", "Async task Cancelled");
        }

        @Override
		protected void onPostExecute(ArrayList<GeoPoint> points) {
        	if (mainPoint != null){
        		ArrayList<GeoPoint> list = new ArrayList<GeoPoint>(); list.add(mainPoint);
        		Drawable defaultMarker = getResources().getDrawable(R.drawable.point);
        		MapMarker mapMarker = new MapMarker(defaultMarker, list);
        		List<Overlay> overlays = map.getOverlays();
        		overlays.add(mapMarker);
        	}
        	
        	if (points.size() != 0){
        		Drawable defaultMarker = getResources().getDrawable(R.drawable.green_point);
        		MapMarker mapMarker = new MapMarker(defaultMarker, points);
        		List<Overlay> overlays = map.getOverlays();
        		overlays.add(mapMarker);
        	}
        	
        	
        	if (title != null){
        		TextView t = (TextView)findViewById(R.id.MapMenuSubtitle);
    			t.setText(title);
        	}
            try{
            	dismissDialog(PROGRESS_DIALOG_ID);
            }catch (Exception e){
            	e.printStackTrace();
            }
            if (mainPoint == null)
            	Toast.makeText(getApplicationContext(), "Location data not found", 100).show();
        }

        @Override
		protected void onPreExecute() {
        	showDialog(PROGRESS_DIALOG_ID);
        	SeCoState tabsState = (SeCoState) getApplicationContext();
        	tuple = (String) tabsState.getSavedTabs().get(tabsState.getCurrentTab()).get(Keys.SELECTED_TUPLE.toString());

            MapView map = (MapView) findViewById(R.id.map);
            map.setBuiltInZoomControls(true);
            this.map = map;
        }
        
    }
}
