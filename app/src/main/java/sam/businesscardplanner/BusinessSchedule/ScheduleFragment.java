package sam.businesscardplanner.BusinessSchedule;

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

import sam.businesscardplanner.DatabaseHandler.DatabaseHandler;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/16/2015.
 */

public class ScheduleFragment extends Fragment{
    List list = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EventRowViewAdapter adapter =
                new EventRowViewAdapter(getActivity().getApplicationContext(), generateData());
        final ListView listView = (ListView) getActivity().findViewById(R.id.event_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId =adapter.getListItemId(position);
                Intent intent = new Intent(ScheduleFragment.this.getActivity(),
                        BusinessEventProfile.class);
                intent.putExtra("ITEM ID", itemId);
                startActivity(intent);
            }
        });
    }

    public void onResume(){
        super.onResume();
        final EventRowViewAdapter adapter =
                new EventRowViewAdapter(getActivity().getApplicationContext(), generateData());
        final ListView listView = (ListView) getActivity().findViewById(R.id.event_list);
        listView.setAdapter(adapter);
    }

    private List<BusinessEvent> generateData(){
        DatabaseHandler db1 = new DatabaseHandler(getActivity().getApplicationContext());
        list = db1.getAllEvent();
        return list;
    }


    /* -------------------Menu bar ---------------------*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this.getActivity(),NewEventActivity.class);
                this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
