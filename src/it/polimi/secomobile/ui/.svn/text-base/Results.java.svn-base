package it.polimi.secomobile.ui;

import it.polimi.secomobile.applogic.QueryCreator;
import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class Results extends Activity {
	
	static final int PROGRESS_DIALOG_ID = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        
        Utils.setSemanticMetadata(getResources().getString(R.string.semanticMetadata));
        new QueryLoader(this).execute();
    }
    
    public class QueryLoader extends AsyncTask<Void, Void, ResultExpandableListAdapter> {
    	
    	private Activity callingActivity;
		private String martName;
    	
        protected QueryLoader(Activity a){
        	this.callingActivity = a;
        }
    	
    	@Override
        protected ResultExpandableListAdapter doInBackground(Void... params) {
            ArrayList<HashMap<String, String>> attributes =
            	(ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra(Keys.ATTRIBUTE_LIST.toString());
            
            Bundle b = getIntent().getBundleExtra(Keys.QUERY_INFO.toString());
        	QueryCreator qc = new QueryCreator(b, attributes);
        	HashMap<String, Object> hm = new HashMap<String, Object>();
        	hm.put("resultTuples", qc.sendQuery());
        	hm.put("semanticIdList", qc.getSemanticOrder());
        	martName = qc.getMartName();
        	
            ResultExpandableListAdapter tadptr = 
            	new ResultExpandableListAdapter(
            		callingActivity,
            		(ArrayList<JSONObject>)hm.get("resultTuples"), 
            		(ArrayList<String>)hm.get("semanticIdList"),
            		martName,
            		getIntent().getBundleExtra(Keys.QUERY_INFO.toString()));
            
        	return tadptr;
        }

        @Override
		protected void onCancelled() {
            Log.e("Net", "Async task Cancelled");
        }

        @Override
		protected void onPostExecute(ResultExpandableListAdapter tadptr) {
        	
        	((ExpandableListView)findViewById(R.id.expandable_tuple_list)).setAdapter(tadptr);
        	
            try{
            	dismissDialog(PROGRESS_DIALOG_ID);
            }catch (Exception e){
            	e.printStackTrace();
            }
            
           
			if(tadptr.isEmpty()){
            	Toast.makeText(callingActivity, "No Results", 100).show();
            	openOptionsMenu();
			}
        }

        @Override
		protected void onPreExecute() {
        	showDialog(PROGRESS_DIALOG_ID);
        }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("Main Menu").setIcon(R.drawable.ic_menu_home).setIntent(new Intent(this, Subjects.class));
		menu.add("History").setIcon(android.R.drawable.ic_menu_recent_history).setIntent(new Intent(this, History.class));
		
		if(((SeCoState)getApplicationContext()).getSavedTabs().size() != 0){
        	menu.add("Cancel").setIcon(android.R.drawable.ic_menu_revert).setIntent(new Intent(this, TabTupleDetail.class));
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId() == 5){
//			Intent i = new Intent(this, TabTupleDetail.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(i);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
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
}
