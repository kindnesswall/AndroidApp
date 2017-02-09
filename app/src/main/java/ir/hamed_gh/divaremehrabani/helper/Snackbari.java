package ir.hamed_gh.divaremehrabani.helper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;

/**
 * Created by Hamed on 8/17/16.
 */

public class Snackbari {
	public static void showS(View view, String text) {
		Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
		show(snackbar, text);
	}

	public static void showL(View view, String text) {
		Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
		show(snackbar, text);
	}

	private static void show(Snackbar snackbar, String text) {
		// Get the Snackbar's layout view
		Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
		// Hide the text
		//Todo Null Exception
//		TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_action);
//		textView.setVisibility(View.GONE);

		// Inflate our custom view
		LayoutInflater layoutInflater =
				(LayoutInflater) snackbar.getView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View snackView = layoutInflater.inflate(R.layout.snakbar_layout, null);
		// Configure the view
		TextView textViewTop = (TextView) snackView.findViewById(R.id.snackbar_tv);
		textViewTop.setText(text);

		// Add the view to the Snackbar's layout
		layout.addView(snackView, 0);
		// Show the Snackbar
		snackbar.show();
	}
}
