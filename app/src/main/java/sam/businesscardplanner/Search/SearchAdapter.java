package sam.businesscardplanner.Search;

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

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.R;

/**
 * Created by Administrator on 8/23/2015.
 */
public class SearchAdapter extends ArrayAdapter<BusinessCard> implements Filterable {

    private final Context context;
    private List<BusinessCard> cardsList;
    private List<BusinessCard> cardsFilterList;
    private ValueFilter valueFilter;

    public SearchAdapter(Context context, List<BusinessCard> cardsList){
        super(context, R.layout.search_layout_row_items, cardsList);

        this.context = context;
        this.cardsList = cardsList;
        this.cardsFilterList = new ArrayList<BusinessCard>(cardsList);
    }

    @Override
    public int getCount() {
        return cardsList.size();
    }

    @Override
    public BusinessCard getItem(int position) {
        return cardsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cardsList.get(position).get_id();
    }

    public int getListItemId(int position){
        return cardsList.get(position).get_id();
    }
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.search_layout_row_items, null);
            TextView nameView = (TextView) convertView.findViewById(R.id.name);
            TextView companyView = (TextView) convertView.findViewById(R.id.company);
            TextView jobView = (TextView) convertView.findViewById(R.id.job);
            TextView businessTypeView = (TextView) convertView.findViewById(R.id.business_type);
            TextView businessCityView = (TextView) convertView.findViewById(R.id.address_city);
            ImageView bcImage = (ImageView) convertView.findViewById(R.id.bc_image);

            nameView.setText(cardsList.get(position).get_name());
            jobView.setText("Job: "+cardsList.get(position).get_job());
            companyView.setText("Company: "+cardsList.get(position).get_company());
            businessTypeView.setText("Business type: "+cardsList.get(position).get_businessType());
            businessCityView.setText("City: "+cardsList.get(position).get_businessType());

            String imagePath = cardsList.get(position).get_image();
            if (imagePath != null) {
                bcImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            String prefix = constraint.toString().toUpperCase();

            if (prefix == null || prefix.length() == 0) {
                synchronized (this) {
                    filterResults.values = cardsList;
                    filterResults.count = cardsList.size();
                }
            } else {
                ArrayList<BusinessCard> filterList = new ArrayList<BusinessCard>();
                ArrayList<BusinessCard> unfilterList = new ArrayList<BusinessCard>();
                synchronized (this) {
                    unfilterList.addAll(cardsList);
                }
                for (int i = 0; i < unfilterList.size(); i++) {
                    if ((unfilterList.get(i).get_name().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(cardsList.get(i));
                    } else if ((unfilterList.get(i).get_businessType().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(cardsList.get(i));
                    } else if ((unfilterList.get(i).get_company().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(cardsList.get(i));
                    } else if ((unfilterList.get(i).get_city().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(cardsList.get(i));
                    } else if ((unfilterList.get(i).get_job().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(cardsList.get(i));
                    }

                    filterResults.count = filterList.size();
                    filterResults.values = filterList;
                }
            }
                return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
                cardsList = (ArrayList<BusinessCard>) results.values;
                notifyDataSetChanged();
                clear();
                int count = cardsList.size();
                for (int i = 0; i < count; i++) {
                    add(cardsList.get(i));
                    notifyDataSetChanged();
                }
            }
        }
}
