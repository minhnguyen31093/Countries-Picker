package com.minhnguyen.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minhnguyen.countriespickerdialog.customview.CountriesPickerDialog;
import com.minhnguyen.countriespickerdialog.model.Country;
import com.minhnguyen.countriespickerdialog.utils.CountriesUtils;
import com.minhnguyen.example.R;

import java.util.List;

/**
 * Created by Minh Nguyen on 7/7/2015.
 */
public class ExampleActivity extends Activity {

    private View imgFlag;
    private TextView txtCountry;
    private TextView txtCountries;
//    private Switch swt;
    private RelativeLayout rel1;
    private RelativeLayout rel2;
    private Country country;
    private List<Country> countries;
    private CountriesPickerDialog countriesPickerDialog;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (countriesPickerDialog == null || !countriesPickerDialog.isShowing()) {
                countriesPickerDialog = new CountriesPickerDialog(ExampleActivity.this, country);
                countriesPickerDialog.setOnCountryPickerDialogListener(onCountryPickerDialogListener);
                String hexColor = String.format("#%06X", (0xFFFFFF & getResources().getColor(R.color.mn_orange)));
                countriesPickerDialog.setBackgroundColor(hexColor);
                countriesPickerDialog.show();
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

    private View.OnClickListener onMultiClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (countriesPickerDialog == null || !countriesPickerDialog.isShowing()) {
                countriesPickerDialog = new CountriesPickerDialog(ExampleActivity.this, countries);
                countriesPickerDialog.setOnCountriesPickerDialogListener(onCountriesPickerDialogListener);
                String hexColor = String.format("#%06X", (0xFFFFFF & getResources().getColor(R.color.mn_orange)));
                countriesPickerDialog.setBackgroundColor(hexColor);
                countriesPickerDialog.setButtonDrawable(getResources().getDrawable(R.drawable.mn_selector_button_background));
                countriesPickerDialog.show();
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
    }

    private void initView() {
        imgFlag = findViewById(R.id.mn_exampleActivity_imgFlag);
        txtCountry = (TextView) findViewById(R.id.mn_exampleActivity_txtCountry);
        txtCountries = (TextView) findViewById(R.id.mn_exampleActivity_txtCountries);
//        swt = (Switch) findViewById(R.id.mn_exampleActivity_swt);
        rel1 = (RelativeLayout) findViewById(R.id.mn_exampleActivity_rel1);
        rel2 = (RelativeLayout) findViewById(R.id.mn_exampleActivity_rel2);
    }

    private void initEvent() {
        imgFlag.setOnClickListener(onClickListener);
        txtCountry.setOnClickListener(onClickListener);
        txtCountries.setOnClickListener(onMultiClickListener);
//        swt.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}
