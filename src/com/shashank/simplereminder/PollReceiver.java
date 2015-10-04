package com.shashank.simplereminder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


public class PollReceiver extends BroadcastReceiver {
  
  private DbHelper mydb;
  ArrayList<Reminder> array_list;
  @Override
  public void onReceive(Context ctxt, Intent i) {
	  
	  Log.i("shashank","inside onReceive after boot complete");
	  int randomInt=0;
	  String alarmType = null;
	  
	  String date=null;
	  String note = null;
	  String time = null;
	  String status = null;
	  int id = 0;
	  mydb =  new DbHelper(ctxt);
	  array_list = mydb.getAllReminders();
	
	
	  for (Reminder reminder : array_list) {
		  time = reminder.getTime();
		  date = reminder.getDate();
		  note = reminder.getNote();
		  randomInt = reminder.getRandomInt();
		  alarmType = reminder.getAlarmType();
		  status = reminder.getStatus();
		  id = reminder.getId();
		  if (status.equals("active")) 
		  {  
		  String [] temp = time.split(":");  
		  
	      int hour = Integer.parseInt(temp[0].trim());
			String s = temp[1].trim();
			String [] temp2 = s.split(" ");
	      
	      
	      int minute = Integer.parseInt(temp2[0]);
	      
	      Log.i("shashank","am_pm = "+ temp2[1]);
	      
	      if (temp2[1].equals("PM")){
	    	  hour = hour+12;
	      }
			
    
			  
			
			
	      Log.i("shashank","hour = "+hour+"minute = "+minute);
	      String [] temp1 = date.split("/");
	      int day = Integer.parseInt(temp1[0].trim());  
	      int month = Integer.parseInt(temp1[1].trim());
	      int year = Integer.parseInt(temp1[2].trim());
	      GregorianCalendar calendar = new GregorianCalendar(year+2000,month-1, day);
	      int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
	      Log.i("shashank","day = "+day+"day_of_week = "+ day_of_week+" month = "+month +" year = "+year);
	      
	     
	      
	      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
	      {
	    	  scheduleAlarms(ctxt,note,hour,minute,day,day_of_week,month,year+2000,randomInt, alarmType, id);
	      }
	      else
	      {
	    	  scheduleAlarmsNew(ctxt,note,hour,minute,day,day_of_week,month,year+2000,randomInt, alarmType, id);
	      }
		  
		  }
	  }
    
  }

