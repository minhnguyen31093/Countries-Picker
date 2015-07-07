package com.minhnguyen.countriespickerdialog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.minhnguyen.countriespickerdialog.R;
import com.minhnguyen.countriespickerdialog.model.Country;
import com.minhnguyen.countriespickerdialog.utils.CountriesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class CountriesAdapter extends ArrayAdapter<Country> implements Filterable {
    Context context;
    LayoutInflater inflater;
    ListView lvwCountries;
    List<Country> filteredData;
    List<Country> countries;

    private ItemFilter mFilter = new ItemFilter();

    public CountriesAdapter(Context context, int resourceId, List<Country> countries) {
        super(context, resourceId, countries);
        this.context = context;
        this.countries = countries;
        this.filteredData = countries;
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            lvwCountries = (ListView) parent;
            view = inflater.inflate(R.layout.mn_list_item_country, null);
            holder.country = (TextView) view.findViewById(R.id.mn_listItemCountry_txtCountry);
            holder.flag = (ImageView) view.findViewById(R.id.mn_listItemCountry_imgFlag);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
//        holder.country.setText(filteredData.get(position).getName() + "(" + filteredData.get(position).getPhone() + ") ");
        holder.country.setText(filteredData.get(position).getName());
        holder.flag.setImageResource(CountriesUtils.getResId(filteredData.get(position).getId()));
        return view;
    }

    @Override
    public Country getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    public Filter getFilter() {
        return mFilter;
    }

    @SuppressLint("DefaultLocale")
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (!TextUtils.isEmpty(constraint)) {
                String filterString = constraint.toString().toLowerCase();
                List<Country> nlist = new ArrayList<Country>();
                // for each item in original country list if country name contain search text add item to new list
                for (Country countryItem : countries) {
                    if (countryItem.getName().toLowerCase().contains(filterString)
                            || countryItem.getNativeName().toLowerCase().contains(filterString)
                            || countryItem.getPhone().contains(filterString)
                            || countryItem.getId().toLowerCase().contains(filterString)
                            || countryItem.getCapital().toLowerCase().contains(filterString)
                            ) {
                        nlist.add(countryItem);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            } else {
                results.values = countries;
                results.count = countries.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            filteredData = (List<Country>) results.values;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        TextView country;
        ImageView flag;
    }
}