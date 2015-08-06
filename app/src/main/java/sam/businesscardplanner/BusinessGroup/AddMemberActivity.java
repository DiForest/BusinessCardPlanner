package sam.businesscardplanner.BusinessGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessCard.RowViewAdapter;
import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/7/2015.
 */
public class AddMemberActivity extends AppCompatActivity {

    List list = null;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //reuse the fragment business card layout, list
        setContentView(R.layout.fragment_business_cards);

        final RowViewAdapter adapter =  new RowViewAdapter(this.getApplicationContext(), generateData());
        final ListView listView = (ListView) this.findViewById(R.id.business_card_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    private List<BusinessCard> generateData(){
        DatabaseHandler db1 = new DatabaseHandler(this.getApplicationContext());
        list = db1.getAllBusinessCard();
        return list;
    }

}
