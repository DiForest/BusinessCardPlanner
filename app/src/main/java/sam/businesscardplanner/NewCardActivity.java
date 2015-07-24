package sam.businesscardplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 7/17/2015.
 */
public class NewCardActivity extends AppCompatActivity {

    ImageView image;
    EditText name, job, company,email,phone,address,workPhone, workAddress,workWebsite;
    TextView group;

    private Toolbar mToolbar;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        image = (ImageView) findViewById(R.id.image);
        name = (EditText)findViewById(R.id.name_label);
        job = (EditText) findViewById(R.id.job_label);
        company = (EditText) findViewById(R.id.company_label);
        email = (EditText) findViewById(R.id.email_label);
        phone = (EditText) findViewById(R.id.phone_label);
        address = (EditText) findViewById(R.id.address_label);
        workAddress = (EditText) findViewById(R.id.address_company_label);
        workPhone = (EditText) findViewById(R.id.phone_company_label);
        workWebsite = (EditText) findViewById(R.id.web_company_label);
        group = (TextView) findViewById(R.id.group_tag);


        //setup the menu
        setUpToolbar();
        setUpNavDrawer();

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
                //save into database
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //setup the toolbar home title can be clicked and open the drawer
    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewCardActivity.this.finish();
                }

            });
        }
    }
}


