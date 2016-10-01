package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyDialogFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);

		ButterKnife.bind(this, rootView);

		try {
			FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

			String title = MyDialogFragment.class.getName();
			fragmentTransaction.replace(R.id.dialog_container, new HomeFragment(), title);
			fragmentTransaction.addToBackStack(title);
			fragmentTransaction.commit();

		} catch (Exception e) {

		}

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();

		getDialog().getWindow().setLayout(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		);
	}

	@Override
	public void onResume() {
		super.onResume();

		getDialog().getWindow().setLayout(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		);
	}
}
