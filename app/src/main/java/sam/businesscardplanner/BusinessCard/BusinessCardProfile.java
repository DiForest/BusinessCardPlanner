package sam.businesscardplanner.BusinessCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/20/2015.
 */
public class BusinessCardProfile extends AppCompatActivity{

    ImageView cardImage;
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

    private Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_card_profile);

        setUpToolbar();
        setUpNavDrawer();

        cardImage = (ImageView) findViewById(R.id.card_image);

        cardName = (TextView) findViewById(R.id.card_title_name);
        cardJob = (TextView) findViewById(R.id.card_title_job);
        cardCompany = (TextView) findViewById(R.id.card_title_company);

        cardCreatedDate = (TextView) findViewById(R.id.card_created_date);
        cardPhone = (TextView) findViewById(R.id.card_phone);
        cardAddress = (TextView) findViewById(R.id.card_address);
        cardEmail = (TextView) findViewById(R.id.card_email);

        cardWorkAddress = (TextView) findViewById(R.id.card_work_address);
        cardWorkPhone = (TextView) findViewById(R.id.card_work_phone);
        cardWorkWebsite = (TextView) findViewById(R.id.card_work_website);

        cardGroup = (TextView) findViewById(R.id.card_group);

        Intent intent = getIntent();
        int itemID = intent.getExtras().getInt("ITEM ID", -1);
        DatabaseHandler cards = new DatabaseHandler(this);
        BusinessCard businessCard = cards.getBusinessCard(itemID);

        cardName.setText(businessCard.get_name());
        cardCompany.setText(businessCard.get_company());
        cardEmail.setText(businessCard.get_email());
        cardWorkPhone.setText(businessCard.get_workPhone());
        cardPhone.setText(businessCard.get_phone());
        cardWorkAddress.setText(businessCard.get_workAddress());
        cardJob.setText(businessCard.get_job());
        cardWorkWebsite.setText(businessCard.get_workWebsite());
        cardAddress.setText(businessCard.get_address());

        /*
        byte[] outImage = businessCard.get_image();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        cardImage.setImageBitmap(theImage);
        */

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
}
