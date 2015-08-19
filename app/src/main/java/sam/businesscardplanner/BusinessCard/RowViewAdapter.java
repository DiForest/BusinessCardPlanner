package sam.businesscardplanner.BusinessCard;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sam.businesscardplanner.R;

/**
 * Created by Administrator on 7/20/2015.
 */
public class RowViewAdapter extends ArrayAdapter<BusinessCard> implements Filterable{

    private final Context context;
    private List<BusinessCard> cardsList;
    private List<BusinessCard> cardListItem;
    private ValueFilter valueFilter;

    public RowViewAdapter(Context context, List<BusinessCard> cardsList){
        super(context, R.layout.business_cards_row_items, cardsList);

        this.context = context;
        this.cardsList = cardsList;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.business_cards_row_items, parent, false);

        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        TextView companyView = (TextView) rowView.findViewById(R.id.company);
        TextView jobView = (TextView) rowView.findViewById(R.id.job);
        ImageView bcImage = (ImageView) rowView.findViewById(R.id.bc_image);
        TextView date = (TextView) rowView.findViewById(R.id.create_date);

        nameView.setText(cardsList.get(position).get_name());
        jobView.setText(cardsList.get(position).get_job());
        companyView.setText(cardsList.get(position).get_company());
        date.setText("" + cardsList.get(position).get_date());

        String imagePath = cardsList.get(position).get_image();
        if(imagePath!= null) {
            bcImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }

        return rowView;
    }

    public int getListItemId(int position){
        return cardsList.get(position).get_id();
    }

    @Override
    public Filter getFilter(){
        if(valueFilter == null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            FilterResults filterResults = new FilterResults();

            if(constraint != null && constraint.length() >0){
                List<BusinessCard> filterList = new ArrayList<BusinessCard>();
                for(int i = 0; i< cardsList.size(); i++){
                    if((cardsList.get(i).get_name().toUpperCase())
                            .contains(constraint.toString().toUpperCase())){
                        filterList.add(cardsList.get(i));
                    }
                }

                filterResults.count = filterList.size();
                filterResults.values = filterList;
            }else{
                filterResults.count = cardsList.size();
                filterResults.values = cardsList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            cardsList = (List<BusinessCard>) results.values;
            notifyDataSetChanged();
        }
    }
}
