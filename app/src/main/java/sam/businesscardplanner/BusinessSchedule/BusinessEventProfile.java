package sam.businesscardplanner.BusinessSchedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView mReminder;
    private TextView mNote;
    private ImageView mBlank;
    private TextView mDescription;

    String title;
    String description ;
    String startDateTime;
    String endDateTime;
    int status = 0;
    String people;

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
        mNote = (TextView) findViewById(R.id.note);
        mBlank = (ImageView) findViewById(R.id.blank_end_date_image);

        Intent intent = getIntent();
        int eventId = intent.getExtras().getInt("ITEM ID", -1);

        DatabaseHandler db = new DatabaseHandler(this);
        BusinessEvent be = db.getEvent(eventId);

        title = be.get_calendar_tile();
        description = be.get_description();
        startDateTime = be.get_startDateTime();
        endDateTime = be.get_endDateTime();
        status = be.get_all_day_status();
        people = be.get_invitedPeople();

        mTitle.setText(title);
        mDescription.setText(be.get_description());
        mStartDateTime.setText("Start at " + startDateTime);
        if (status == 0) {
            mEndDateTime.setText("End at " + endDateTime);
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
}
