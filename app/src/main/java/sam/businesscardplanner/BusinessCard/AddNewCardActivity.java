package sam.businesscardplanner.BusinessCard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/17/2015.
 */
public class AddNewCardActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText jobEditText;
    private EditText companyEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText workPhoneEditText;
    private EditText workAddressEditText;
    private EditText workWebsiteEditText;

    private ImageView imageView;
    private Bitmap imageBitmap;
    private Button btnAddImage;

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private Toolbar mToolbar;

    /* ------------------------------------ activity ----------------------------------*/
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

        btnAddImage = (Button) findViewById(R.id.btn_add_image);
        btnAddImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onCreateDialog();
            }
        });

        mAlbumStorageDirFactory = new BaseAlbumDirFactory(); 
    }
    //set the album name
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    //set the file path
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("PlannerImage", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private void setPic() {

		/* Get the size of the ImageView */
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		/* Associate the Bitmap to the ImageView */
        imageView.setImageBitmap(bitmap);

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                handleBigCameraPhoto();
            } else
            if (requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected void onSaveInstanceState(Bundle outsState){
        outsState.putParcelable(BITMAP_STORAGE_KEY, imageBitmap);
        super.onSaveInstanceState(outsState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        imageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
    }

    private void callCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;

        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }

        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
    }

    private void callGallery() {
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
                "Select Picture"), GALLERY_REQUEST);
    }

    protected void onCreateDialog() {
        final CharSequence[] items = {"Take from Camera", "Take from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take from Camera")) {
                    callCamera();
                }else if (items[which].equals("Take from Gallery")) {
                    callGallery();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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
                //saveInfo();
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

    /*
    public void saveInfo(){
        //get bitmap from image view
        Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        //convert to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();

        String name = nameEditText.getText().toString();
        String job = jobEditText.getText().toString();
        String company = companyEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String phoneWork = workPhoneEditText.getText().toString();
        String addressWork = workAddressEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String website = workWebsiteEditText.getText().toString();

        if(TextUtils.isEmpty(name) ) {
            nameEditText.setError("Please fill up the name.");
            return;
        }

        if(TextUtils.isEmpty(job) ) {
            jobEditText.setError("Please fill up the job.");
            return;
        }

        if(TextUtils.isEmpty(company)) {
            companyEditText.setError("Please fill up the company.");
            return;
        }

        if(TextUtils.isEmpty(phone) ) {
            phoneEditText.setError("Please fill up the name.");
            return;
        }

        businessCard.set_name(name);
        businessCard.set_job(job);
        businessCard.set_company(company);
        businessCard.set_phone(phone);
        businessCard.set_workPhone(phoneWork);
        businessCard.set_workAddress(addressWork);
        businessCard.set_address(address);
        businessCard.set_email(email);
        businessCard.set_date(getDateTime());
        businessCard.set_image(imageInByte);
        businessCard.set_workWebsite(website);

        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        db.addBusinessCard(businessCard);
        finish();
    }

    private String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month_ = calendar.get(Calendar.MONTH) +1;
        int day_ = calendar.get(Calendar.DATE);

        String date = ("" + day_+"/"+month_+"/"+year);
        return date;
    }
    */
}


