package ir.hamed_gh.divaremehrabani.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;


/**
 * Created by aseman on 3/28/16.
 */
public class BaseFragment extends Fragment {

    protected Context context;
    protected AppCompatActivity mainActivity;
    private TextView mToolbarTitleTextView;

    protected void init() {
        context = getActivity();
        mainActivity = (AppCompatActivity) getActivity();

        mToolbarTitleTextView = (TextView) ((AppCompatActivity) context).findViewById(R.id.toolbar_title_textView);
    }
}
