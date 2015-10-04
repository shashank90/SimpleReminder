package com.shashank.simplereminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.inmobi.commons.InMobi;
import com.inmobi.commons.InMobi.LOG_LEVEL;
import com.inmobi.monetization.IMBanner;
import android.view.WindowManager;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetReminder extends ActionBarActivity {
	TextView TextViewDate1; 
	EditText EditTextNote1;
	TextView TextViewTime1;
	Button button1;
	Button button2;
	Calendar myCalendar;
	Calendar calnow;
	Calendar calset;

	static int x=0;
	int flag1;
	int flag2;
	int randomInt;
	private DbHelper mydb ;
	public String textfield;
	String type=null;
	private RadioGroup radioAlarmType;
	private RadioButton radioButton;
	static final int DATE_PICKER_ID = 1111;
	 	 int year;
	     int month;
	     int day;
	     int day_of_week;
	     int hour;
	     int minute;
	     String status;
	     public String getStatus() {
			return status;
		}



		public void setStatus(String status) {
			this.status = status;
		}
		boolean future_date=false;
	     public int getHour() {
			return hour;
		}



		public void setHour(int hour) {
			this.hour = hour;
		}



		public int getMinute() {
			return minute;
		}



		public void setMinute(int minute) {
			this.minute = minute;
		}
		String dateString;
	   
	public String getDateString() {
			return dateString;
		}



		public void setDateString(String dateString) {
			this.dateString = dateString;
		}



	public int getYear() {
			return year;
		}



		public void setYear(int year) {
			this.year = year;
		}



		public int getMonth() {
			return month;
		}



		public void setMonth(int month) {
			this.month = month;
		}



		public int getDay() {
			return day;
		}



		public void setDay(int day) {
			this.day = day;
		}



		public int getDay_of_week() {
			return day_of_week;
		}



		public void setDay_of_week(int day_of_week) {
			this.day_of_week = day_of_week;
		}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_reminder);
		radioAlarmType = (RadioGroup) findViewById(R.id.radioAlarmType1);
		mydb = new DbHelper(this);
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(1000);
	TextViewDate1=(TextView)findViewById(R.id.TextViewDate1);
	EditTextNote1=(EditText)findViewById(R.id.EditTextNote1);
	TextViewTime1=(TextView)findViewById(R.id.TextViewTime1);
	
	TextViewTime1.setPaintFlags(TextViewTime1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	TextViewDate1.setPaintFlags(TextViewDate1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	button1=(Button)findViewById(R.id.ButtonSet1);
	button2=(Button)findViewById(R.id.ButtonCancel1);
	
	flag1=0;
	flag2=0;
	
	
	myCalendar = Calendar.getInstance();
	calnow = Calendar.getInstance();
	calset = Calendar.getInstance();
	//InMobi.setLogLevel(LOG_LEVEL.DEBUG);
	IMBanner imbanner = new IMBanner(this, "483c881577c3454f9b5e885466170a96",getOptimalSlotSize(this));
	LinearLayout parent = (LinearLayout)findViewById(R.id.linearLayout4);
	parent.addView(imbanner);
	
	button1.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	int selectedId = radioAlarmType.getCheckedRadioButtonId();
        	radioButton = (RadioButton) findViewById(selectedId);
        	type = (String) radioButton.getText();
        	if(flag1==1 && flag2==1 && !EditTextNote1.getText().toString().isEmpty()) 
        	{	
        	boolean temp;
        	
        		temp=sendAlarm(randomInt, type);
        	if (temp)
        	{	setStatus("active");
        		if(mydb.insertContact(EditTextNote1.getText().toString(), TextViewDate1.getText().toString(), TextViewTime1.getText().toString(),type,randomInt,getStatus())){
        			//Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        			Log.i("shashank","inserted reminder");
        		}		
             
        		else{
        			//Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
        			Log.i("shashank","Unable to insert reminder");
        		}
        		finish(); 
        	}
        	else 
        	{
        		AlertDialog alertDialog = new AlertDialog.Builder(SetReminder.this).create();
 		    	alertDialog.setTitle("Select Date");
 		    	alertDialog.setMessage("Please enter a future date/time!");
 		    	alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
 		    			new DialogInterface.OnClickListener() {
  	    	        	public void onClick(DialogInterface dialog, int which) {
  	    	        		dialog.dismiss();
  	    	        	}
  	    	    	});
 		    	alertDialog.show();

        		}
        	}
        	else 
        	{
        		AlertDialog alertDialog = new AlertDialog.Builder(SetReminder.this).create();
 		    	alertDialog.setTitle("Invalid Entry");
 		    	alertDialog.setMessage("Please fill empty fields!");
 		    	alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
 		    			new DialogInterface.OnClickListener() {
  	    	        	public void onClick(DialogInterface dialog, int which) {
  	    	        		dialog.dismiss();
  	    	        	}
  	    	    	});
 		    	alertDialog.show();


        	}
        }
    });
	button2.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
     	disableAlarm(randomInt);
     	TextViewDate1.setText("");
     	TextViewTime1.setText("");
     	EditTextNote1.setText("");
     	
        }
    });
	
	final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	       	Calendar caltemp = Calendar.getInstance();
  	    	Calendar caltempnow = Calendar.getInstance();
  	    	caltemp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
 		    caltemp.set(Calendar.MONTH, monthOfYear);
 		    caltemp.set(Calendar.YEAR, year);
 		    if (caltemp.before(caltempnow)) 
 		    {
 		    	AlertDialog alertDialog = new AlertDialog.Builder(SetReminder.this).create();
 		    	alertDialog.setTitle("Select Date");
 		    	alertDialog.setMessage("Please enter a future date!!");
 		    	alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
 		    			new DialogInterface.OnClickListener() {
  	    	        	public void onClick(DialogInterface dialog, int which) {
  	    	        		dialog.dismiss();
  	    	        	}
  	    	    	});
 		    	alertDialog.show();
 		    }
 		    else 
 		    {
 		    	setDay(dayOfMonth);
 		    	setMonth(monthOfYear);
 		    	setYear(year);
 		    	calset.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			    calset.set(Calendar.MONTH, monthOfYear);
			    calset.set(Calendar.YEAR, year);

 		    	GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
 		    	int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
 		    	setDay_of_week(day_of_week);

 		    	Log.i("shashank","just set dayOfMonth = " + dayOfMonth+"month = " + monthOfYear+" day of week = "+ day_of_week + " year = "+ getYear());

 		    	int mm = getMonth()+1;
 		    	int yy = getYear() - 2000;
 		    	TextViewDate1.setText(getDay() + "/" + mm + "/" + yy);
 		    	setDateString((String)TextViewDate1.getText());
 		    	Log.i("shashank","dateString = "+getDateString());
 		    	flag1 = 1;
 		    }
	    }

	};
	final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

	    @Override
	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	    	
		  
		        setHour(hourOfDay);
		        setMinute(minute);
		        calset.set(Calendar.HOUR_OF_DAY,hourOfDay);
			    calset.set(Calendar.MINUTE, minute);

		        Log.i("shashank","set time hour = "+hourOfDay+ " minute = "+minute);
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
			   TextViewTime1.setText(hour + " : " + minute + " " + am_pm);
			  }

	};

	   TextViewDate1.setOnClickListener(new OnClickListener() {
		   
	        @Override
	        public void onClick(View v) {
	        	Log.i("shashank","textview got clicked");
	            // TODO Auto-generated method stub
	        				new DatePickerDialog(SetReminder.this, date, myCalendar
	                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	           
	           

	            
	        }
	    });

	   TextViewTime1.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            new TimePickerDialog(SetReminder.this, time, myCalendar
	                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),false
	                   ).show();
	        }
	    });
	
	
	   Log.i("shashank","end of create");
	   
		  
}
	
