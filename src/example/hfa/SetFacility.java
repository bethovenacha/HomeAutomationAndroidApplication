package example.hfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SetFacility extends Activity {
	//Button btnUploadOn,btnUploadOff;
	public static final String KEY_URI="example.hfa.URI";
	public static final String KEY_PATH="example.hfa.PATH";
	public static final String KEY_FILENAME = "example.hfa.FILENAME";
	
	EditText etfn;
	JSONObject json;
	String FacilityName="";
	String id="";
	 String ipAddress ="http://5.175.134.242";
	//String ipAddress ="http://192.168.1.1";
	// String ipAddress = "http://192.168.56.1";//"http://192.168.0.101"; //"http://192.168.56.1";//"http://192.168.0.101"; //This contains the ip address
	 MyWebControl webcontrol;//This is the instantiation of MyWebcontrol class which controls data from the web
	 String Uri=ipAddress + "/php/AndroidVersion1/controller.php";//This is the address where data is passed and controlled
	// String type[];
	 Spinner spnType, spnLine,spnStatus, spnLocation;
	 Button btnSet;
	 String reg_id="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_facility);
		
		btnSet = (Button) findViewById(R.id.btnSetFacility);
		spnType = (Spinner) findViewById(R.id.spType);
		spnStatus = (Spinner) findViewById(R.id.spStatus);
		spnLine = (Spinner) findViewById(R.id.Spinner03);
		spnLocation = (Spinner) findViewById(R.id.spLocation);
		
		//btnUploadOn = (Button) findViewById(R.id.btnUploadOnImage);	
		//btnUploadOff = (Button) findViewById(R.id.btnUploadOffImage);	
		//uploadImageOn();
		//uploadImageOff();
		webcontrol = new MyWebControl();
		
		Intent i= getIntent();
		FacilityName = i.getStringExtra(HomePage.FACILITY_NAME);
		id = i.getStringExtra(HomePage.FACILITY_ID);//facility line
		reg_id = i.getStringExtra(HomePage.REG_ID);
		
		etfn = (EditText) findViewById(R.id.etFacilityName);
		etfn.setText(FacilityName);
		
		try {
			json = webcontrol.getFacility(Uri, "getFacilityType", id);
			JSONArray typeArray= json.getJSONArray("type");
				List<String> typeList = new ArrayList<String>();
				for(int j=0;j<typeArray.length();j++){		
					//Add type from homepageActivity
					typeList.add(typeArray.get(j).toString());
				}
				// Creating adapter for spinner
				ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, typeList);
				// Drop down layout style - list view with radio button
				typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// attaching data adapter to spinner
				spnType.setAdapter(typeAdapter);
				////////////////////////////////////////////////////////////////////
			json =null;
			json = webcontrol.getFacility(Uri, "getFacilityStatus", id);
			JSONArray statusArray= json.getJSONArray("status");
				List<String> statusList = new ArrayList<String>();
				for(int k=0;k<statusArray.length();k++){		
					//Add status from homepageActivity
					statusList.add(statusArray.get(k).toString());
				}
				ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, statusList);
				statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnStatus.setAdapter(statusAdapter);
				///////////////////////////////////////////////////////////////////////
		
				json =null;
				
				
			json = webcontrol.getFacility(Uri, "getFacilityLine", id);
			JSONArray lineArray= json.getJSONArray("line");
				List<String> lineList = new ArrayList<String>();
				for(int l=0;l<lineArray.length();l++){		
					//Add line from homepageActivity
					lineList.add(lineArray.get(l).toString());
				}
				ArrayAdapter<String> lineAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, lineList);
				lineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnLine.setAdapter(lineAdapter);
				///////////////////////////////////////////////////////////////////////
			json =null;
			json = webcontrol.getFacility(Uri, "getFacilityLocation", id);
			JSONArray locationArray= json.getJSONArray("location");
				List<String> locationList = new ArrayList<String>();
				for(int m=0;m<locationArray.length();m++){		
					//Add location from homepageActivity
					locationList.add(locationArray.get(m).toString());
				}
				ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, locationList);
				locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnLocation.setAdapter(locationAdapter);
				///////////////////////////////////////////////////////////////////////
		
				btnSetListener();	
			//
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
	public void createDialog(String title,String content){
		//This function displays the title and content from desired output
		Dialog d= new Dialog(SetFacility.this);
		d.setTitle(title);
	
		TextView tv= new TextView(SetFacility.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}
	private void btnSetListener(){
		
		btnSet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String type = spnType.getSelectedItem().toString();
				String line = spnLine.getSelectedItem().toString();
				String location = spnLocation.getSelectedItem().toString();
				String status = spnStatus.getSelectedItem().toString();
				try {
					json =null;
				
					//id is facility line
					//etfn is facility name
					//reg_id is the clients id
					json = webcontrol.checkFacility("checkFacility", Uri, id, line, type, status, location,etfn.getText().toString(),reg_id);
							
					if(json.getString("updateMessage").equals("Facility Updated.")){
						Intent i= new Intent(SetFacility.this,HomePage.class);
						i.putExtra(MainActivity.REG_ID,reg_id );
						startActivity(i);
						createDialog("Operation Status",json.getString("updateMessage"));
						
					}else{
						
						showAlert(
								json.getString("statId"),
								json.getString("typeID"),
								json.getString("locID"),
								json.getString("facID"),//This is the id of the facility to be overriden
								json.getString("facLine")
								);
						
					}
				
					
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
		});
		
	}
	
	public void showAlert( String statId,  String typeId,  String locId, String facId, String facLine){
		final String statID= statId;
		final String typeID= typeId;
		final String locID = locId;
		final String facID = facId;
		final String facLINE = facLine;
		
		
		
		new AlertDialog.Builder(this) 
		.setTitle("Operation") 
		.setMessage("You selected a used line.Do you wish to proceed") 
		.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(SetFacility.this,"Operation Aborted." , Toast.LENGTH_SHORT).show(); 
			}
		})
		.setPositiveButton("Proceed", new DialogInterface.OnClickListener() { 
		
		public void onClick(DialogInterface dlg, int sumthin) { 
			
			json= null;
			try {
				json = webcontrol.updateFacility("overideFacility", Uri,id, statID,typeID,locID,facID,facLINE,etfn.getText().toString());
				
				if(json.getString("settingFacility").equals("true")){
					Intent i= new Intent(SetFacility.this,HomePage.class);
					startActivity(i);
					createDialog(id.toString(),"StatusId:"+statID);
					//createDialog("Operation","Facility Updated.");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
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
			
			Toast.makeText(SetFacility.this, "Renamed", Toast.LENGTH_SHORT).show(); 
			
			
		} 
		}) 
		.show(); 
		
		
	}
	/*
	private void showCustomDialog(){
		FileChooserDialog dialog = new FileChooserDialog(SetFacility.this);
		 dialog.setFilter(".*jpg|.*png|.*gif|.*JPG|.*PNG|.*GIF|.*jpeg|.*JPEG");
		 dialog.setShowConfirmation(true, false);
		 dialog.show();
		 
		 dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {
	        
			@Override
			public void onFileSelected(Dialog source, File file) {
				
				String Uri=ipAddress + "/homeAutomationWeb/php/AndroidVersion1/upload.php"; //offline
				
				Intent i= new Intent (SetFacility.this,UploadToServer.class);
				i.putExtra(KEY_URI, Uri);
				i.putExtra(KEY_PATH, file.getPath());
				i.putExtra(KEY_FILENAME, file.getName());
				startActivity(i);
			}
			@Override
			public void onFileSelected(Dialog source, File folder, String name) {
				// TODO Auto-generated method stub
				Toast toast = Toast.makeText(source.getContext(), "File created: " + folder.getName() + "/" + name, Toast.LENGTH_LONG);
	             toast.show();
			}
	     });	
	}
	
	private void uploadImageOn(){
		btnUploadOn .setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				showCustomDialog();
				
			}
		});
	}
	private void uploadImageOff(){
		btnUploadOff.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showCustomDialog();
			}
		});
	}
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_set_facility, menu);
		return true;
	}

}
