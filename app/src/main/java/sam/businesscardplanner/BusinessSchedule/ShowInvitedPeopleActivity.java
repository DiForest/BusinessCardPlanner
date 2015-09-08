package sam.businesscardplanner.BusinessSchedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessCard.BusinessCardProfile;
import sam.businesscardplanner.BusinessCard.RowViewAdapter;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 9/9/2015.
 */
public class ShowInvitedPeopleActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private List<BusinessCard> invitedPoeple;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.add_member);
        setUpNavDrawer();
        setUpToolbar();
        setTitle("Invited People");

        Intent intent = getIntent();
        String people = intent.getExtras().getString("nameList");
        displayData(people);
    }

    private void displayData(String people){
        final RowViewAdapter adapter =  new RowViewAdapter(this.getApplicationContext(),
                generateData(people));
        final ListView listView = (ListView) this.findViewById(R.id.business_card_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = adapter.getListItemId(position);
                viewMemberProfile(itemId);
            }
        });
    }

    private void viewMemberProfile(int itemId){
        Intent intent = new Intent(this, BusinessCardProfile.class);
        intent.putExtra("ITEM ID", itemId);
        startActivity(intent);
    }

    private List<BusinessCard> generateData(String people){
        List<BusinessCard> list = new ArrayList<BusinessCard>();
        DatabaseHandler db1 = new DatabaseHandler(this);
        String[] nameList = people.split(",");
        for(String name : nameList){
            list.add(db1.getBusinessCard(name));
        }

        return list;
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowInvitedPeopleActivity.this.finish();
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

        return true;

    }
}
