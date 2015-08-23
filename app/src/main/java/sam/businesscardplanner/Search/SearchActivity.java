package sam.businesscardplanner.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessCard.BusinessCardProfile;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/21/2015.
 */
public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar mToolbar;
    private SearchAdapter adapter;
    private ListView listView;
    private SearchView searchView;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.search_layout);
        setUpNavDrawer();
        setUpToolbar();
        setTitle("Search");

        listView = (ListView) findViewById(R.id.search_list);
        searchView = (SearchView) findViewById(R.id.search_view);
        adapter = new SearchAdapter(getApplicationContext(),generateData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemID = adapter.getListItemId(position);
                viewMemberProfile(itemID);
            }
        });
        searchView.setOnQueryTextListener(this);
    }

    private void viewMemberProfile(int itemId){
        Intent intent = new Intent(this, BusinessCardProfile.class);
        intent.putExtra("ITEM ID", itemId);
        startActivity(intent);
    }

    private List<BusinessCard> generateData(){
        DatabaseHandler db = new DatabaseHandler(getBaseContext());
        List<BusinessCard> businessCardList = db.getAllBusinessCard();
        return businessCardList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchActivity.this.finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
