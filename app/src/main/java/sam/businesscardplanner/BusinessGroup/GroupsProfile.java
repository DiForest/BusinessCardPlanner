package sam.businesscardplanner.BusinessGroup;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessCard.BusinessCardProfile;
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
    private TextView groupDescription;

    private List list = null;
    private ListView memberList;

    private int EDIT_GROUP = 2;

    private Context context;
    private int GROUP_ID ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_profile);
        setUpToolbar();
        setUpNavDrawer();

        setUpElement();

        //get the id and display the information
        Intent intent = getIntent();
        GROUP_ID = intent.getExtras().getInt("ITEM ID", -1);
        final DatabaseHandler groupsDB = new DatabaseHandler(this);
        final BusinessGroups businessGroups = groupsDB.getGroup(GROUP_ID);

        //setup the member list view
       displayMemberList(GROUP_ID);

        //set the activity label
        setTitle(businessGroups.get_name());

        //set up the information
        groupName.setText(businessGroups.get_name());
        groupCreateDate.setText("Created at " + businessGroups.get_created_date());
        groupMemberNumber.setText("Group member "+ businessGroups.get_member_count());
        groupDescription.setText(businessGroups.get_description());
    }

    public void onResume(){
        super.onResume();
        displayMemberList(GROUP_ID);
    }

    public void setUpElement(){
        //set up all the elements of the layout
        groupName = (TextView) findViewById(R.id.group_name);
        groupMemberNumber = (TextView) findViewById(R.id.group_member_number);
        groupCreateDate = (TextView) findViewById(R.id.group_created_date);
        memberList = (ListView) findViewById(R.id.member_list);
        groupDescription = (TextView) findViewById(R.id.group_descrption);
    }

    /* ------------------- Display member list ----------------*/
    public void displayMemberList(int groupId){
        final GroupMemberListAdapter adapter = new GroupMemberListAdapter(getApplicationContext(),
                generateMemberList(groupId));
        memberList.setAdapter(adapter);

        memberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = adapter.getListItemId(position);
                viewMemberProfile(itemId);
            }
        });

        memberList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = adapter.getListItemId(position);
                confirmDeleteMember(itemId);
                return true;
            }
        });
    }

    private void viewMemberProfile(int itemId){
        Intent intent = new Intent(this, BusinessCardProfile.class);
        intent.putExtra("ITEM ID", itemId);
        startActivity(intent);
    }

    private List<BusinessCard> generateMemberList(int groupId){
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
                editOrAddMember();
                return true;
            case R.id.action_delete:
                confirmDeleteBox();
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

    private void confirmDeleteBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete Group");
        alertDialogBuilder
                .setMessage("Confirm delete this group?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getBaseContext());
                        db.deleteGroups(GROUP_ID);
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

    private void confirmDeleteMember(final int itemId){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Remove member");
        alertDialogBuilder
                .setMessage("Do you want to remove this member from group?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getBaseContext());
                        db.removeMember(itemId, GROUP_ID);
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

    protected void editOrAddMember() {
        final CharSequence[] items = {"Edit group", "Add member"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Edit group")) {
                    editGroup();
                } else if (items[which].equals("Add member")) {
                    addMember(GROUP_ID);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addMember(int groupId){
        Intent intent = new Intent(this, AddMemberActivity.class);
        intent.putExtra("GROUP ID", groupId);
        startActivity(intent);
    }

    private void editGroup(){
        Intent intent = new Intent(this, AddNewGroupActivity.class);
        intent.putExtra("ADD OR EDIT",EDIT_GROUP);
        intent.putExtra("groupId", GROUP_ID);
        startActivity(intent);
    }
}
