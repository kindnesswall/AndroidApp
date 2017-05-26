package ir.hamed_gh.divaremehrabani.helper;

import android.widget.Toast;

import ir.hamed_gh.divaremehrabani.app.AppController;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class Toasti {

	public static void showS(String message) {
		Toast.makeText(AppController.getAppContext(), message, Toast.LENGTH_SHORT).show();
	}

	public static void showL(String message) {
		Toast.makeText(AppController.getAppContext(), message, Toast.LENGTH_LONG).show();
	}
}
