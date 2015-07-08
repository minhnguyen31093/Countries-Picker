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
import com.minhnguyen.countriespickerdialog.customview.CountriesPickerDialog;
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
    List<Country> listSelectedCountries;

    private ItemFilter mFilter = new ItemFilter();
    private int choiceMode = CountriesPickerDialog.CHOICE_MODE_SINGLE;

    public CountriesAdapter(Context context, int resourceId, List<Country> countries, int choiceMode) {
        super(context, resourceId, countries);
        this.context = context;
        this.countries = countries;
        this.filteredData = countries;
        this.choiceMode = choiceMode;
        inflater = LayoutInflater.from(context);
        listSelectedCountries = new ArrayList<Country>();
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
        if (lvwCountries.isItemChecked(position)) {
            view.setBackgroundResource(R.drawable.mn_blue_light);
        } else {
            view.setBackgroundResource(R.drawable.mn_white);
        }
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

    public List<Country> getList() {
        return filteredData;
    }

    public List<Country> getListSelectedCountries() {
        return this.listSelectedCountries;
    }

    public int getListSelectedCountriesCount() {
        return this.listSelectedCountries.size();
    }

    public void addItemToListSelectedCountries(Country selectedCountry) {
        if (!this.listSelectedCountries.contains(selectedCountry)) {
            this.listSelectedCountries.add(selectedCountry);
        }
    }

    public void removeItemInListSelectedCountries(Country selectedCountry) {
        for (Country countryItem : this.listSelectedCountries) {
            if (countryItem.getId().equals(selectedCountry.getId())) {
                this.listSelectedCountries.remove(countryItem);
                break;
            }
        }
    }

    private void scrollToSelected(Country country) {
        int count = filteredData.size();
        for (int i = 0; i < count; i++) {
            if (country.getId().equals(filteredData.get(i).getId())) {
                lvwCountries.setItemChecked(i, true);
                lvwCountries.setSelection(i);
                break;
            }
        }
    }

    private void scrollToSelectedMulti(List<Country> countries) {
        // for each item in list selected countries
        for (Country countryItem : getListSelectedCountries()) {
            int count = filteredData.size();
            // for each item in search list countries
            for (int i = 0; i < count; i++) {
                // if selected item = search item
                if (countryItem.getId().equals(filteredData.get(i).getId())) {
                    // set check for item
                    lvwCountries.setItemChecked(i, true);
                    if (lvwCountries.getCheckedItemIds().length == 1) {
                        // focus item
                        lvwCountries.setSelection(i);
                    }
//                    if (lvwCountries.getCheckedItemCount() == 1) {
//                        // focus at first item
//                        lvwCountries.setSelection(i);
//                    }
                    break;
                }
            }
        }
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

            // Set checked for selected items
            if (choiceMode == CountriesPickerDialog.CHOICE_MODE_MULTIPLE) {
                // check if selected countries is not null
                if (getListSelectedCountries() != null && getListSelectedCountriesCount() > 0) {
                    scrollToSelectedMulti(getListSelectedCountries());
                }
            } else {
                if (getListSelectedCountries() != null && getListSelectedCountriesCount() > 0) {
                    scrollToSelected(getListSelectedCountries().get(0));
                }
            }
        }
    }

    public static class ViewHolder {
        TextView country;
        ImageView flag;
    }
}