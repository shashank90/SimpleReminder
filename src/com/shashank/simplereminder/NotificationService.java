package com.shashank.simplereminder;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.shashank.simplereminder.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class NotificationService extends Service {
	static int x=0;
	private DbHelper mydb;
	SharedPreferences sharedpreferences;
	private NotificationManager notificationManager;
	ArrayList<Reminder> array_list;
	private MediaPlayer mMediaPlayer; 
	String status;
	public static final String sound = "soundKey";
	public static final String viber = "viberKey";
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent1, int flags, int startid)
	{	
		 mydb = new DbHelper(this);
         array_list = mydb.getAllReminders();
         int id=0;
		String content="";
		if(intent1!=null)
		content = intent1.getStringExtra("content");
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		Log.i("shashank","hour = "+hour);
		for( Reminder reminder: array_list)
        {
			String time = reminder.getTime();
			  String [] temp = time.split(":");  
			  
		      int reminderHour = Integer.parseInt(temp[0].trim());
				String s = temp[1].trim();
				String [] temp2 = s.split(" ");
	  
		      if (temp2[1].equals("PM")){
		    	  reminderHour = reminderHour+12;
		      }
		      
       	 	if(content.equals(reminder.getNote()))
       	 	{
       	 		id = reminder.getId();
       	 		if (reminder.getAlarmType().equals("Once"))
       	 		{
       	 			setStatus("inactive");
       	 			mydb.updateStatus(id,getStatus());
       	 		}
       	 	}
        }
	    Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", id);
        Log.i("shashank","id "+id+"note "+content);
        dataBundle.putString("note", content);
        
		Random randomGenerator = new Random();
		int r = randomGenerator.nextInt(1000);
		int random = randomGenerator.nextInt(1000);
		//Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

	   Intent intent = new Intent(this.getApplicationContext(), ViewReminder.class);
	   intent.putExtras(dataBundle);
	  
	  
       PendingIntent pIntent = PendingIntent.getActivity(this.getApplicationContext(), random, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      
       Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       
       sharedpreferences = getSharedPreferences(Settings.MyPREFERENCES, Context.MODE_PRIVATE);
		 if (sharedpreferences.contains(sound))
	      {
			 Log.i("shashank",sharedpreferences.getString(sound, ""));
			 if(sharedpreferences.getString(sound, "").equals("Default"))
				 
				 soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			 
			 if(sharedpreferences.getString(sound, "").equals("Good Morning"))
				 
				 soundUri = Uri.parse("android.resource://" + getPackageName() + "/"
			               + R.raw.goodmorning);
			 
			 if(sharedpreferences.getString(sound, "").equals("Metallic"))
				 
				 soundUri = Uri.parse("android.resource://" + getPackageName() + "/"
			               + R.raw.metallic);
			 
			 if(sharedpreferences.getString(sound, "").equals("Little Dwarf"))
				 
				 soundUri = Uri.parse("android.resource://" + getPackageName() + "/"
			               + R.raw.littledwarf);
			 
			 if(sharedpreferences.getString(sound, "").equals("Time has Come"))
				 
				 soundUri = Uri.parse("android.resource://" + getPackageName() + "/"
			               + R.raw.timecame);
	      }
		 
		 long[] viberPattern = new long[] { 50, 700, 400, 700, 400 };
		 if (sharedpreferences.contains(viber))
	      {
       
		 if(sharedpreferences.getString(viber, "").equals("Once"))
			 viberPattern = new long[] { 50, 700, 400 };
			 
		 
		 if(sharedpreferences.getString(viber, "").equals("Twice"))
			 viberPattern = new long[] { 50, 700, 400, 700, 400 };
			 
		 
		 if(sharedpreferences.getString(viber, "").equals("Thrice"))
			 viberPattern = new long[] { 50, 700, 400, 700, 400, 700, 400 };
	      }
		 
       Notification n  = new Notification.Builder(this)
    	
       .setContentTitle("Simple Reminder")
       .setContentText(content)
       .setSmallIcon(R.drawable.notification)
       .setContentIntent(pIntent)
       .setVibrate(viberPattern)
       .setSound(soundUri)
       .setAutoCancel(true)
      .build();
       
       

       notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);



       // If you want to hide the notification after it was selected, do the code below

        n.flags |= Notification.FLAG_AUTO_CANCEL;

        Log.i("shashank","inside Notification Service, random = "+ r);

       notificationManager.notify(r, n);
		return START_NOT_STICKY;
		
		
		}
	
	 
	 
       public void onDestroy() {
           // TODO Auto-generated method stub
           Log.i("SG","Alam Services Destroyed");
           super.onDestroy();
       }
}
