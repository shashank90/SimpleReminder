package com.shashank.simplereminder;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterReminder extends ArrayAdapter<Reminder> {
    private Activity activity;
    private ArrayList<Reminder> lReminder;
    private int textViewResourceId;
    private static LayoutInflater inflater = null;
    

    public AdapterReminder (Activity activity, int textViewResourceId,ArrayList<Reminder> _lReminder) {
        super(activity, textViewResourceId,_lReminder);
        try {
            this.activity = activity;
            this.lReminder = _lReminder;
            this.textViewResourceId = textViewResourceId;
            
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lReminder.size();
    }
    public Reminder getItem(Reminder position) {
        return position;
    }

   

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_note;
        public TextView display_date;
        public TextView display_time;
        public TextView display_status;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
            	Log.i("shashank","inside getView adapter");
                vi = inflater.inflate(textViewResourceId,parent, false);
                holder = new ViewHolder();

                holder.display_note = (TextView) vi.findViewById(R.id.note);
                holder.display_date = (TextView) vi.findViewById(R.id.date);
                holder.display_time = (TextView) vi.findViewById(R.id.time);
                holder.display_status = (TextView) vi.findViewById(R.id.status);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_note.setText(lReminder.get(position).Note);
            holder.display_date.setText("Date: "+lReminder.get(position).Date);
            holder.display_time.setText("Time: "+lReminder.get(position).Time);
            holder.display_status.setText(lReminder.get(position).status);

        } catch (Exception e) {


        }
        return vi;
    }
}