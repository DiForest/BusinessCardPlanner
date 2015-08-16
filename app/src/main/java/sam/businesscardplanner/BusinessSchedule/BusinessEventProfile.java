package sam.businesscardplanner.BusinessSchedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    private Context context;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_profile);

        setUpToolbar();
        setUpNavDrawer();

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
