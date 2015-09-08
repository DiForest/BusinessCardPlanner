package sam.businesscardplanner.BusinessGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

public class AddNewGroupActivity extends AppCompatActivity {

    EditText editGroupName;
    EditText editGroupDescription;
    Toolbar mToolbar;

    private int ADD_OR_EDIT_STATUS;
    private int GROUP_ID;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setUpNavDrawer();
        setUpToolbar();

        editGroupName = (EditText) findViewById(R.id.edit_group_name);
        editGroupDescription = (EditText) findViewById(R.id.edit_group_description);

        Intent intent = getIntent();
        int addOrEdit = intent.getExtras().getInt("ADD OR EDIT", -1);

        if(addOrEdit == 2){
            ADD_OR_EDIT_STATUS = 2;
            setTitle("Edit group");
            GROUP_ID = intent.getExtras().getInt("groupId", -1);
            setInformation();
        }else{
            ADD_OR_EDIT_STATUS = 1;
            setTitle("Create new group");
        }

    }

    private void setInformation(){
        DatabaseHandler db = new DatabaseHandler(this);
        BusinessGroups businessGroups = db.getGroup(GROUP_ID);

        editGroupName.setText(businessGroups.get_name());
        editGroupDescription.setText(businessGroups.get_description());
    }

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
        getMenuInflater().inflate(R.menu.menu_add_group_name, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_next:
                saveInfo();
                AddNewGroupActivity.this.finish();
                //next actiivty to add member
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveInfo(){
        String groupName = editGroupName.getText().toString();
        String description = editGroupDescription.getText().toString();
        if(TextUtils.isEmpty(groupName)){
            Toast.makeText(this.getApplicationContext(),
                    "Must have a group name", Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(description)){
            Toast.makeText(this.getApplicationContext(),
                    "Must have a description", Toast.LENGTH_LONG).show();

        }else {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month_ = calendar.get(Calendar.MONTH) + 1;
            int day_ = calendar.get(Calendar.DATE);

            int date = year * 10000 + month_ * 100 + day_;

            DatabaseHandler db = new DatabaseHandler(getBaseContext());

            BusinessGroups businessGroups = new BusinessGroups();
            businessGroups.set_name(groupName);
            businessGroups.set_description(description);

            if (ADD_OR_EDIT_STATUS == 1) {
                businessGroups.set_member_count(0);
                businessGroups.set_created_date(date);
                db.addGroup(businessGroups);
                Toast.makeText(this.getApplicationContext(),
                        "Created Successfully", Toast.LENGTH_LONG)
                        .show();
            } else if (ADD_OR_EDIT_STATUS == 2) {
                db.updateGroups(businessGroups, GROUP_ID);
                Toast.makeText(this.getApplicationContext(),
                        "Updated Successfully", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    //setup the toolbar home title can be clicked and open the drawer
    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewGroupActivity.this.finish();
                }

            });
        }
    }
}
