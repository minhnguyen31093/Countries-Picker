# Countries Picker
  * Dialog that allow you to search and pick country from list 
  * Dialog that allow you to search and pick countries from list 
  * Drop Down List to pick country 
  * Auto Complete Text View to search country

**Require API 8 or later**

## How to use:

### Gradle
``` java
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.minhnguyen31093:countries-picker:0.9.0'
}
```
###1. Single choice:
>If you put country in CountriesPickerDialog it will be selected that item.

``` java
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
```
	
###2. Multi choice:
>If you put countries in CountriesPickerDialog it will be selected these items

``` java
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
```

###3. Drop Down List
>Use customview CountrySpinner in layout:

``` java
<com.github.minhnguyen31093.countriespicker.customview.CountrySpinner/>

spinner.serHint("Select Country");
spinner.setOnCountrySpinnerListener(onCountrySpinnerListener);
private CountrySpinner.OnCountrySpinnerListener onCountrySpinnerListener = new CountrySpinner.OnCountrySpinnerListener() {
        @Override
        public void onCompleted(Country country) {
            //selected country
        }
};
```
	
###4. Search Country
>Use customview CountrySearch in layout:

``` java
<com.github.minhnguyen31093.countriespicker.customview.CountrySearch/>
	
txtCountry.setOnCountrySearchListener(onCountrySearchListener);
private CountrySearch.OnCountrySearchListener onCountrySearchListener = new CountrySearch.OnCountrySearchListener() {
        @Override
        public void onCompleted(Country country) {
            //selected country
        }
};
```
	
## Example
- Single choice
- Multi choice
- Drop Down List
- Search

<img src="http://i.imgur.com/ksU5vOm.png" width="200"/> <img src="http://i.imgur.com/7vVhfUw.png" width="200"/> <img src="http://i.imgur.com/dN8SmTL.png" width="200"/> <img src="http://i.imgur.com/FTnhs75.png" width="200"/> <img src="http://i.imgur.com/Bqt8w5S.png" width="200"/>

#[License](https://github.com/minhnguyen31093/Countries-Picker/blob/master/LICENSE)
Copyright 2015 Minh Nguyen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
