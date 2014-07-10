package it.polimi.secomobile.net;

import it.polimi.secomobile.data.Keys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.os.Bundle;
import android.util.Log;

public class RestClient {

	private HttpClient httpclient;
	
	private String baseRegistryURL = "http://testing2.seco:8090/mart/registry/";
	private String baseEngineURL = "http://testing1.seco:8084/engine/";
		
	private HttpGet httpGetMarts; 
	private HttpGet httpGetAccessPatterns; 
	private HttpGet httpGetInterfaces;
	private HttpGet httpGetConnectionPatterns;
	
	private HttpPost httpPostSession;
	private HttpPost httpPostExecution;
	private HttpPost httpPostMore;
	
	private String sessionID;
	private String executionID;

	
	public RestClient() {
		// Http client initialization
		httpclient = new DefaultHttpClient();

		// request objects initialization
		httpGetMarts = new HttpGet(baseRegistryURL + "marts");
		httpGetAccessPatterns = new HttpGet(baseRegistryURL + "access-patterns");
		httpGetInterfaces = new HttpGet(baseRegistryURL + "interfaces");
		
		httpGetConnectionPatterns = new HttpGet(baseRegistryURL + "connection-patterns");
		
		httpPostSession = new HttpPost(baseEngineURL + "sessions");
	}
	
	public ArrayList<JSONObject> getMartsList(){
		return getJSONList(httpGetMarts);
	}
	
	public ArrayList<JSONObject> getAccessPatternList(){
		return getJSONList(httpGetAccessPatterns);
	}
	
	public ArrayList<JSONObject> getInterfaceList(){
		return getJSONList(httpGetInterfaces);
	}
	
	public ArrayList<JSONObject> getMartList(List<String> idlist){
		return getJSONListById(httpGetMarts, idlist);
	}
	
	public ArrayList<JSONObject> getAccessPatternList(List<String> idlist){
		return getJSONListById(httpGetAccessPatterns, idlist);
	}
	
	public ArrayList<String> getAccessPatternIdList(List<String> idlist){
		return getAPIdListByMartId(httpGetAccessPatterns, idlist);
	}
	
	public ArrayList<JSONObject> getInterfaceList(List<String> idlist){
		return getJSONListById(httpGetInterfaces, idlist);
	}
	
	public ArrayList<String> getInterfaceIdList(List<String> idlist){
		return getInterfaceIdListByAPId(httpGetInterfaces, idlist);
	}
	
	public ArrayList<JSONObject> getConnectionPatternList(List<String> idlist){
		return getJSONListById(httpGetConnectionPatterns, idlist);
	}
	
	public ArrayList<String> getConnectionPatternIdList(List<String> idlist){
		return getConnectionIdListByAPId(httpGetConnectionPatterns, idlist);
	}
	
