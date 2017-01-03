package ir.hamed_gh.divaremehrabani.helper;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Hamed on 11/12/16.
 */

public class ApiRequest {

    private Listener listener;
    private AdapterListener adapterListener;
    private ActivityListener activityListener;
    private Context mContext;

    public ApiRequest(Context context, AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
        mContext = context;
    }

    public ApiRequest(Context context, Listener listener) {
        this.listener = listener;
        mContext = context;
    }

    public ApiRequest(Context context, ActivityListener activityListener) {
        this.activityListener = activityListener;
        mContext = context;
    }

    public interface Listener {
        void onResponse(Call call, Response response);

        void onFailure(Call call, Throwable t);
    }

    public interface AdapterListener {
        void onResponse(Call call, Response response, int position);

        void onFailure(Call call, Throwable t);
    }

    public interface ActivityListener {
        void onResponse(Call call, Response response, int position, String action);

        void onFailure(Call call, Throwable t);
    }

    private class HandlingResponse {
        public Call call;
        public Response response;
        public CallbackWithRetry callbackWithRetry;
        public int position;
        public String action;

        public HandlingResponse(
                Call call,
                Response response,
                CallbackWithRetry callbackWithRetry,
                int position,
                String action
        ) {
            this.call = call;
            this.response = response;
            this.callbackWithRetry = callbackWithRetry;
            this.position = position;
            this.action = action;
        }

        public HandlingResponse(
                Call call,
                Response response,
                CallbackWithRetry callbackWithRetry,
                int position
        ) {
            this.call = call;
            this.response = response;
            this.callbackWithRetry = callbackWithRetry;
            this.position = position;
        }

        public HandlingResponse(
                Call call,
                Response response,
                CallbackWithRetry callbackWithRetry

        ) {
            this.call = call;
            this.response = response;
            this.callbackWithRetry = callbackWithRetry;
        }

    }

    private void handlingOnResponse(HandlingResponse handlingResponse) {

        if (!handlingResponse.response.isSuccessful()) {
            ConnectionDetector.ShowServerProblemDialog(mContext, handlingResponse.callbackWithRetry);
            return;
        }


        if (listener != null) {
            listener.onResponse(handlingResponse.call, handlingResponse.response);
        } else if (adapterListener != null) {
            adapterListener.onResponse(handlingResponse.call, handlingResponse.response,
                    handlingResponse.position);
        }
    }

}
