package sam.businesscardplanner.BusinessGroup;

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
public class GroupsFragment extends Fragment {

    private Intent intent;
    List list = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //intialise the groups list
        final GroupRowViewActivity adapter = new GroupRowViewActivity(getActivity().getApplication(), generateData());
        final ListView listView = (ListView) getActivity().findViewById(R.id.group_list);
        listView.setAdapter(adapter);

        //clicking on any business card in the list
        //start group profile activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = adapter.getListItemId(position);

                //pass the selected item id to new activity
                Intent intent = new Intent(
                        GroupsFragment.this.getActivity(), GroupsProfile.class);
                intent.putExtra("ITEM ID", itemId);
                startActivity(intent);
            }
        });
    }

    public void onResume(){
        super.onResume();
        final GroupRowViewActivity adapter = new GroupRowViewActivity(getActivity().getApplication(), generateData());
        final ListView listView = (ListView) getActivity().findViewById(R.id.group_list);
        listView.setAdapter(adapter);
    }

    //get all the group list and pass to list adapter
    private List<BusinessGroups> generateData(){
        DatabaseHandler db1 = new DatabaseHandler(getActivity().getApplicationContext());
        list = db1.getAllGroup();
        return list;
    }

    //setup the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.menu_group, menu);
    }

    //setup the menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                //add a new group
                Intent intent = new Intent(this.getActivity(), AddNewGroupActivity.class);
                intent.putExtra("ADD OR EDIT", 1);
                this.startActivity(intent);
                return true;
            case R.id.action_sort_by_date:
                triggerSortByDate();
                return true;
            case R.id.action_sort_by_name:
                triggerSortByName();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void triggerSortByDate(){
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
        list.clear();
        list = db.getAllGroupInOrderDate();
        final GroupRowViewActivity adapter = new GroupRowViewActivity(getActivity().getApplicationContext(),
                list);
        final ListView listView = (ListView) getActivity().findViewById(R.id.group_list);
        listView.setAdapter(adapter);
    }

    private void triggerSortByName(){
        list.clear();
        final GroupRowViewActivity adapter = new GroupRowViewActivity(getActivity().getApplicationContext(),
                generateData());
        final ListView listView = (ListView) getActivity().findViewById(R.id.group_list);
        listView.setAdapter(adapter);
    }

}
