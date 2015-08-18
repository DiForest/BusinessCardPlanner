package sam.businesscardplanner.BusinessGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessCard.RowViewAdapter;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/7/2015.
 */
public class AddMemberActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private int GROUP_ID;
    private int ITEM_ID ;

    private List<BusinessCard> allMemberInGroupList = null;
    private List<BusinessCard> allData = null;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //reuse the fragment business card layout, list
        setContentView(R.layout.add_member);
        setUpNavDrawer();
        setUpToolbar();
        setTitle("Select member..");

        //get the group id
        Intent intent = getIntent();
        GROUP_ID = intent.getExtras().getInt("GROUP ID", -1);
        displayData();
    }

    private void displayData(){
        final RowViewAdapter adapter =  new RowViewAdapter(this.getApplicationContext(),
                generateData());
        final ListView listView = (ListView) this.findViewById(R.id.business_card_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ITEM_ID = adapter.getListItemId(position);
                checkDataDuplicate();
            }
        });
    }

    private List<BusinessCard> generateData(){
        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        List<BusinessCard> businessCardList = db.getAllBusinessCard();
        return businessCardList;
    }

    private void checkDataDuplicate(){
        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        BusinessCard cardWanted = db.getBusinessCard(ITEM_ID);
        String cardName = cardWanted.get_name();
        allMemberInGroupList = db.getAllMemberFromGroup(GROUP_ID);
        if(allMemberInGroupList.size()>0) {
            if (db.checkDuplicateMember(ITEM_ID, GROUP_ID)) {
                Toast.makeText(getApplicationContext(),
                        "Card " + cardName + " already exist in the group ", Toast.LENGTH_LONG)
                        .show();
                finish();
            } else {
                addMember();
            }
        }else{
            addMember();
        }
    }

    private void addMember(){
        DatabaseHandler db = new DatabaseHandler(getBaseContext());

        BusinessGroups bg = new BusinessGroups();
        BusinessCard bc = new BusinessCard();
        bc = db.getBusinessCard(ITEM_ID);
        bg = db.getGroup(GROUP_ID);
        String cardName = bc.get_name();
        String groupName = bg.get_name();

        //add the member into database
        db.addMemberInGroup(GROUP_ID, ITEM_ID);
        Toast.makeText(getApplicationContext(),
                "Added " + cardName + " into " + groupName, Toast.LENGTH_LONG)
                .show();

        Intent intent = new Intent();
        intent.putExtra("Member", cardName);
        setResult(RESULT_OK, intent);
        AddMemberActivity.this.finish();
    }


    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddMemberActivity.this.finish();
                }

            });
        }
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    //setup the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        /*
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(newText);
                System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(query);
                System.out.println("on query submit: "+query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        */
        return true;

    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                //next actiivty to add member
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
