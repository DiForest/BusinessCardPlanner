package sam.businesscardplanner.BusinessCard;

import android.content.Context;
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
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        ImageView bcImage = (ImageView) rowView.findViewById(R.id.bc_image);
        TextView date = (TextView) rowView.findViewById(R.id.create_date);

        // 4. Set the text for textView
        labelView.setText(cardsList.get(position).get_name());
        valueView.setText(cardsList.get(position).get_company());
        date.setText(cardsList.get(position).get_date());

        /*
        byte[] outImage = cardsList.get(position).get_image();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        bcImage.setImageBitmap(theImage);

        byte[] outImage = cardsList.get(position).get_image();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);

        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        Float width  = new Float(theImage.getWidth());
        Float height = new Float(theImage.getHeight());
        Float ratio = width/height;
        theImage = Bitmap.createScaledBitmap(theImage, (int)(THUMBNAIL_HEIGHT*ratio), THUMBNAIL_HEIGHT, false);

        int padding = (THUMBNAIL_WIDTH - theImage.getWidth())/2;
        bcImage.setPadding(padding, 0, padding, 0);
        bcImage.setImageBitmap(theImage);
    */
        BusinessCard cards = cardsList.get(position);

        return rowView;
    }

    public int getListItemId(int position){
        return cardsList.get(position).get_id();
    }
}
