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

import java.util.Calendar;
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

        final String currentDate = getCurrentDate();

        final EventRowViewAdapter adapter =
                new EventRowViewAdapter(getActivity().getApplicationContext(), generateData(currentDate));
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
        final String currentDate = getCurrentDate();
        final EventRowViewAdapter adapter =
                new EventRowViewAdapter(getActivity().getApplicationContext(),
                        generateData(currentDate));
        final ListView listView = (ListView) getActivity().findViewById(R.id.event_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = adapter.getListItemId(position);
                Intent intent = new Intent(ScheduleFragment.this.getActivity(),
                        BusinessEventProfile.class);
                intent.putExtra("ITEM ID", itemId);
                startActivity(intent);
            }
        });
    }

    private List<BusinessEvent> generateData(String date){
        DatabaseHandler db1 = new DatabaseHandler(getActivity().getApplicationContext());
        list = db1.getAllEvent(date);
        return list;
    }

    private String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH) +1;
        int dd = c.get(Calendar.DAY_OF_MONTH);

        String mmFormat = "";
        if(mm<10)
            mmFormat = "0"+mm;
        else
            mmFormat = ""+mm;

        String ddFormat = "";
        if(dd<10)
            ddFormat = "0"+dd;
        else
            ddFormat=""+dd;

        return yy + "/" + mmFormat + "/" + ddFormat  ;
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
