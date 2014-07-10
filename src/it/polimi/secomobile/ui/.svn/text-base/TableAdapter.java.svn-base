package it.polimi.secomobile.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TableAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<JSONObject> tupleList;
	private ArrayList<String> idList;
	
	public TableAdapter(Context context, ArrayList<JSONObject> tupleList, ArrayList<String> idList) { 
	    this.context = context;
	    this.tupleList = tupleList;
	    this.idList = idList;
	}
	
	public int getCount() {                        
	    return tupleList.size();
	}
	
	public Object getItem(int position) {     
	    return tupleList.get(position);
	}
	
	public long getItemId(int position) {  
	    return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) { 
		JSONObject tuple = tupleList.get(position);
	    return new ExpandableView(this.context, tuple, idList);
	}

}

