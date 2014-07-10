package it.polimi.secomobile.ui;

import it.polimi.secomobile.applogic.SearchAPMenuItemListener;
import it.polimi.secomobile.applogic.SearchConnectionMenuItemListener;
import it.polimi.secomobile.applogic.SearchInterfaceMenuItemListener;
import it.polimi.secomobile.applogic.SearchMenuItemListener;
import it.polimi.secomobile.data.JSONParser;
import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.MartIds;
import it.polimi.secomobile.data.Services;
import it.polimi.secomobile.net.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Search extends Activity {
	
	static final int PROGRESS_DIALOG_ID = 0;
	protected List<String> serviceIdlist;
	
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;

	}

	
    @Override
    protected Dialog onCreateDialog(int id) {
    	ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Retrieving information from the server");
        return progressDialog;
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	super.onPrepareDialog(id, dialog);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("Main Menu").setIcon(R.drawable.ic_menu_home).setIntent(new Intent(this, Subjects.class));
		menu.add("History").setIcon(android.R.drawable.ic_menu_recent_history).setIntent(new Intent(this, History.class));
		
		return super.onCreateOptionsMenu(menu);
	}
    
    public class SeCoLoader extends AsyncTask<Services, Void, ArrayList<HashMap<String, String>>> {
    	
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Services... params) {
    		return getServiceNameList(params[0]);
        }

        @Override
		protected void onCancelled() {
            Log.e("Net", "Async task Cancelled");
        }

        @Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> s) {
        	
            String[] columns = new String[] {"name", "description"};
            int[] renderTo = new int[] {R.id.name, R.id.description};
        	
        	((ListView)findViewById(R.id.list)).setAdapter(
        			new SimpleAdapter(
        					getApplicationContext(), s,
        					R.layout.two_text_view, columns, renderTo));
        	
            try{
            	dismissDialog(PROGRESS_DIALOG_ID);
            }catch (Exception e){
            	e.printStackTrace();
            }
        }

        @Override
		protected void onPreExecute() {
        	showDialog(PROGRESS_DIALOG_ID);
        	TextView tv = (TextView)findViewById(R.id.SearchMenuTitle);
    		switch (getIntent().getIntExtra(Keys.SUBJECT_ID.toString(), 0)) {
    			case R.id.BusinessButton:
    				tv.setText(R.string.business_button);
    				serviceIdlist = MartIds.businessIds;
    				break;
    				
    			case R.id.FreeTimeButton:
    				tv.setText(R.string.freetime_button);
    				serviceIdlist = MartIds.freeTimeIds;
    				break;
    	
    			case R.id.EducationButton:
    				tv.setText(R.string.education_button);
    				serviceIdlist = MartIds.educationIds;
    				break;
    				
    			case R.id.ScienceButton:
    				tv.setText(R.string.science_button);
    				serviceIdlist = MartIds.scienceIds;
    				break;
    				
    			default:
    				break;
    		}
        }
        
        protected ArrayList<HashMap<String, String>> getServiceNameList(Services serviceType){
    		RestClient rc = new RestClient();
    		ArrayList<JSONObject> ml;
    		switch (serviceType) {
    		case MART:
    			ml = rc.getMartList(serviceIdlist);
    			((ListView)findViewById(R.id.list)).setOnItemClickListener(
    					new SearchMenuItemListener(serviceIdlist));
    			break;
    			
    		case ACC_PATTERN:
    			serviceIdlist = rc.getAccessPatternIdList(serviceIdlist);
    			Log.i("AccPattern", serviceIdlist.toString());
    			ml = rc.getAccessPatternList(serviceIdlist);
            	((ListView)findViewById(R.id.list)).setOnItemClickListener(
            			new SearchAPMenuItemListener(serviceIdlist,
            					getIntent().getBundleExtra(Keys.QUERY_INFO.toString())));
    			break;
    			
    		case INTERFACE:
    			serviceIdlist = rc.getInterfaceIdList(serviceIdlist);
    			ml = rc.getInterfaceList(serviceIdlist);
            	((ListView)findViewById(R.id.list)).setOnItemClickListener(
            			new SearchInterfaceMenuItemListener(serviceIdlist, 
            					getIntent().getBundleExtra(Keys.QUERY_INFO.toString())));
    			break;
    			
    		case CONNECTIONS:
    			serviceIdlist = rc.getConnectionPatternIdList(serviceIdlist);
    			ml = rc.getConnectionPatternList(serviceIdlist);
    			JSONParser p = new JSONParser(ml);
    			HashMap<String, String> targets = p.getConnectionTargetTable();
    			HashMap<String, String> sourceIdTable = rc.getSourceIdTableFromAPList(targets); 
    			((ListView)findViewById(R.id.list)).setOnItemClickListener(
            			new SearchConnectionMenuItemListener(serviceIdlist, 
            					getIntent().getBundleExtra(Keys.QUERY_INFO.toString()), 
            					targets,
            					sourceIdTable));
    			break;
    		default:
    			ml = new ArrayList<JSONObject>();
    		}
    		JSONParser parser = new JSONParser(ml);
    		return parser.getMartNameDescriptionList();
        }
    }
}
