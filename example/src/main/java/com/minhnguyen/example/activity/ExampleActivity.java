package com.minhnguyen.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.minhnguyen.countriespickerdialog.customview.CountriesPickerDialog;
import com.minhnguyen.countriespickerdialog.model.Country;
import com.minhnguyen.countriespickerdialog.utils.CountriesUtils;
import com.minhnguyen.example.R;

/**
 * Created by Minh Nguyen on 7/7/2015.
 */
public class ExampleActivity extends Activity {

    private View imgFlag;
    private TextView txtCountry;
    private Country country;
    private CountriesPickerDialog countriesPickerDialog;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (countriesPickerDialog == null || !countriesPickerDialog.isShowing()) {
                countriesPickerDialog = new CountriesPickerDialog(ExampleActivity.this, country);
                countriesPickerDialog.setOnCountryPickerDialogListener(onCountryPickerDialogListener);
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
    }

    private void initEvent() {
        imgFlag.setOnClickListener(onClickListener);
        txtCountry.setOnClickListener(onClickListener);
    }
}
