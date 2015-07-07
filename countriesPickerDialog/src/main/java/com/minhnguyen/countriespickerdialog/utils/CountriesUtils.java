/**
 * 
 */
package com.minhnguyen.countriespickerdialog.utils;

import android.content.Context;
import android.util.Log;

import com.minhnguyen.countriespickerdialog.R;
import com.minhnguyen.countriespickerdialog.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class CountriesUtils {
	
	private static String TAG = CountriesUtils.class.getSimpleName();

    public static String loadJSONFromAsset(Context context, String jsonFile) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFile);//"file_name.json"
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<Country> getlistCountriesFromJson (Context context) {
        List<Country> countries = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, "countries_list.json"));
            JSONArray objCountries = obj.getJSONArray("countries");
            int lenght = objCountries.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject objCountry = objCountries.getJSONObject(i);
                Country country = new Country();
                country.setId(objCountry.optString("id"));
                country.setName(objCountry.optString("name"));
                country.setNativeName(objCountry.optString("native"));
                country.setPhone(objCountry.optString("phone"));
                country.setContinent(objCountry.optString("continent"));
                country.setCapital(objCountry.optString("capital"));
                country.setCurrency(objCountry.optString("currency"));
                country.setLanguages(objCountry.optString("languages"));
                countries.add(country);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
//        return sortCountriesByPhone(countries);
        return countries;
    }

	/**
	 * get list countries
	 * @return
	 */
	public static ArrayList<String> getListCountries() {
		String[] locales = Locale.getISOCountries();
		ArrayList<String> countries = new ArrayList<String>();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			countries.add(obj.getDisplayCountry());
		}
		return countries;
	}
	
	/**
	 * get list countries code
	 * @return
	 */
	public static ArrayList<String> getListCountriesCode(){
		String[] locales = Locale.getISOCountries();
		ArrayList<String> countries = new ArrayList<String>();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			countries.add(obj.getCountry());
		}
		return countries;
	}
	
	/**
	 * get list countries model
	 * @return
	 */
	public static List<Country> getAllCountries() {
		try {
			List<Country> countryList = new ArrayList<Country>();

			ArrayList<String> listCountries = getListCountries();
			ArrayList<String> listCountriesCode = getListCountriesCode();

			for (int i = 0; i < listCountries.size(); i++) {
				Country country = new Country();
				country.setName(listCountries.get(i));
				country.setId(listCountriesCode.get(i));
				countryList.add(country);
			}
			
			sortListCountries(countryList);
			
			return countryList;

		} catch (Exception e) {
			Log.e(TAG, "Failure to get countries list.", e);
		}	
		return null;
	}
	
	/**
	 * get country code by country name
	 * @param countryName
	 * @return
	 */
	public static String getCountryCode(String countryName){
		if (countryName != null){
			String[] locales = Locale.getISOCountries();
			for (String countryCode : locales) {
				Locale obj = new Locale("", countryCode);
				if(obj.getDisplayCountry().toLowerCase(Locale.getDefault())
						.contains(countryName.toLowerCase(Locale.getDefault()))){
					return obj.getCountry();
				}
			}
		}
		return "";
	}
	
	/**
	 * get country name by country code 
	 * @param code
	 * @return
	 */
	public static String getCountryName(String code){
		if(code != null){
			Locale obj = new Locale("",code);
			return obj.getDisplayCountry();
		} 
		return "";
	}
	
	/**
	 * filter country by country name
	 * @param filterName filter name
	 * @return list filter country
	 */
	public static ArrayList<String> filterByCountryName(String filterName){
		String[] locales = Locale.getISOCountries();
		ArrayList<String> countries = new ArrayList<String>();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			if (obj.getDisplayCountry().toLowerCase(Locale.getDefault())
					.contains(filterName.toLowerCase(Locale.getDefault()))){
				countries.add(obj.getDisplayCountry());
			}
		}
		return countries;
	}
	
	/**
	 * sort the all countries list based on country code
	 * @param listCountries
	 * @return listCountries
	 */
	public static List<Country> sortListCountries(List<Country> listCountries) {
		Collections.sort(listCountries, new Comparator<Country>() {
			@Override
			public int compare(Country country1, Country country2) {
				return String.valueOf(country1.getId()).compareTo(country2.getId());
			}
		});
		return listCountries;
	}

    public static List<Country> sortCountriesByPhone(List<Country> listCountries) {
        Collections.sort(listCountries, new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                return Integer.valueOf(country1.getPhone().replace("+", "")).compareTo(Integer.valueOf(country2.getPhone().replace("+", "")));
            }
        });
        return listCountries;
    }

	@SuppressWarnings("rawtypes")
	public static int getResId(String code) {
		try {
			code = "flag_" + code.toLowerCase(Locale.getDefault());
			Class res = R.drawable.class;
			Field field = res.getField(code);
			int drawableId = field.getInt(null);
			return drawableId;
		} catch (Exception e) {
			Log.e(TAG, "Failure to get drawable id.", e);
		}
		return -1;
	}
}