public static Integer getOptimalSlotSize(Activity ctxt) {
		 Display display = ((WindowManager) ctxt.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		        DisplayMetrics displayMetrics = new DisplayMetrics();
		        display.getMetrics(displayMetrics);
		        double density = displayMetrics.density;
		        double width = displayMetrics.widthPixels;
		        double height = displayMetrics.heightPixels;
		int[][] maparray = { { IMBanner.INMOBI_AD_UNIT_728X90, 728, 90 }, {
		            IMBanner.INMOBI_AD_UNIT_468X60, 468, 60 }, {
		                IMBanner.INMOBI_AD_UNIT_320X50, 320, 50 } };
		        for (int i = 0; i < maparray.length; i++) {
		                if (maparray[i][1] * density <= width
		                        && maparray[i][2] * density <= height) {
		                    return maparray[i][0];
		                }
		            }
		        return IMBanner.INMOBI_AD_UNIT_320X50;
}		
	
public boolean sendAlarm(int r, String type)
{
	
	Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
	int currentMonth=cal1.get(Calendar.MONTH);

	int Cur_year = calnow.get(Calendar.YEAR);
	int Cur_month = calnow.get(Calendar.MONTH);
	int Cur_day = calnow.get(Calendar.DAY_OF_MONTH);
	int Cur_hour = calnow.get(Calendar.HOUR_OF_DAY);
	int Cur_minute = calnow.get(Calendar.MINUTE);
	Cur_month = Cur_month+1;
	int mm = getMonth()+1;
	String calsetString = getYear()+"-"+mm+"-"+getDay();
	String calnowString = Cur_year+"-"+Cur_month+"-"+Cur_day;
	Log.i("shashank","calset string "+calsetString);
	Log.i("shashank","calnow string "+calnowString);
	DateTime dateTimeset = new DateTime(calsetString);
	DateTime dateTimenow = new DateTime(calnowString);
	
	int diffInDays = Days.daysBetween(dateTimenow.toLocalDate(), dateTimeset.toLocalDate()).getDays();
	Log.i("shashank","diffIndays = "+diffInDays);
	
	
	Cur_month = Cur_month - 1;
	int diffInMonths = (getYear()-Cur_year)*12 + (getMonth()-Cur_month);
	Log.i("shashank","diffInMonths = "+diffInMonths);

		
		if (type.equals("Once")) 
		{	
			cal2.set(Calendar.DAY_OF_MONTH,getDay());
			cal2.set(Calendar.MONTH,getMonth());
			cal2.set(Calendar.HOUR_OF_DAY,getHour());
			cal2.set(Calendar.MINUTE,getMinute());
			
			
		
			if (cal2.before(cal1))
			{
				
				future_date = false;
				return future_date;
			}	
			myCalendar = Calendar.getInstance();
			myCalendar.set(Calendar.DAY_OF_MONTH,getDay());
			myCalendar.set(Calendar.MONTH,getMonth());
			myCalendar.set(Calendar.HOUR_OF_DAY,getHour());
			myCalendar.set(Calendar.MINUTE,getMinute());
			myCalendar.set(Calendar.SECOND, 0);
		
				future_date = true;
				
			  Log.i("shashank","inside send alarm random = "+ r+"type = "+type);
		Intent myIntent = new Intent(SetReminder.this, AlarmReceiver.class);
		myIntent.putExtra("alarm_message", "O'Doyle Rules!");
		 
		 myIntent.putExtra("content", EditTextNote1.getText().toString());
		 
		PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminder.this,
				r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		

		alarmManager.set(AlarmManager.RTC, myCalendar.getTimeInMillis(),
				pendingIntent);
		Log.i("shashank","Notification is set for"+myCalendar.getTime());
		Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
				   Toast.LENGTH_SHORT).show();
		
			
		
		}
		else if (type.equals("Daily")){
			
			Log.i("shashank","Notification is set for calset "+calset.getTime());
			Log.i("shashank","Notification is set for calnow "+calnow.getTime());
			myCalendar = Calendar.getInstance();
			if(calset.before(calnow))
	    	{
	    		myCalendar.add(Calendar.DAY_OF_MONTH,1);
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
	    		
	    	}
	    	else
	    	{
	    		myCalendar.add(Calendar.DAY_OF_MONTH,diffInDays);
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
	    	}

		
				future_date = true;
			
				  Log.i("shashank","inside send alarm random = "+ r+"type = "+type);
			Intent myIntent = new Intent(SetReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", EditTextNote1.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminder.this,
					r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			

			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,
					pendingIntent);	
			Log.i("shashank","Notification is set for"+myCalendar.getTime());
			Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
					   Toast.LENGTH_SHORT).show();
			
				
				
			}
		else if (type.equals("Weekly")) {
			
			myCalendar = Calendar.getInstance();
			if(calset.before(calnow))
	    	{
	    		myCalendar.add(Calendar.WEEK_OF_YEAR, 1);
	    		myCalendar.set(Calendar.DAY_OF_WEEK, getDay_of_week());
				myCalendar.set(Calendar.HOUR_OF_DAY, getHour()); 										
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);

	    		
	    	}
	    	else
	    	{
	    		myCalendar.add(Calendar.DAY_OF_MONTH,diffInDays);
	    		myCalendar.set(Calendar.DAY_OF_WEEK, getDay_of_week());
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
				
	    	}

		
		
				future_date = true;
			
				  Log.i("shashank","inside send alarm random = "+ r+"type ="+type);
			Intent myIntent = new Intent(SetReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", EditTextNote1.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminder.this,
					r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			

			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,
					pendingIntent);	
			Log.i("shashank","Notification is set for"+myCalendar.getTime());
			Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
					   Toast.LENGTH_SHORT).show();
			
			
		}
		else if (type.equals("Monthly")) {
			
			myCalendar = Calendar.getInstance();
			if(calset.before(calnow))
	    	{
	    		myCalendar.add(Calendar.MONTH, 1);
	    		myCalendar.set(Calendar.DAY_OF_MONTH, getDay());
				myCalendar.set(Calendar.HOUR_OF_DAY, getHour()); 										
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);

	    		
	    	}
	    	else
	    	{	
	    		myCalendar.add(Calendar.MONTH,diffInMonths);
	    		myCalendar.set(Calendar.DAY_OF_MONTH, getDay());
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
				
	    	}

			
				future_date = true;
				
				
				  Log.i("shashank","inside send alarm random = "+ r+"type = "+type);
			Intent myIntent = new Intent(SetReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", EditTextNote1.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminder.this,
					r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			
			
			if (currentMonth == Calendar.JANUARY || currentMonth == Calendar.MARCH || currentMonth == Calendar.MAY || currentMonth == Calendar.JULY 
		            || currentMonth == Calendar.AUGUST || currentMonth == Calendar.OCTOBER || currentMonth == Calendar.DECEMBER){
		    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 31, pendingIntent);
		    }
		    if (currentMonth == Calendar.APRIL || currentMonth == Calendar.JUNE || currentMonth == Calendar.SEPTEMBER 
		            || currentMonth == Calendar.NOVEMBER){
		        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);
		        }


		    if  (currentMonth == Calendar.FEBRUARY){//for feburary month)
		        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();    
		            if(cal.isLeapYear(Calendar.YEAR)){//for leap year feburary month  
		                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 29, pendingIntent);
		            }
		            else{ //for non leap year feburary month
		                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 28, pendingIntent);
		            }
		    }
			
			
		    Log.i("shashank","Notification is set for"+myCalendar.getTime());
			Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
					   Toast.LENGTH_SHORT).show();
			
			
		}
		else {
				future_date = true;
				myCalendar.set(Calendar.DAY_OF_MONTH,getDay());
				myCalendar.set(Calendar.MONTH,getMonth());
				myCalendar.set(Calendar.HOUR_OF_DAY,getHour());
				myCalendar.set(Calendar.MINUTE,getMinute());
		
				  Log.i("shashank","inside send alarm default , random = "+ r);
			Intent myIntent = new Intent(SetReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", EditTextNote1.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminder.this,
					r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			

			alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), 
					pendingIntent);	
			Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
					   Toast.LENGTH_SHORT).show();
			
			
			
			
		}

	
	return future_date;
}		

public void disableAlarm(int r)
{
	
	AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);         
	Intent myIntent = new Intent(SetReminder.this, AlarmReceiver.class);
	myIntent.putExtra("alarm_message", "O'Doyle Rules!");
	
	PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminder.this,
			r, myIntent, 0);         
	aManager.cancel(pendingIntent);
	flag1=0;
	flag2=0;
	Log.i("Shashank", "cancelling notification");
}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_reminder, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}
	
}
