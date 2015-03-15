package com.shashank.simplereminder;



import com.shashank.simplereminder.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;


public class NotificationService extends Service {
	
	private NotificationManager notificationManager;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent1, int flags, int startid)
	{	
		String content="";
		if(intent1!=null)
		content = intent1.getStringExtra("content");
		
		
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

	   Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

       PendingIntent pIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	   
	  


       Notification n  = new Notification.Builder(this)
       .setContentTitle("Simple Reminder")
       .setContentText(content)
       .setSmallIcon(R.drawable.ic_launcher)
       .setContentIntent(pIntent)
       .setSound(soundUri)

       .setAutoCancel(true)
      .build();
       
       

       notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);



       // If you want to hide the notification after it was selected, do the code below

        n.flags |= Notification.FLAG_AUTO_CANCEL;



       notificationManager.notify(0, n);
		return START_NOT_STICKY;
		
		
		}
       public void onDestroy() {
           // TODO Auto-generated method stub
           Log.i("SG","Alam Services Destroyed");
           super.onDestroy();
       }
}
