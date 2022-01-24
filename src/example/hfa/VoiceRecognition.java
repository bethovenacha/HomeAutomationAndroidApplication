package example.hfa;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VoiceRecognition extends Activity {
	static final int check = 1111;//REQUEST CODE
	Button speak;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_recognition);
		speak = (Button) findViewById(R.id.btnSpeak);	
	}
	
	public void say(){
		
		speak.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
					Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					
					i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					
					i.putExtra(RecognizerIntent.EXTRA_PROMPT, "You may now say the facility name.");
					
					startActivityForResult(i,check);
					
				}catch(Exception ex){
					
					createDialog("Error Recognizing Voice.",ex.toString());
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == check && resultCode == RESULT_OK){
			ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		}
	}
	public void createDialog(String title,String content){
		//This function displays the title and content from desired output
		Dialog d= new Dialog(VoiceRecognition.this);
		d.setTitle(title);
		TextView tv= new TextView(VoiceRecognition.this);
		tv.setText(content);
		d.setContentView(tv);
		d.show();	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_voice_recognition, menu);
		return true;
	}

}
