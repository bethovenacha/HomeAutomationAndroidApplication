package example.hfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import example.hfa.HomePage.singleRow;



import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class LocationMenu extends Activity {
	
	String reg_id = "";

	TextView tvname;
	singleRow temp;
	String home="";
	JSONObject json;
	JSONArray locAr;
	 
	 String ipAddress ="http://5.175.134.242";
	//String ipAddress ="http://192.168.1.1";//"http://192.168.0.101"; // "http://192.168.56.1";////This contains the ip address
	 MyWebControl webcontrol = new MyWebControl();//This is the instantiation of MyWebcontrol class which controls data from the web
	 String Uri=ipAddress + "/php/AndroidVersion1/controller.php";//This is the address where data is passed and controlled
	//String location[];
	ListView locationListView;
	ArrayList <singleRow> myObjectList ;
	public static final String THE_HOME="example.hfa.HOME";
	public static final String THE_LOC="example.hfa.LOC";
	public static final String REG_ID="example.hfa.REG_ID";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_menu);
		
		
		Intent i= getIntent();
		home = i.getStringExtra(HomePage.SELECTED_HOME);
		reg_id = i.getStringExtra(HomePage.REG_ID);
		
		locationListView = (ListView) findViewById(R.id.lvLocations);
		
		try {
			//createDialog("Home:" + home,"Registration id:" + reg_id);
			json = new JSONObject();
			json = webcontrol.getLocation(home, Uri,reg_id);
			locAr= json.getJSONArray("locFromHome");
			
			locationListView.setAdapter(new MyAdapter(LocationMenu.this));
			locationListener();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void locationListener(){
		locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			
				
				try {
					Intent i= new Intent(LocationMenu.this,FacilityGrid.class);
					i.putExtra(THE_HOME, home);
					i.putExtra(THE_LOC, locAr.get(arg2).toString());
					i.putExtra(REG_ID, reg_id);
					startActivity(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		});
	}
	public void createDialog(String title,String content){
		//This function displays the title and content from desired output
		Dialog d= new Dialog(LocationMenu.this);
		d.setTitle(title);
		TextView tv= new TextView(LocationMenu.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}
	
	class singleRow extends ListActivity{	
		String name;
		singleRow(String name){
			this.name = name;
		}	
	}
	class MyAdapter extends  BaseAdapter{
		Context context;	
		MyAdapter(Context c) throws ClientProtocolException, JSONException, IOException{
			context = c;		
			myObjectList = new ArrayList<singleRow>();	
			for(int k=0;k<locAr.length();k++){
				myObjectList.add(new singleRow(locAr.get(k).toString()));	
			}		
		}
		
		@Override
		public int getCount() {
			return myObjectList.size();//returns the size of the list
		}
		@Override
		public Object getItem(int index) {
			return myObjectList.get(index);//gets the object based on index
		}
		@Override
		public long getItemId(int i) {
			return i;//gets the item
		}
		@Override
		public View getView(final int index, View view, ViewGroup group) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.location_row_model,group, false);
			
			tvname = (TextView) row.findViewById(R.id.tvLocation);	
			temp = myObjectList.get(index);
			tvname.setText(temp.name);
				
			return row;
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_location_menu, menu);
		return true;
	}

}
