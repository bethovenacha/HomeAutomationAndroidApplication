package example.hfa;


import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	EditText username,password;
	JSONObject json;
	ConnectivityManager connMgr ;
	 //String ipAddress ="http://5.175.134.242:3306";
	InetAddress inetAddr;
	 String ipAddress ="http://5.175.134.242";
	 //IpAddress="http://192.168.56.1";//"http://192.168.56.1";//"http://192.168.0.101"; //"http://192.168.56.1";//"http://192.168.0.101"; //
	ImageView lg;
	 String hostname;
	 public static final String REG_ID="example.hfa.REG_ID";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
	
		username = (EditText) findViewById(R.id.etUsername );
		password = (EditText) findViewById(R.id .etPassword );
		lg = (ImageView) findViewById(R.id.imgLogin);
		
		lg.setOnClickListener(new View.OnClickListener() {
			
			@Override			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				MyWebControl webcontrol = new MyWebControl();	
				
				try {
					ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					
					 inetAddr = InetAddress.getByName("5.175.134.242");					
					 hostname="";
					 hostname = inetAddr.getHostAddress();
					
					 connMgr = (ConnectivityManager) 
						        getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo s = connMgr.getActiveNetworkInfo();
						
						if(s!=null && s.isConnected()){
							String url =  ipAddress + "/php/AndroidVersion1/connection.php";
							json = webcontrol.connect(hostname, "root", "kennethoczon", "homeautomationdb", url);
							String status = json.getString("conStatus");
							if(status.equals("true")){
								
								json = webcontrol.login(username.getText().toString(), password.getText().toString(), ipAddress + "/php/AndroidVersion1/controller.php");
								
								String type = json.getString("reg_type");
								String id = json.getString("reg_id");
								
								if(type.equals("admin")){
									
									Intent i = new Intent(MainActivity.this,HomePage.class);
									
									i.putExtra(REG_ID, id);
									startActivity(i);								
								}else{
									//super admin here
									
								}
								
							}else
							{
								createDialog("Login Failed.","Please try again.");
							}
						}else{
							
							createDialog("Your phone is not Connected",s.getState().toString());
						}
				
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
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
		Dialog d= new Dialog(MainActivity.this);
		d.setTitle(title);
		TextView tv= new TextView(MainActivity.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}
		
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();//This method makes the activity splash kill itself
		finish();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
}


