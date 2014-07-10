package it.polimi.secomobile.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

public class Utils {

	public static boolean started = false;

	private static JSONObject semanticJSON = null;
	
	public static ArrayList<String> semanticOrder = 
		new ArrayList<String>() {{
		   add("string");
		   add("date");
		   add("time");
		   add("currency");
		   add("distance");
		   add("zip");
		   add("coordlong");
		   add("coordlat");
		   add("number");
		   add("url");
		   add("integer");
		   add("float");
		   add("boolean");
		}};
		
	public static HashMap<String, Integer> inputTypesMapping = 
		new HashMap<String, Integer>(){{
			put("STRING", InputType.TYPE_CLASS_TEXT);
			put("DECIMAL", InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
			put("INTEGER", InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);
			put("DATETIME", InputType.TYPE_CLASS_DATETIME);
			put("BOOLEAN", InputType.TYPE_CLASS_TEXT);
		}};
	
	public static String semanticMetadata;
	
	public static void setSemanticMetadata(String sMetadata){
		semanticMetadata = sMetadata;
	}
	
	public static JSONObject getSemanticJSON() {
		
		try {
			if (semanticJSON == null)
				semanticJSON = new JSONObject(semanticMetadata);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return semanticJSON;
	}
	
	public static JSONObject getSemanticJSON(String id) {
		
		JSONObject item = new JSONObject();
		try {
			Log.i("SERVICES", getSemanticJSON().toString());
			JSONArray services = getSemanticJSON().getJSONObject("data").getJSONArray("services");
			for (int a = 0; a < services.length(); a++) {
				if (services.getJSONObject(a).get("serviceID").equals(id))
					item = services.getJSONObject(a);
			}
			return item;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return item;
	}
	
	public static ArrayList<JSONObject> getOutputSignatureMetadata(String id) {
		
		JSONObject json = getSemanticJSON(id);
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		try {
			JSONArray outputSignature = json.getJSONArray("outputSignature");
			for (int a = 0; a < outputSignature.length(); a++) {
				JSONObject attribute = outputSignature.getJSONObject(a);
				objList.add(attribute);
			}
			return objList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objList;

	}
	

}
