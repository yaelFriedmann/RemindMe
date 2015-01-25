package com.example.yael.remindme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.lang.reflect.Type;

public class show_reminders extends Activity {

    public static final String Reminders = "MyReminders";

    ListView remindersListView;
    ReminderAdapter reminderAdapter;
    ArrayList<Reminder> listReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminders);
        GetRestoreReminders();
        AddTheNewReminder();
    }

    private void AddTheNewReminder() {
        Intent intentAddReminder = getIntent();
        Reminder newReminder = (Reminder)intentAddReminder.getSerializableExtra(add_reminder_activity.New_Reminder);
        listReminders.add(newReminder);
        addReminderAdapter();
    }

    private void GetRestoreReminders() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(Reminders, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = settings.getString(Reminders, "");
        Type type = new TypeToken<ArrayList<Reminder>>(){}.getType();
        listReminders =  gson.fromJson(json,type);
        if (listReminders==null)
        {
           listReminders =new ArrayList<>();
        }

    }

    private void addReminderAdapter() {
        // Getting adapter by passing xml data ArrayList
        reminderAdapter =new ReminderAdapter(this, listReminders);
        if (remindersListView == null)
        {
            remindersListView = (ListView)findViewById(R.id.listReimnders);
        }
        remindersListView.setAdapter(reminderAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop(){
        super.onStop();
        StoreReminder();
    }

    private void StoreReminder() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(Reminders, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        Gson gson = new Gson();
        String listRemindersJson = gson.toJson(listReminders);
        editor.putString(Reminders, listRemindersJson);
        // Commit the edits!
        editor.commit();
    }
}
