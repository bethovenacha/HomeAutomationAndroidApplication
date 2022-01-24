package example.hfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class FacilityGrid extends Activity {
	String reg_id="";
	String home="";
	String location="";
	JSONArray names;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter customGridAdapter;
	GridView gridView;
	JSONObject json;
	MyWebControl webcontrol= new MyWebControl();
	 String ipAddress ="http://5.175.134.242";
	 String Uri=ipAddress + "/php/AndroidVersion1/controller.php";//This is the address where data is passed and controlled
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facgridview);
		
		Intent i= getIntent();
		home = i.getStringExtra(LocationMenu.THE_HOME);
		location = i.getStringExtra(LocationMenu.THE_LOC);
		reg_id = i.getStringExtra(LocationMenu.REG_ID);
		
		try {
			getFacilities(home,location,Uri,reg_id);
			setupGridView();
			gridViewListener();
			
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
	private void getFacilities(String hom,String loc,String url,String reg_id) throws ClientProtocolException, JSONException, IOException, InterruptedException, ExecutionException{
		json = new JSONObject();
		
		json = webcontrol.getFacilit(hom,loc,url,reg_id);
		names= json.getJSONArray("fnames");
		
		Bitmap mm = BitmapFactory.decodeResource(getResources(), R.drawable.btnon);
		//Bitmap MainImage = Bitmap.createScaledBitmap(mm, 40, 60, false);
		for(int k=0;k<names.length();k++){
			gridArray.add(new Item(mm,names.get(k).toString()));
			
		}		
	}
	private void setupGridView(){	
		gridView = (GridView) findViewById(R.id.gvFacilities);
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		gridView.setAdapter(customGridAdapter);			
	}
	private void gridViewListener(){
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {	
				json=null;
				try {
					json=webcontrol.changeBit(names.get(position).toString(),Uri);
					
					if(json.getString("facbitupdate").equals("on")){
						//Chage grid image
						createDialog("Operation","Facility is now on.");
					}else{
						createDialog("Operation","Facility is now off.");
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
				
			}
		
		});
	}
	
public void createDialog(String title,String content){
		//This function displays the title and content from desired output
		Dialog d= new Dialog(FacilityGrid.this);
		d.setTitle(title);
		TextView tv= new TextView(FacilityGrid.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_facility__grid__view, menu);
		return true;
	}

}
