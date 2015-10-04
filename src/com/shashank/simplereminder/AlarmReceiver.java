package com.shashank.simplereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent!=null){
			//Toast.makeText(context, "inside AlarmReceiver", Toast.LENGTH_LONG).show();
		String s1;
		s1=intent.getStringExtra("content");
		   Intent myIntent = new Intent(context, NotificationService.class);
		   myIntent.putExtra("content", s1);
           context.startService(myIntent);
		}
		
	}
	
	

}
