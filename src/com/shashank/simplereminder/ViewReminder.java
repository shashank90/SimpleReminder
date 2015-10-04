package com.shashank.simplereminder;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.inmobi.monetization.IMBanner;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ViewReminder extends ActionBarActivity {
	TextView note;
	TextView date;
	TextView time;
	private RadioGroup radioAlarmType;
	
	private RadioButton radioButtonOnce;
	private RadioButton radioButtonDaily;
	private RadioButton radioButtonWeekly;
	private RadioButton radioButtonMonthly;
	private DbHelper mydb ;
	int id_To_Update = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reminder);
		Log.i("shashank","in onCreate()");
		  note = (TextView) findViewById(R.id.TextViewNote3);
	      date = (TextView) findViewById(R.id.TextViewDate3);
	      date.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	      time = (TextView) findViewById(R.id.TextViewTime3);
	      time.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	      radioButtonOnce = (RadioButton)findViewById(R.id.radioOnce3);
	      radioButtonDaily = (RadioButton)findViewById(R.id.radioDaily3);
	      radioButtonWeekly = (RadioButton)findViewById(R.id.radioWeekly3);
	      radioButtonMonthly = (RadioButton)findViewById(R.id.radioMonthly3);
	      IMBanner imbanner = new IMBanner(this, "483c881577c3454f9b5e885466170a96",getOptimalSlotSize(this));
		  	LinearLayout parent = (LinearLayout)findViewById(R.id.linearLayout24);
		  	parent.addView(imbanner);
		  	radioAlarmType = (RadioGroup) findViewById(R.id.radioAlarmType3);
		  	
		      mydb = new DbHelper(this);
		      Bundle extras = getIntent().getExtras();
		      int Value = extras.getInt("id");
		      String remNote = extras.getString("note");
		      boolean present = mydb.isDataPresent(remNote);
		      if (present)
		      {
		      if (Value > 0) {
		      Cursor rs = mydb.getData(Value);
	          id_To_Update = Value;
	          Log.i("shashank","View reminder id is "+id_To_Update);
	          
	          if(rs !=null && rs.getCount()>0 )
	          {
	           rs.moveToFirst();
	          }
	          
	          
	          String note1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_NOTE));
	          String date1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_DATE));
	          String time1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_TIME));
	          String type1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_ALARMTYPE));
	         
	          
	          
	          
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
		    
	          if (!rs.isClosed()) 
	          {
	             rs.close();
	          }
	          
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
		 }  
		      else {
		    	  
			      
			    	  note.setText((CharSequence)"Oops!! Looks like this reminder got deleted ");
			          note.setFocusable(false);
			          note.setClickable(false);
			          for (int i = 0; i < radioAlarmType.getChildCount(); i++) {
			        	  radioAlarmType.getChildAt(i).setEnabled(false);
			        	  }
			      
		      }
		      
		      note.setFocusable(false);
	          note.setClickable(false);
	          
	          date.setFocusableInTouchMode(false); 
	          date.setClickable(false);
	          
	          time.setFocusable(false); 
	          time.setClickable(false);
	          
		      for (int i = 0; i < radioAlarmType.getChildCount(); i++) {
	        	  radioAlarmType.getChildAt(i).setEnabled(false);
	        	  }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_reminder, menu);
		return true;
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

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_view_reminder);
		Log.i("shashank","in onResume()");
		  note = (TextView) findViewById(R.id.TextViewNote3);
	      date = (TextView) findViewById(R.id.TextViewDate3);
	      date.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	      time = (TextView) findViewById(R.id.TextViewTime3);
	      time.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	      radioButtonOnce = (RadioButton)findViewById(R.id.radioOnce3);
	      radioButtonDaily = (RadioButton)findViewById(R.id.radioDaily3);
	      radioButtonWeekly = (RadioButton)findViewById(R.id.radioWeekly3);
	      radioButtonMonthly = (RadioButton)findViewById(R.id.radioMonthly3);
	      IMBanner imbanner = new IMBanner(this, "483c881577c3454f9b5e885466170a96",getOptimalSlotSize(this));
		  	LinearLayout parent = (LinearLayout)findViewById(R.id.linearLayout24);
		  	parent.addView(imbanner);
		  	radioAlarmType = (RadioGroup) findViewById(R.id.radioAlarmType3);
		  	
		      mydb = new DbHelper(this);
		      Bundle extras = getIntent().getExtras();
		      int Value = extras.getInt("id");
		      String remNote = extras.getString("note");
		      boolean present = mydb.isDataPresent(remNote);
		      Log.i("shashank","rem Note = "+ remNote+" id is "+Value+" present "+present);
		      if (present)
		      {
		      if (Value > 0) {
		      Cursor rs = mydb.getData(Value);
	          id_To_Update = Value;
	          Log.i("shashank","View reminder id is "+id_To_Update);
	          
	          if(rs !=null && rs.getCount()>0 )
	          {
	           rs.moveToFirst();
	          }
	          
	          
	          String note1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_NOTE));
	          String date1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_DATE));
	          String time1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_TIME));
	          String type1 = rs.getString(rs.getColumnIndex(DbHelper.REMINDERS_COLUMN_ALARMTYPE));
	         
	          
	          
	          
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
		    
	          if (!rs.isClosed()) 
	          {
	             rs.close();
	          }
	          
	          note.setText((CharSequence)note1);
	       

	          date.setText((CharSequence)date1);
	       
	          
	          time.setText((CharSequence)time1);
	       
	          
	          for (int i = 0; i < radioAlarmType.getChildCount(); i++) {
	        	  radioAlarmType.getChildAt(i).setEnabled(true);
	        	  }
	         
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
		      }
		      else
		      {
		    	  note.setText((CharSequence)"Oops! Looks like this reminder got deleted ");
		          note.setFocusable(false);
		          note.setClickable(false);
		 
		      }
		      
		      note.setFocusable(false);
	          note.setClickable(false);
	          
	          date.setFocusableInTouchMode(false); 
	          date.setClickable(false);
	          
	          time.setFocusable(false); 
	          time.setClickable(false);
	          
		      for (int i = 0; i < radioAlarmType.getChildCount(); i++) {
	        	  radioAlarmType.getChildAt(i).setEnabled(false);
	        	  }

	}
}
