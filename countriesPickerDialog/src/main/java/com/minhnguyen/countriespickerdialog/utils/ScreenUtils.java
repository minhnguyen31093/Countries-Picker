package com.minhnguyen.countriespickerdialog.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class ScreenUtils {
	
	public static int getHeightScreen(Context context){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		return displaymetrics.heightPixels;
	}		

	public static int getWidthScreen(Context context){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		return displaymetrics.widthPixels;
	}
}
