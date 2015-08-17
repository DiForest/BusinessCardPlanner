package sam.businesscardplanner.BusinessSchedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
    private Button mBtnEdit;
    private ImageView mBlank;

    private Context context;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_profile);

        setUpToolbar();
        setUpNavDrawer();

        mTitle = (TextView) findViewById(R.id.event_title);
        mStartDateTime = (TextView) findViewById(R.id.txt_start_time);
        mEndDateTime = (TextView) findViewById(R.id.txt_end_time);
        mInvitedPeople = (TextView) findViewById(R.id.invited_people);
        mReminder = (TextView) findViewById(R.id.reminder);
        mNote = (TextView) findViewById(R.id.note);
        mBtnEdit = (Button) findViewById(R.id.btn_edit_event);
        mBlank = (ImageView) findViewById(R.id.blank_end_date_image);

        Intent intent = getIntent();
        final int eventId = intent.getExtras().getInt("ITEM ID", -1);
        final DatabaseHandler db = new DatabaseHandler(this);
        final BusinessEvent be = db.getEvent(eventId);

        mTitle.setText(be.get_calendar_tile());
        mStartDateTime.setText(be.get_startDateTime());
        if(be.get_all_day_status() == 0){
            mEndDateTime.setText(be.get_endDateTime());
        }else {
            mEndDateTime.setVisibility(View.GONE);
            mBlank.setVisibility(View.GONE);
        }

        String people = be.get_invitedPeople();
        String peopleInput = be.get_invitedPeopleInput();
        if(people.length()>0 || peopleInput.length()>0){
            mInvitedPeople.setText(people);
        }else
            mInvitedPeople.setText("None");

        mBtnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
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
