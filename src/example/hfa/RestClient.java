package example.hfa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

public class RestClient {
	static String url;
	static JSONObject json;//This passes and receive javascript object notation from and to android
	static JSONArray jsonArray;
	static HttpParams httpParams;//Sets up the http parameter such as connection time out
	static HttpClient client; //Variable that represents the client
	static HttpPost request;//Variable that represents the request
	static HttpResponse response;//This is the data that contains the response of the php script
	static HttpEntity entity; 
	static InputStream instream;
	static BufferedReader bufferedReader;
	// This function converts any data extracted from the request into a string
	public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	   
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	

	public static JSONObject getJSON(int TimeOut,String myurl, JSONObject j, String JSONheader) throws ClientProtocolException, IOException, JSONException, InterruptedException, ExecutionException{
			
		json = new JSONObject();
		json.put("timeOut", TimeOut + "");
		json.put("url", myurl);
		json.put("jasonObject", j.toString());
		json.put("jasonHeader",JSONheader );
		
		
		JSONObject jReceiver = new JSONObject();
		
		myTask mt = new myTask();
		jReceiver = mt.execute(json).get();
		
		return jReceiver;	
	}
	
	
}
class myTask extends AsyncTask<JSONObject, Integer, JSONObject>{
	
	static String url;
	static JSONObject json;//This passes and receive javascript object notation from and to android
	static JSONArray jsonArray;
	static HttpParams httpParams;//Sets up the http parameter such as connection time out
	static HttpClient client; //Variable that represents the client
	static HttpPost request;//Variable that represents the request
	static HttpResponse response;//This is the data that contains the response of the php script
	static HttpEntity entity; 
	static InputStream instream;
	static BufferedReader bufferedReader;
	@Override
	protected JSONObject doInBackground(JSONObject... params) {
		
		json = new JSONObject();
		try {
			String myurl = params[0].getString("url");
			int timeOut = Integer.parseInt(params[0].getString("timeOut"));
			String jsonHeader = params[0].getString("jasonHeader");
			String jObject = params[0].getString("jasonObject");
				
				ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,timeOut);// sets connection time out
				client = new DefaultHttpClient(httpParams); //represents the client and contains connection properties					
				url = myurl;		 			 
				request = new HttpPost(url);	//This is the request of the client	 				
				
				request.setEntity(new ByteArrayEntity(jObject.getBytes("UTF8")));
				
				request.setHeader(jsonHeader, jsonHeader);//sets the header of the data as json					
				response = client.execute(request); //sends the request of the client and tries to get a response							
				entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				int status = response.getStatusLine().getStatusCode();
				
				if(status == 200){
					if (entity != null) {
						instream = entity.getContent();			
						String res = RestClient.convertStreamToString(instream);
						json = new JSONObject(res);
									
					}else{
						json = null;
					}			
				}
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//sets the request as byte array
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		
		 ProgressDialog pd = new ProgressDialog(null);
		 
         pd.setMessage("loading");
         pd.show();
	}

	
	
		
}

