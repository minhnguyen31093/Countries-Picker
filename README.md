# Countries Picker
-Dialog that allow you to search and pick country from list 
-Dialog that allow you to search and pick countries from list 
-Drop Down List to pick country 
-Auto Complete Text View to search country

Require API 8 or later

How to use:

+Gradle
repositories {
    maven {
        url  "http://dl.bintray.com/minhnguyen31093/maven"
    }
}

dependencies {
    compile 'com.github.minhnguyen31093:countries-picker:0.9.0'
}

+Single choice:
	If you put country in CountriesPickerDialog it will be selected that item
	CountriesPickerDialog countriesPickerDialog = new CountriesPickerDialog(context, country);
	countriesPickerDialog.setOnCountryPickerDialogListener(onCountryPickerDialogListener);
	countriesPickerDialog.setBackgroundColor(getResources().getColor(R.color.yourcolor));
	countriesPickerDialog.show();

	private CountriesPickerDialog.OnCountryPickerDialogListener onCountryPickerDialogListener = new CountriesPickerDialog.OnCountryPickerDialogListener() {
		@Override
		public void onSelectedCountry(Country country) {
			//selected country
		}
	};

	
+Multi choice:
	If you put countries in CountriesPickerDialog it will be selected these items
	CountriesPickerDialog countriesPickerDialog = new CountriesPickerDialog(context, countries);
	countriesPickerDialog.setOnCountriesPickerDialogListener(onCountriesPickerDialogListener);
	countriesPickerDialog.setBackgroundColor(getResources().getColor(R.color.yourcolor));
	countriesPickerDialog.setButtonDrawable(getResources().getDrawable(R.drawable.yourdrawable));
	countriesPickerDialog.show();

	private CountriesPickerDialog.OnCountriesPickerDialogListener onCountriesPickerDialogListener = new CountriesPickerDialog.OnCountriesPickerDialogListener() {
		@Override
		public void onSelectedCountries(List<Country> countries) {
			//selected countries
		}
	};


+Drop Down List
	Use customview CountrySpinner in layout:
	<com.github.minhnguyen31093.countriespicker.customview.CountrySpinner/>
	spinner.serHint("Select Country");
	spinner.setOnCountrySpinnerListener(onCountrySpinnerListener);
	
    private CountrySpinner.OnCountrySpinnerListener onCountrySpinnerListener = new CountrySpinner.OnCountrySpinnerListener() {
        @Override
        public void onCompleted(Country country) {
            //selected country
        }
    };
	
	
+Search Country
	Use customview CountrySearch in layout:
	<com.github.minhnguyen31093.countriespicker.customview.CountrySearch/>
	txtCountry.setOnCountrySearchListener(onCountrySearchListener);
	
    private CountrySearch.OnCountrySearchListener onCountrySearchListener = new CountrySearch.OnCountrySearchListener() {
        @Override
        public void onCompleted(Country country) {
            //selected country
        }
    };

Example
<img src="http://i.imgur.com/ksU5vOm.png"/>

Single choice
<img src="http://i.imgur.com/7vVhfUw.png"/>

Multi choice
<img src="http://i.imgur.com/dN8SmTL.png"/>

Drop Down List
<img src="http://i.imgur.com/FTnhs75.png"/>

Search
<img src="http://i.imgur.com/Bqt8w5S.png"/>
