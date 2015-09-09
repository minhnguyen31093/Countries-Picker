package com.minhnguyen.countriespickerdialog.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.minhnguyen.countriespickerdialog.adapter.CountryAdapter;
import com.minhnguyen.countriespickerdialog.model.Country;

/**
 * Created by Minh. Nguyen Le on 9/9/2015.
 */
public class CountrySearch extends AutoCompleteTextView {

    private Context context;
    private CountryAdapter countryAdapter;
    private OnCountrySearchListener onCountrySearchListener;

    private TextWatcher onTextChanged = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (countryAdapter != null) {
                countryAdapter.getFilter().filter(s.toString());
            }
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Country country = countryAdapter.getItem(position);
            CountrySearch.this.setText(country.getName());
            if (onCountrySearchListener != null) {
                onCountrySearchListener.onCompleted(country);
            }
        }
    };

    public CountrySearch(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CountrySearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CountrySearch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init() {
        initEvent();
        fillData();
    }

    private void initEvent() {
        this.addTextChangedListener(onTextChanged);
        this.setOnItemClickListener(onItemClickListener);
    }

    private void fillData() {
        countryAdapter = new CountryAdapter(context, CountryAdapter.CHOICE_MODE_SEARCH);
        this.setAdapter(countryAdapter);
    }

    public void setOnCountrySearchListener(OnCountrySearchListener onCountrySearchListener) {
        this.onCountrySearchListener = onCountrySearchListener;
    }

    public interface OnCountrySearchListener {
        void onCompleted(Country country);
    }
}
