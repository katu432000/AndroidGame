package com.utils;

import android.widget.Toast;

public class Utils {

	
	public static void showToast(String text) {
		Toast.makeText(MyApp.getContext(), text, Toast.LENGTH_SHORT).show();
	}
}
