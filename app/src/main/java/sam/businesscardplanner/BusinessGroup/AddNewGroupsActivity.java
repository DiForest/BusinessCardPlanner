package sam.businesscardplanner.BusinessGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import sam.businesscardplanner.DatabaseHandler.GroupsDatabaseHandler;
import sam.businesscardplanner.R;



public class AddNewGroupsActivity extends AppCompatActivity {

    EditText editGroupName;
    EditText editGroupDescription;
    Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        editGroupName = (EditText) findViewById(R.id.edit_group_name);
        editGroupDescription =(EditText) findViewById(R.id.edit_group_description);

        saveInfo();
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
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                saveInfo();
                AddNewGroupsActivity.this.finish();
                //next actiivty to add member
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveInfo(){
        String groupName = editGroupName.getText().toString();
        String groupDescription = editGroupDescription.getText().toString();

        BusinessGroups businessGroups = new BusinessGroups();
        businessGroups.set_name(groupName);
        businessGroups.set_description(groupDescription);
        //temporary function
        GroupsDatabaseHandler db = new GroupsDatabaseHandler(getBaseContext());
        db.addGroup(businessGroups);
    }

    //setup the toolbar home title can be clicked and open the drawer
    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewGroupsActivity.this.finish();
                }

            });
        }
    }
}
