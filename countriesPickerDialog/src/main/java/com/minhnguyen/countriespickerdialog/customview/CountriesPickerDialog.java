package com.minhnguyen.countriespickerdialog.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.minhnguyen.countriespickerdialog.R;
import com.minhnguyen.countriespickerdialog.adapter.CountriesAdapter;
import com.minhnguyen.countriespickerdialog.model.Country;
import com.minhnguyen.countriespickerdialog.utils.CountriesUtils;
import com.minhnguyen.countriespickerdialog.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class CountriesPickerDialog extends Dialog {


    private Context context;

    // Default mode
    private int choiceMode;

    private Country country;
    private List<Country> countries;
    private SearchView searchView;
    private ListView lvwCountries;
    private TextView tvNoData;
    private Button btnSelect;
    private CountriesAdapter lvwAdapter;
    private List<Country> allCountries;
    private OnCountryPickerDialogListener onCountryPickerDialogListener;
    private OnCountriesPickerDialogListener onCountriesPickerDialogListener;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            countries = lvwAdapter.getListSelectedCountries();
            if (countries != null) {
                CountriesUtils.sortListCountries(countries);
                if (onCountriesPickerDialogListener != null) {
                    onCountriesPickerDialogListener.onSelectedCountries(countries);
                }
                CountriesPickerDialog.this.dismiss();
            }
        }
    };

    public OnCountryPickerDialogListener getOnCountryPickerDialogListener() {
        return onCountryPickerDialogListener;
    }

    public void setOnCountryPickerDialogListener(OnCountryPickerDialogListener onCountryPickerDialogListener) {
        this.onCountryPickerDialogListener = onCountryPickerDialogListener;
    }


    public OnCountriesPickerDialogListener getOnCountriesPickerDialogListener() {
        return onCountriesPickerDialogListener;
    }

    public void setOnCountriesPickerDialogListener(OnCountriesPickerDialogListener onCountriesPickerDialogListener) {
        this.onCountriesPickerDialogListener = onCountriesPickerDialogListener;
    }

    public interface OnCountryPickerDialogListener {
        public void onSelectedCountry(Country country);
    }

    public interface OnCountriesPickerDialogListener {
        public void onSelectedCountries(List<Country> countries);
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
            if (choiceMode == CountriesAdapter.CHOICE_MODE_MULTIPLE) { //Multi
                if (lvwCountries.isItemChecked(position)) {
                    lvwAdapter.addItemToListSelectedCountries(lvwAdapter.getList().get(position));
                } else {
                    lvwAdapter.removeItemInListSelectedCountries(lvwAdapter.getList().get(position));
                }
                lvwAdapter.notifyDataSetChanged();
            } else { //Single
                country = lvwAdapter.getItem(position);
                if (onCountryPickerDialogListener != null) {
                    onCountryPickerDialogListener.onSelectedCountry(country);
                }
                CountriesPickerDialog.this.dismiss();
            }
        }
    };

    public CountriesPickerDialog(Context context, Country country) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.country = country;
        choiceMode = CountriesAdapter.CHOICE_MODE_SINGLE;
        setUp();
        initView();
        initEvent();
        fillData();
    }

    public CountriesPickerDialog(Context context, List<Country> countries) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        this.context = context;
        this.countries = countries;
        choiceMode = CountriesAdapter.CHOICE_MODE_MULTIPLE;
        setUp();
        initView();
        initEvent();
        fillData();
    }

    public void onBackPressed() {
        CountriesPickerDialog.this.dismiss();
    }

    private void setUp() {
        setContentView(R.layout.mn_countries_picker_dialog);
        setCancelable(true);
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.mn_countriesPickerDialog_svwSearchView);
        lvwCountries = (ListView) findViewById(R.id.mn_countriesPickerDialog_lvwCountry);
        tvNoData = (TextView) findViewById(R.id.mn_countriesPickerDialog_tvNoData);
        btnSelect = (Button) findViewById(R.id.mn_countriesPickerDialog_btnSelect);
    }

    private void initEvent() {
        searchView.setContext(context);
        searchView.addTextChangedListener(onTextChanged);
        lvwCountries.setOnItemClickListener(onItemClickListener);
    }

    private void fillData() {
        setDialogPadding();
        lvwAdapter = new CountriesAdapter(context, choiceMode);
        lvwCountries.setAdapter(lvwAdapter);
        lvwCountries.setTextFilterEnabled(true);
        lvwCountries.setChoiceMode(choiceMode);

        if (choiceMode == CountriesAdapter.CHOICE_MODE_MULTIPLE) { // Multi
            btnSelect.setVisibility(View.VISIBLE);
            btnSelect.setOnClickListener(onClickListener);
            if (countries != null && countries.size() > 0) {
                scrollToSelectedMulti(countries);
            }
        } else { // Single
            if (country != null) {
                scrollToSelected(country);
            }
        }
    }

    private void setDialogPadding() {
        int padding = 128;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        getWindow().setBackgroundDrawableResource(R.drawable.mn_background_radius);
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setLayout(ScreenUtils.getWidthScreen(context) - padding, ScreenUtils.getHeightScreen(context) - padding);
    }

    public void setHint(String hint) {
        if (searchView != null) {
            searchView.setHint(hint);
        }
    }

    public void setBackgroundColor(int color) {
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        if (searchView != null) {
            searchView.setColor(hexColor);
        }
        if (btnSelect != null) {
            btnSelect.setBackgroundColor(Color.parseColor(hexColor));
        }
    }

    public void setButtonDrawable(Drawable drawable) {
        if (btnSelect != null) {
            btnSelect.setBackgroundDrawable(drawable);
        }
    }

    private void scrollToSelected(Country country) {
        int count = allCountries.size();
        for (int i = 0; i < count; i++) {
            if (country.getId().equals(allCountries.get(i).getId())) {
                lvwAdapter.addItemToListSelectedCountries(country);
                lvwCountries.setItemChecked(i, true);
                lvwCountries.setSelection(i);
                break;
            }
        }
    }

    private void scrollToSelectedMulti(List<Country> countries) {
        // for each item in list selected countries
        for (Country countryItem : countries) {
            int count = allCountries.size();
            // for each item in country list
            for (int i = 0; i < count; i++) {
                // if selected item = item
                if (countryItem.getId().equals(allCountries.get(i).getId())) {
                    // add checked item to list
                    lvwAdapter.addItemToListSelectedCountries(countryItem);
                    // set check for item
                    lvwCountries.setItemChecked(i, true);
                    if (lvwCountries.getCheckedItemIds().length == 1) {
                        // focus item
                        lvwCountries.setSelection(i);
                    }
//                    if (lvwCountries.getCheckedItemCount() == 1) {
//                        // focus item
//                        lvwCountries.setSelection(i);
//                    }
                    break;
                }
            }
        }
    }
}
