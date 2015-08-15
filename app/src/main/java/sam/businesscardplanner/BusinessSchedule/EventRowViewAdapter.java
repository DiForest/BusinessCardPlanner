package sam.businesscardplanner.BusinessSchedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/12/2015.
 */
public class EventRowViewAdapter extends ArrayAdapter<BusinessEvent> {
    private final Context context;
    private final List<BusinessEvent> eventList;

    public EventRowViewAdapter(Context context, List<BusinessEvent> eventList){
        super(context, R.layout.event_row_items,eventList);

        this.context = context;
        this.eventList = eventList;

    }

    public View getView (int position, View convertView , ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.event_row_items, parent, false);

        TextView eventTitle = (TextView) rowView.findViewById(R.id.event_title);
        TextView eventDay = (TextView) rowView.findViewById(R.id.label_date);
        TextView eventMonth = (TextView) rowView.findViewById(R.id.label_month);

        //String dateTime = eventList.get(position).get_startDateTime();
       // String[] dateTimeString = dateTime.split("/");
       // String day = dateTimeString[0];
        //String month = dateTimeString[1];
        //String year = dateTimeString[2];

        eventTitle.setText(eventList.get(position).get_calendar_tile());
        eventDay.setText("12");
        eventMonth.setText("Nov");

        return rowView;
    }

   // public int getListItemId(int position){
        //return eventList.get(position).get_calendar_id();
    //}

    private String defineMonth(String monthValue){
        String monthString = "";
        switch(monthValue) {
            case "1":
                monthString = "Jan";
                break;
            case "2" :
                monthString = "Feby";
                break;
            case "3" :
                monthString = "Mar";
                break;
            case "4" :
                monthString = "Apr";
                break;
            case "5" :
                monthString = "May";
                break;
            case "6" :
                monthString = "Jun";
                break;
            case "7" :
                monthString = "Jul";
                break;
            case "8" :
                monthString = "Aug";
                break;
            case "9":
                monthString = "Sep";
                break;
            case "10" :
                monthString = "Oct";
                break;
            case "11":
                monthString = "Nov";
                break;
            case "12":
                monthString = "Dec";
                break;
        }

        return monthString;
    }

}
