package example.hfa;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

//This class is the splash screen of the project
public class Splash extends Activity{

	MediaPlayer anySong;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout .hfa_splash);//hfa splash is the xml file that represents the form
		
	 anySong = MediaPlayer.create(Splash.this , R.raw.memories);
		
		anySong.start();
		
		//Thread class allows you to do multiple actions at the same time
		Thread timer= new Thread(){	
			public void run(){
				
				try{
					sleep(15000);			
				}catch(InterruptedException e){
					e.printStackTrace();
					
				}finally{
					Intent mainClassIntent= new Intent(Splash.this,MainActivity.class);
					startActivity(mainClassIntent);
				}
			}
		};
		timer.start();
	}

	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();//This method makes the activity splash kill itself
		anySong.release();
		finish();
		
	}
	
	

}
