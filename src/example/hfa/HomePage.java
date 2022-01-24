package example.hfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class HomePage extends Activity  {
	Button btnVoice;
	JSONArray  distinctHome;
	GridView gridView; //This is a gridView control from homePage activity
	ArrayList<Item> gridArray = new ArrayList<Item>();//This is an array list which contains objects to be dispalyed on Grid View
	 CustomGridViewAdapter customGridAdapter;//This is an object of the class CustomGridViewAdapter
	 String ipAddress ="http://5.175.134.242";
	 MyWebControl webcontrol = new MyWebControl();//This is the instantiation of MyWebcontrol class which controls data from the web
	 String Uri=ipAddress + "/php/AndroidVersion1/controller.php";//This is the address where data is passed and controlled
	
	TextView tvname;//This is a control that contains the name of the facility
	ListView list,homeList;//This ListView of facilities
	ArrayList <singleRow> myObjectList;//This is an array list which contains data of the Grid View
	ArrayList <singleRowHome> myObjectList2;
	ArrayList<schedRow> myObjectList3;
	
	JSONObject json; //an object which would contain data being put into it
	TabHost tabhost; //This is the tab
	TabSpec tabspec;//This gets the specifications of the tab
	
	JSONArray name,description, ids;
	
	
	TextView tvdesc,listId,tvHomee ;//contains the home name of facilities
	

	
	JSONArray fn,sDate,eDate,sHour,eHour,sMin,eMin,sampm,eampm,schedfid,homeNAME,locationNAME;
	
	ListView scheduleListView;
	
	singleRow temp;//object of singleRow class
	singleRowHome temp2;
	schedRow temp3;
	
	byte[] images;	//receives longblob from mysql database
	
	public static final String FACILITY_NAME="example.hfa.FACILITYNAME";//variable to be passed on to SetFacility Activity
	public static final String FACILITY_LOCATION="example.hfa.FACILITYLOCATION";
	public static final String FACILITY_TYPE="example.hfa.FACILITTYPE";
	public static final String FACILITY_STATUS="example.hfa.FACILITYSTATUS";
	public static final String FACILITY_ID="example.hfa.FACILITYID";//This is the facility line
	
	public static final String SELECTED_HOME="example.hfa.SELECTEDHOME";
	
	public static final String SCHEDULE_FNAME="example.hfa.SCHEDULEFNAME";
	public static final String SCHEDULE_FROMDATE="example.hfa.FROMDATE";
	public static final String SCHEDULE_TODATE="example.hfa.TODATE";
	
	public static final String SCHEDULE_SHOUR="example.hfa.SHOUR";
	public static final String SCHEDULE_SMIN="example.hfa.SMIN";
	public static final String SCHEDULE_SAMPM="example.hfa.SAMPM";
	
	public static final String SCHEDULE_EHOUR="example.hfa.EHOUR";
	public static final String SCHEDULE_EMIN="example.hfa.EMIN";
	public static final String SCHEDULE_EAMPM="example.hfa.EAMPM";
	String reg_id="";
	public static final String REG_ID="example.hfa.REG_ID";
	
	public static final String MYHOME="example.hfa.MYHOME";
	public static final String MYLOCATION="example.hfa.MYLOCATION";
	ImageButton img;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		//THIS FUNCTION SETS UP THE TAB IN ACTIVITY HOME PAGE
		//SETS UP ONITEMCLICK LISTENER OF THE LISTVIEW IN HOME PAGE
		//FILLS THE ARRAYS FROM MYSQL DATABASE
		Intent i = getIntent();
		reg_id = i.getStringExtra(MainActivity.REG_ID);
		
		setupTabs();
		list = (ListView) findViewById(R.id.listFacility);
		homeList = (ListView) findViewById(R.id.lvHome);
		scheduleListView = (ListView) findViewById(R.id.lvsched);

		listListener();
		homeListListener();
		try {
			fillArrayName();
		    fillScheduleData();
			list.setAdapter(new MyAdapter(HomePage.this));
		
			homeList.setAdapter(new MyHomeAdapter(HomePage.this));
			scheduleListView .setAdapter(new schedAdapter(HomePage.this));
			schedListener();
			//setupGridView();	
			//gridViewListener();
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
	
	
	


	private void schedListener(){
		scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				try {
					Intent i = new Intent(HomePage.this,AddSchedule.class);
					
					i.putExtra(SCHEDULE_FNAME,fn.get(arg2).toString());
					i.putExtra(SCHEDULE_FROMDATE, sDate.get(arg2).toString());
					i.putExtra(SCHEDULE_TODATE, eDate.get(arg2).toString());
					i.putExtra(SCHEDULE_SHOUR, sHour.get(arg2).toString());
					i.putExtra(SCHEDULE_SMIN, sMin.get(arg2).toString());
					i.putExtra(SCHEDULE_SAMPM, sampm.get(arg2).toString());
					i.putExtra(SCHEDULE_EHOUR, eHour.get(arg2).toString());
					i.putExtra(SCHEDULE_EMIN, eMin.get(arg2).toString());
					i.putExtra(SCHEDULE_EAMPM, eampm.get(arg2).toString());
					i.putExtra(REG_ID, reg_id);
					i.putExtra(MYHOME, homeNAME.get(arg2).toString());
					i.putExtra(MYLOCATION, locationNAME.get(arg2).toString());
					
					startActivity(i);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
	}
	private void homeListListener() {
		// TODO Auto-generated method stub
		homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try{
				Intent ii = new Intent(HomePage.this,LocationMenu.class);
				ii.putExtra(SELECTED_HOME, distinctHome.get(arg2).toString());
				ii.putExtra(REG_ID, reg_id);
				startActivity(ii);
				}catch(Exception e){
					createDialog("",e.toString());
				}
			}
		});
	}

	private void listListener(){
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//createDialog(arg2+"",name[arg2]+"");
				Intent i= new Intent(HomePage.this,SetFacility.class);
				try {
					i.putExtra(FACILITY_NAME, name.get(arg2).toString());
					i.putExtra(FACILITY_ID, ids.get(arg2).toString());
					i.putExtra(REG_ID, reg_id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				startActivity(i);
				
			}
		});
	
	}
	private void setupGridView(){	
		//gridView = (GridView) findViewById(R.id.gvFacilities);
		//customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		//gridView.setAdapter(customGridAdapter);			
	}
	
	private void gridViewListener(){
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {		
				/*
				try {
					
					//createDialog(ids[position],ids[position]);
					json = webcontrol.getFacility(Uri,"getSingleImage",ids[position]);
					JSONArray  singleImage= json.getJSONArray("sImage");
					byte [] b=null ;
					
						 b = Base64.decode(singleImage.get(0).toString(), 0) ;	
						Bitmap MainImage = BitmapFactory.decodeByteArray(b, 0, b.length);
						
						singleRow s= new  singleRow(name[position],description[position],MainImage,ids[position]);
						myObjectList.set(position, s);
						
						Item item = new Item(MainImage ,description[position] + "\n" + name[position]);
						gridArray.set(position , item);
						
						setupGridView();
						
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			
			}
		
		});
	}
	
	private void fillScheduleData() throws ClientProtocolException, JSONException, IOException, InterruptedException, ExecutionException{
		json=new JSONObject();
		json = null;
		
		
		json= webcontrol.getSchedule(Uri,"getSchedule",reg_id);
		
		 fn= json.getJSONArray("facilityName");
		sDate = json.getJSONArray("fromDate");
		eDate = json.getJSONArray("toDate");
		sHour = json.getJSONArray("startHour");
		eHour = json.getJSONArray("endHour");
		sMin = json.getJSONArray("startMinute");
		eMin = json.getJSONArray("endMinute");
		sampm = json.getJSONArray("sampm");
		eampm = json.getJSONArray("eampm");
		schedfid= json.getJSONArray("fid");
		homeNAME = json.getJSONArray("homeName");
		locationNAME = json.getJSONArray("locationName");		
	}
	private void fillArrayName() throws ClientProtocolException, JSONException, IOException, InterruptedException, ExecutionException{
				
		json=new JSONObject();
		
		json = webcontrol.getFacility(Uri,"getFacility",reg_id);	
		 name= json.getJSONArray("fac_name");
		 description= json.getJSONArray("home_name");
		 ids= json.getJSONArray("ids");
		
		/*
		for(int k=0;k<image.length();k++)
			{
				 images = Base64.decode(image.get(k).toString(), k) ;	
			}	
			*/		
	}
	
	class schedRow extends ListActivity{
		 String sDate,eDate,sHour,eHour,sMin,eMin,sampm,eampm,schedfid;
		String name,homeNAMES,locationNAMES;	
		
		schedRow(String name,
				String sDate,String eDate,String sHour,
				String eHour,String sMin,String eMin,String sampm,String eampm,String schedfid,
				String homename, String locationname
				
				){
			this.name = name;
			this.sDate =sDate;
			this.eDate =eDate;
			this.sHour = sHour;
			this.eHour = eHour;
			this.sMin = sMin;
			this.eMin = eMin;
			this.sampm =sampm;
			this.eampm = eampm;
			this.schedfid=schedfid;
			this.homeNAMES = homename;
			this.locationNAMES = locationname;
		}
		
	}
	class schedAdapter extends  BaseAdapter{
		//This adapter sets up the value of list view by making an array list and pass it as a dataset
		Context context;	
		schedAdapter(Context c) throws ClientProtocolException, JSONException, IOException{
			
			context = c;		
			myObjectList3 = new ArrayList<schedRow>();	
		
			for(int k=0;k<fn.length();k++){
				
				myObjectList3.add(new schedRow(
									fn.get(k).toString(),
									sDate.get(k).toString(),
									eDate.get(k).toString(),
									sHour.get(k).toString(),
									eHour.get(k).toString(),
									sMin.get(k).toString(),
									eMin.get(k).toString(),
									sampm.get(k).toString(),
									eampm.get(k).toString(),
									schedfid.get(k).toString(),
									homeNAME.get(k).toString(), 
									locationNAME.get(k).toString()
									
								 )
				
								);	
			
			}		
		}
		
		@Override
		public int getCount() {
			return myObjectList3.size();//returns the size of the list
		}
		@Override
		public Object getItem(int index) {
			return myObjectList3.get(index);//gets the object based on index
		}
		@Override
		public long getItemId(int i) {
			return i;//gets the item
		}
		@Override
		public View getView(final int index, View view, ViewGroup group) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.myschedule,group, false);
			
		TextView tvSchedFname = (TextView) row.findViewById(R.id.tvScheduleFacilityName);
		TextView date = (TextView) row.findViewById(R.id.tvScheduleDate);
		TextView Time = (TextView) row.findViewById(R.id.tvScheduleTime);
		TextView hometv = (TextView) row.findViewById(R.id.lblScheduleHome);
		TextView locationtv = (TextView) row.findViewById(R.id.lblScheduleLocation);
			
			temp3 = myObjectList3.get(index);
			tvSchedFname.setText(temp3.name);
			date.setText(temp3.sDate + " -> " + temp3.eDate);
			Time.setText(temp3.sHour + ":"+ temp3.sMin + temp3.sampm + " -> " + temp3.eHour + ":"+ temp3.eMin +temp3.eampm);
			hometv.setText("Home:" + temp3.homeNAMES);
			locationtv.setText("Location:" + temp3.locationNAMES);
			
			return row;
		}

	}
	
	class singleRow extends ListActivity{	
		//This class receives the value of list view
		//name is the name of the facility
		//description is the home name
		//images is a byte which is actually converted to bitmap
		String name; 
		String description;//home name
		//Bitmap images;
		String id;
		
		
		singleRow(String name, String description,String id){
			this.name = name;
			this.description = description;
			this.id= id;
		}
		
	}
	
	class MyAdapter extends  BaseAdapter{
		//This adapter sets up the value of list view by making an array list and pass it as a dataset
		Context context;	
		MyAdapter(Context c) throws ClientProtocolException, JSONException, IOException{
			//This constructor gets a value of context .
			//It fetches data from mysql database
			// fills the images needed by the gridview in homepage
			context = c;		
			myObjectList = new ArrayList<singleRow>();	
			//json=null;
			//json = webcontrol.getFacility(Uri,"getFacility","");
			//JSONArray  image= json.getJSONArray("images");
			//byte[] images2=null;	
			for(int k=0;k<name.length();k++){
				//images2 = Base64.decode(image.get(k).toString(), k) ;	
				//Bitmap MainImage = BitmapFactory.decodeByteArray(images2, 0, images2.length);
				//myObjectList.add(new singleRow(name[k],description[k],MainImage,ids[k]));	
				myObjectList.add(new singleRow(name.get(k).toString(),description.get(k).toString(),ids.get(k).toString()));	
				//gridArray.add(new Item(MainImage ,description[k] + "\n" + name[k]));
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
			//This function uses a layout inflater and inflates the facility_layout xml
			//It instantiates new objects such as tvname,tvdesc, imgView and fills data from the array list 
			//which is myObjectList
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.facility_menu,group, false);
			
			tvname = (TextView) row.findViewById(R.id.tvFacilityName);
			 tvdesc = (TextView) row.findViewById(R.id.tvFacilityHome);
			 TextView tvId= (TextView) row.findViewById(R.id.tvId);
			//ImageView imgView = (ImageView) row.findViewById(R.id.imgFacilityImage);
			ImageView check= (ImageView) row.findViewById(R.id.imgCheck);
			
			temp = myObjectList.get(index);
			tvname.setText(temp.name);
			tvdesc.setText(temp.description);
			Bitmap rr = BitmapFactory.decodeResource(getResources(), R.drawable.rightarrow);
			Bitmap rrr = Bitmap.createScaledBitmap(rr, 40, 60, false);
			check.setImageBitmap(rrr);
			tvId.setText(temp.id + "-");
			
			/*
			LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row2 = inflater2.inflate(R.layout.listviewhome,group, false);
		
			tvHome = (TextView) row2.findViewById(R.id.tvHome);
			ImageView homeImg = (ImageView) findViewById(R.id.imgHomeCheck);
			
			tvHome.setText(temp.description);
			homeImg.setImageResource(R.drawable.tick);
			*/
			//Bitmap mm = Bitmap.createScaledBitmap(temp.images, 100, 100, false);
			
			//imgView.setImageBitmap(mm);
			
			
			
			return row;
		}

	}
	
	class singleRowHome extends ListActivity{		
		String name2;
		String description2;
		String id2;

		singleRowHome(String name, String description,String id){
			this.name2 = name;
			this.description2 = description;
			this.id2= id;
		}
		
	}
	class MyHomeAdapter extends  BaseAdapter{
		//This adapter sets up the value of list view by making an array list and pass it as a dataset
		Context context2;
		
		MyHomeAdapter(Context c) throws ClientProtocolException, JSONException, IOException, InterruptedException, ExecutionException{

			context2 = c;		
			myObjectList2 = new ArrayList<singleRowHome>();	
			json=new JSONObject();
			json = null;
			json = webcontrol.getFacility(Uri,"getFacilityDistinctHome",reg_id);
			 distinctHome= json.getJSONArray("distinctHomeName");
				
			for(int k=0;k< distinctHome.length();k++){
				myObjectList2.add(new singleRowHome(name.get(k).toString(), distinctHome.get(k).toString(),ids.get(k).toString()));	
				//gridArray.add(new Item(MainImage ,description[k] + "\n" + name[k]));
			}	
			
		}
		
		@Override
		public int getCount() {
			return myObjectList2.size();//returns the size of the list
		}
		@Override
		public Object getItem(int index) {
			return myObjectList2.get(index);//gets the object based on index
		}
		@Override
		public long getItemId(int i) {
			return i;//gets the item
		}
		@Override
		public View getView(final int index, View view, ViewGroup group) {
				
			LayoutInflater inflater2 = (LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row2 = inflater2.inflate(R.layout.listviewhome,group, false);
		
			tvHomee = (TextView) row2.findViewById(R.id.tvHome);	
			temp2 = myObjectList2.get(index);
			tvHomee.setText(temp2.description2);
	
			return row2;
		}

	}
	
	public void showSetFacility(){
		//This function shows the SetFacility xml layout or activity
		Intent i = new Intent(HomePage.this,SetFacility.class);
		startActivity(i);		
	}

	public void createDialog(String title,String content){
		//This function displays the title and content from desired output
		Dialog d= new Dialog(HomePage.this);
		d.setTitle(title);
		TextView tv= new TextView(HomePage.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}

	private void setupTabs(){
		//THIS FUNCTION SETS UP TABS ON THE HOMEPAGE CLASS
		tabhost = (TabHost) findViewById(R.id.mytabhost);
		tabhost.setup();
		
		tabspec = tabhost.newTabSpec("homeTag");//creates a tag into the tabhost
		tabspec.setContent(R.id.tab1);//sets the first tab
		tabspec.setIndicator("Home");//sets the name of the tab		
		
		tabhost.addTab(tabspec);//adds the tab
			
		tabspec = tabhost.newTabSpec("settingsTag");//creates a tag into the tabhost
		tabspec.setContent(R.id.tab2);//sets the second tab
		tabspec.setIndicator("Settings");//sets the name of the tab
		
		tabhost.addTab(tabspec);//adds the tab
		
		tabspec = tabhost.newTabSpec("facilitiesTag");//creates a tag into the tabhost
		tabspec.setContent(R.id.tab3);//sets the second tab
		tabspec.setIndicator("Facilities");//sets the name of the tab
		
		tabhost.addTab(tabspec);//adds the tab
		
		tabspec = tabhost.newTabSpec("scheduleTag");//creates a tag into the tabhost
		tabspec.setContent(R.id.tab4);//sets the second tab
		tabspec.setIndicator("Scheduling");//sets the name of the tab
		tabhost.addTab(tabspec);//adds the tab
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_page, menu);
		return true;
	}

}
