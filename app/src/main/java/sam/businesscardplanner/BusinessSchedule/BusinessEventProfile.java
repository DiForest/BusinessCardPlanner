package sam.businesscardplanner.BusinessSchedule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/17/2015.
 */
public class BusinessEventProfile extends AppCompatActivity{

    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mStartDateTime;
    private TextView mEndDateTime;
    private TextView mInvitedPeople;
    private ImageView mBlank;
    private TextView mDescription;
    private int EVENT_ID ;

    String title;
    String description ;
    int status = 0;
    String people;
    int startDate;
    int startTime;
    int endDate;
    int endTime;

    private Context context;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_profile);
        setUpToolbar();
        setUpNavDrawer();
        setTitle("");

        mTitle = (TextView) findViewById(R.id.event_title);
        mDescription = (TextView) findViewById(R.id.event_description);
        mStartDateTime = (TextView) findViewById(R.id.txt_start_date);
        mEndDateTime = (TextView) findViewById(R.id.txt_end_date);
        mInvitedPeople = (TextView) findViewById(R.id.invited_people_card);
        mBlank = (ImageView) findViewById(R.id.blank_end_date_image);

        Intent intent = getIntent();
        EVENT_ID = intent.getExtras().getInt("ITEM ID", -1);

        DatabaseHandler db = new DatabaseHandler(this);
        BusinessEvent be = db.getEvent(EVENT_ID);

        title = be.get_calendar_tile();
        description = be.get_description();
        startDate = be.get_startDate();
        startTime = be.get_startTime();
        endDate = be.get_endDate();
        endTime = be.get_endTime();
        status = be.get_all_day_status();
        people = be.get_invitedPeople();

        mTitle.setText(title);
        mDescription.setText(be.get_description());
        mStartDateTime.setText("Start at " + startDate + " " + startTime);
        if (status == 0) {
            mEndDateTime.setText("End at " + endDate + " " + endTime);
        } else {
            mEndDateTime.setVisibility(View.GONE);
            mBlank.setVisibility(View.GONE);
        }

        if(people.length()>1){
            mInvitedPeople.setText(be.get_invitedPeople());
        }else
            mInvitedPeople.setText("none");
    }


    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessEventProfile.this.finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                confirmDeleteBox();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDeleteBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete Event");
        alertDialogBuilder
                .setMessage("Confirm delete this event?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getBaseContext());
                        db.deleteEvent(EVENT_ID);
                        Toast.makeText(getApplicationContext(),
                                "Delete successful", Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
