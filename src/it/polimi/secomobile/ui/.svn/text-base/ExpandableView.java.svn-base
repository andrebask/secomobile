package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.JSONParser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableView extends LinearLayout {     
	
    private JSONObject tuple;
	private ArrayList<String> idList;
	private Context context;
	private LinearLayout.LayoutParams params = 
			new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
											LayoutParams.WRAP_CONTENT);

	public ExpandableView(Context context, JSONObject tuple, ArrayList<String> idList ) {
       
    	super(context);
    	this.tuple = tuple;
    	this.idList = idList;
    	this.context = context;
        
        this.setOrientation(VERTICAL);        
        
        params.setMargins(1, 1, 1, 1);
        //Log.i("IDLIST", idList.toString());
        if (idList.size() > 0){
        	createTitleView();
    	}else{
    		createTitleViewNoSemantic();
        }
    }
	
	private void createTitleView(){
    	try {
    		String key = idList.get(0);
    		String s = tuple.getString(key);
	        TextView cell = new TextView(context);
			cell.setText(s);
			cell.setTextSize(18f);
			cell.setTextColor(Color.WHITE);
	        addView(cell, params);        
		} catch (JSONException e) {
			createTitleViewNoSemantic();
			//e.printStackTrace();
		}catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createTitleViewNoSemantic(){
		
		ArrayList<JSONObject> t = new ArrayList<JSONObject>();
		t.add(tuple);
		
		JSONParser parser = new JSONParser(t);
		String title = parser.getTitleFromTuple();
		
        TextView cell = new TextView(context);
		cell.setText(title);
		cell.setTextSize(18f);
		cell.setTextColor(Color.WHITE);
        addView(cell, params);    

	}
}
