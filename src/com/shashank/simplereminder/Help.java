package com.shashank.simplereminder;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help extends ActionBarActivity {

	Button button1;
	TextView TextViewQ1;
	TextView TextViewQ2;
	TextView TextViewQ3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		button1=(Button)findViewById(R.id.ButtonOK);
		TextViewQ1=(TextView)findViewById(R.id.TextViewQ1);
		TextViewQ2=(TextView)findViewById(R.id.TextViewQ2);
		TextViewQ3=(TextView)findViewById(R.id.TextViewQ3);
		
		TextViewQ1.setPaintFlags(TextViewQ1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		TextViewQ2.setPaintFlags(TextViewQ2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		TextViewQ3.setPaintFlags(TextViewQ3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		
		button1.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	     	finish();
	     	
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
