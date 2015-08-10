package sam.businesscardplanner.BusinessCard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/17/2015.
 */
public class AddNewCardActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText jobEditText;
    EditText companyEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText addressEditText;
    EditText workPhoneEditText;
    EditText workAddressEditText;
    EditText workWebsiteEditText;

    ImageView imageView;
    Button btnAddImage;

    BusinessCard businessCard = new BusinessCard();

    private Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        //setup all the elements tag
        imageView = (ImageView) findViewById(R.id.business_card_image);
        btnAddImage = (Button) findViewById(R.id.btn_add_image);
        nameEditText = (EditText)findViewById(R.id.name_label);
        jobEditText = (EditText) findViewById(R.id.job_label);
        companyEditText = (EditText) findViewById(R.id.company_label);
        emailEditText = (EditText) findViewById(R.id.email_label);
        phoneEditText = (EditText) findViewById(R.id.phone_label);
        addressEditText = (EditText) findViewById(R.id.address_label);
        workAddressEditText = (EditText) findViewById(R.id.address_company_label);
        workPhoneEditText = (EditText) findViewById(R.id.phone_company_label);
        workWebsiteEditText = (EditText) findViewById(R.id.web_company_label);

        //setup the menu
        setUpToolbar();
        setUpNavDrawer();

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
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
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_next:
                saveInfo();
                finish();
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
                    AddNewCardActivity.this.finish();
                }

            });
        }
    }

    public void saveInfo(){
        //get bitmap from image view
        Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        //convert to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();

        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        db.addBusinessCard(new BusinessCard("java", imageInByte));
    }

    private String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month_ = calendar.get(Calendar.MONTH) +1;
        int day_ = calendar.get(Calendar.DATE);

        String date = ("" + day_+"/"+month_+"/"+year);
        return date;
    }
}