   void scheduleAlarmsNew(Context ctxt, String note,int hour, int minute, int day, int day_of_week ,int month,int year,int r, String type, int id) 
  {
    AlarmManager mgr=
        (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
    Calendar myCalendar=null;
    boolean future_event = false;
    Calendar calnow = Calendar.getInstance();
    Calendar calset = Calendar.getInstance();
    myCalendar = Calendar.getInstance();  
    month = month-1;
    int Cur_month = calnow.get(Calendar.MONTH);
	int Cur_day = calnow.get(Calendar.DAY_OF_MONTH);
	int Cur_hour = calnow.get(Calendar.HOUR_OF_DAY);
	int Cur_minute = calnow.get(Calendar.MINUTE);
	int Cur_day_of_week = calnow.get(Calendar.DAY_OF_WEEK);
	int Cur_year= calnow.get(Calendar.YEAR);
    int currentMonth=myCalendar.get(Calendar.MONTH);
   
    Log.i("shashank","set day_of_week = "+ day_of_week);
    Log.i("shashank","cur day_of_week = "+ calnow.get(Calendar.DAY_OF_WEEK));
    
    calset.set(Calendar.DAY_OF_MONTH, day);
    calset.set(Calendar.MONTH, month);
    calset.set(Calendar.YEAR, year);
    calset.set(Calendar.HOUR_OF_DAY,hour);
    calset.set(Calendar.MINUTE, minute);
  
  
    
    long miliSecondForDate1 = calnow.getTimeInMillis();
    long miliSecondForDate2 = calset.getTimeInMillis();

    // Calculate the difference in millisecond between two dates
    long diffInMilis = miliSecondForDate2 - miliSecondForDate1;


    long diffInSecond = diffInMilis / 1000;
    long diffInMinute = diffInMilis / (60 * 1000);
    long diffInHour = diffInMilis / (60 * 60 * 1000);
    //long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
    //long diffInWeeks = diffInMilis / (7*24 * 60 * 60 * 1000);
    int diffInWeeks ;
    month = month+1;
    Cur_month = Cur_month + 1;
    String calsetString = year+"-"+month+"-"+day;
    String calnowString = Cur_year+"-"+Cur_month+"-"+Cur_day;
    System.out.println(calsetString);
    System.out.println(calnowString);
    DateTime dateTimeset = new DateTime(calsetString);
    DateTime dateTimenow = new DateTime(calnowString);
    int diffInDays = Days.daysBetween(dateTimenow.toLocalDate(), dateTimeset.toLocalDate()).getDays();
	Log.i("shashank","diffIndays = "+diffInDays);
    diffInWeeks = Weeks.weeksBetween(dateTimenow,dateTimeset).getWeeks();
    System.out.println("diffInWeeks "+diffInWeeks);
    month = month - 1;
    Cur_month = Cur_month - 1;

    long diffInMonths = ( year - Cur_year) * 12 + (month - Cur_month);

	if (type.equals("Once")) 
	{
		//Toast.makeText(ctxt, "Booting completed!!!", Toast.LENGTH_LONG).show();
		 	
		  if(calset.after(calnow))
		  {
		
			  myCalendar.add(Calendar.MONTH,((int)diffInMonths));
			  myCalendar.set(Calendar.DAY_OF_MONTH,day);
			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
			  myCalendar.set(Calendar.MINUTE, minute);
			  myCalendar.set(Calendar.SECOND, 0);
			  Log.i("shashank","Set future month");
			  Log.i("shashank","this was before");
			  future_event = true;
		  }
		  else
		  {
			  Log.i("shashank", "setting Once activity status to inactive");
			  mydb.updateStatus(id, "inactive");
		  }			
		
		//Toast.makeText(ctxt, "future_event = "+future_event+"Once  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
		
		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
				Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
				myIntent.putExtra("content", note);
				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				 Log.i("shashank","future_event = "+future_event+"Once content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
				 
			if (future_event){
				Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
					mgr.setExact(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),
					pendingIntent);	
			 }
		
	}
	else if (type.equals("Daily"))
	{
		myCalendar = Calendar.getInstance();
		 
		  if(calset.before(calnow))
		  {
			  Log.i("shashank","before date ");
			  if (hour < Cur_hour)
				{	
					
				  	myCalendar.add(Calendar.DAY_OF_MONTH, 1);
					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					myCalendar.set(Calendar.MINUTE, minute);
					myCalendar.set(Calendar.SECOND, 0);
					
					future_event = true;
				}
				if (hour > Cur_hour)
				{
					//check if day of month has to be set
					Log.i("shashank","on same day");
					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					myCalendar.set(Calendar.MINUTE, minute);
					myCalendar.set(Calendar.SECOND, 0);
					
					future_event = true;
				}
		  
				if (hour == Cur_hour)
				{
					if(minute > Cur_minute)
					{
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						Log.i("shashank","set next min");
						
						future_event = true;
					}
					if(minute == Cur_minute)
					{
						
						
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
						myCalendar.set(Calendar.MINUTE, minute+1);
						myCalendar.set(Calendar.SECOND, 0);
						Log.i("shashank","set cur min");
						
						future_event = true;
					}
					if (minute < Cur_minute)
					{			 
						myCalendar.add(Calendar.DAY_OF_MONTH, 1);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						Log.i("shashank","set upcoming min");
						
						future_event = true;
					}
				}

		  }
		  
		  else {
		
		
			  	myCalendar.add(Calendar.DAY_OF_MONTH,diffInDays);
	    		myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
				myCalendar.set(Calendar.MINUTE, minute);
				myCalendar.set(Calendar.SECOND, 0);
				
				future_event = true;
			  Log.i("shashank","Set future week");
		  }
		  
		
		//Toast.makeText(ctxt, "future_event = "+future_event+"daily  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
				Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
				myIntent.putExtra("content", note);
				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				 Log.i("shashank","future_event = "+future_event+"daily content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
				if (future_event) 
				{
					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
					mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
						pendingIntent);
				}
		
				
	}
	else if (type.equals("Weekly")) {
	
	myCalendar = Calendar.getInstance();
		
	  if (calset.before(calnow)){

		  
		 if (day_of_week < Cur_day_of_week)
		 {
			  Log.i("shashank","Set next week");
			  myCalendar.add(Calendar.WEEK_OF_YEAR, 1);
			  myCalendar.set(Calendar.DAY_OF_WEEK,day_of_week);
			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
			  myCalendar.set(Calendar.MINUTE, minute);
			  myCalendar.set(Calendar.SECOND, 0);
			  
			  future_event = true;
		 }
			  else if (day_of_week == Cur_day_of_week)
		  {
			  Log.i("shashank","Set today");
			  Log.i("shashank","cal set day of week"+ day_of_week);
			  
			  if (hour < Cur_hour)
				{	
					System.out.println("before hours today; weekly");
					myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					myCalendar.set(Calendar.MINUTE, minute);
					myCalendar.add(Calendar.WEEK_OF_YEAR, 1);
					myCalendar.set(Calendar.SECOND, 0);
					
					future_event = true;
				}
				if (hour > Cur_hour)
				{
					
					myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					myCalendar.set(Calendar.MINUTE, minute);
					myCalendar.set(Calendar.SECOND, 0);
					
					future_event = true;
				}
				if (hour == Cur_hour)
				{
					if(minute > Cur_minute)
					{
						
						myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","set next min");
						future_event = true;
					}
					if(minute == Cur_minute)
					{
						
						myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
						myCalendar.set(Calendar.MINUTE, minute+1);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","set cur min");
						future_event = true;
					}
					if (minute < Cur_minute)
					{
				
						myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour); 
						myCalendar.add(Calendar.WEEK_OF_YEAR, 1);						
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","set upcoming min");
						future_event = true;
					}
				}
		
		  }
			  else {
				  
					myCalendar.set(Calendar.DAY_OF_WEEK, day_of_week);
					myCalendar.set(Calendar.HOUR_OF_DAY, hour);
					myCalendar.set(Calendar.MINUTE, minute);
					myCalendar.set(Calendar.SECOND, 0);
					
					future_event = true;
			  }
	
			  
	  }
	  else{
		  //set my calendar to hour and minute and add week
		  Log.i("shashank","Set in future");
		  //myCalendar.add(Calendar.WEEK_OF_YEAR, (int)(diffInWeeks));
		  Log.i("shashank","set day of week = " + day_of_week);
		  myCalendar.add(Calendar.DAY_OF_MONTH,((int)diffInDays));
		  myCalendar.set(Calendar.DAY_OF_WEEK,day_of_week);
		  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
		  myCalendar.set(Calendar.MINUTE, minute);
		  myCalendar.set(Calendar.SECOND, 0);
		  
		  future_event = true;
	  }
	  
		//Toast.makeText(ctxt, "future_event = "+future_event+"weekly  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_WEEK)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
				Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
				myIntent.putExtra("content", note);
				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				 Log.i("shashank","future_event = "+future_event+"weekly content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_WEEK)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
				if (future_event) 
				{
					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
					mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7,
						pendingIntent);
				}
		
	}
	else if (type.equals("Monthly")) {
		
		myCalendar = Calendar.getInstance();
		
		  if(calset.before(calnow))
		  {
			 
				if (day > Cur_day)
				{	
					myCalendar.set(Calendar.MONTH, Cur_month);
					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					myCalendar.set(Calendar.MINUTE, minute);
					myCalendar.set(Calendar.DAY_OF_MONTH, day);
					myCalendar.set(Calendar.SECOND, 0);
					
					Log.i("shashank","future day same month "+Cur_month);
					future_event = true;
				}
				if (day < Cur_day)
				  {
						myCalendar.add(Calendar.MONTH, 1);
						myCalendar.set(Calendar.DAY_OF_MONTH, day);
						myCalendar.set(Calendar.HOUR_OF_DAY, hour);
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","for anything else, add month");
						future_event=true;
				  }
			  if (day == Cur_day) 
			  {
				  if (hour < Cur_hour)
				  {	
					
					  myCalendar.add(Calendar.MONTH, 1);
					  myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
					  myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					  myCalendar.set(Calendar.MINUTE, minute);
					  myCalendar.set(Calendar.SECOND, 0);
					
					  future_event = true;
				  }
				  if (hour > Cur_hour)
				  {
					  
					  myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
					  myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
					  myCalendar.set(Calendar.MINUTE, minute);
					  myCalendar.set(Calendar.SECOND, 0);
					  
					  future_event = true;
				  }
		  
				  if (hour == Cur_hour)
				  {
					if(minute > Cur_minute)
					{
						myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","set next min");
						future_event = true;
					}
					if(minute == Cur_minute)
					{
						
						myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
						myCalendar.set(Calendar.MINUTE, minute+1);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","set cur min");
						future_event = true;
					}
					if (minute < Cur_minute)
					{			 
						myCalendar.add(Calendar.MONTH, 1);
						myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);
						myCalendar.set(Calendar.MINUTE, minute);
						myCalendar.set(Calendar.SECOND, 0);
						
						Log.i("shashank","set upcoming min");
						future_event = true;
					}
				  }
			  }
		  
			  
		  }
		  else
		  {
			  myCalendar.add(Calendar.MONTH,((int)diffInMonths));
			  myCalendar.set(Calendar.DAY_OF_MONTH,day);
			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
			  myCalendar.set(Calendar.MINUTE, minute);
			  myCalendar.set(Calendar.SECOND, 0);
			  future_event = true;
			  Log.i("shashank","Set future month");
		  }
	

		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
	//	Toast.makeText(ctxt, "future_event = "+future_event+"monthly  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
		Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
				myIntent.putExtra("content", note);
				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				 Log.i("shashank","future_event = "+future_event+"monthly  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
		
		if (currentMonth == Calendar.JANUARY || currentMonth == Calendar.MARCH || currentMonth == Calendar.MAY || currentMonth == Calendar.JULY 
	            || currentMonth == Calendar.AUGUST || currentMonth == Calendar.OCTOBER || currentMonth == Calendar.DECEMBER){
				if (future_event){
					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
					mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 31, pendingIntent);
			}
	    }
	    if (currentMonth == Calendar.APRIL || currentMonth == Calendar.JUNE || currentMonth == Calendar.SEPTEMBER 
	            || currentMonth == Calendar.NOVEMBER){
				if(future_event) {
					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
					mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);
				}	
	        }


