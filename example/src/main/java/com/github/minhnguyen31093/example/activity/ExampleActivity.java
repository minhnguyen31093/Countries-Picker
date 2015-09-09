package com.github.minhnguyen31093.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.minhnguyen31093.countriespicker.customview.CountriesPickerDialog;
import com.github.minhnguyen31093.countriespicker.customview.CountrySearch;
import com.github.minhnguyen31093.countriespicker.customview.CountrySpinner;
import com.github.minhnguyen31093.countriespicker.model.Country;
import com.github.minhnguyen31093.countriespicker.utils.CountriesUtils;
import com.github.minhnguyen31093.example.R;

import java.util.List;

/**
 * Created by Minh Nguyen on 7/7/2015.
 */
public class ExampleActivity extends Activity {

    private View imgFlag;
    private TextView txtCountry;
    private TextView txtCountries;
//    private Switch swt;
    private CountrySpinner spnCountry;
    private CountrySearch atxtCountry;
    private RelativeLayout rel1;
    private RelativeLayout rel2;
    private Country country;
    private List<Country> countries;
    private CountriesPickerDialog countriesPickerDialog;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mn_exampleActivity_imgFlag:
                case R.id.mn_exampleActivity_txtCountry:
                    if (countriesPickerDialog == null || !countriesPickerDialog.isShowing()) {
                        countriesPickerDialog = new CountriesPickerDialog(ExampleActivity.this, country);
                        countriesPickerDialog.setOnCountryPickerDialogListener(onCountryPickerDialogListener);
                        countriesPickerDialog.setBackgroundColor(getResources().getColor(R.color.mn_orange));
                        countriesPickerDialog.show();
                    }
                    break;
                case R.id.mn_exampleActivity_txtCountries:
                    if (countriesPickerDialog == null || !countriesPickerDialog.isShowing()) {
                        countriesPickerDialog = new CountriesPickerDialog(ExampleActivity.this, countries);
                        countriesPickerDialog.setOnCountriesPickerDialogListener(onCountriesPickerDialogListener);
                        countriesPickerDialog.setBackgroundColor(getResources().getColor(R.color.mn_orange));
                        countriesPickerDialog.setButtonDrawable(getResources().getDrawable(R.drawable.mn_selector_button_background));
                        countriesPickerDialog.show();
                    }
                    break;
            }
        }
    };

    private CountriesPickerDialog.OnCountryPickerDialogListener onCountryPickerDialogListener = new CountriesPickerDialog.OnCountryPickerDialogListener() {
        @Override
        public void onSelectedCountry(Country country) {
            if (country != null) {
                ExampleActivity.this.country = country;
                if (!android.text.TextUtils.isEmpty(country.getId())) {
                    imgFlag.setBackgroundResource(CountriesUtils.getResId(country.getId()));
                    txtCountry.setText(country.getName());
                }
            }
        }
    };

    private CountriesPickerDialog.OnCountriesPickerDialogListener onCountriesPickerDialogListener = new CountriesPickerDialog.OnCountriesPickerDialogListener() {
        @Override
        public void onSelectedCountries(List<Country> countries) {
            ExampleActivity.this.countries = countries;
            if (countries != null && countries.size() > 0) {
                String strCountries = "";
                for (Country country : countries) {
                    strCountries += country.getName() + ", ";
                }
                txtCountries.setText(strCountries);
            }
        }
    };

    private CountrySpinner.OnCountrySpinnerListener onCountrySpinnerListener = new CountrySpinner.OnCountrySpinnerListener() {
        @Override
        public void onCompleted(Country country) {
            country.getId();
            country.getName();
            country.getCapital();
            //TODO put your code here
        }
    };

    private CountrySearch.OnCountrySearchListener onCountrySearchListener = new CountrySearch.OnCountrySearchListener() {
        @Override
        public void onCompleted(Country country) {
            country.getId();
            country.getName();
            country.getCapital();
            //TODO put your code here
        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                rel1.setVisibility(View.VISIBLE);
                rel2.setVisibility(View.GONE);
            } else {
                rel1.setVisibility(View.GONE);
                rel2.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_example);
        initView();
        initEvent();
        fillData();
    }

    private void initView() {
        imgFlag = findViewById(R.id.mn_exampleActivity_imgFlag);
        txtCountry = (TextView) findViewById(R.id.mn_exampleActivity_txtCountry);
        txtCountries = (TextView) findViewById(R.id.mn_exampleActivity_txtCountries);
        rel1 = (RelativeLayout) findViewById(R.id.mn_exampleActivity_rel1);
        rel2 = (RelativeLayout) findViewById(R.id.mn_exampleActivity_rel2);
        spnCountry = (CountrySpinner) findViewById(R.id.mn_exampleActivity_spnCountry);
        atxtCountry = (CountrySearch) findViewById(R.id.mn_exampleActivity_atxtCountry);
//        swt = (Switch) findViewById(R.id.mn_exampleActivity_swt);
    }

    private void initEvent() {
        imgFlag.setOnClickListener(onClickListener);
        txtCountry.setOnClickListener(onClickListener);
        txtCountries.setOnClickListener(onClickListener);
        atxtCountry.setOnCountrySearchListener(onCountrySearchListener);
        spnCountry.setOnCountrySpinnerListener(onCountrySpinnerListener);
//        swt.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private void fillData() {
        spnCountry.serHint("Select Country");
    }
}
