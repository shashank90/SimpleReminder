package com.shashank.simplereminder;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "Reminders.db";
	public static final String REMINDERS_TABLE_NAME = "reminders";
	public static final String REMINDERS_COLUMN_ID = "id";
	public static final String REMINDERS_COLUMN_NOTE = "note";
	public static final String REMINDERS_COLUMN_DATE = "date";
	public static final String REMINDERS_COLUMN_TIME = "time";
	public static final String REMINDERS_COLUMN_ALARMTYPE = "alarmType";
	public static final String REMINDERS_COLUMN_RANDOMINT = "randomInt";
	public static final String REMINDERS_COLUMN_STATUS = "status";
	
	static int Version=2;
	
	public DbHelper(Context context)
	   {
	      super(context, DATABASE_NAME , null, Version);
	   }
	@Override
	   public void onCreate(SQLiteDatabase db) {
	      // TODO Auto-generated method stub
	      db.execSQL(
	      "create table reminders " +
	      "(id integer primary key, note text,date text,time text, alarmType text, randomInt integer, status text)"
	      );
	   }
	 @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      // TODO Auto-generated method stub
		 /*
		 String TYPE = " text ";
		 if (oldVersion == 1)
		 {
			 db.execSQL("ALTER TABLE " + REMINDERS_TABLE_NAME + " ADD COLUMN " + REMINDERS_COLUMN_STATUS + TYPE+"DEFAULT active");
		 }
		 */
	//	 else 
	//	 {	 
	      db.execSQL("DROP TABLE IF EXISTS reminders");
	      onCreate(db);
	//	 }
		 
	   }
	 public boolean insertContact  (String note, String date, String time, String alarmType,Integer randomInt, String status)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("note", note);
	      contentValues.put("date", date);
	      contentValues.put("time", time);
	      contentValues.put("alarmType", alarmType);
	      contentValues.put("randomInt", randomInt);
	      contentValues.put("status", status);
	      db.insert("reminders", null, contentValues);
	      return true;
	   }
	 public Cursor getData(int id){
	      SQLiteDatabase db = this.getReadableDatabase();
	     
	      Log.i("shashank", "DbHelper id is "+id);
	      Cursor res =  db.rawQuery( "select * from reminders where id="+id+"", null );
	      if (res != null)
	      {
	    	  Log.i("shashank", "res is not empty");
	      }
	      else
	      {
	    	  Log.i("shashank", "res is empty");
	      }
	      return res;
	   }
	 public int numberOfRows(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, REMINDERS_TABLE_NAME);
	      return numRows;
	   }
	 public boolean isDataPresent(String note)
	 {
		   ArrayList<Reminder> array_list = new ArrayList<Reminder>();
		      
		      
		      SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from reminders", null );
		      res.moveToFirst();
		      
		      while(res!=null && res.isAfterLast() == false) {
		    	  Reminder rem = new Reminder();
		    	  rem.setId(res.getInt(res.getColumnIndex(DbHelper.REMINDERS_COLUMN_ID)));
		    	  rem.setNote(res.getString(res.getColumnIndex(REMINDERS_COLUMN_NOTE)));
		    	    array_list.add(rem);
			         
			         res.moveToNext();
			      }
		      
		      for(Reminder rem1 : array_list)
		      {
		    	  if (note.equals(rem1.getNote()))
		    	  {
		    		  Log.i("shashank","note "+note);
		    		  return true;
		    	  }
		      }
		    return false;  
	 }
	 public boolean updateContact (Integer id, String note, String date, String time, String alarmType, Integer randomInt, String status)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put("note", note);
	      contentValues.put("date", date);
	      contentValues.put("time", time);
	      contentValues.put("alarmType", alarmType);
	      contentValues.put("randomInt", randomInt);
	      contentValues.put("status", status);
	      
	      db.update("reminders", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
	      return true;
	   }
	 public boolean updateStatus(Integer id, String status)
	 {
		 SQLiteDatabase db = this.getWritableDatabase();
	     ContentValues contentValues = new ContentValues();
	     contentValues.put("status", status);
	     db.update("reminders", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
		 return true;
	 }
	 public Integer deleteContact (Integer id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      return db.delete("reminders", 
	      "id = ? ", 
	      new String[] { Integer.toString(id) });
	   }
	 public ArrayList<Reminder> getAllReminders()
	   {
		 
	      ArrayList<Reminder> array_list = new ArrayList<Reminder>();
	      
	      
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from reminders", null );
	      res.moveToFirst();
	      
	      while(res!=null && res.isAfterLast() == false){
	    	  Reminder rem = new Reminder();
	    	  rem.setNote(res.getString(res.getColumnIndex(REMINDERS_COLUMN_NOTE)));
	    	  rem.setDate(res.getString(res.getColumnIndex(REMINDERS_COLUMN_DATE)));
	    	  rem.setTime(res.getString(res.getColumnIndex(REMINDERS_COLUMN_TIME)));
	    	  rem.setAlarmType(res.getString(res.getColumnIndex(REMINDERS_COLUMN_ALARMTYPE)));
	    	  rem.setRandomInt(res.getInt(res.getColumnIndex(DbHelper.REMINDERS_COLUMN_RANDOMINT)));
	    	  rem.setId(res.getInt(res.getColumnIndex(DbHelper.REMINDERS_COLUMN_ID)));
	    	  rem.setStatus(res.getString(res.getColumnIndex(REMINDERS_COLUMN_STATUS)));
	    	  
	         array_list.add(rem);
	         
	         res.moveToNext();
	      }
	   return array_list;
	   }
	   
}
