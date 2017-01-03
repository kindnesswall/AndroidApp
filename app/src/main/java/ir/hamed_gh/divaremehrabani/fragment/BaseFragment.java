package ir.hamed_gh.divaremehrabani.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by aseman on 3/28/16.
 */
public class BaseFragment extends Fragment implements ApiRequest.Listener{

    protected Context context;
    protected AppCompatActivity mainActivity;
    private TextView mToolbarTitleTextView;
    protected ApiRequest apiRequest;
    protected void init() {
        context = getActivity();
        mainActivity = (AppCompatActivity) getActivity();

        mToolbarTitleTextView = (TextView) ((AppCompatActivity) context).findViewById(R.id.toolbar_title_textView);

        apiRequest = new ApiRequest(context, this);
    }

    @Override
    public void onResponse(Call call, Response response) {

    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}
