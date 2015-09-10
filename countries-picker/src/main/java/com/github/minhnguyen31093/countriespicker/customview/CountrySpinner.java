package com.github.minhnguyen31093.countriespicker.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.minhnguyen31093.countriespicker.R;
import com.github.minhnguyen31093.countriespicker.adapter.CountryAdapter;
import com.github.minhnguyen31093.countriespicker.model.Country;

/**
 * Created by Minh. Nguyen Le on 9/9/2015.
 */
public class CountrySpinner extends Spinner {

    private Context context;
    private CountryAdapter countryAdapter;
    private OnCountrySpinnerListener onCountrySpinnerListener;
    private int mHint;

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                Country country = countryAdapter.getItem(position);
                Log.v(CountrySpinner.class.getSimpleName(), country != null ? country.getName() : "Select?");
                if (onCountrySpinnerListener != null) {
                    onCountrySpinnerListener.onCompleted(country);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public CountrySpinner(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CountrySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initType(context, attrs, 0);
        init();
    }

    public CountrySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.context = context;
        initType(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        initEvent();
        fillData();
    }

    private void initType(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountrySpinner, defStyle, defStyle);
        mHint = typeArray.getResourceId(R.styleable.CountrySpinner_hint, R.string.mn_search_view_hint);
        typeArray.recycle();
    }

    private void initEvent() {
        this.setOnItemSelectedListener(onItemSelectedListener);
    }

    private void fillData() {
        countryAdapter = new CountryAdapter(context, CountryAdapter.CHOICE_MODE_SELECT);
        countryAdapter.setHint(context.getResources().getString(mHint));
        this.setAdapter(countryAdapter);
    }
    public void serHint(String hint) {
        countryAdapter.setHint(hint);
    }

    public void setOnCountrySpinnerListener(OnCountrySpinnerListener onCountrySpinnerListener) {
        this.onCountrySpinnerListener = onCountrySpinnerListener;
    }

    public interface OnCountrySpinnerListener {
        void onCompleted(Country country);
    }
}
