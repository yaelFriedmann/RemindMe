package com.example.yael.remindme;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yael on 12/21/2014.
 */
public class ReminderAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Reminder> reminders;
    private static LayoutInflater inflater=null;


    TextView reminder_title;  // reminder title
    ImageView whenOrWhere_image; // thumb image
    TextView reminder_date; // reminder date

    public ReminderAdapter(Activity a, ArrayList<Reminder> reminders)
    {
        activity = a;
        this.reminders=reminders;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return reminders.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.reminders_list, null);

        reminder_title = (TextView)vi.findViewById(R.id.reminder_title);
        whenOrWhere_image=(ImageView)vi.findViewById(R.id.when_or_when_image);
        reminder_date = (TextView)vi.findViewById(R.id.date_text);
        Reminder reminder;
        reminder = reminders.get(position);
        SetReminderValues(reminder);


        return vi;
    }

    private void SetReminderValues(Reminder reminder) {
        // Setting all values in listView
        reminder_title.setText(reminder.GetTiTle());

        if (reminder.GetRemindLocationOrTime() == Reminder.RemindLocationOrTime.Time)
        {
            whenOrWhere_image.setImageResource(R.drawable.time);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat tf = new SimpleDateFormat("HH:mm");
            if (reminder.GetTime()!= null)
            {
                reminder_date.setText(df.format(reminder.GetDate()).concat(", ").concat(tf.format(reminder.GetTime())));
            }
            else
            {
                reminder_date.setText(df.format(reminder.GetDate()));
            }
        }
        else
        {
            whenOrWhere_image.setImageResource(R.drawable.location);
        }
    }
}
