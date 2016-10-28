package hamed_gh.ir.divaaremehrabani.helper;

import android.widget.Toast;

import hamed_gh.ir.divaaremehrabani.app.AppController;

/**
 * Created by aseman on 3/5/16.
 */
public class Toasti {

    public static void showS(String message) {
        Toast.makeText(AppController.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showL(String message) {
        Toast.makeText(AppController.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
