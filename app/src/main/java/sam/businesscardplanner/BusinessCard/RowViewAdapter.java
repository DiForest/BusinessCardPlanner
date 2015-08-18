package sam.businesscardplanner.BusinessCard;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/20/2015.
 */
public class RowViewAdapter extends ArrayAdapter<BusinessCard> implements Filterable{

    private final Context context;
    private final List<BusinessCard> cardsList;

    public static final int THUMBNAIL_HEIGHT = 48;
    public static final int THUMBNAIL_WIDTH = 66;

    public RowViewAdapter(Context context, List<BusinessCard> cardsList){
        super(context, R.layout.business_cards_row_items, cardsList);

        this.context = context;
        this.cardsList = cardsList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.business_cards_row_items, parent, false);

        // 3. Get the two text view from the rowView
        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        TextView companyView = (TextView) rowView.findViewById(R.id.company);
        TextView jobView = (TextView) rowView.findViewById(R.id.job);
        ImageView bcImage = (ImageView) rowView.findViewById(R.id.bc_image);
        TextView date = (TextView) rowView.findViewById(R.id.create_date);

        // 4. Set the text for textView
        nameView.setText(cardsList.get(position).get_name());
        jobView.setText(cardsList.get(position).get_job());
        companyView.setText(cardsList.get(position).get_company());
        date.setText(cardsList.get(position).get_date());

        String imagePath = cardsList.get(position).get_image();
        if(imagePath!= null) {
            bcImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }

        return rowView;
    }


    public int getListItemId(int position){
        return cardsList.get(position).get_id();
    }
}
