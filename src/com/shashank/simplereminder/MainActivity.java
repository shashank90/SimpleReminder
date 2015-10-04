package com.shashank.simplereminder;




import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.InMobi.LOG_LEVEL;
import com.shashank.simplereminder.R;

import android.os.Bundle;
import android.os.Parcelable;





import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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


public class MainActivity extends ActionBarActivity {
	private ListView obj;
	AdapterReminder adReminder;
	   DbHelper mydb;
	   ArrayList<Reminder> array_list;
	   private static final String LIST_STATE = "listState";
	   private Parcelable mListState = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_main);
    	 //getApplicationContext().deleteDatabase(DbHelper.DATABASE_NAME);
    	 
    	 mydb = new DbHelper(this);
         array_list = mydb.getAllReminders();
         adReminder= new AdapterReminder (MainActivity.this, R.layout.row_layout, array_list);
         InMobi.initialize(this, "483c881577c3454f9b5e885466170a96");
         InMobi.setLogLevel(LOG_LEVEL.DEBUG);
         
         if (checkPlayServices())
         {
        	 Log.i("shashank","yes it's there!!");
         }
         else {
        	 Log.i("shashank","Not there!!");
         }
         Log.i("shashank","onCreate");
         obj = (ListView)findViewById(R.id.listView1);
         obj.setEmptyView(findViewById( R.id.empty_list_view ));
         obj.setAdapter(adReminder);
         obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
               // TODO Auto-generated method stub
               int id_To_Search = arg2;
              
               Reminder value=array_list.get(id_To_Search);
               
               Bundle dataBundle = new Bundle();
               dataBundle.putInt("id", value.getId());
               
               Log.i("shashank","MainActivity id is "+value.getId());
               
               Intent intent = new Intent(getApplicationContext(),SetReminder.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
               intent.putExtras(dataBundle);
               startActivity(intent);
             
            }
         });
    	 


	}
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  switch (requestCode) {
	    case REQUEST_CODE_RECOVER_PLAY_SERVICES:
	      if (resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, "Google Play Services must be installed.",
	            Toast.LENGTH_SHORT).show();
	        finish();
	      }
	      return;
	  }
	  super.onActivityResult(requestCode, resultCode, data);
	}
	private boolean checkPlayServices() {
	    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    Log.i("shashank","status "+ status);
	    if (status != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
	          showErrorDialog(status);
	        } else {
	          Toast.makeText(this, "This device is not supported.", 
	              Toast.LENGTH_LONG).show();
	          finish();
	        }
	        return false;
	      }
	      return true;
	}
	void showErrorDialog(int code) {
		  GooglePlayServicesUtil.getErrorDialog(code, this, 
		      REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
		}
	@Override
	public void onResume()
	{
		super.onResume();
		setContentView(R.layout.activity_main);
		 mydb = new DbHelper(this);
		 Log.i("shashank","onResume");
         array_list = mydb.getAllReminders();
         
         if (checkPlayServices())
         {
        	 Log.i("shashank","yes it's there!!");
         }
         else {
        	 Log.i("shashank","Not there!!");
         }
         
         adReminder= new AdapterReminder (MainActivity.this, R.layout.row_layout, array_list);
         obj = (ListView)findViewById(R.id.listView1);
         obj.setEmptyView(findViewById( R.id.empty_list_view ));
         obj.setAdapter(adReminder);
         if(mListState!=null)
         {
        	 obj.onRestoreInstanceState(mListState);
         }
         obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
               // TODO Auto-generated method stub
               int id_To_Search = arg2;
               Reminder value=array_list.get(id_To_Search);
              
               Bundle dataBundle = new Bundle();
               dataBundle.putInt("id", value.getId());
              Log.i("shashank","MainActivity id is "+value.getId());
               Intent intent = new Intent(getApplicationContext(),EditReminder.class);
               
               intent.putExtras(dataBundle);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
               intent.putExtras(dataBundle);
               startActivity(intent);
               
            }
         });
    	 
         mListState = null;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_create:
	            sendMessage();
	            return true;
	        case R.id.action_help:
	            Intent intent = new Intent(this,Help.class);
	            startActivity(intent);
	            return true;    
	        case R.id.action_settings:
	        	Intent i = new Intent(this, Settings.class);
	        	startActivity(i);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void sendMessage() {
	    Intent intent = new Intent(this, SetReminder.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	    
	}
@Override 
protected void onRestoreInstanceState(Bundle state)
{
	super.onRestoreInstanceState(state);
	mListState = state.getParcelable(LIST_STATE);
	
}
@Override 
protected void onSaveInstanceState(Bundle state)
{
	super.onSaveInstanceState(state);
	mListState = (Parcelable) obj.onSaveInstanceState();
	state.putParcelable(LIST_STATE, mListState);
}
}
