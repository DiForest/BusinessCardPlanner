package sam.businesscardplanner.BusinessSchedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private EditText mPeopleInput;
    private ImageView mPeopleImage;
    private ImageView mPeopleInputCancel;

    private TextView mNote;
    private TextView mTag;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mStartTime;
    private TextView mEndTime;
    private EditText mTitle;
    private EditText mLocation;

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

        switchAllDay = (Switch) findViewById(R.id.switch_all_day);
        mStartDate = (TextView) findViewById(R.id.txt_start_date);
        mEndDate = (TextView) findViewById(R.id.txt_end_date);
        mStartTime = (TextView) findViewById(R.id.txt_start_time);
        mEndTime = (TextView) findViewById(R.id.txt_end_time);

        mReminderDate = (TextView) findViewById(R.id.txt_notice_date);
        mReminderTime = (TextView) findViewById(R.id.txt_notice_time);
        mReminderCancel = (ImageView) findViewById(R.id.notification_cancel);

        mPeople = (TextView) findViewById(R.id.txt_people);
        mPeopleInput = (EditText)  findViewById(R.id.input_people);
        mPeopleImage = (ImageView) findViewById(R.id.people_image);
        mPeopleInputCancel = (ImageView) findViewById(R.id.invite_people_cancel);

        mNote = (TextView) findViewById(R.id.txt_note);
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
                invitePeople(peopleList);
                for(String peopleInvited : peopleList)
                mPeople.setText(peopleInvited + " ");
            }
        });

        mPeopleInputCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeopleInput.setText("");
                mPeopleInput.setVisibility(View.GONE);
                mPeopleImage.setVisibility(View.GONE);
                mPeopleInputCancel.setVisibility(View.GONE);
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

        currentDateString = dd + "/" + mm + "/" + yy + " " ;
        return currentDateString;
    }

    private String getTimeString(){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        currentTimeString = hour+ ":" + minute;
        return currentTimeString;
    }

    //show the date picker
    private void showDatePicker(final int status){
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if(status == 3){
                            mReminderDate.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year + " ");
                        }
                        else if(status == 2)
                        {
                            mStartDate.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year + " ");
                        } else if (status == 1)
                        {
                            mEndDate.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year + " ");
                        }
                    }
                }, yy, mm, dd);
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
                },hour,minute,true);
        tpd.setTitle("Select Time");
        tpd.show();
    }

    /* ------------------------ invite people  ---------------*/
    private void invitePeople(final ArrayList<String> peopleList){
        final CharSequence[] items = {"Choose from business card", "Custom"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invite People");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Choose from business card")) {
                    choosePeople();
                } else if (items[which].equals("Custom")) {
                    mPeopleInput.setVisibility(View.VISIBLE);
                    mPeopleImage.setVisibility(View.VISIBLE);
                    mPeopleInputCancel.setVisibility(View.VISIBLE);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void choosePeople(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select One Name:-");
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
                        LinearLayout linearLayout =  (LinearLayout)findViewById(R.id.add_people_layout);

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
        String startDateTime = dateTimeFormat(startDate, startTime);
        /*
        String pplList = "";
        if(invitedPeople.size()>0){
            pplList = invitedPeople.get(0);
            if(invitedPeople.size()>1){
                for(int i =1 ; i<invitedPeople.size() ; i++){
                    pplList = ","+ invitedPeople.get(i);
                }
                calendar.set_invitedPeopleInput(pplList);
            }else
                calendar.set_invitedPeopleInput(pplList);
        }
*/
        calendar.set_calendar_tile(title);
        //calendar.set_All_day_status(ALL_DAY_STATUS);
        calendar.set_startDateTime(startDateTime);

        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        db.addEvent(calendar);
        /*
        Toast.makeText(this.getApplicationContext(),
                "Click title" + title + "all day " + ALL_DAY_STATUS
                + " ppl :" + pplList , Toast.LENGTH_LONG)
                .show();

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title","testing testing");
        startActivity(intent);
        */
    }

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


