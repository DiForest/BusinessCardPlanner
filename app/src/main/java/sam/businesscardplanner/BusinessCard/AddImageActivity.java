package sam.businesscardplanner.BusinessCard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/11/2015.
 */
public class AddImageActivity extends AppCompatActivity {

    ImageView image;
    Bitmap theImage;
    BusinessCard businessCard;

    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static String name = "";
    private static String company = "";
    private static String job="";
    private static String date = "";
    private static String workAddress = "";
    private static String workPhone = "";
    private static String phone = "";
    private static String address = "";
    private static String website = "";
    private static String email = "";

    private Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_image);
        setUpToolbar();
        setUpNavDrawer();

        /*
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        company = intent.getStringExtra("company");
        job = intent.getStringExtra("job");
        date = intent.getStringExtra("date");
        workAddress = intent.getStringExtra("workAddress");
        workPhone = intent.getStringExtra("workPhone");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        website = intent.getStringExtra("website");
        email = intent.getStringExtra("email");
        */

        image = (ImageView) findViewById(R.id.business_card_image);

        byte[] outImage = businessCard.get_image();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        image.setImageBitmap(theImage)
        ;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display dialog box
                onCreateDialog();
            }
        });

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
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                //saveInfo();
                AddImageActivity.this.finish();
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
                    AddImageActivity.this.finish();
                }

            });
        }
    }

    protected void onCreateDialog() {

        final CharSequence[] items = {"Take from Camera", "Take from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void callCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", true);
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);

        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PICK_FROM_GALLERY);
    }

    /*
    public void saveInfo(){
        DatabaseHandler mdb = new DatabaseHandler(getBaseContext());

        businessCard.set_name(name);
        businessCard.set_date(date);
        businessCard.set_address(address);
        businessCard.set_job(job);
        businessCard.set_company(company);
        businessCard.set_email(email);
        businessCard.set_workAddress(workAddress);
        businessCard.set_workPhone(workPhone);
        businessCard.set_workWebsite(website);
        businessCard.set_phone(phone);
        businessCard.set_image(image);
        mdb.addBusinessCard(businessCard);
    }
    */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap yourImage = extras.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte imageInByte[] = stream.toByteArray();
                        Log.e("output before convert", imageInByte.toString());
                        // Inserting Contacts
                        Log.d("Insert: ", "Inserting ..");

                        DatabaseHandler db = new DatabaseHandler(getBaseContext());
                        db.addBusinessCard(new BusinessCard("java", imageInByte));

                        Intent i = new Intent(AddImageActivity.this,
                                AddImageActivity.class);
                        startActivity(i);
                        finish();
                    }
                    break;
                case PICK_FROM_GALLERY:
                    Bundle extras2 = data.getExtras();
                    if (extras2 != null) {
                        Bitmap yourImage = extras2.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte imageInByte[] = stream.toByteArray();
                        Log.e("output before convert", imageInByte.toString());
                        // Inserting Contacts
                        Log.d("Insert: ", "Inserting ..");

                        DatabaseHandler db = new DatabaseHandler(getBaseContext());
                        db.addBusinessCard(new BusinessCard("java", imageInByte));

                        Intent i = new Intent(AddImageActivity.this,
                                AddImageActivity.class);
                        startActivity(i);
                        finish();
                    }
                    break;
            }
        }
    }
}
