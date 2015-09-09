package com.github.minhnguyen31093.countriespicker.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.minhnguyen31093.countriespicker.R;
import com.github.minhnguyen31093.countriespicker.model.Country;
import com.github.minhnguyen31093.countriespicker.utils.CountriesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class CountryAdapter extends ArrayAdapter<Country> implements Filterable {

    // Choice mode
    public static final int CHOICE_MODE_SELECT = 0;
    public static final int CHOICE_MODE_SEARCH = 1;
    private LayoutInflater inflater;
    private List<Country> countries, filteredData;
    private int choiceMode;
    private String hint = "Search Country Name, Code, Capital";

    private ItemFilter mFilter = new ItemFilter();

    public CountryAdapter(Context context, int choiceMode) {
        super(context, R.layout.mn_list_small_item_country);
        this.countries = CountriesUtils.getlistCountriesFromJson(context);
        this.filteredData = countries;
        this.choiceMode = choiceMode;
        inflater = LayoutInflater.from(context);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.mn_list_small_item_country, parent, false);
            holder.country = (TextView) convertView.findViewById(R.id.mn_listSmallItemCountry_txtCountry);
            holder.flag = (ImageView) convertView.findViewById(R.id.mn_listSmallItemCountry_imgFlag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillData(holder, position);
        return convertView;
    }

    private void fillData(ViewHolder holder, int position) {
        if (choiceMode == CHOICE_MODE_SELECT) {
            if (position == 0) {
                holder.flag.setImageResource(R.drawable.flag_default);
                holder.country.setText(hint);
            } else {
                holder.flag.setImageResource(CountriesUtils.getResId(filteredData.get(position - 1).getId()));
                holder.country.setText(filteredData.get(position - 1).getName());
            }
        } else {
            holder.flag.setImageResource(CountriesUtils.getResId(filteredData.get(position).getId()));
            holder.country.setText(filteredData.get(position).getName());
        }
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public Country getItem(int position) {
        if (choiceMode == CHOICE_MODE_SELECT) {
            if (position < 1) {
                return null;
            }
            return filteredData.get(position - 1);
        } else {
            return filteredData.get(position);
        }
    }

    @Override
    public int getCount() {
        if (choiceMode == CHOICE_MODE_SELECT) {
            return filteredData.size() + 1;
        } else {
            return filteredData.size();
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

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

    public static class ViewHolder {
        TextView country;
        ImageView flag;
    }
}