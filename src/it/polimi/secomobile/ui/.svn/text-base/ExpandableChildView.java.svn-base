package it.polimi.secomobile.ui;

import it.polimi.secomobile.applogic.ExtendSearchClickListener;
import it.polimi.secomobile.data.JSONParser;
import it.polimi.secomobile.data.Keys;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableChildView extends LinearLayout {     
	
    private JSONObject tuple;
	private ArrayList<String> idList;
	private ArrayList<String> celltexts = new ArrayList<String>();
	private Context context;
	private LinearLayout.LayoutParams params = 
			new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
											LayoutParams.WRAP_CONTENT);
	private String martName;
	private Bundle queryInfo;
	
    public ExpandableChildView(Context context, JSONObject tuple, ArrayList<String> idList, String martName, Bundle queryInfo) {
       
    	super(context);
        
    	this.tuple = tuple;
    	this.idList = idList;
    	this.context = context;
    	this.martName = martName;
		this.queryInfo = queryInfo;
		
		queryInfo.putString(Keys.SELECTED_TUPLE.toString(), tuple.toString());
		
        this.setOrientation(VERTICAL);        

        params.setMargins(0, 0, 0, 0);
        setBackgroundColor(Color.DKGRAY);
        //Log.i("IDLISTCHILD", idList.toString());
        
        if (idList.size() > 1){
            	
            createExpandedView();
   
        }else{
            
            createExpandedViewNoSemantic();
            	
        }
        addActionButton();
    }
    
	private void createExpandedView(){
		try{
			for (int i = 1; i < idList.size(); i++) {
					String key = idList.get(i);
					String s = tuple.getString(key);
					if (!s.contains("}}") && !s.equals("null")){
				        if (isNumber(s))
				        	s = key + ": " + s;
						celltexts.add(s);
				        TextView cell = createCell(s);
				        addView(cell, params); 
					}
			}
		} catch (JSONException e) {
			createExpandedViewNoSemantic();
			e.printStackTrace();
		}catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}	
		
	}
	
	private void createExpandedViewNoSemantic(){
		JSONArray keys = tuple.names();
		for (int i = 0; i < keys.length(); i++) {
	    	try {
	    		String key = keys.getString(i);
	    		String s = tuple.getString(key);
				if (!s.contains("}}") && !s.equals("null") 
						&& !key.equals("tupleId") 
						&& !key.equals("tupleScore")
						&& !key.toLowerCase().contains("title")){
					celltexts.add(s);
			        TextView cell = createCell(s);
			        addView(cell, params);        
				}
			} catch (JSONException ee) {
				ee.printStackTrace();
			}
		}
	}
	
	private boolean isNumber(String num){
	    try{
	        Integer.parseInt(num);
	    } catch(NumberFormatException e) {
		    try{
		        Float.parseFloat(num);
		    } catch(NumberFormatException ee) {
			    try{
			        Double.parseDouble(num);
			    } catch(NumberFormatException eee) {
			        return false;
			    }
		    }
	    }
	    return true;
	}
	
	private TextView createCell(String s){
		TextView cell = new TextView( context );
		cell.setTextSize(14f);
		cell.setPadding(10, 1, 1, 1);
		cell.setTextColor(Color.WHITE);
		cell.setText(Html.fromHtml(s));
        cell.setMaxLines(5);
        //Ellipsize does not work for a bug in Android (Issue 2254)
        //cell.setEllipsize(TruncateAt.END); 
		cell.setAutoLinkMask(Linkify.WEB_URLS);
		
		if (cell.getLinksClickable()){
			cell.setMovementMethod(LinkMovementMethod.getInstance());
			cell.setLinkTextColor(Color.WHITE);
		}
		
		return cell;
	}
	
	protected void addActionButton(){
		try {
			String key = idList.get(0);
			String title = tuple.getString(key);
			celltexts.add(0, title);
		} catch (JSONException e) {
			try {
				String title = tuple.getString("title");
				celltexts.add(0, title);
			} catch (JSONException ee) {
				try {
					String title = tuple.getString("name");
					celltexts.add(0, title);
				} catch (JSONException eee) {
					celltexts.add(0, "");
					e.printStackTrace();
				}
			}
		} catch (IndexOutOfBoundsException ie){
			ArrayList<JSONObject> t = new ArrayList<JSONObject>();
			t.add(tuple);
			JSONParser parser = new JSONParser(t);
			String title = parser.getTitleFromTuple();
			celltexts.add(0, title);
		}
        LinearLayout b = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.select_tuple_button, null);
        celltexts.add(0, martName);
        b.setOnClickListener(new ExtendSearchClickListener(context, celltexts, queryInfo));
        addView(b, params); 
	}
}
