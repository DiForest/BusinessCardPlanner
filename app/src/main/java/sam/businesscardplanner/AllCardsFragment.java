package sam.businesscardplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 7/16/2015.
 */
public class AllCardsFragment extends Fragment {

    ArrayList<BusinessCard> imageArry = new ArrayList<BusinessCard>();
    List list = null;
    ListView business_card_list;
    DataBaseHandler db;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    Bitmap theImage;
    byte[] imageName;
    int imageId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_all_cards, container, false);

        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

    //set up the listview
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        BussinessCardAdapter BCadapter =  new BussinessCardAdapter(getActivity().getApplicationContext(), generateData());
        ListView listView = (ListView) getActivity().findViewById(R.id.business_card_list);
        listView.setAdapter(BCadapter);

    }



    private List<BusinessCard> generateData(){
        DataBaseHandler db1 = new DataBaseHandler(getActivity().getApplicationContext());
        list = db1.getAllBusinessCard();
        return list;
    }


    //setup the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this.getActivity(),NewCardActivity.class);
                this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    protected void onCreateDialog() {

        final CharSequence[] items = { "Camera", "Gallery" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

     //open gallery method

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
        getActivity().startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == getActivity().RESULT_OK) {

            switch (requestCode) {
                case CAMERA_REQUEST:

                    Bundle extras = data.getExtras();

                    if (extras != null) {
                        Bitmap bmp = extras.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Intent i = new Intent(getActivity(),
                                NewCardActivity.class);
                        i.putExtra("picture", byteArray);
                        startActivity(i);
                        getActivity().finish();
                    }
                    break;
                case PICK_FROM_GALLERY:

                    Bundle extras2 = data.getExtras();

                    if (extras2 != null) {
                        Bitmap bmp = extras2.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        //testing code
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Delete entry")
                                .setMessage("Are you sure you want to delete this entry?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        Intent i = new Intent(getActivity(),
                                NewCardActivity.class);
                        i.putExtra("picture", byteArray);
                        startActivity(i);
                    }
                    break;

            }
        }
    }


    */


}
