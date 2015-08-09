package sam.businesscardplanner.BusinessGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/30/2015.
 */
public class GroupRowViewAdapter extends ArrayAdapter<BusinessGroups> {

    private final Context context;
    private final List<BusinessGroups> groupsList;


    public GroupRowViewAdapter(Context context, List<BusinessGroups> groupsList) {
        super(context, R.layout.groups_row_items,groupsList);

        this.context = context;
        this.groupsList = groupsList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.groups_row_items, parent, false);

        TextView  groupName = (TextView) rowView.findViewById(R.id.name);
        TextView groupCreatedDate = (TextView) rowView.findViewById(R.id.create_date);
        TextView groupMember = (TextView) rowView.findViewById(R.id.member_number);

        groupName.setText(groupsList.get(position).get_name());
        groupCreatedDate.setText("created by "+groupsList.get(position).get_created_date());

        //display the number of member
        int group_member_number = groupsList.get(position).get_group_member();
        groupMember.setText(group_member_number + " members");

        BusinessGroups groups = groupsList.get(position);

        return rowView;
    }

    public int getListItemId(int position){
        return groupsList.get(position).get_id();
    }
    
}
