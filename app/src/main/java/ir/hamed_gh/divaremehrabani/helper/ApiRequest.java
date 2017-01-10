package ir.hamed_gh.divaremehrabani.helper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.Location;
import ir.hamed_gh.divaremehrabani.model.api.TokenOutput;
import okhttp3.ResponseBody;
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

    public void getGifts(){

        Call<List<Gift>> call = AppController.service.getGifts("1","0","10");

        call.enqueue(new CallbackWithRetry<List<Gift>>(call,mContext) {
            @Override
            public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call,response,this));
            }
        });

    }

    public void getToken(String verificationCode){

        Call<TokenOutput> call = AppController.accountService.token(
                AppController.getStoredString(Constants.TELEPHONE),
                verificationCode,
                "password"
        );

        call.enqueue(new CallbackWithRetry<TokenOutput>(call,mContext) {
            @Override
            public void onResponse(Call<TokenOutput> call, Response<TokenOutput> response) {
                handlingOnResponse(new HandlingResponse(call,response,this));
            }
        });

    }

    public void register(String telephone){

        AppController.storeString(Constants.TELEPHONE, telephone);
        Call<ResponseBody> call = AppController.accountService.register(telephone);

        call.enqueue(new CallbackWithRetry<ResponseBody>(call,mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call,response,this));
            }
        });

    }

//    public void uploadGiftImage(RequestBody requestBody) {
//        Call<ResponseBody> result = AppController.service.uploadGiftImage(
//                AppController.getStoredString(Constants.TOKEN),
//                "me.jpg",
//                requestBody
//        );
//
//        result.enqueue(new CallbackWithRetry<ResponseBody>(result, mContext) {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                handlingOnResponse(new HandlingResponse(call, response, this));
//            }
//
//        });
//
//    }

    public void getLocations(){

        Call<List<Location>> call = AppController.service.getLocations();

        call.enqueue(new CallbackWithRetry<List<Location>>(call,mContext) {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                handlingOnResponse(new HandlingResponse(call,response,this));
            }
        });

    }

	public void getCategories(){

		Call<ArrayList<Category>> call = AppController.service.getCategories();

		call.enqueue(new CallbackWithRetry<ArrayList<Category>>(call,mContext) {
			@Override
			public void onResponse(Call<ArrayList<Category>> call,
			                       Response<ArrayList<Category>> response) {

				handlingOnResponse(new HandlingResponse(call,response,this));

			}
		});

	}

}
