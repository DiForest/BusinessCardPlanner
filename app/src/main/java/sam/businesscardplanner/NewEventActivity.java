package sam.businesscardplanner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Administrator on 7/17/2015.
 */
public class NewEventActivity extends AppCompatActivity {

    private TextView startDate, endDate, repeatReminder, reminderTime, invitedPeople, note;
    private TextView txtStartDate, txtEndDate;
    private EditText editTitle, editLocation;
    private Switch switchAllDay;

    private Toolbar mToolbar;

    private String currentDateTimeString;
    private int yy, mm, dd;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        switchAllDay = (Switch) findViewById(R.id.switch_all_day);
        startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        //date time label text
        txtStartDate = (TextView) findViewById(R.id.txt_start_date);
        txtEndDate = (TextView) findViewById(R.id.txt_end_date);

        repeatReminder = (TextView) findViewById(R.id.edit_repeat_time);
        reminderTime = (TextView) findViewById(R.id.edit_reminder_time);
        invitedPeople = (TextView) findViewById(R.id.edit_invite_people);
        note = (TextView) findViewById(R.id.edit_note);
        editLocation = (EditText) findViewById(R.id.edit_location);
        editTitle = (EditText) findViewById(R.id.edit_title);

        startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDatePicker();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showDatePicker();
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
                    startDate.setVisibility(View.GONE);
                    endDate.setVisibility(View.GONE);
                    txtStartDate.setVisibility(View.GONE);
                    txtEndDate.setVisibility(View.GONE);
                } else {
                    startDate.setVisibility(View.VISIBLE);
                    endDate.setVisibility(View.VISIBLE);
                    txtStartDate.setVisibility(View.VISIBLE);
                    txtEndDate.setVisibility(View.VISIBLE);
                }
            }
        });

        //setup the menu
        setUpToolbar();
        setUpNavDrawer();

        //set up the calendar format
        final Calendar c = Calendar.getInstance();
        yy = c.get(Calendar.YEAR);
        mm = c.get(Calendar.MONTH) +1;
        dd = c.get(Calendar.DAY_OF_MONTH);
        currentDateTimeString = dd + "/" + mm + "/" + yy + " " ;

        //set the current date
        startDate.setText(currentDateTimeString);
        endDate.setText(currentDateTimeString);

    }

    //setup the toolbar
    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    //show the date picker
    private void showDatePicker(){
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        startDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year +" ");

                    }
                }, yy, mm, dd);
        dpd.show();
    }

    //setup the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //setup the toolbar home title can be clicked and open the drawer
    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        NewEventActivity.this.finish();
                    }

            });
        }
    }
}