	    if  (currentMonth == Calendar.FEBRUARY){//for feburary month)
	        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();    
	            if(cal.isLeapYear(Calendar.YEAR)){//for leap year feburary month  
				if(future_event) {
					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
	                mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 29, pendingIntent);
					}
	            }
	            else{ //for non leap year feburary month
				if(future_event) {
					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
	                mgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 28, pendingIntent);
					}
	            }
	    }
				
		
	}
	else {

		  if(calset.after(calnow))
		  {
		
			  myCalendar.add(Calendar.MONTH,((int)diffInMonths));
			  myCalendar.set(Calendar.DAY_OF_MONTH,day);
			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
			  myCalendar.set(Calendar.MINUTE, minute);
			  myCalendar.set(Calendar.SECOND, 0);
			  
			  System.out.println("Set future month");
			  System.out.println("this was before");
			  future_event = true;
		  }
		  else
		  {
			  Log.i("shashank", "setting Once activity status to inactive");
			  mydb.updateStatus(id, "inactive");
			  //set inactive
		  }
			
	
	//Toast.makeText(ctxt, "future_event = "+future_event+"default(else)  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
	
	Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
			Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
			myIntent.putExtra("content", note);
			 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
						r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			 Log.i("shashank","future_event = "+future_event+"default(else) content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
		if (future_event){
				Log.i("shashank","Notification has been set for "+ myCalendar.getTime());
				mgr.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),
				pendingIntent);	
		 }

	}
  }	
   
   void scheduleAlarms(Context ctxt, String note,int hour, int minute, int day, int day_of_week ,int month,int year,int r, String type, int id) 
   {
     AlarmManager mgr=
         (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
     Calendar myCalendar=null;
     boolean future_event = false;
     Calendar calnow = Calendar.getInstance();
     Calendar calset = Calendar.getInstance();
     myCalendar = Calendar.getInstance();  
     month = month-1;
     int Cur_month = calnow.get(Calendar.MONTH);
 	int Cur_day = calnow.get(Calendar.DAY_OF_MONTH);
 	int Cur_hour = calnow.get(Calendar.HOUR_OF_DAY);
 	int Cur_minute = calnow.get(Calendar.MINUTE);
 	int Cur_day_of_week = calnow.get(Calendar.DAY_OF_WEEK);
 	int Cur_year= calnow.get(Calendar.YEAR);
     int currentMonth=myCalendar.get(Calendar.MONTH);
    
     Log.i("shashank","set day_of_week = "+ day_of_week);
     Log.i("shashank","cur day_of_week = "+ calnow.get(Calendar.DAY_OF_WEEK));
     
     calset.set(Calendar.DAY_OF_MONTH, day);
     calset.set(Calendar.MONTH, month);
     calset.set(Calendar.YEAR, year);
     calset.set(Calendar.HOUR_OF_DAY,hour);
     calset.set(Calendar.MINUTE, minute);
   
   
     
     long miliSecondForDate1 = calnow.getTimeInMillis();
     long miliSecondForDate2 = calset.getTimeInMillis();

     // Calculate the difference in millisecond between two dates
     long diffInMilis = miliSecondForDate2 - miliSecondForDate1;


     long diffInSecond = diffInMilis / 1000;
     long diffInMinute = diffInMilis / (60 * 1000);
     long diffInHour = diffInMilis / (60 * 60 * 1000);
     //long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
     //long diffInWeeks = diffInMilis / (7*24 * 60 * 60 * 1000);
     int diffInWeeks ;
     month = month+1;
     Cur_month = Cur_month + 1;
     String calsetString = year+"-"+month+"-"+day;
     String calnowString = Cur_year+"-"+Cur_month+"-"+Cur_day;
     System.out.println(calsetString);
     System.out.println(calnowString);
     DateTime dateTimeset = new DateTime(calsetString);
     DateTime dateTimenow = new DateTime(calnowString);
     int diffInDays = Days.daysBetween(dateTimenow.toLocalDate(), dateTimeset.toLocalDate()).getDays();
 	Log.i("shashank","diffIndays = "+diffInDays);
     diffInWeeks = Weeks.weeksBetween(dateTimenow,dateTimeset).getWeeks();
     System.out.println("diffInWeeks "+diffInWeeks);
     month = month - 1;
     Cur_month = Cur_month - 1;

     long diffInMonths = ( year - Cur_year) * 12 + (month - Cur_month);

 	if (type.equals("Once")) 
 	{
 		//Toast.makeText(ctxt, "Booting completed!!!", Toast.LENGTH_LONG).show();
 		 	
 		  if(calset.after(calnow))
 		  {
 		
 			  myCalendar.add(Calendar.MONTH,((int)diffInMonths));
 			  myCalendar.set(Calendar.DAY_OF_MONTH,day);
 			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
 			  myCalendar.set(Calendar.MINUTE, minute);
 			  myCalendar.set(Calendar.SECOND, 0);
 			  Log.i("shashank","Set future month");
 			  Log.i("shashank","this was before");
 			  future_event = true;
 		  }
 		  else
 		  {
 			  Log.i("shashank", "setting Once activity status to inactive");
 			  mydb.updateStatus(id, "inactive");
 		  }			
 		
 		//Toast.makeText(ctxt, "future_event = "+future_event+"Once  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
 		
 		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
 				Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
 				myIntent.putExtra("content", note);
 				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
 							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
 				 Log.i("shashank","future_event = "+future_event+"Once content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
 				 
 			if (future_event){
 				Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 					mgr.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),
 					pendingIntent);	
 			 }
 		
 	}
 	else if (type.equals("Daily"))
 	{
 		myCalendar = Calendar.getInstance();
 		 
 		  if(calset.before(calnow))
 		  {
 			  Log.i("shashank","before date ");
 			  if (hour < Cur_hour)
 				{	
 					
 				  	myCalendar.add(Calendar.DAY_OF_MONTH, 1);
 					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					myCalendar.set(Calendar.MINUTE, minute);
 					myCalendar.set(Calendar.SECOND, 0);
 					
 					future_event = true;
 				}
 				if (hour > Cur_hour)
 				{
 					//check if day of month has to be set
 					Log.i("shashank","on same day");
 					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					myCalendar.set(Calendar.MINUTE, minute);
 					myCalendar.set(Calendar.SECOND, 0);
 					
 					future_event = true;
 				}
 		  
 				if (hour == Cur_hour)
 				{
 					if(minute > Cur_minute)
 					{
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						Log.i("shashank","set next min");
 						
 						future_event = true;
 					}
 					if(minute == Cur_minute)
 					{
 						
 						
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
 						myCalendar.set(Calendar.MINUTE, minute+1);
 						myCalendar.set(Calendar.SECOND, 0);
 						Log.i("shashank","set cur min");
 						
 						future_event = true;
 					}
 					if (minute < Cur_minute)
 					{			 
 						myCalendar.add(Calendar.DAY_OF_MONTH, 1);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						Log.i("shashank","set upcoming min");
 						
 						future_event = true;
 					}
 				}

 		  }
 		  
 		  else {
 		
 		
 			  	myCalendar.add(Calendar.DAY_OF_MONTH,diffInDays);
 	    		myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 				myCalendar.set(Calendar.MINUTE, minute);
 				myCalendar.set(Calendar.SECOND, 0);
 				
 				future_event = true;
 			  Log.i("shashank","Set future week");
 		  }
 		  
 		
 		//Toast.makeText(ctxt, "future_event = "+future_event+"daily  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
 		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
 				Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
 				myIntent.putExtra("content", note);
 				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
 							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
 				 Log.i("shashank","future_event = "+future_event+"daily content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
 				if (future_event) 
 				{
 					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 					mgr.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
 						pendingIntent);
 				}
 		
 				
 	}
 	else if (type.equals("Weekly")) {
 	
 	myCalendar = Calendar.getInstance();
 		
 	  if (calset.before(calnow)){

 		  
 		 if (day_of_week < Cur_day_of_week)
 		 {
 			  Log.i("shashank","Set next week");
 			  myCalendar.add(Calendar.WEEK_OF_YEAR, 1);
 			  myCalendar.set(Calendar.DAY_OF_WEEK,day_of_week);
 			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
 			  myCalendar.set(Calendar.MINUTE, minute);
 			  myCalendar.set(Calendar.SECOND, 0);
 			  
 			  future_event = true;
 		 }
 			  else if (day_of_week == Cur_day_of_week)
 		  {
 			  Log.i("shashank","Set today");
 			  Log.i("shashank","cal set day of week"+ day_of_week);
 			  
 			  if (hour < Cur_hour)
 				{	
 					System.out.println("before hours today; weekly");
 					myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
 					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					myCalendar.set(Calendar.MINUTE, minute);
 					myCalendar.add(Calendar.WEEK_OF_YEAR, 1);
 					myCalendar.set(Calendar.SECOND, 0);
 					
 					future_event = true;
 				}
 				if (hour > Cur_hour)
 				{
 					
 					myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
 					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					myCalendar.set(Calendar.MINUTE, minute);
 					myCalendar.set(Calendar.SECOND, 0);
 					
 					future_event = true;
 				}
 				if (hour == Cur_hour)
 				{
 					if(minute > Cur_minute)
 					{
 						
 						myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","set next min");
 						future_event = true;
 					}
 					if(minute == Cur_minute)
 					{
 						
 						myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
 						myCalendar.set(Calendar.MINUTE, minute+1);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","set cur min");
 						future_event = true;
 					}
 					if (minute < Cur_minute)
 					{
 				
 						myCalendar.set(Calendar.DAY_OF_WEEK, Cur_day_of_week);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour); 
 						myCalendar.add(Calendar.WEEK_OF_YEAR, 1);						
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","set upcoming min");
 						future_event = true;
 					}
 				}
 		
 		  }
 			  else {
 				  
 					myCalendar.set(Calendar.DAY_OF_WEEK, day_of_week);
 					myCalendar.set(Calendar.HOUR_OF_DAY, hour);
 					myCalendar.set(Calendar.MINUTE, minute);
 					myCalendar.set(Calendar.SECOND, 0);
 					
 					future_event = true;
 			  }
 	
 			  
 	  }
 	  else{
 		  //set my calendar to hour and minute and add week
 		  Log.i("shashank","Set in future");
 		  //myCalendar.add(Calendar.WEEK_OF_YEAR, (int)(diffInWeeks));
 		  Log.i("shashank","set day of week = " + day_of_week);
 		  myCalendar.add(Calendar.DAY_OF_MONTH,((int)diffInDays));
 		  myCalendar.set(Calendar.DAY_OF_WEEK,day_of_week);
 		  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
 		  myCalendar.set(Calendar.MINUTE, minute);
 		  myCalendar.set(Calendar.SECOND, 0);
 		  
 		  future_event = true;
 	  }
 	  
 		//Toast.makeText(ctxt, "future_event = "+future_event+"weekly  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_WEEK)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
 		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
 				Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
 				myIntent.putExtra("content", note);
 				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
 							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
 				 Log.i("shashank","future_event = "+future_event+"weekly content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_WEEK)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
 				if (future_event) 
 				{
 					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 					mgr.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7,
 						pendingIntent);
 				}
 			
 	}
 	else if (type.equals("Monthly")) {
 		
 		myCalendar = Calendar.getInstance();
 		
 		  if(calset.before(calnow))
 		  {
 			 
 				if (day > Cur_day)
 				{	
 					myCalendar.set(Calendar.MONTH, Cur_month);
 					myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					myCalendar.set(Calendar.MINUTE, minute);
 					myCalendar.set(Calendar.DAY_OF_MONTH, day);
 					myCalendar.set(Calendar.SECOND, 0);
 					
 					Log.i("shashank","future day same month "+Cur_month);
 					future_event = true;
 				}
 				if (day < Cur_day)
 				  {
 						myCalendar.add(Calendar.MONTH, 1);
 						myCalendar.set(Calendar.DAY_OF_MONTH, day);
 						myCalendar.set(Calendar.HOUR_OF_DAY, hour);
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","for anything else, add month");
 						future_event=true;
 				  }
 			  if (day == Cur_day) 
 			  {
 				  if (hour < Cur_hour)
 				  {	
 					
 					  myCalendar.add(Calendar.MONTH, 1);
 					  myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
 					  myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					  myCalendar.set(Calendar.MINUTE, minute);
 					  myCalendar.set(Calendar.SECOND, 0);
 					
 					  future_event = true;
 				  }
 				  if (hour > Cur_hour)
 				  {
 					  
 					  myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
 					  myCalendar.set(Calendar.HOUR_OF_DAY, hour);  
 					  myCalendar.set(Calendar.MINUTE, minute);
 					  myCalendar.set(Calendar.SECOND, 0);
 					  
 					  future_event = true;
 				  }
 		  
 				  if (hour == Cur_hour)
 				  {
 					if(minute > Cur_minute)
 					{
 						myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","set next min");
 						future_event = true;
 					}
 					if(minute == Cur_minute)
 					{
 						
 						myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);  
 						myCalendar.set(Calendar.MINUTE, minute+1);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","set cur min");
 						future_event = true;
 					}
 					if (minute < Cur_minute)
 					{			 
 						myCalendar.add(Calendar.MONTH, 1);
 						myCalendar.set(Calendar.DAY_OF_MONTH, Cur_day);
 						myCalendar.set(Calendar.HOUR_OF_DAY, Cur_hour);
 						myCalendar.set(Calendar.MINUTE, minute);
 						myCalendar.set(Calendar.SECOND, 0);
 						
 						Log.i("shashank","set upcoming min");
 						future_event = true;
 					}
 				  }
 			  }
 		  
 			  
 		  }
 		  else
 		  {
 			  myCalendar.add(Calendar.MONTH,((int)diffInMonths));
 			  myCalendar.set(Calendar.DAY_OF_MONTH,day);
 			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
 			  myCalendar.set(Calendar.MINUTE, minute);
 			  myCalendar.set(Calendar.SECOND, 0);
 			  future_event = true;
 			  Log.i("shashank","Set future month");
 		  }
 	

 		Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
 	//	Toast.makeText(ctxt, "future_event = "+future_event+"monthly  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
 		Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
 				myIntent.putExtra("content", note);
 				 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
 							r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
 				 Log.i("shashank","future_event = "+future_event+"monthly  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
 		
 		if (currentMonth == Calendar.JANUARY || currentMonth == Calendar.MARCH || currentMonth == Calendar.MAY || currentMonth == Calendar.JULY 
 	            || currentMonth == Calendar.AUGUST || currentMonth == Calendar.OCTOBER || currentMonth == Calendar.DECEMBER){
 				if (future_event){
 					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 					mgr.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 31, pendingIntent);
 			}
 	    }
 	    if (currentMonth == Calendar.APRIL || currentMonth == Calendar.JUNE || currentMonth == Calendar.SEPTEMBER 
 	            || currentMonth == Calendar.NOVEMBER){
 				if(future_event) {
 					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 					mgr.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);
 				}	
 	        }


 	    if  (currentMonth == Calendar.FEBRUARY){//for feburary month)
 	        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();    
 	            if(cal.isLeapYear(Calendar.YEAR)){//for leap year feburary month  
 				if(future_event) {
 					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 	                mgr.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 29, pendingIntent);
 					}
 	            }
 	            else{ //for non leap year feburary month
 				if(future_event) {
 					Log.i("shashank","Note = "+note+" Notification has been set for "+ myCalendar.getTime());
 	                mgr.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 28, pendingIntent);
 					}
 	            }
 	    }
 				
 		
 	}
 	else {

 		  if(calset.after(calnow))
 		  {
 		
 			  myCalendar.add(Calendar.MONTH,((int)diffInMonths));
 			  myCalendar.set(Calendar.DAY_OF_MONTH,day);
 			  myCalendar.set(Calendar.HOUR_OF_DAY,hour);
 			  myCalendar.set(Calendar.MINUTE, minute);
 			  myCalendar.set(Calendar.SECOND, 0);
 			  
 			  System.out.println("Set future month");
 			  System.out.println("this was before");
 			  future_event = true;
 		  }
 		  else
 		  {
 			  Log.i("shashank", "setting Once activity status to inactive");
 			  mydb.updateStatus(id, "inactive");
 			  //set inactive
 		  }
 			
 	
 	//Toast.makeText(ctxt, "future_event = "+future_event+"default(else)  content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
 	
 	Log.i("shashank","inside fetching from db and resetting alarm random = "+ r+"type = "+type);
 			Intent myIntent=new Intent(ctxt, AlarmReceiver.class);
 			myIntent.putExtra("content", note);
 			 PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt,
 						r, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
 			 Log.i("shashank","future_event = "+future_event+"default(else) content = "+note +"day ="+myCalendar.get(Calendar.DAY_OF_MONTH)+ "month = "+myCalendar.get(Calendar.MONTH)+"hour ="+myCalendar.get(Calendar.HOUR_OF_DAY)+"minute ="+myCalendar.get(Calendar.MINUTE));
 		if (future_event){
 				Log.i("shashank","Notification has been set for "+ myCalendar.getTime());
 				mgr.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),
 				pendingIntent);	
 		 }

 	  }
   }	
}