package sam.businesscardplanner.BusinessGroup;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/1/2015.
 */
public class GroupMemberListAdapter extends ArrayAdapter<BusinessCard> {

    private final Context context;
    private final List<BusinessCard> memberList;

    TextView memberName;
    ImageView memberImage;

    public GroupMemberListAdapter(Context context, List<BusinessCard> memberList) {
        super(context, R.layout.groups_profile_member_list, memberList);

        this.context = context;
        this.memberList = memberList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.groups_profile_member_list, parent,
                false);

        memberName = (TextView) rowView.findViewById(R.id.name);
        memberImage = (ImageView) rowView.findViewById(R.id.bc_image);

        memberName.setText(memberList.get(position).get_name());
        String imagePath = memberList.get(position).get_image();
        if(imagePath!= null) {
            memberImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
        return rowView;
    }

    public int getListItemId(int position){
        int itemId = memberList.get(position).get_id();
        return itemId;
    }

}
