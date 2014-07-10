package it.polimi.secomobile.ui;

import it.polimi.secomobile.applogic.SubmitClickListener;
import it.polimi.secomobile.data.JSONParser;
import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.Utils;
import it.polimi.secomobile.net.RestClient;

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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Parameters extends Activity {
	
	static final int PROGRESS_DIALOG_ID = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters);
        
        new AttributeLoader().execute();
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
    
    public class AttributeLoader extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
    	
    	private boolean isConnection = false;
    	
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
        	try{
	        	isConnection = getIntent()
								.getBundleExtra(Keys.QUERY_INFO.toString())
									.getBoolean(Keys.IS_CONNECTION.toString());
	        	
        	}catch (NullPointerException e){e.printStackTrace();}
        	
    		return getServiceAttributeList();
        }

        @Override
		protected void onCancelled() {
            Log.e("Net", "Async task Cancelled");
        }

        @Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> s) {
        	
        	LinearLayout ll = new LinearLayout(getApplicationContext());
        	ll.setOrientation(LinearLayout.VERTICAL);
        	for (HashMap<String, String> hashMap : s) {
        		LinearLayout ev = new LinearLayout(getApplicationContext());
        		ev.setOrientation(LinearLayout.HORIZONTAL);
        		
        		RelativeLayout rl = new RelativeLayout(getApplicationContext());
        		
        		TextView tv = new TextView(getApplicationContext());
        		tv.setText(hashMap.get(Keys.ATTRIBUTE_NAME.toString()));
        		
        		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        		        								LayoutParams.WRAP_CONTENT,
        		        								LayoutParams.MATCH_PARENT);
        		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, tv.getId());

        		String datatype = hashMap.get(Keys.ATTRIBUTE_DATATYPE.toString());
        		
        		rl.addView(tv);
        		
        		if (datatype.equals("BOOLEAN")){
					CheckBox et = new CheckBox(getApplicationContext());
	        		rl.addView(et, lp);
				}else if (datatype.equals("DATETIME")){
					DatePicker et = new DatePicker(getApplicationContext());
	        		rl.addView(et, lp);
				}else{
					boolean edtx = !isConnection;
        			
					if (isConnection){
						Log.i("PARAMETERS", "isConnection");
						String text = hashMap.get(Keys.ATTRIBUTE_VALUE.toString());
						Log.i("PARAMETERS", "EXCEPTION????");
						if (text != null){
							Log.i("PARAMETERS", "EQUALS????");
							TextView et = new TextView(getApplicationContext());
							et.setText(text);
							et.setTextSize(16f);
							et.setWidth(150);
							rl.addView(et, lp);
						}else
							edtx = true;
					}
					if(edtx){
	            		EditText et = new EditText(getApplicationContext());
	            		
	        			et.setHint(datatype);
	            		et.setSingleLine();
	            		if (datatype.equals("DECIMAL")
								|| datatype.equals("INTEGER"))
	            			et.setInputType(Utils.inputTypesMapping.get(datatype));
	            		et.setWidth(150);
	            		rl.addView(et, lp);
					} 

				}
        		
        		ev.addView(rl);
        		
        		ll.addView(ev);
			}
        	((ScrollView)findViewById(R.id.ParScrollView)).addView(ll);
        	
        	if(true)
				((Button)findViewById(R.id.SubmitButton)).setOnClickListener(
											new SubmitClickListener(
													ll, 
													s, 
													getIntent().getBundleExtra(Keys.QUERY_INFO.toString())));
        	else{
        		SubmitClickListener scl = new SubmitClickListener(ll, s, getIntent().getBundleExtra(Keys.QUERY_INFO.toString()));
        		scl.onClick(((ScrollView)findViewById(R.id.ParScrollView)));
        	}
        		
			
            try{
            	dismissDialog(PROGRESS_DIALOG_ID);
            }catch (Exception e){
            	e.printStackTrace();
            }
        }

        @Override
		protected void onPreExecute() {
        	showDialog(PROGRESS_DIALOG_ID);
        }
        
        protected ArrayList<HashMap<String, String>> getServiceAttributeList(){
    		RestClient rc = new RestClient();
    		ArrayList<JSONObject> ml;
    		Bundle b = getIntent().getBundleExtra(Keys.QUERY_INFO.toString());
			ml = rc.getQueryAttributeList(b);
			JSONParser parser = new JSONParser(ml);
			if (isConnection){
				SeCoState tabsState = ((SeCoState)getApplicationContext());
				Bundle queryInfo = getIntent().getBundleExtra(Keys.QUERY_INFO.toString());
				String conn = queryInfo.getString(Keys.CONNECTION_ID.toString());
				String tuple = (String) tabsState.getSavedTabs()
												.get(tabsState.getCurrentTab())
												.get(Keys.SELECTED_TUPLE.toString());	
				
				ArrayList<HashMap<String, String>> al = 
					rc.getConnectionQueryAttributeList(parser.getServiceAttributeList(),
														conn, tuple);
				return al;
			}	
    		return parser.getServiceAttributeList();
        }
        
    }
}
