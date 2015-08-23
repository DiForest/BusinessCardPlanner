package sam.businesscardplanner.BusinessCard;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/20/2015.
 */
public class BusinessCardProfile extends AppCompatActivity{

    ImageView cardImage;
    TextView mDate;
    TextView cardName;
    TextView cardJob;
    TextView cardCompany;
    TextView cardPhone;
    TextView cardAddress;
    TextView cardCreatedDate;
    TextView cardGroup;
    TextView cardWorkPhone;
    TextView cardWorkAddress;
    TextView cardWorkWebsite;
    TextView cardEmail;
    TextView cardBusinessType;
    TextView cardNote;

    private Toolbar mToolbar;

    private int ITEM_ID;
    private final int EDIT =  2;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_card_profile);
        setUpToolbar();
        setUpNavDrawer();

        declareElement();
        Intent intent = getIntent();
        ITEM_ID = intent.getExtras().getInt("ITEM ID", -1);
        setInformation();
    }

    public void onResume(){
        super.onResume();
        setInformation();
    }

    private void declareElement(){
        cardImage = (ImageView) findViewById(R.id.card_image);

        cardName = (TextView) findViewById(R.id.card_title_name);
        cardJob = (TextView) findViewById(R.id.card_title_job);
        cardCompany = (TextView) findViewById(R.id.card_title_company);
        mDate = (TextView) findViewById(R.id.card_created_date);

        cardCreatedDate = (TextView) findViewById(R.id.card_created_date);
        cardPhone = (TextView) findViewById(R.id.card_phone);
        cardAddress = (TextView) findViewById(R.id.card_address);
        cardEmail = (TextView) findViewById(R.id.card_email);

        cardWorkAddress = (TextView) findViewById(R.id.card_work_address);
        cardWorkPhone = (TextView) findViewById(R.id.card_work_phone);
        cardWorkWebsite = (TextView) findViewById(R.id.card_work_website);

        cardGroup = (TextView) findViewById(R.id.card_group);
        cardBusinessType = (TextView) findViewById(R.id.card_business_type);
        cardNote = (TextView) findViewById(R.id.card_note);
    }

    private void setInformation(){
        DatabaseHandler cards = new DatabaseHandler(this);
        BusinessCard businessCard = cards.getBusinessCard(ITEM_ID);

        String addressStreet = businessCard.get_street();
        String addressCity = businessCard.get_city();
        String addressState = businessCard.get_state();

        cardName.setText(businessCard.get_name());
        setTitle(businessCard.get_name());
        cardCompany.setText("Worked at " + businessCard.get_company());
        cardEmail.setText(businessCard.get_email());
        cardAddress.setText(addressStreet + ", " + addressCity + ", " + addressState);

        String workStreet = businessCard.get_workStreet();
        String workCity = businessCard.get_workCity();
        String workState = businessCard.get_workState();

        cardBusinessType.setText(businessCard.get_businessType());
        cardWorkPhone.setText(businessCard.get_workPhone());
        cardPhone.setText(businessCard.get_phone());
        cardJob.setText(businessCard.get_job());
        cardWorkAddress.setText(workStreet + ", " + workCity + ", " + workState);
        cardWorkWebsite.setText(businessCard.get_workWebsite());
        mDate.setText("Created on " + businessCard.get_date());
        cardNote.setText(businessCard.get_note());

        String imagePath = businessCard.get_image();
        if(imagePath!= null) {
            Uri uri = Uri.parse(imagePath);
            cardImage.setImageURI(uri);
        }

        getInvolvedGroup(ITEM_ID);
    }

    public void getInvolvedGroup(int itemId){
        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        String groupName = db.getAllGroupOfMember(itemId);
        if(groupName.length()>1) {
            cardGroup.setText(groupName);
        }
        else{
            cardGroup.setText("none");
        }
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
                    BusinessCardProfile.this.finish();
                }

            });
        }
    }

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
                Intent intent = new Intent(this,AddNewCardActivity.class );
                intent.putExtra("ADD OR EDIT", EDIT);
                intent.putExtra("itemID", ITEM_ID);
                startActivity(intent);
                return true;

            case R.id.action_delete:
                confirmDeleteBox(ITEM_ID);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDeleteBox(final int itemId){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete card");
        alertDialogBuilder
                .setMessage("Confirm delete this card?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getBaseContext());
                        db.deleteBusinessCard(itemId);
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
}
