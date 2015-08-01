package sam.businesscardplanner.BusinessCard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import sam.businesscardplanner.DatabaseHandler.CardsDatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/17/2015.
 */
public class AddNewCardActivity extends AppCompatActivity {

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

    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;

    private Toolbar mToolbar;

    private String selectedImagePath;

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

        imageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog();
            }
        });

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
                AddNewCardActivity.this.finish();
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
        String name = nameEditText.getText().toString();
        String job = jobEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String company = companyEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String workAddress = workAddressEditText.getText().toString();
        String workPhone = workPhoneEditText.getText().toString();
        String workWebsite = workWebsiteEditText.getText().toString();
        CardsDatabaseHandler mdb = null;

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

        /*
        imageImageView.buildDrawingCache();
        Bitmap bmap = imageImageView.getDrawingCache();
        byte[] data = getBitmapAsByteArray(bmap);
        businessCard.set_image(data);
        */

        mdb = new CardsDatabaseHandler(getBaseContext());
        mdb.addBusinessCard(businessCard);

    }

    private String getDateTime() {
        String date = "2015-10-10";
        return date;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    protected void onCreateDialog() {

        final CharSequence[] items = { "Camera", "Gallery" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select State");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    callCamera();
                } else
                    callGallery();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void callCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PICK_FROM_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        ImageView image = (ImageView) findViewById(R.id.image);
        if (resultCode == RESULT_OK ) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(photo);
                    }
                    break;
                case PICK_FROM_GALLERY:
                    Bundle extras2 = data.getExtras();
                    if (extras2 != null) {
                        Uri selectedImageUri = data.getData();
                        selectedImagePath = getPath(selectedImageUri);
                        System.out.println("Image Path : " + selectedImagePath);
                        image.setImageURI(selectedImageUri);
                    }
                    break;
            }
        }
    }

    public String getPath(Uri uri) {
        String res = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}


