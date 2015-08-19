package sam.businesscardplanner.BusinessSchedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/17/2015.
 */
public class NewEventActivity extends AppCompatActivity {

    private TextView mReminderDate;
    private TextView mReminderTime;
    private ImageView mReminderCancel;

    private TextView mPeople;
    private TextView mNote;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mStartTime;
    private TextView mEndTime;
    private EditText mTitle;
    private EditText mLocation;
    private EditText mDescription;

    private int ALL_DAY_STATUS = 0;
    private boolean NOTIFICATION_STATUS = true;
    private int PEOPLE_INVITED = 0;
    private static final int END_TIME_OR_DATE = 1;
    private static final int START_TIME_OR_DATE = 2;
    private static final int NOTIFICATION = 3;

    private LinearLayout mEndDateTimeSector;
    private ImageView iconBlank;
    private Switch switchAllDay;

    private Toolbar mToolbar;

    private String currentDateString;
    private String currentTimeString;
    private int yy;
    private int mm;
    private int dd;
    private int hour;
    private int minute;

    private final ArrayList<String> peopleList = new ArrayList<String>();
    private ArrayList<String> invitedPeople = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        //setup the menu
        setUpToolbar();
        setUpNavDrawer();

        mEndDateTimeSector = (LinearLayout) findViewById(R.id.end_date_time_sector);

        //set up elements
        switchAllDay = (Switch) findViewById(R.id.switch_all_day);
        mStartDate = (TextView) findViewById(R.id.txt_start_date);
        mEndDate = (TextView) findViewById(R.id.txt_end_date);
        mStartTime = (TextView) findViewById(R.id.txt_start_time);
        mEndTime = (TextView) findViewById(R.id.txt_end_time);

        mReminderDate = (TextView) findViewById(R.id.txt_notice_date);
        mReminderTime = (TextView) findViewById(R.id.txt_notice_time);
        mReminderCancel = (ImageView) findViewById(R.id.notification_cancel);

        mPeople = (TextView) findViewById(R.id.txt_people);
        mDescription = (EditText) findViewById(R.id.edit_description);

        mLocation = (EditText) findViewById(R.id.txt_location);
        mTitle = (EditText) findViewById(R.id.edit_title);
        iconBlank = (ImageView) findViewById(R.id.icon_blank);

        //set the current date
        mStartDate.setText(getDateString());
        mEndDate.setText(getDateString());
        mStartTime.setText(getTimeString());
        mEndTime.setText(getTimeString());

