package example.hfa;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddSchedule extends Activity {
	String reg_id="";
	String home_name="";
	String location_name = "";
	public static final String REG_ID="example.hfa.REG_ID";
	JSONObject json;
	EditText fname,fromDate,toDate;
	TimePicker startTime,endTime;
	Button add,edit,delete;
	 String ipAddress ="http://5.175.134.242";
	 //String ipAddress ="http://192.168.56.1";//"http://192.168.0.101";// "http://192.168.56.1";//" //This contains the ip address
	 MyWebControl webcontrol = new MyWebControl();//This is the instantiation of MyWebcontrol class which controls data from the web
	 String Uri=ipAddress + "/php/AndroidVersion1/controller.php";//This is the address where data is passed and controlled
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);
		
		Intent i = getIntent();
		
		reg_id = i.getStringExtra(HomePage.REG_ID);
		home_name = i.getStringExtra(HomePage.MYHOME);
		location_name = i.getStringExtra(HomePage.MYLOCATION);
		
		
		fname = (EditText) findViewById(R.id.editText1);
		fromDate = (EditText) findViewById(R.id.editText2);
		toDate = (EditText) findViewById(R.id.EditText01);
		startTime =  (TimePicker) findViewById(R.id.timePicker1);
		endTime = (TimePicker) findViewById(R.id.timePicker2);
		
		add =(Button) findViewById(R.id.button1);
		edit = (Button) findViewById(R.id.Button01);
		delete = (Button) findViewById(R.id.Button02);
		
		addListen();
		editListen();
		
		fname.setText(i.getStringExtra(HomePage.SCHEDULE_FNAME));
		fromDate.setText(i.getStringExtra(HomePage.SCHEDULE_FROMDATE));
		toDate.setText(i.getStringExtra(HomePage.SCHEDULE_TODATE));
		
		 int shour = Integer.parseInt(i.getStringExtra(HomePage.SCHEDULE_SHOUR));
		 int smin = Integer.parseInt(i.getStringExtra(HomePage.SCHEDULE_SMIN));
		 
		 int ehour = Integer.parseInt(i.getStringExtra(HomePage.SCHEDULE_EHOUR));
		 int emin = Integer.parseInt(i.getStringExtra(HomePage.SCHEDULE_EMIN));
		 
		startTime.setCurrentHour(shour);
		startTime.setCurrentMinute(smin);
		
		endTime.setCurrentHour(ehour);
		endTime.setCurrentMinute(emin);
						
	}
		
	private void editListen(){
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(fname.equals("") || fromDate.length()==0 || toDate.length()==0){
					createDialog("Operation failed.","Please check the fields.");	
				}else{
					String startAmpm = checkampm(startTime.getCurrentHour());
					String startHour = startTime.getCurrentHour().toString();
					String startMin = startTime.getCurrentMinute().toString();
					
					String endAmpm = checkampm(endTime.getCurrentHour());
					String endHour = endTime.getCurrentHour().toString();
					String endMin= endTime.getCurrentMinute().toString();
					json=new JSONObject();
					
					try 
					{
						
						json = webcontrol.addSchedule("compareSched",Uri,fname.getText().toString(), 
														fromDate.getText().toString()
														,toDate.getText().toString(),startHour,
														startMin,startAmpm,endHour,endMin,endAmpm,reg_id,
														home_name,location_name
													 );
												
							if(json.getString("sched_stat").equals("conflict")){
								showAlertConflict(
										json.getString("fn").toString(),
										json.getString("fd").toString(),
										json.getString("td").toString(),
										json.getString("sh").toString(),
										json.getString("sm").toString(),
										json.getString("sampm").toString(),
										json.getString("eh").toString(),
										json.getString("em").toString(),
										json.getString("eampm").toString()
										
										);
							}else{
								 showAlertNoConflict(
										 fname.getText().toString(), 
											fromDate.getText().toString()
											,toDate.getText().toString(),startHour,
											startMin,startAmpm,endHour,endMin,endAmpm
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
						
			}
		});
	}
	
	private void addListen(){
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(fname.equals("") || fromDate.length()==0 || toDate.length()==0){
					createDialog("Operation failed.","Please check the fields.");	
				}else{
					String startAmpm = checkampm(startTime.getCurrentHour());
					String startHour = startTime.getCurrentHour().toString();
					String startMin = startTime.getCurrentMinute().toString();
					
					String endAmpm = checkampm(endTime.getCurrentHour());
					String endHour = endTime.getCurrentHour().toString();
					String endMin= endTime.getCurrentMinute().toString();
					json=new JSONObject();
					
					try 
					{
						
						json = webcontrol.addSchedule("compareSched",Uri,fname.getText().toString(), 
														fromDate.getText().toString()
														,toDate.getText().toString(),startHour,
														startMin,startAmpm,endHour,endMin,endAmpm,reg_id,
														home_name,location_name
													 );
												
							if(json.getString("sched_stat").equals("conflict")){
								showAlertConflict(
										json.getString("fn").toString(),
										json.getString("fd").toString(),
										json.getString("td").toString(),
										json.getString("sh").toString(),
										json.getString("sm").toString(),
										json.getString("sampm").toString(),
										json.getString("eh").toString(),
										json.getString("em").toString(),
										json.getString("eampm").toString()
										
										);
							}else{
								 showAlertNoConflict(
										 fname.getText().toString(), 
											fromDate.getText().toString()
											,toDate.getText().toString(),startHour,
											startMin,startAmpm,endHour,endMin,endAmpm
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
						
			}
		});
	}
	
	public void showAlertConflict(
			final String fn,
			String fd,
			String td,
			String sh,
			String sm,
			String sampm,
			String eh,
			String em,
			String eampm
			){
		
		new AlertDialog.Builder(this) 
		.setTitle("Operation") 
		.setMessage("There is a conflict in schedule.\n Do you wish to override?\n" 
			+ "\nFacility Name: " +	fn + "\n"
			+ "Date:\n" + fd + " -> " + td
			+ "\nTime:\n" + sh + ":" + sm + sampm + " -> " + eh + ":" + em + eampm
			
				) 
		.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(AddSchedule.this,"Operation Aborted." , Toast.LENGTH_SHORT).show(); 
			}
		})
		.setPositiveButton("Override", new DialogInterface.OnClickListener() { 
		
		public void onClick(DialogInterface dlg, int sumthin) { 
			String startAmpm = checkampm(startTime.getCurrentHour());
			String startHour = startTime.getCurrentHour().toString();
			String startMin = startTime.getCurrentMinute().toString();
			
			String endAmpm = checkampm(endTime.getCurrentHour());
			String endHour = endTime.getCurrentHour().toString();
			String endMin= endTime.getCurrentMinute().toString();
			json= null;
			try {
				json = webcontrol.updSchedule(
						"updateSchedule",Uri,fn, 
						fromDate.getText().toString()
						,toDate.getText().toString(),startHour,
						startMin,startAmpm,endHour,endMin,endAmpm
					
						);
				if(json.getString("update_stat").equals("successful")){	
					Toast.makeText(AddSchedule.this, json.getString("update_stat").toString(), Toast.LENGTH_SHORT).show(); 
				}else{
					Toast.makeText(AddSchedule.this, "Update failed, please try again.", Toast.LENGTH_SHORT).show(); 
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
		}) 
		.show(); 
		
		
	}
public void showAlertNoConflict(
		final String fn,
		final String fd,
		final String td,
		final String sh,
		final String sm,
		final String sampm,
		final String eh,
		final String em,
		final String eampm
		){
		
		new AlertDialog.Builder(this) 
		.setTitle("Operation") 
		.setMessage("No conflict in schedule. Are you sure you want to proceed?") 
		.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(AddSchedule.this,"Operation Aborted." , Toast.LENGTH_SHORT).show(); 
			}
		})
		.setPositiveButton("Proceed", new DialogInterface.OnClickListener() { 
		
		public void onClick(DialogInterface dlg, int sumthin) { 
			
			json= null;
			try {
				json = webcontrol.realAddSchedule(
						"addSchedule",
						Uri,
						 fn,
						 fd,
						 td,
						 sh,
						 sm,
						 sampm,
						 eh,
						 em,
						 eampm
						);
				
				if(json.getString("real_insertion").equals("successful")){
					Toast.makeText(AddSchedule.this, "Schedule Inserted", Toast.LENGTH_SHORT).show(); 
					Intent i= new Intent(AddSchedule.this,HomePage.class);
					startActivity(i);
				}else{
					Toast.makeText(AddSchedule.this, "Insertion failed.", Toast.LENGTH_SHORT).show(); 
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
		}) 
		.show(); 
		
		
	}
	
	private String checkampm(int hour){
		String ampm="";
		  if (hour == 0) {
		         hour += 12;
		         ampm = "am";
		      } else if (hour == 12) {
		    	  ampm = "pm";
		      } else if (hour > 12) {
		         hour -= 12;
		         ampm = "pm";
		      } else {
		    	  ampm = "am";
		      }
		  return ampm;
	}
	public void createDialog(String title,String content){
		//This function displays the title and content from desired output
		Dialog d= new Dialog(AddSchedule.this);
		d.setTitle(title);
		TextView tv= new TextView(AddSchedule.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_schedule, menu);
		return true;
	}

}
