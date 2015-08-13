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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.AddMemberActivity;
import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessCard.RowViewAdapter;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/31/2015.
 */
public class GroupsProfile extends AppCompatActivity{

    private Toolbar mToolbar;

    private TextView groupName;
    private TextView groupMemberNumber;
    private TextView groupCreateDate;
    private Button btnAddMember;
    private Button btnDeteleGroup;

    private List list = null;
    private ListView memberList;

    private Context context;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_profile);

        setUpToolbar();
        setUpNavDrawer();

        //set up all the elements of the layout
        groupName = (TextView) findViewById(R.id.group_name);
        groupMemberNumber = (TextView) findViewById(R.id.group_member_number);
        groupCreateDate = (TextView) findViewById(R.id.group_created_date);

        btnAddMember = (Button) findViewById(R.id.add_member);
        btnDeteleGroup = (Button) findViewById(R.id.btn_detele_group);

        //get the id and display the information
        Intent intent = getIntent();
        final int groupId = intent.getExtras().getInt("ITEM ID", -1);
        final DatabaseHandler groupsDB = new DatabaseHandler(this);
        final BusinessGroups businessGroups = groupsDB.getGroup(groupId);

        //setup the member list view
        memberList = (ListView) findViewById(R.id.member_list);

        final RowViewAdapter adapter =  new RowViewAdapter(this.getApplicationContext(),
                generateData(groupId));
        memberList.setAdapter(adapter);

        //set the activity label
        setTitle(businessGroups.get_name());

        //set up the information
        groupName.setText(businessGroups.get_name());
        groupCreateDate.setText(businessGroups.get_created_date());
        int group_member_number = businessGroups.get_group_member();
        String s = String.valueOf(group_member_number);
        groupMemberNumber.setText("eee");

        //set up the detele button
        btnDeteleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //detele the group
                groupsDB.deteleGroups(businessGroups);
            }
        });

        //add member into the group
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMemberIntent = new Intent(GroupsProfile.this,AddMemberActivity.class);
                addMemberIntent.putExtra("groupID", groupId);
                startActivity(addMemberIntent);
            }
        });
    }

    private List<BusinessCard> generateData(int groupId){
        DatabaseHandler db1 = new DatabaseHandler(this);
        //get all member of the group
        list = db1.getAllMemberFromGroup(groupId);
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
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
