package sam.businesscardplanner.BusinessCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/16/2015.
 */
public class AllCardsFragment extends Fragment {
    List list = null;

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

        final RowViewAdapter adapter =  new RowViewAdapter(getActivity().getApplicationContext(), generateData());
        final ListView listView = (ListView) getActivity().findViewById(R.id.business_card_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllCardsFragment.this.getActivity(),
                        BusinessCardProfile.class);
                intent.putExtra("KEY", id);
                startActivity(intent);
            }
        });
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
}
