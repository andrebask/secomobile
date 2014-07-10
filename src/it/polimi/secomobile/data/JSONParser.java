package it.polimi.secomobile.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	private ArrayList<JSONObject> jsonPage;
	
	public JSONParser(ArrayList<JSONObject> jsonPage) {
		this.jsonPage = jsonPage;
	}
	
	public ArrayList<String> getMartNameList(){
		ArrayList<String> s = new ArrayList<String>();
    	for (JSONObject jObj : jsonPage) {
    		try {
    			s.add(jObj.getString("name"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
        return s;
	}
	
	public ArrayList<HashMap<String, String>> getMartNameDescriptionList(){
		ArrayList<HashMap<String, String>> s = new ArrayList<HashMap<String, String>>();
    	for (JSONObject jObj : jsonPage) {
    		try {
    			HashMap<String, String> nd = new HashMap<String, String>();
    			nd.put("name",jObj.getString("name"));
    			nd.put("description",jObj.getString("description"));
    			s.add(nd);
			} catch (JSONException e) {
				try{
	    			HashMap<String, String> nd = new HashMap<String, String>();
	    			nd.put("name",jObj.getString("name"));
	    			nd.put("description","");
	    			s.add(nd);
					e.printStackTrace();
				} catch (JSONException ee) { e.printStackTrace();}
			}
		}
        return s;
	}
	
	public ArrayList<HashMap<String, String>> getServiceAttributeList(){
		ArrayList<HashMap<String, String>> s = new ArrayList<HashMap<String, String>>();
    	for (JSONObject jObj : jsonPage) {
    		try{
	    		String name;
	    		try {
	    			name = jObj.getString("name");
	    		}catch (JSONException e) {
	    			try {
	    				name = jObj.getString("__id__");
					} catch (Exception e2) {
						break;
					}
				}
    			HashMap<String, String> nd = new HashMap<String, String>();
    			
    			String n = name.substring(0,1).toUpperCase();
    			String ame = name.substring(1);
    			nd.put(Keys.ATTRIBUTE_NAME.toString(), n + ame);
    			nd.put(Keys.ATTRIBUTE_ID.toString(), jObj.getString("__id__"));
    			nd.put(Keys.ATTRIBUTE_DATATYPE.toString(), jObj.getString("dataType"));
    			s.add(nd);
    		}catch (JSONException e) {
    			e.printStackTrace();
			}
		}
        return s;
	}
	
	public HashMap<String, String> getConnectionTargetTable(){
		HashMap<String, String> hm = new HashMap<String, String>();
    	for (JSONObject jObj : jsonPage) {
    		try {
    			hm.put(jObj.getString("__id__"), jObj.getString("targetAccessPatternId"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
        return hm;
	}

	public String getTitleFromTuple() {
		JSONObject tuple = jsonPage.get(0);
		JSONArray keys = tuple.names();
		for (int i = 0; i < keys.length(); i++) {
			
        	try {
        		String key = keys.getString(i);
        		String s = tuple.getString(key);
    			if (key.toLowerCase().contains("title")){
    				return s;
    			}
			} catch (JSONException ee) {
				ee.printStackTrace();
			}
		}
		for (int i = 0; i < keys.length(); i++) {
			
        	try {
        		String key = keys.getString(i);
        		String s = tuple.getString(key);
    			if (key.toLowerCase().contains("name")){
    				return s;
    			}
			} catch (JSONException ee) {
				ee.printStackTrace();
			}
		}
		for (int i = 0; i < keys.length(); i++) {
			
        	try {
        		String key = keys.getString(i);
        		String s = tuple.getString(key);
    			if (!s.contains("}}") && !s.equals("null") 
    					&& !key.equals("tupleId") 
    					&& !key.equals("tupleScore")){
    				return s;
    			}
			} catch (JSONException ee) {
				ee.printStackTrace();
			}
		}
		
		try {
			String key = keys.getString(0);
			return tuple.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
}
