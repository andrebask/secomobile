package it.polimi.secomobile.applogic;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.Utils;
import it.polimi.secomobile.net.RestClient;
import it.polimi.secomobile.ui.SearchAccPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class QueryCreator {
	
	public class SearchMenuItemListener implements OnItemClickListener {
	
		private List<String> idlist;
		
		public SearchMenuItemListener(List<String> idlist) {
			super();
			this.idlist = idlist;
		}
	
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			Context c = parent.getContext();
			Intent i = new Intent(c, SearchAccPattern.class);
			i.putExtra(Keys.MART_ID.toString(), idlist.get(position));
			TextView martNamel = (TextView)((LinearLayout)v).getChildAt(0);
			
			Bundle queryInfo = new Bundle();
			queryInfo.putString(Keys.MART_ID.toString(), idlist.get(position));
			queryInfo.putString(Keys.SELECTED_MART_NAME.toString(), (String) martNamel.getText());
			i.putExtra(Keys.QUERY_INFO.toString(), queryInfo);
			
			c.startActivity(i);
		}
	
	}

	private String jsonExecution;
	private RestClient restclient;
	private JSONObject interfaceJson;
	private String serviceID;
	private Bundle queryInfo;
	
	public QueryCreator(Bundle queryInfo, ArrayList<HashMap<String, String>> attributes) {
		
		this.queryInfo = queryInfo;
		
		serviceID = queryInfo.getString(Keys.INTERFACE_ID.toString());
		
		jsonExecution = String.format("{\"sourceId\" : \"%s\",\"accessMethodId\" : \"%s\",\"inputTuple\" : {",
										queryInfo.getString(Keys.MART_ID.toString()),
										serviceID);
		
		restclient = new RestClient();
		List<String> idlist = Arrays.asList(queryInfo.getString(Keys.INTERFACE_ID.toString()));
		interfaceJson = restclient.getInterfaceList(idlist).get(0);
		JSONArray inputAttributes;
		try {
			inputAttributes = interfaceJson.getJSONArray("inputAttributes");
			StringBuilder sb = new StringBuilder();
			sb.append(jsonExecution);
			for (int a = 0; a < inputAttributes.length(); a++) {
				JSONObject item = inputAttributes.getJSONObject(a);
				for (HashMap<String, String> map : attributes) {
					Log.i("Interface", map.get(Keys.ATTRIBUTE_ID.toString()) + " ||| " + item.getString("patternSignatureComponentId"));
					if (map.get(Keys.ATTRIBUTE_ID.toString()).equals(item.getString("patternSignatureComponentId"))){
						sb.append(String.format("\"%s\" : \"%s\",", item.getString("name"), map.get(Keys.ATTRIBUTE_VALUE.toString())));
					}
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("}}");
			jsonExecution = sb.toString();
			Log.i("Interface", jsonExecution);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<JSONObject> sendQuery(){
		try {
			restclient.createEngineSession();
			restclient.createEngineExecution(new JSONObject(jsonExecution));
			String results = restclient.sendEngineMoreRequest(
					new JSONObject("{\"maxFetches\" : \"5\",\"maxTuples\" : \"30\",\"maxTime\" : \"10000\"}"));
			
			ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
			JSONObject json = new JSONObject(results);
			JSONArray items = json.getJSONArray("tuples");
			for (int a = 0; a < items.length(); a++) {
				JSONObject item = items.getJSONObject(a);
				objList.add(item);
			}
			return objList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<JSONObject>();
	}

	public ArrayList<String> getSemanticOrder() {
		
		ArrayList<JSONObject> semantic = Utils.getOutputSignatureMetadata(serviceID);
		
		ArrayList<String> idList = new ArrayList<String>();
		try {		
			JSONObject toremove = null;
			for (JSONObject attribute : semantic) {
				if (attribute.getString("__id__").toLowerCase().contains("title")){
					idList.add(attribute.getString("__id__"));
					toremove = attribute;
				}
			}
			semantic.remove(toremove);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String semanticType : Utils.semanticOrder) {
			for (JSONObject attribute : semantic) {
				try {
					if (attribute.getString("semanticType").toLowerCase().equals(semanticType))
						idList.add(attribute.getString("__id__"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		
		//Log.i("IDLIST1", idList.toString());
		
		return idList;
	}

	public String getMartName() {
		ArrayList<String> idList = new ArrayList<String>();
		idList.add(queryInfo.getString(Keys.MART_ID.toString()));
		JSONObject mart = restclient.getMartList(idList).get(0);
		
		try {
			return mart.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
		

}
