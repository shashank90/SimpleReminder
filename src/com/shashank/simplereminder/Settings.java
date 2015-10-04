package com.shashank.simplereminder;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends ActionBarActivity {
	
	private RadioGroup radioSound;
	private RadioGroup radioViber;
	
	private RadioButton radioDefault;
	private RadioButton radioGoodMorning;
	private RadioButton radioMetallic;
	private RadioButton radioTimeCame;
	private RadioButton radioLittleDwarf;
	private RadioButton radioButton;
	private RadioButton radioButtonv
	;
	private RadioButton radioViberOnce;
	private RadioButton radioViberTwice;
	private RadioButton radioViberThrice;

	Button button1;
	
	
	public static final String MyPREFERENCES = "Settings" ;
	SharedPreferences sharedpreferences;
	
	public static final String sound = "soundKey";
	public static final String viber = "viberKey";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	      button1=(Button)findViewById(R.id.ButtonSet5);
	
	      radioSound = (RadioGroup) findViewById(R.id.radioSound);
	      radioDefault = (RadioButton)findViewById(R.id.radioDefault);
	      radioGoodMorning = (RadioButton)findViewById(R.id.radioGoodMorning);
	      radioMetallic = (RadioButton)findViewById(R.id.radioMetallic);
	      radioTimeCame= (RadioButton)findViewById(R.id.radioTimeCame);
	      radioLittleDwarf = (RadioButton)findViewById(R.id.radioLittleDwarf);
	      
	      radioViber = (RadioGroup) findViewById(R.id.radioViber);
	      radioViberOnce = (RadioButton)findViewById(R.id.radioViberOnce);
	      radioViberTwice = (RadioButton)findViewById(R.id.radioViberTwice);
	      radioViberThrice = (RadioButton)findViewById(R.id.radioViberThrice);
	      
	      sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			 if (sharedpreferences.contains(sound))
		      {
				 Log.i("shashank",sharedpreferences.getString(sound, ""));
				 if(sharedpreferences.getString(sound, "").equals("Default"))
					 
					 radioSound.check(radioDefault.getId());
				 
				 if(sharedpreferences.getString(sound, "").equals("Good Morning"))
					 
					 radioSound.check(radioGoodMorning.getId());
				 
				 if(sharedpreferences.getString(sound, "").equals("Metallic"))
					 
					 radioSound.check(radioMetallic.getId());
				 
				 if(sharedpreferences.getString(sound, "").equals("Little Dwarf"))
					 
					 radioSound.check(radioLittleDwarf.getId());
				 
				 if(sharedpreferences.getString(sound, "").equals("Time has Come"))
					 
					 radioSound.check(radioTimeCame.getId());

		      }
			 
			 if (sharedpreferences.contains(viber))
		      {
				 Log.i("shashank",sharedpreferences.getString(viber, ""));
				 
				 if(sharedpreferences.getString(viber, "").equals("Once"))
					 
					 radioViber.check(radioViberOnce.getId());
				 
				 if(sharedpreferences.getString(viber, "").equals("Twice"))
					 
					 radioViber.check(radioViberTwice.getId());
				 
				 if(sharedpreferences.getString(viber, "").equals("Thrice"))
					 
					 radioViber.check(radioViberThrice.getId());
				 
		      }

	      button1.setOnClickListener(new View.OnClickListener() {
	    	  Editor editor=sharedpreferences.edit();
	          public void onClick(View v) {
	        	String soundSelect; 
	        	String viberSelect;
	        	int selectedId = radioSound.getCheckedRadioButtonId();
	        	int vselectedId = radioViber.getCheckedRadioButtonId();
	          	radioButton = (RadioButton) findViewById(selectedId);
	          	radioButtonv = (RadioButton) findViewById(vselectedId);
	          	soundSelect = (String) radioButton.getText();
	          	viberSelect = (String)radioButtonv.getText();
	          	Log.i("shashank","sound = "+soundSelect);
	          	
	          	 editor.putString(sound, soundSelect);
	          	 editor.putString(viber, viberSelect);
		            editor.commit();
		          
		            finish();
	          	}
	          });
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
