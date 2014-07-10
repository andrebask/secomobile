package it.polimi.secomobile.ui;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

public class ResultExpandableListAdapter extends BaseExpandableListAdapter {
    // Sample data set.  children[i] contains the children (String[]) for groups[i].
	private Context context;
	private ArrayList<JSONObject> tupleList;
	private ArrayList<String> idList;
	private String martName;
	private ExpandableListView view;
	private Bundle queryInfo;
	
	public ResultExpandableListAdapter(Context context, ArrayList<JSONObject> tupleList, ArrayList<String> idList, String martName, Bundle queryInfo) { 
	    this.context = context;
	    this.tupleList = tupleList;
	    this.idList = idList;
	    this.martName = martName;
	    this.queryInfo = queryInfo;
	}
    
    public Object getChild(int groupPosition, int childPosition) {
        return tupleList.get(groupPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
		JSONObject tuple = tupleList.get(groupPosition);
	    return new ExpandableChildView(this.context, tuple, idList, martName, queryInfo);
    }

    public Object getGroup(int groupPosition) {
        return tupleList.get(groupPosition);
    }

    public int getGroupCount() {
        return tupleList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
		JSONObject tuple = tupleList.get(groupPosition);
		this.view = (ExpandableListView)parent;
	    return new ExpandableView(this.context, tuple, idList);
    }

    @Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
		int count =  getGroupCount();
		for (int i = 0; i <count ; i++){
			if (i != groupPosition){
				view.collapseGroup(i);
			}
		}
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }
    
}
