package sam.businesscardplanner.BusinessGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.DatabaseHandler.CardsDatabaseHandler;
import sam.businesscardplanner.DatabaseHandler.GroupsDatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/31/2015.
 */
public class GroupsProfile extends AppCompatActivity{

    private Toolbar mToolbar;

    TextView groupName;
    TextView groupDescription;
    TextView groupCreateDate;
    List list = null;

    Context context;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_profile);

        setUpToolbar();
        setUpNavDrawer();

        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        groupCreateDate = (TextView) findViewById(R.id.group_created_date);

        Intent intent = getIntent();
        int itemID = intent.getExtras().getInt("ITEM ID", -1);
        GroupsDatabaseHandler groupsDB = new GroupsDatabaseHandler(this);
        BusinessGroups businessGroups = groupsDB.getGroup(itemID);

        groupName.setText(businessGroups.get_name());
        groupDescription.setText(businessGroups.get_description());
        groupCreateDate.setText(businessGroups.get_created_date());

        final GroupMemberListAdapter adapter = new GroupMemberListAdapter(this,generateData());
        final ListView listView = (ListView) findViewById(R.id.member_list);
        listView.setAdapter(adapter);
    }

    private List<BusinessCard> generateData(){
        CardsDatabaseHandler db1 = new CardsDatabaseHandler(this);
        list = db1.getAllBusinessCard();
        return list;
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
                    GroupsProfile.this.finish();
                }

            });
        }
    }
}
