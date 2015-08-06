package sam.businesscardplanner.BusinessGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/31/2015.
 */
public class GroupsProfile extends AppCompatActivity{

    private Toolbar mToolbar;

    TextView groupName;
    TextView groupDescription;
    TextView groupCreateDate;
    Button btnAddMember;
    Button btnDeteleGroup;
    List list = null;

    Context context;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_profile);

        setUpToolbar();
        setUpNavDrawer();

        //set up all the elements of the layout
        groupName = (TextView) findViewById(R.id.group_name);
        groupDescription = (TextView) findViewById(R.id.group_description);
        groupCreateDate = (TextView) findViewById(R.id.group_created_date);

        btnAddMember = (Button) findViewById(R.id.add_member);
        btnDeteleGroup = (Button) findViewById(R.id.btn_detele_group);

        //get the id and display the information
        Intent intent = getIntent();
        int itemID = intent.getExtras().getInt("ITEM ID", -1);
        final DatabaseHandler groupsDB = new DatabaseHandler(this);
        final BusinessGroups businessGroups = groupsDB.getGroup(itemID);

        //set the activity label
        setTitle(businessGroups.get_name());

        //set up the information
        groupName.setText(businessGroups.get_name());
        groupCreateDate.setText(businessGroups.get_created_date());

        //set up the detele button
        btnDeteleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //detele the group
                groupsDB.deteleGroups(businessGroups);
            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMemberIntent = new Intent(GroupsProfile.this,AddMemberActivity.class);
                startActivity(addMemberIntent);

            }
        });
    }

    private List<BusinessCard> generateData(){
        DatabaseHandler db1 = new DatabaseHandler(this);
        list = db1.getAllBusinessCard();
        return list;
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    //setup the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:

                return true;
        }
        return super.onOptionsItemSelected(item);
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
