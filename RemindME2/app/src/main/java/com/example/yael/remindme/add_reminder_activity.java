package com.example.yael.remindme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class add_reminder_activity extends FragmentActivity implements com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener, com.sleepbot.datetimepicker.time.TimePickerDialog.OnTimeSetListener {

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    public final static String New_Reminder = "New_Reminder";
    private RadioGroup radioGroupWhenWhere;
    private Reminder reminder;
    private EditText DisplayDate;
    private EditText DisplayTime;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;

    com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog;
    com.sleepbot.datetimepicker.time.TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_activity);
        addCurrentTime();
        addListenerOnEditDate();
        addListenerOnEditTime();
        addListenerLocationTimeRadioGroup();
        SetDatePicker();

    }

    private void SetDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        timePickerDialog = com.sleepbot.datetimepicker.time.TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);
    }

    private void addCurrentTime()
    {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    private void addListenerOnEditTime() {
        DisplayTime = (EditText) findViewById(R.id.Time);

        DisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }

        });
    }

    private void addListenerLocationTimeRadioGroup() {
        radioGroupWhenWhere = (RadioGroup) findViewById(R.id.radioGroupWhenWhere);
        radioGroupWhenWhere.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                String selectedRadioBtn= (String) ((RadioButton) findViewById(checkedId)).getText();
                if (selectedRadioBtn.equalsIgnoreCase("When"))
                {
                    DisplayDate.setVisibility(View.VISIBLE);
                    DisplayTime.setVisibility(View.VISIBLE);

                }
                else if (selectedRadioBtn.equalsIgnoreCase("Where"))
                {
                    DisplayDate.setVisibility(View.INVISIBLE);
                    DisplayTime.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void addListenerOnEditDate() {
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);

//        DisplayDate = (EditText) findViewById(R.id.Date);
//        DisplayDate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showDialog(DATE_DIALOG_ID);
//            }
//
//        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);


        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            DisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into textview
                    DisplayTime.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reminder_activity, menu);
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
    public void AddReminder(View view)
    {
        Intent intent = new Intent(this, show_reminders.class);
        EditText title = (EditText) findViewById(R.id.TitleText);
        String titleText = title.getText().toString();
        AddLocationOrTimeToReminder(titleText);
        intent.putExtra(New_Reminder,reminder);
        startActivity(intent);
    }

    private void AddLocationOrTimeToReminder(String titleText) {
        int i = radioGroupWhenWhere.getCheckedRadioButtonId();
        String selectedRadioBtn= (String) ((RadioButton) findViewById(i)).getText();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate = new Date();
        Time remiderTime = new Time(hour,minute,0);
        try {
            myDate = df.parse(DisplayDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (selectedRadioBtn.equalsIgnoreCase("When"))
        {
            reminder = new Reminder(titleText, Reminder.RemindLocationOrTime.Time,myDate,remiderTime);
        }
        else if (selectedRadioBtn.equalsIgnoreCase("Where"))
        {
            reminder = new Reminder(titleText, Reminder.RemindLocationOrTime.Location,myDate,remiderTime);
        }
    }


    @Override
    public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }
}
