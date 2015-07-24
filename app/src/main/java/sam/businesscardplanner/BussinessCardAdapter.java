package sam.businesscardplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 7/20/2015.
 */
public class BussinessCardAdapter extends ArrayAdapter<BusinessCard> {

    private final Context context;
    private final ArrayList<BusinessCard> cardsArrayList;

    public BussinessCardAdapter(Context context, ArrayList<BusinessCard> cardsArrayList){
        super(context, R.layout.list_cards_items, cardsArrayList);

        this.context = context;
        this.cardsArrayList = cardsArrayList;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_cards_items, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        ImageView bcImage = (ImageView) rowView.findViewById(R.id.bc_image);
        TextView createdate = (TextView) rowView.findViewById(R.id.create_date);

        // 4. Set the text for textView
        labelView.setText(cardsArrayList.get(position).get_name());
        valueView.setText(cardsArrayList.get(position).get_company());

        // 5. retrn rowView
        return rowView;
    }
}
