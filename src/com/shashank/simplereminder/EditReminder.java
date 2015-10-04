package com.shashank.simplereminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.inmobi.commons.InMobi;
import com.inmobi.commons.InMobi.LOG_LEVEL;
import com.inmobi.monetization.IMBanner;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditReminder extends ActionBarActivity {

	EditText note;
	TextView date;
	TextView time;
	private DbHelper mydb ;
	int id_To_Update = 0;
	Button button1;
	Button button2;
	int randomInt;
	int randomIntsaved=0;
	private RadioGroup radioAlarmType;
	private RadioButton radioButton;
	private RadioButton radioButtonOnce;
	private RadioButton radioButtonDaily;
	private RadioButton radioButtonWeekly;
	private RadioButton radioButtonMonthly;
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
	int flag1,flag2;
     boolean future_date=false;
	
	String type = null;
	
	Calendar myCalendar;
	Calendar calnow;
	Calendar calset;
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
		String note1=null;
		String date1=null;
		String time1=null;
		String type1=null;
		setContentView(R.layout.activity_edit_reminder);
		note = (EditText) findViewById(R.id.EditTextNote2);
	      date = (TextView) findViewById(R.id.TextViewDate2);
	      date.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	      time = (TextView) findViewById(R.id.TextViewTime2);
	      time.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	      button1=(Button)findViewById(R.id.ButtonSet2);
	      button2=(Button)findViewById(R.id.ButtonCancel2);
	      radioButtonOnce = (RadioButton)findViewById(R.id.radioOnce2);
	      radioButtonDaily = (RadioButton)findViewById(R.id.radioDaily2);
	      radioButtonWeekly = (RadioButton)findViewById(R.id.radioWeekly2);
	      radioButtonMonthly = (RadioButton)findViewById(R.id.radioMonthly2);
	      
	      flag1 = 0;
	      flag2 = 0;

                 
         
          
          

	      IMBanner imbanner = new IMBanner(this, "483c881577c3454f9b5e885466170a96",getOptimalSlotSize(this));
	  	LinearLayout parent = (LinearLayout)findViewById(R.id.linearLayout12);
	  	parent.addView(imbanner);
	      
	      Random randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(1000);
			Log.i("shashank","randomInt is "+randomInt);
			
		radioAlarmType = (RadioGroup) findViewById(R.id.radioAlarmType2);
		myCalendar = Calendar.getInstance();
	    calnow = Calendar.getInstance();
	    calset = Calendar.getInstance();
	    
	      mydb = new DbHelper(this);
	      Bundle extras = getIntent().getExtras();
	      int Value = extras.getInt("id");
	      
	      if (Value > 0) {
	      Cursor rs = mydb.getData(Value);
          id_To_Update = Value;
          Log.i("shashank","Edit reminder id is "+id_To_Update);
          
          if(rs !=null && rs.getCount()>0 )
          {
           rs.moveToFirst();
          }
          
          
          note1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_NOTE));
          date1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_DATE));
          time1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_TIME));
          type1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_ALARMTYPE));
          randomIntsaved = rs.getInt(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_RANDOMINT));
          String status1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_STATUS));
          Log.i("shashank","randomIntsaved is "+randomIntsaved);
          
          String [] temp = time1.split(":");  
		  
	      int hour = Integer.parseInt(temp[0].trim());
			String s = temp[1].trim();
			String [] temp2 = s.split(" ");
	      
	      Log.i("shashank", "date = "+date1);
	      int minute = Integer.parseInt(temp2[0]);
	      
	      Log.i("shashank","am_pm = "+ temp2[1]);
	      
	      if (temp2[1].equals("PM")){
	    	  hour = hour+12;
	      }
			
    
	      Log.i("shashank","hour = "+hour+"minute = "+minute);
	      String [] temp1 = date1.split("/");
	      int day = Integer.parseInt(temp1[0].trim());  
	      int month = Integer.parseInt(temp1[1].trim());
	      int year = Integer.parseInt(temp1[2].trim());
	      GregorianCalendar calendar = new GregorianCalendar(year+2000,month-1, day);
	      int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
	      Log.i("shashank","day = "+day+"day_of_week = "+ day_of_week+" month = "+month +" year = "+year);
	      	month = month - 1;
	      	setDay(day);
	      	setMonth(month);
	    	setYear(year+2000);
	    	setHour(hour);
		    setMinute(minute);
		    setDay_of_week(day_of_week);
		    calset.set(Calendar.DAY_OF_MONTH, getDay());
		    calset.set(Calendar.MONTH, getMonth());
		    calset.set(Calendar.YEAR, getYear());
		    calset.set(Calendar.HOUR_OF_DAY,getHour());
		    calset.set(Calendar.MINUTE, getMinute());
		    flag1=1;flag2=1;
		    Log.i("shashank","Saved Calendar time is "+ calset.getTime());
          if (!rs.isClosed()) 
          {
             rs.close();
          }
          
          setStatus(status1);
          
	      note.setText((CharSequence)note1);
	      date.setText((CharSequence)date1);
	      time.setText((CharSequence)time1);
	      
          if (type1.equals("Once")) {
        	  radioAlarmType.check(radioButtonOnce.getId());
          	}
          if (type1.equals("Daily"))
          {
        	  radioAlarmType.check(radioButtonDaily.getId());
          }
          if (type1.equals("Weekly"))
          {
        	  radioAlarmType.check(radioButtonWeekly.getId());
          }
          if (type1.equals("Monthly"))
          {
        	  radioAlarmType.check(radioButtonMonthly.getId());
          }
	      }
	      final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

	  	    @Override
	  	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	  	            int dayOfMonth) {
	  	        // TODO Auto-generated method stub
	  	    	 
	  	    	Calendar caltemp = Calendar.getInstance();
	  	    	Calendar caltempnow = Calendar.getInstance();
	  	    	caltemp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	 		    caltemp.set(Calendar.MONTH, monthOfYear);
	 		    caltemp.set(Calendar.YEAR, year);
	 		    if (caltemp.before(caltempnow)) 
	 		    {
	 		    	AlertDialog alertDialog = new AlertDialog.Builder(EditReminder.this).create();
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
	 		    	date.setText(getDay() + "/" + mm + "/" + yy);
	 		    	setDateString((String)date.getText());
	 		    	Log.i("shashank","dateString = "+getDateString());
	 		    	flag1=1;
	 		    }
	  	    }

	  	};
	  	final TimePickerDialog.OnTimeSetListener time2 = new TimePickerDialog.OnTimeSetListener() {

	  	    @Override
	  	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	  	    	
	  		       setHour(hourOfDay);
	  		       setMinute(minute);
	  		       calset.set(Calendar.HOUR_OF_DAY,hourOfDay);
	 		       calset.set(Calendar.MINUTE, minute); 
	  		        Log.i("shashank","set time");
	  		        flag2 = 1;
	  		     
	  			   int hour;
	  			   String am_pm;
	  			   if (hourOfDay > 12) {
	  			    hour = hourOfDay - 12;
	  			    am_pm = "PM";
	  			   } else {
	  			    hour = hourOfDay;
	  			    am_pm = "AM";
	  			   }
	  			   time.setText(hour + " : " + minute + " " + am_pm);
	  			  }

	  	};
	      date.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		        	   new DatePickerDialog(EditReminder.this, date2, myCalendar
			                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
			                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		        }
		    });

		   time.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            new TimePickerDialog(EditReminder.this, time2, myCalendar
		                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),false
		                   ).show();
		        }
		    });
	      
	      button1.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) {
	        	int selectedId = radioAlarmType.getCheckedRadioButtonId();
	          	radioButton = (RadioButton) findViewById(selectedId);
	          	type = (String) radioButton.getText();
	          	Log.i("shashank","type = "+type);
	        	if(flag1==1 && flag2==1 && !note.getText().toString().isEmpty()) 
	        	{
	        	  boolean temp;
	        	  temp = sendAlarm(randomInt,type);
	        	  if (temp)
	        	  {
	        		  disableAlarm(randomIntsaved);
	        	  if(id_To_Update>0){
	        		  	setStatus("active");
			            if(mydb.updateContact(id_To_Update,note.getText().toString(), date.getText().toString(), time.getText().toString(),type,randomInt,getStatus())){
			              // Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
			            	Log.i("shashank","Updated");
			         
			            }		
			            else{
			              // Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
			            	Log.i("shashank","Not updated");
			            }
			         }
	        	  finish();
	        	  }
	        	  else {
	        			AlertDialog alertDialog = new AlertDialog.Builder(EditReminder.this).create();
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
	        		AlertDialog alertDialog = new AlertDialog.Builder(EditReminder.this).create();
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
	       	
	       	note.setText("");
	       	date.setText("");
	       	time.setText("");
	       	
	          }
	      });
	
	      note.setFocusable(false);
          note.setClickable(false);

          
          date.setFocusable(false); 
          date.setClickable(false);
          

         
          time.setFocusable(false); 
          time.setClickable(false);
          
          for (int i = 0; i < radioAlarmType.getChildCount(); i++) {
        	  radioAlarmType.getChildAt(i).setEnabled(false);
        	  }
          
          button1.setClickable(false);
          button2.setClickable(false);

	}

	public static Integer getOptimalSlotSize(Activity ctxt) {
		 Display display = ((WindowManager) ctxt
		 .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
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
	
	public void disableAlarm(int r)
	{
		Log.i("shashank","inside disable alarm random = "+ r);
		AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);         
		Intent myIntent = new Intent(EditReminder.this, AlarmReceiver.class);
		myIntent.putExtra("alarm_message", "O'Doyle Rules!");
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(EditReminder.this,
				r, myIntent, 0);         
		aManager.cancel(pendingIntent);
		
		Log.i("Shashank", "cancelling notification");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_reminder, menu);
		return true;
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
		Intent myIntent = new Intent(EditReminder.this, AlarmReceiver.class);
		myIntent.putExtra("alarm_message", "O'Doyle Rules!");
		 
		 myIntent.putExtra("content", note.getText().toString());
		 
		PendingIntent pendingIntent = PendingIntent.getBroadcast(EditReminder.this,
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
				Log.i("shashank", "before");
	    		myCalendar.add(Calendar.DAY_OF_MONTH,1);
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
	    		
	    	}
	    	else
	    	{
	    		Log.i("shashank", "after");
	    		myCalendar.add(Calendar.DAY_OF_MONTH,diffInDays);
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
				
	    	}
			
			

				future_date = true;
			
				  Log.i("shashank","inside send alarm random = "+ r+"type = "+type);
			Intent myIntent = new Intent(EditReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", note.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(EditReminder.this,
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
			Intent myIntent = new Intent(EditReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", note.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(EditReminder.this,
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
	    	{	myCalendar.add(Calendar.MONTH,diffInMonths);
	    		myCalendar.set(Calendar.DAY_OF_MONTH, getDay());
	    		myCalendar.set(Calendar.HOUR_OF_DAY, getHour());  
				myCalendar.set(Calendar.MINUTE, getMinute());
				myCalendar.set(Calendar.SECOND, 0);
				
	    	}

			

				future_date = true;
				
				
				  Log.i("shashank","inside send alarm random = "+ r+"type = "+type);
			Intent myIntent = new Intent(EditReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", note.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(EditReminder.this,
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
			Intent myIntent = new Intent(EditReminder.this, AlarmReceiver.class);
			myIntent.putExtra("alarm_message", "O'Doyle Rules!");
			 
			 myIntent.putExtra("content", note.getText().toString());
			 
			PendingIntent pendingIntent = PendingIntent.getBroadcast(EditReminder.this,
					r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			

			alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), 
					pendingIntent);	
			Log.i("shashank","Notification is set for"+myCalendar.getTime());
			Toast.makeText(getApplicationContext(), "Notification is set for"+myCalendar.getTime(),
					   Toast.LENGTH_SHORT).show();
			
			
			
			
		}

	
	
	return future_date;
	
	
	}	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch(item.getItemId()) 
		{
		case R.id.Edit_Reminder:
			
			  note.setEnabled(true);
		      note.setFocusableInTouchMode(true);
		      note.setClickable(true);
		      note.requestFocus();

		      date.setEnabled(true);
		      date.setFocusable(true);
		      date.setClickable(true);
		     

		      time.setEnabled(true);
		      time.setFocusable(true);
		      time.setClickable(true);
		     
		      
		      for (int i = 0; i < radioAlarmType.getChildCount(); i++) {
	        	  radioAlarmType.getChildAt(i).setEnabled(true);
	        	  }
		      button1.setClickable(true);
		      button2.setClickable(true);
		      
		      return true;
		case R.id.Delete_Reminder:	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		      builder.setMessage("Are you sure, you want to delete it.")
		      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		        	 Log.i("shashank", "deleting !!"+id_To_Update);
		            mydb.deleteContact(id_To_Update);
		            disableAlarm(randomIntsaved);
		            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
		      
		            finish();
		               
		         }
		      })
		      .setNegativeButton("No", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		            // User cancelled the dialog
		         }
		      });
		      AlertDialog d = builder.create();
		      d.setTitle("Are you sure");
		      d.show();
		
		      return true;
		case R.id.Stop_Reminder:
			if (getStatus().equals("active"))
			{
			AlertDialog.Builder builderstop = new AlertDialog.Builder(this);
		      builderstop.setMessage("Are you sure, you want to stop reminder.")
		      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		            
		  	
		     
		            disableAlarm(randomIntsaved);
		            Toast.makeText(getApplicationContext(), "Stopped Successfully", Toast.LENGTH_SHORT).show();  
		         	  if(id_To_Update>0){
		        		  	setStatus("inactive");
				            if(mydb.updateStatus(id_To_Update,getStatus())){
				             //  Toast.makeText(getApplicationContext(), "Updated status", Toast.LENGTH_SHORT).show();	
				            	Log.i("shashank","Updated status");
				         
				            }		
				            else{
				              // Toast.makeText(getApplicationContext(), "not Updated status", Toast.LENGTH_SHORT).show();
				            	Log.i("shashank","Not updated status");
				            }
				         }
		            
		         }
		      })
		      .setNegativeButton("No", new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int id) {
		            // User cancelled the dialog
		         }
		      });
		      AlertDialog dstop = builderstop.create();
		      dstop.setTitle("Are you sure");
		      dstop.show();
			}
			else 
			{
				AlertDialog alertDialog = new AlertDialog.Builder(EditReminder.this).create();
 		    	alertDialog.setTitle("Info");
 		    	alertDialog.setMessage("Reminder already stopped!");
 		    	alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
 		    			new DialogInterface.OnClickListener() {
  	    	        	public void onClick(DialogInterface dialog, int which) {
  	    	        		dialog.dismiss();
  	    	        	}
  	    	    	});
 		    	alertDialog.show();
			}
		   default:
		    	  return super.onOptionsItemSelected(item);
		}
		
	}
}