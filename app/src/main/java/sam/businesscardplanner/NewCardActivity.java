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

    ImageView imageImageView;
    EditText nameEditText;
    EditText jobEditText;
    EditText companyEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText addressEditText;
    EditText workPhoneEditText;
    EditText workAddressEditText;
    EditText workWebsiteEditText;
    TextView groupEditText;

    private Toolbar mToolbar;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        imageImageView = (ImageView) findViewById(R.id.image);
        nameEditText = (EditText)findViewById(R.id.name_label);
        jobEditText = (EditText) findViewById(R.id.job_label);
        companyEditText = (EditText) findViewById(R.id.company_label);
        emailEditText = (EditText) findViewById(R.id.email_label);
        phoneEditText = (EditText) findViewById(R.id.phone_label);
        addressEditText = (EditText) findViewById(R.id.address_label);
        workAddressEditText = (EditText) findViewById(R.id.address_company_label);
        workPhoneEditText = (EditText) findViewById(R.id.phone_company_label);
        workWebsiteEditText = (EditText) findViewById(R.id.web_company_label);
        groupEditText = (TextView) findViewById(R.id.group_tag);


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
                saveInfo();
                NewCardActivity.this.finish();
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

    public void saveInfo(){
        String name = nameEditText.getText().toString();
        String job = jobEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String company = companyEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String workAddress = workAddressEditText.getText().toString();
        String workPhone = workPhoneEditText.getText().toString();
        String workWebsite = workWebsiteEditText.getText().toString();
        String group = "nope";
        DataBaseHandler mdb = null;

        BusinessCard businessCard = new BusinessCard();
        businessCard.set_name(name);
        businessCard.set_company(company);
        businessCard.set_address(address);
        businessCard.set_job(job);
        businessCard.set_email(email);
        businessCard.set_phone(phone);
        businessCard.set_workPhone(workPhone);
        businessCard.set_workWebsite(workWebsite);
        businessCard.set_workAddress(workAddress);
        businessCard.set_category(group);

        mdb = new DataBaseHandler(getBaseContext());
        mdb.addBusinessCard(businessCard);

    }
}


