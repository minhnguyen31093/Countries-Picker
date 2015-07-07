package com.minhnguyen.countriespickerdialog.customview;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.minhnguyen.countriespickerdialog.R;
import com.minhnguyen.countriespickerdialog.adapter.CountriesAdapter;
import com.minhnguyen.countriespickerdialog.model.Country;
import com.minhnguyen.countriespickerdialog.utils.CountriesUtils;

import java.util.List;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class CountriesPickerDialog extends Dialog {

    private Context context;
    private Country country;
    private SearchView searchView;
    private ListView lvwCountries;
    private TextView tvNoData;
    private CountriesAdapter lvwAdapter;
    private List<Country> countries;
    private OnCountryPickerDialogListener onCountryPickerDialogListener;

    public OnCountryPickerDialogListener getOnCountryPickerDialogListener() {
        return onCountryPickerDialogListener;
    }

    public void setOnCountryPickerDialogListener(OnCountryPickerDialogListener onCountryPickerDialogListener) {
        this.onCountryPickerDialogListener = onCountryPickerDialogListener;
    }

    public interface OnCountryPickerDialogListener {
        public void onSelectedCountry(Country country);
    }

    private TextWatcher onTextChanged = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            lvwCountries.clearChoices();
            lvwAdapter.getFilter().filter(s.toString());
            if (lvwAdapter.getCount() > 0) {
                tvNoData.setVisibility(View.GONE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    };

    // Listview item click (Single)
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            country = lvwAdapter.getItem(position);
            if (onCountryPickerDialogListener != null) {
                onCountryPickerDialogListener.onSelectedCountry(country);
            }
            CountriesPickerDialog.this.dismiss();
        }
    };

    public CountriesPickerDialog(Context context, Country country) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.country = country;
        setUp();
        initView();
        initEvent();
        fillData();
    }

    public void onBackPressed() {
        CountriesPickerDialog.this.dismiss();
    };

    private void setUp() {
        setContentView(R.layout.mn_countries_picker_dialog);
        setCancelable(true);
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.mn_countriesPickerDialog_svwSearchView);
        lvwCountries = (ListView) findViewById(R.id.mn_countriesPickerDialog_lvwCountry);
        tvNoData = (TextView) findViewById(R.id.mn_countriesPickerDialog_tvNoData);
    }

    private void initEvent() {
        searchView.setContext(context);
        searchView.addTextChangedListener(onTextChanged);
        lvwCountries.setOnItemClickListener(onItemClickListener);
    }

    private void fillData() {
        countries = CountriesUtils.getlistCountriesFromJson(context);
        lvwAdapter = new CountriesAdapter(context, R.layout.mn_list_item_country, countries);
        lvwCountries.setAdapter(lvwAdapter);
        lvwCountries.setTextFilterEnabled(true);

        if (country != null) {
            if (!TextUtils.isEmpty(country.getId())) {
                scrollToSelectedCode(country.getPhone(), country.getId());
            } else {
                scrollToSelectedPhone(country.getPhone());
            }
        }
    }

    private void scrollToSelectedPhone(String phone) {
        if (!TextUtils.isEmpty(phone) && countries != null) {
            int size = countries.size();
            for (int i = 0; i < size; i++) {
                if (phone.equals(countries.get(i).getPhone())) {
                    lvwCountries.setItemChecked(i, true);
                    lvwCountries.setSelection(i);
                    break;
                }
            }
        }
    }

    private void scrollToSelectedCode(String phone, String code) {
        if (!TextUtils.isEmpty(phone) && countries != null) {
            int size = countries.size();
            for (int i = 0; i < size; i++) {
                if (phone.equals(countries.get(i).getPhone()) && code.equals(countries.get(i).getId())) {
                    lvwCountries.setItemChecked(i, true);
                    lvwCountries.setSelection(i);
                    break;
                }
            }
        }
    }
}
