package example.hfa;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyWebControl {
	int timeOut = 600000; // = 30 seconds
	
	String url;
	JSONObject json;//This passes and receive javascript object notation from and to android
	JSONArray jsonArray;
	int clientId =0;
	public JSONObject connect(String host, String conusername, String conpassword, String condatabase,String myurl) throws ClientProtocolException, IOException, InterruptedException, ExecutionException{
		
		try {				
			
			json = new JSONObject();				
			json.put("host", host);
			json.put("conusername", conusername);
			json.put("conpassword", conpassword);
			json.put("condatabase", condatabase);	
			json = RestClient.getJSON(timeOut,myurl, json, "json");
			//connectionStatus = json.getString("conStatus");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block			
			//Try to connect to mysqlite
			
			e.printStackTrace();		
			
		}
		return json;
	}	
	public JSONObject login(String username, String password,String myurl) throws ClientProtocolException, IOException, JSONException, InterruptedException, ExecutionException{
		json = new JSONObject();				
		json.put("username", username);
		json.put("password", password);	
		json = RestClient.getJSON(timeOut, myurl, json, "json");
		//connectionStatus = json.getString("conStatus");
		return json ;
	}
	public void setClientId(int id){
		this.clientId = id;
		
	}
	public JSONObject getFacility(String uri, String command, String ids) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		json = new JSONObject();	
		json.put("command", command);
		json.put("id", ids);
		json = RestClient.getJSON(timeOut, uri, json, "json");
		
		return json;
	}
	public JSONObject checkFacility(String command,String uri,String id,String line,String type,String status,String location,String name, String reg_id) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException{
		json = new JSONObject();	
		json.put("checkFacility", command);
		json.put("idFac", id);
		json.put("line", line);
		json.put("type", type);
		json.put("location", location);
		json.put("status", status);
		json.put("name", name);
		json.put("reg_id", reg_id);
		
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	public JSONObject updateFacility(String command, String uri, String id, String statID, String typeID, String locID, String facID, String facLINE,String Name) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		
		json = new JSONObject();	
		json.put("overrideFacility", command);
		json.put("idTobeOverriden", facID);
		json.put("lineOverride", facLINE);
		json.put("typeOverride", typeID);
		json.put("locationOverride", locID);
		json.put("statusOverride", statID);
		json.put("idOfNewFacility", id);
		json.put("newFacilityName", Name);
		
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	public JSONObject getLocation(String home,String uri, String reg_id) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		
		json = new JSONObject();
		json.put("Home", home);
		json.put("reg_id", reg_id);
		json = RestClient.getJSON(timeOut, uri, json, "json");
		
		return json;
		
	}
	public JSONObject getFacilit(String hom, String loc,String uri, String reg_id) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		json = new JSONObject();
		json.put("HomeGrid", hom);
		json.put("LocationGrid", loc);
		json.put("reg_id", reg_id);
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	public JSONObject changeBit(String fname,String uri) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		json.put("Facname", fname);
		
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	public JSONObject getSchedule(String uri, String command, String reg_id) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		json = new JSONObject();
		json.put("getSchedule", command);	
		json.put("id", reg_id);
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	
	public JSONObject addSchedule(String command, String uri, String fname, String fromDate,
			String toDate, String startHour, String startMin,
			String startAmpm, String endHour, String endMin, String endAmpm,String reg_id,
			String homeName,String locationName) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		json = new JSONObject();
		json.put("compareSched", command);
		json.put("fname",fname);		
		json.put("fromDate", fromDate);
		json.put("toDate",toDate);		
		json.put("startHour", startHour);
		json.put("startMin",startMin);
		json.put("startAmpm",startAmpm);		
		json.put("endHour",endHour);
		json.put("endMin", endMin);
		json.put("endAmpm",endAmpm);
		json.put("reg_id", reg_id);
		json.put("homeName", homeName);
		json.put("locationName", locationName);
		
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	public JSONObject updSchedule(String command, String uri, String fn,
			String fd, String td, String startHour, String startMin,
			String startAmpm, String endHour, String endMin, String endAmpm) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		json = new JSONObject();
		json.put("updSched", command);
		json.put("fn",fn);
		
		json.put("fd", fd);
		json.put("td",td);
		
		json.put("sh", startHour);
		json.put("sm",startMin);
		json.put("sampm",startAmpm);
		
		json.put("eh",endHour);
		json.put("em", endMin);
		json.put("eampm",endAmpm);
		
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	public JSONObject realAddSchedule(String command, String uri, String fn,
			String fd, String td, String sh, String sm, String sampm,
			String eh, String em, String eampm) throws JSONException, ClientProtocolException, IOException, InterruptedException, ExecutionException {
		
		json = new JSONObject();
		json.put("raddSched", command);
		json.put("rfname",fn);
		
		json.put("rfromDate", fd);
		json.put("rtoDate",td);
		
		json.put("rstartHour", sh);
		json.put("rstartMin",sm);
		json.put("rstartAmpm",sampm);
		
		json.put("rendHour",eh);
		json.put("rendMin", em);
		json.put("rendAmpm",eampm);
			
		json = RestClient.getJSON(timeOut, uri, json, "json");
		return json;
	}
	
}
