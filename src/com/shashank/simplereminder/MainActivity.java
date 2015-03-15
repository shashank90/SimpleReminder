package com.shashank.simplereminder;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.shashank.simplereminder.R;

import android.os.Bundle;





import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MainActivity extends Activity {

	
	EditText edittext; 
	EditText et;
	EditText edittext2;
	Button button1;
	Button button2;
	Calendar myCalendar;
	int flag1;
	int flag2;
	public String textfield;
	public static final String Note = "NoteKey";
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edittext=(EditText)findViewById(R.id.editText1);
		edittext2=(EditText)findViewById(R.id.editText2);
		
		
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		et=(EditText)findViewById(R.id.editText);
		flag1=0;
		flag2=0;
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		edittext2.setText(sharedpreferences.getString(Note, ""));
		
		myCalendar = Calendar.getInstance();
		
		button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
         	sendAlarm();
        	Editor editor=sharedpreferences.edit();
        	
        	editor.putString(Note, edittext2.getText().toString());
	            editor.commit();
	            
            }
        });
		button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
         	disableAlarm();
         	edittext.setText("");
         	et.setText("");
         	edittext2.setText("");
         	getSharedPreferences(MyPREFERENCES, 0).edit().clear().commit();
            }
        });
		
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		    	Log.i("shashank","set date");
		        myCalendar.set(Calendar.YEAR, year);
		        myCalendar.set(Calendar.MONTH, monthOfYear);
		        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        flag1=1;
		       
		        updateLabel();
		    }

		};
		final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

		    @Override
		    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		    	
			        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			        myCalendar.set(Calendar.MINUTE, minute);
			        
			        Log.i("shashank","set time");
			     flag2=1;
			     
				   int hour;
				   String am_pm;
				   if (hourOfDay > 12) {
				    hour = hourOfDay - 12;
				    am_pm = "PM";
				   } else {
				    hour = hourOfDay;
				    am_pm = "AM";
				   }
				   et.setText(hour + " : " + minute + " " + am_pm);
				  }

		};

		   edittext.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            new DatePickerDialog(MainActivity.this, date, myCalendar
		                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
		                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		        }
		    });

		   et.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            new TimePickerDialog(MainActivity.this, time, myCalendar
		                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),false
		                   ).show();
		        }
		    });
		
		
		   Log.i("shashank","end of create");
		  
	}

	public void sendAlarm()
	{
		if(flag1==1 && flag2==1 && edittext2.getText().toString()!=null) {
			  Log.i("shashank","inside send alarm");
		Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
		myIntent.putExtra("alarm_message", "O'Doyle Rules!");
		 
		 myIntent.putExtra("content", edittext2.getText().toString());
		 
		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
				124234, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		

		alarmManager.set(AlarmManager.RTC, myCalendar.getTimeInMillis(),
				pendingIntent);	
		Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
				   Toast.LENGTH_SHORT).show();
		
		
		
		}
	}		
	
    private void updateLabel() {

  String myFormat = "dd/MM/yy"; //In which you need put here
  SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

  edittext.setText(sdf.format(myCalendar.getTime()));
  }

    public void disableAlarm()
    {
    	AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);         
    	Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
		myIntent.putExtra("alarm_message", "O'Doyle Rules!");
	
		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
				124234, myIntent, 0);         
    	aManager.cancel(pendingIntent);
    	flag1=0;
    	flag2=0;
    	Log.i("Shashank", "cancelling notification");
    }
    
	
    	 



	
}