        /**** -------------- Button listener -------------------*/
        mStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDatePicker(START_TIME_OR_DATE);
            }
        });

        mEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDatePicker(END_TIME_OR_DATE);
            }
        });

        mStartTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showTimePicker(START_TIME_OR_DATE);
            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showTimePicker(END_TIME_OR_DATE);
            }
        });

        mReminderDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDatePicker(NOTIFICATION);
                mReminderTime.setVisibility(View.VISIBLE);
                mReminderCancel.setVisibility(View.VISIBLE);
            }
        });

        mReminderTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTimePicker(END_TIME_OR_DATE);
            }
        });

        mReminderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReminderTime.setVisibility(View.GONE);
                mReminderDate.setText("Notification");
                mReminderCancel.setVisibility(View.GONE);
            }
        });

        mPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePeople();
                for(String peopleInvited : peopleList)
                    mPeople.setText(peopleInvited + " ");
            }
        });

        //set the switch to ON
        switchAllDay.setChecked(false);
        //attach a listener to check for changes in state
        switchAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    mEndDateTimeSector.setVisibility(View.GONE);
                    ALL_DAY_STATUS = 0;
                } else {
                    mEndDateTimeSector.setVisibility(View.VISIBLE);
                    ALL_DAY_STATUS = 1;

                }
            }
        });
    }

    /* -----------------date time picker method ------------------------ */

    private String getDateString(){
        final Calendar c = Calendar.getInstance();
        yy = c.get(Calendar.YEAR);
        mm = c.get(Calendar.MONTH) +1;
        dd = c.get(Calendar.DAY_OF_MONTH);

        String mmFormat = "";
        if(mm<10)
            mmFormat = "0"+mm;
        else
            mmFormat = ""+mm;

        String ddFormat = "";
        if(dd<10)
            ddFormat = "0"+dd;
        else
            ddFormat=""+dd;

        currentDateString = yy + "/" + mmFormat + "/" + ddFormat  ;
        return currentDateString;
    }

    private String getTimeString(){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        String hour24 = "";
        if(hour>=0 && hour< 10)
            hour24 = "0" + hour;
        else
            hour24 = "" + hour;


        String minute24 = "";
        if(minute<10)
            minute24 = "0" + minute;
        else
            minute24 = ""+minute;
        currentTimeString = hour24 + ":" + minute24;
        return currentTimeString;
    }

    //show the date picker
    private void showDatePicker(final int status){
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if(status == 2)
                        {
                            mStartDate.setText(year + "/"
                                    + (monthOfYear +1) + "/"
                                    + dayOfMonth);
                        } else if (status == 1)
                        {
                            mEndDate.setText(year + "/"
                                    + (monthOfYear +1) + "/"
                                    + dayOfMonth);
                        }
                    }
                }, yy, mm -1 , dd);
        dpd.setTitle("Select Date");
        dpd.show();
    }

    private void showTimePicker(final int status){
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(status == 3){
                            mReminderTime.setText(hourOfDay + ":" + minute);
                        } else if (status == 2) {
                            mStartTime.setText(hourOfDay + ":" + minute);
                        } else if(status ==1){
                            mEndTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                },hour, minute, true);
        tpd.setTitle("Select Time");
        tpd.show();
    }

    /* ------------------------ invite people  ---------------*/


    private void choosePeople(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Invite People:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice);
        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        List<BusinessCard> list = db.getAllBusinessCard();

        for(int i = 0 ; i<list.size(); i++) {
            arrayAdapter.add(list.get(i).get_name());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String peopleName = arrayAdapter.getItem(which);
                        if (invitedPeople.contains(peopleName)) {
                            Toast.makeText(getApplicationContext(),
                                    "The card was added",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.add_people_layout);

                            TextView valueTV = new TextView(getApplicationContext());
                            valueTV.setText(peopleName);
                            invitedPeople.add(peopleName);
                            valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            valueTV.setPadding(36, 16, 16, 16);
                            valueTV.setTextSize(16);
                            linearLayout.addView(valueTV);
                        }
                    }
                });
        builderSingle.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1){
            if(resultCode == RESULT_OK){
                String people = data.getStringExtra("Member");
                peopleList.add(people);
            }
        }
    }

    /* ------------------- Save Info ----------------*/

    private void saveInfo(){
        BusinessEvent calendar = new BusinessEvent();
        String title = mTitle.getText().toString();

        String startDate = mStartDate.getText().toString();
        String startTime = mStartTime.getText().toString();

        //20150403 format int
        int startDateInt = convertToInt(startDate);
        int startTimeInt = convertTimeInt(startTime);

        String endDate;
        int endDateInt;
        int endTimeInt;
        String endTime;
        boolean status ;
        String location = mLocation.getText().toString();
        String description = mDescription.getText().toString();

        if(ALL_DAY_STATUS == 0){
            endDate = startDate;
            endTime = "23:59";
            endDateInt = startDateInt;
            endTimeInt = 2359;
            status = true;
        }else{
            endDate = mEndDate.getText().toString();
            endDateInt = convertToInt(endDate);
            endTime = mEndTime.getText().toString();
            endTimeInt = convertTimeInt(endTime);
            status = false;
        }

        String people= "";
        for(String temp : invitedPeople){
            people = people + temp  + "   ";
        }

        calendar.set_calendar_tile(title);
        calendar.set_All_day_status(ALL_DAY_STATUS);
        calendar.set_startDate(startDateInt);
        calendar.set_startTime(startTimeInt);
        calendar.set_endDate(endDateInt);
        calendar.set_endTime(endTimeInt);
        calendar.set_invitedPeople(people);
        calendar.set_description(description);

        int startDay = breakDateFormat(2,startDate);
        int startMonth = breakDateFormat(1,startDate) -1;
        int startYear = breakDateFormat(0,startDate);
        int startHH = breakTimeFormat(0, startTime);
        int startMM = breakTimeFormat(1, startTime);

        Calendar beginT = Calendar.getInstance();
        beginT.set(startYear, startMonth, startDay, startHH, startMM);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginT.getTimeInMillis() )
                .putExtra(CalendarContract.Events.ALL_DAY, status)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.DESCRIPTION,description)
                .putExtra(CalendarContract.Events.EVENT_TIMEZONE,
                        TimeZone.getDefault().toString())
                .putExtra(CalendarContract.Events.TITLE, title);

        int endYear, endMonth,endDay,endHH,endMM;
        Calendar endT;
        if(!status) {
            endDay = breakDateFormat(2, endDate);
            endMonth = breakDateFormat(1, endDate)-1;
            endYear = breakDateFormat(0, endDate);
            endHH = breakTimeFormat(0, endTime);
            endMM = breakTimeFormat(1, endTime);
            endT = Calendar.getInstance();
            endT.set(endYear, endMonth, endDay, endHH, endMM);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endT.getTimeInMillis());
        }

        startActivity(intent);

        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        db.addEvent(calendar);
    }

    public int convertToInt(String dateTime){
        String[] date = dateTime.split("/");

        int yy = Integer.parseInt(date[0]);
        int mm = Integer.parseInt(date[1]);
        int dd = Integer.parseInt(date[2]);
        int d = yy *10000 + mm * 100 + dd;
        return d;
    }

    public int convertTimeInt(String time){
        String[] date = time.split(":");

        int hh = Integer.parseInt(date[0]);
        int mm = Integer.parseInt(date[1]);
        int d = hh *100 + mm;
        return d;
    }

    public String checkTimeFormat(String time){
        String[] temp = time.split(":");
        String hh = temp[0];
        String mm = temp[1];

        int HH = Integer.parseInt(hh);
        int MM = Integer.parseInt(mm);

        if( HH >= 0 && HH < 10 ){
            hh = "0"+hh;
        }

        if( MM >0 && MM <10 ){
            mm = "0"+mm;
        }

        return hh + ":" +mm;
    }


    public int breakDateFormat(int part,String date ){
        String[] dateSplit = date.split("/");
        return Integer.parseInt(dateSplit[part]);
    }

    public int breakTimeFormat(int part, String time){
        String[] timeSplit = time.split(":");
        return Integer.parseInt(timeSplit[part]);
    }

    /* --------------------- format method -------------------*/
    private String dateTimeFormat(String date, String time){
        return date + " " + time;
    }

    /* -------------------MENU --------------------*/

    //setup the toolbar
    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
    //setup the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                saveInfo();
                Toast.makeText(this.getApplicationContext(),
                        "Create new event successfully",
                        Toast.LENGTH_LONG).show();
                NewEventActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //setup the toolbar home title can be clicked and open the drawer
    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_cross);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewEventActivity.this.finish();
                }

            });
        }
    }
}