	public Boolean createEngineSession(){
		HttpResponse response;
		try {
			response = httpclient.execute(httpPostSession);
			Log.i("Result",response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {

				InputStream instream = entity.getContent();
	            InputStreamReader ir = new InputStreamReader(instream);
	            BufferedReader br = new BufferedReader(ir, 8*1024);
	            
	            String inputLine;
	            StringBuilder sb = new StringBuilder();
	            while((inputLine = br.readLine()) != null)
	            	sb.append(inputLine);
	            br.close();   
	            
				entity.consumeContent();
				instream.close();
				sessionID = sb.toString();
				Log.i("Result", sb.toString());
			}
			
			return true;
		} catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		return false;
	}
	
	public Boolean createEngineExecution(JSONObject task){
		
		httpPostExecution = new HttpPost(baseEngineURL + "sessions/" + sessionID + "/executions");
		
		StringEntity se;
		try {
			se = new StringEntity(task.toString());
			httpPostExecution.setEntity(se);
			httpPostExecution.setHeader("Accept", "application/json");
			httpPostExecution.setHeader("Content-type", "application/json");


			HttpResponse response;
			response = httpclient.execute(httpPostExecution);
			Log.i("Result",response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {

				InputStream instream = entity.getContent();
	            InputStreamReader ir = new InputStreamReader(instream);
	            BufferedReader br = new BufferedReader(ir, 8*1024);
	            
	            String inputLine;
	            StringBuilder sb = new StringBuilder();
	            while((inputLine = br.readLine()) != null)
	            	sb.append(inputLine);
	            br.close();   
	            
				entity.consumeContent();
				instream.close();
				sb.deleteCharAt(0);
				sb.deleteCharAt(sb.length()-1);
				executionID = sb.toString();
				Log.i("Result", sb.toString());
			}
			
			return true;
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();} 
		catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		return false;
	}
	
	public String sendEngineMoreRequest(JSONObject costraints){
		
		httpPostMore = new HttpPost(baseEngineURL + "sessions/" + sessionID +
														"/executions/" + executionID + 
														"/more");
		
		StringEntity se;
		try {
			se = new StringEntity(costraints.toString());
			httpPostMore.setEntity(se);
			httpPostMore.setHeader("Accept", "application/json");
			httpPostMore.setHeader("Content-type", "application/json");


			HttpResponse response;
			response = httpclient.execute(httpPostMore);
			Log.i("Result",response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {

				InputStream instream = entity.getContent();
	            InputStreamReader ir = new InputStreamReader(instream);
	            BufferedReader br = new BufferedReader(ir, 8*1024);
	            
	            String inputLine;
	            StringBuilder sb = new StringBuilder();
	            while((inputLine = br.readLine()) != null)
	            	sb.append(inputLine + "\n");
	            br.close();   
	            
				entity.consumeContent();
				instream.close();
				Log.i("Result", sb.toString());
				return sb.toString();
			}
			
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();} 
		catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		return new String();
	}
	
	public ArrayList<JSONObject> getQueryAttributeList(Bundle b) {
		String apId = b.getString(Keys.ACCESS_PATTERN_ID.toString());
		
		HttpGet hg = new HttpGet(baseRegistryURL + "access-patterns" + "/" + apId);
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		try{
			String result = getJSONpage(hg);
			
			JSONObject json = new JSONObject(result);
			JSONArray attributes = json.getJSONArray("inputAttributes");
			for (int a = 0; a < attributes.length(); a++) {
				JSONObject item = attributes.getJSONObject(a);
				objList.add(item);
			}
		} catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (JSONException e) {e.printStackTrace();}
		
		return objList;
	}

	public ArrayList<HashMap<String, String>> getConnectionQueryAttributeList(ArrayList<HashMap<String, String>> attributes, String conn, String tuple) {
		
		try{
			JSONObject JSONTuple = new JSONObject(tuple);
			ArrayList<String> idlist = new ArrayList<String>(); idlist.add(conn);
			
			JSONObject cp = getConnectionPatternList(idlist).get(0);
			
			idlist = new ArrayList<String>(); idlist.add(cp.getString("sourceAccessPatternId"));
			
			JSONObject apSource = getAccessPatternList(idlist).get(0);
			JSONArray apSourceAtts = apSource.getJSONArray("outputSignature");
			JSONArray costraints = cp.getJSONArray("constraints");
			
			Log.i("CONNECTION", "costraints: "+costraints.toString());
			
			for (HashMap<String, String> att : attributes) {
				Log.i("CONNECTION", "cicle: "+costraints.toString());
				for (int a = 0; a < costraints.length(); a++) {
					JSONObject item = costraints.getJSONObject(a);
					String sourceAttId = item.getString("sourceAttributeId");
					String targetAttId = item.getString("targetAttributeId");
					Log.i("CONNECTIONCICLE2", targetAttId + " == " + att.get(Keys.ATTRIBUTE_ID.toString()));
					Log.i("CONNECTIONCICLE2", sourceAttId);
					if (targetAttId.equals(att.get(Keys.ATTRIBUTE_ID.toString()))){
						for (int i = 0; i < apSourceAtts.length(); i++) {
							JSONObject sourceAtt = apSourceAtts.getJSONObject(i);
							if(sourceAtt.getString("__id__").equals(sourceAttId)){
								try{
									att.put(Keys.ATTRIBUTE_VALUE.toString(), JSONTuple.getString(sourceAtt.getString("name")));
									Log.i("CONNECTIONCICLE2", JSONTuple.getString(sourceAtt.getString("name")));
								}catch (Exception e) {
									att.put(Keys.ATTRIBUTE_VALUE.toString(), JSONTuple.getString((sourceAtt.getString("name")).replace("m_", "")));
									Log.i("CONNECTIONCICLE2", JSONTuple.getString((sourceAtt.getString("name")).replace("m_", "")));
								}
							}
						}
					}
				}
			}
			
		} catch (JSONException e) {e.printStackTrace();}
		
		return attributes;
	}
	
	public HashMap<String, String> getSourceIdTableFromAPList(HashMap<String, String> targets) {
		
		HashMap<String, String> sources = new HashMap<String, String>();
		
		ArrayList<String> idLt = new ArrayList<String>();
		for (String key : targets.keySet()) {
			idLt.add(targets.get(key));
		}
		
		ArrayList<JSONObject> list = getAccessPatternList(idLt);
		
		for (JSONObject jsonObject : list) {
			try {
				sources.put(jsonObject.getString("__id__"), jsonObject.getString("serviceMartId"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return sources;
	}
	
	private String getJSONpage(HttpGet getRequest) throws IllegalStateException, IOException{
		
		HttpResponse response;
		response = httpclient.execute(getRequest);
		
		Log.i("Result",response.getStatusLine().toString());

		HttpEntity entity = response.getEntity();

		if (entity != null) {

			InputStream instream = entity.getContent();
            InputStreamReader ir = new InputStreamReader(instream);
            BufferedReader br = new BufferedReader(ir, 8*1024);
            
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while((inputLine = br.readLine()) != null)
            	sb.append(inputLine + "\n");
            br.close();   
            
			entity.consumeContent();
			instream.close();
			
            return sb.toString();

		}
		return new String();
	}
	
	private String resolveReferences(String jsonString) {
		if (jsonString.contains("$ref")){
			try {
				JSONObject json = new JSONObject(jsonString);
				HashMap<String, JSONObject> attTable = new HashMap<String, JSONObject>();
				JSONArray items = json.getJSONArray("items");
				for (int a = 0; a < items.length(); a++) {
					JSONObject item = items.getJSONObject(a);
					JSONArray atts = item.getJSONArray("inputAttributes");
					for (int i = 0; i < atts.length(); i++) {
						try {
							JSONObject att = atts.getJSONObject(i);
							String id = att.getString("__id__");
							attTable.put(id, att);
						} catch (JSONException ei) {
							JSONObject att = atts.getJSONObject(i);
							String idref = att.getString("$ref");
							jsonString.replace("{\n      \"ref\" : \"" + idref + "\"\n    }", attTable.get(idref).toString());
							Log.i("STAMP", attTable.get(idref).toString());
						}
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return jsonString;
	}

	private ArrayList<JSONObject> getJSONList(HttpGet getRequest)
	{
		
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		try {
	            String result = getJSONpage(getRequest);
				
				JSONObject json = new JSONObject(result);

				JSONArray items = json.getJSONArray("items");
				for (int a = 0; a < items.length(); a++) {
					JSONObject item = items.getJSONObject(a);
					objList.add(item);
				}
				return objList;
		} catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (JSONException e) {e.printStackTrace();}
		
		return objList;
	}
	
	private ArrayList<JSONObject> getJSONListById(HttpGet getRequest, List<String> idlist)
	{
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		for (String id : idlist) {
			try{
				String result = getJSONpage(new HttpGet(getRequest.getURI() + "/" + id));
				
				JSONObject json = new JSONObject(result);
				objList.add(json);
			} catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (JSONException e) {e.printStackTrace();}
		}
		return objList;
	}
	
	private ArrayList<JSONObject> getAPJSONListByMartId(HttpGet getRequest, List<String> idlist)
	{
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		for (String id : idlist) {
			try {
				String result = getJSONpage(getRequest);
				
				JSONObject json = new JSONObject(result);

			
				JSONArray items = json.getJSONArray("items");
				JSONObject item;
				for (int a = 0; a < items.length(); a++) {
					item = items.getJSONObject(a);
					if (item.getString("serviceMartId").equals(id)){
						objList.add(item);
					}
				}
				return objList;
			} catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (JSONException e) {e.printStackTrace();}
		}
		return objList;
	}
	
	private ArrayList<String> getAPIdListByMartId(HttpGet getRequest, List<String> idlist)
	{
		ArrayList<String> objList = new ArrayList<String>();
		for (String id : idlist) {
			try {
				String result = getJSONpage(getRequest);

				JSONObject json = new JSONObject(result);

				JSONArray items = json.getJSONArray("items");
				JSONObject item;
				for (int a = 0; a < items.length(); a++) {
					item = items.getJSONObject(a);
					if (item.getString("serviceMartId").equals(id)){
						objList.add(item.getString("__id__"));
					}
				}
			} catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (JSONException e) {e.printStackTrace();}
		}
		return objList;
	}

	private ArrayList<JSONObject> getInterfaceJSONListByAPId(HttpGet getRequest, List<String> idlist) {
		ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
		for (String id : idlist) {
			try {
				String result = getJSONpage(getRequest);
				
				JSONObject json = new JSONObject(result);

				JSONArray items = json.getJSONArray("items");
				JSONObject item;
				for (int a = 0; a < items.length(); a++) {
					item = items.getJSONObject(a);
					if (item.getString("serviceMartId").equals(id)){
						objList.add(item);
					}
				}
				return objList;
			} catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (JSONException e) {e.printStackTrace();}
		}
		return objList;
	}
	
	private ArrayList<String> getInterfaceIdListByAPId(HttpGet getRequest, List<String> idlist)
	{
		ArrayList<String> objList = new ArrayList<String>();
		for (String id : idlist) {
			try {
				String result = getJSONpage(getRequest);
				
				JSONObject json = new JSONObject(result);

				JSONArray items = json.getJSONArray("items");
				JSONObject item;
				for (int a = 0; a < items.length(); a++) {
					item = items.getJSONObject(a);
					if (item.getString("accessPatternId").equals(id)){
						objList.add(item.getString("__id__"));
					}
				}
			} catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (JSONException e) {e.printStackTrace();}
		}
		return objList;
	}


	private ArrayList<String> getConnectionIdListByAPId(HttpGet getRequest, List<String> idlist) {
		ArrayList<String> objList = new ArrayList<String>();
		for (String id : idlist) {
			try {
				String result = getJSONpage(getRequest);
				
				JSONObject json = new JSONObject(result);

				JSONArray items = json.getJSONArray("items");
				JSONObject item;
				for (int a = 0; a < items.length(); a++) {
					item = items.getJSONObject(a);
					if (item.getString("sourceAccessPatternId").equals(id)){
						objList.add(item.getString("__id__"));
					}
				}
			} catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (JSONException e) {e.printStackTrace();}
		}
		return objList;
	}


	

	
}
