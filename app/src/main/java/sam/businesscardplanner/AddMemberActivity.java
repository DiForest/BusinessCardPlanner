package sam.businesscardplanner;

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
import sam.businesscardplanner.BusinessGroup.BusinessGroups;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;

/**
 * Created by Administrator on 8/7/2015.
 */
public class AddMemberActivity extends AppCompatActivity {

    private List list = null;
    private Toolbar mToolbar;
    private int GROUP_ID;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //reuse the fragment business card layout, list
        setContentView(R.layout.add_member);
        setUpNavDrawer();
        setUpToolbar();
        setTitle("Select member..");

        //get the group id
        Intent intent = getIntent();
        GROUP_ID = intent.getExtras().getInt("groupID", -1);

        //set up the list
        final RowViewAdapter adapter =  new RowViewAdapter(this.getApplicationContext(), generateData());
        final ListView listView = (ListView) this.findViewById(R.id.business_card_list);
        listView.setAdapter(adapter);

        //on click any card to add the member
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemID = adapter.getListItemId(position);
                DatabaseHandler db = new DatabaseHandler(getBaseContext());

                BusinessGroups bg = new BusinessGroups();
                BusinessCard bc = new BusinessCard();
                bc = db.getBusinessCard(itemID);
                bg = db.getGroup(GROUP_ID);
                String cardName = bc.get_name();
                String groupName = bg.get_name();

                //add the member into database
                db.addMemberInGroup(GROUP_ID, itemID);
                Toast.makeText(getApplicationContext(),
                        "Added " + cardName + " into " + groupName, Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent();
                intent.putExtra("Member", cardName);
                setResult(RESULT_OK, intent);
                AddMemberActivity.this.finish();
            }
        });
    }

    private List<BusinessCard> generateData(){
        DatabaseHandler db1 = new DatabaseHandler(this.getApplicationContext());
        list = db1.getAllBusinessCard();
        return list;
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
