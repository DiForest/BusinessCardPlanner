package sam.businesscardplanner.BusinessGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

public class AddNewGroupActivity extends AppCompatActivity {

    EditText editGroupName;
    Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        setUpNavDrawer();
        setUpToolbar();

        editGroupName = (EditText) findViewById(R.id.edit_group_name);
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

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month_ = calendar.get(Calendar.MONTH) +1;
        int day_ = calendar.get(Calendar.DATE);

        String date = ("" + day_+"/"+month_+"/"+year);

        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        BusinessGroups businessGroups= new BusinessGroups();
        businessGroups.set_name(groupName);
        businessGroups.set_created_date(date);
        db.addGroup(businessGroups);

        //Intent intent = new Intent(this, AddNewGroupActivity.class);
        //intent.putExtra("groupName", groupName);
        //startActivity(intent);
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
