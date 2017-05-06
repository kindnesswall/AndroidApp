package ir.hamed_gh.divaremehrabani.helper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.model.GetGiftPathQuery;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;
import ir.hamed_gh.divaremehrabani.model.api.StartLastIndex;
import ir.hamed_gh.divaremehrabani.model.api.User;
import ir.hamed_gh.divaremehrabani.model.api.input.BookmarkInput;
import ir.hamed_gh.divaremehrabani.model.api.input.RecievedRequestListInput;
import ir.hamed_gh.divaremehrabani.model.api.input.RequestGiftInput;
import ir.hamed_gh.divaremehrabani.model.api.output.TokenOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
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

    public void getGifts(final GetGiftPathQuery getGiftPathQuery) {

        Call<List<Gift>> call = AppController.service.getGifts(
                getGiftPathQuery.cityId,
                getGiftPathQuery.regionId,
                getGiftPathQuery.categoryId,
                getGiftPathQuery.startIndex,
                getGiftPathQuery.lastIndex,
                getGiftPathQuery.searchText);

        call.enqueue(new CallbackWithRetry<List<Gift>>(call, mContext) {
            @Override
            public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<List<Gift>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void acceptRequest(String giftId, String fromUserId) {

        Call<ResponseBody> call = AppController.service.acceptRequest(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                giftId,
                fromUserId
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void deleteMyRequest(String giftId, final int position) {

        Call<ResponseBody> call = AppController.service.deleteMyRequest(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                giftId
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this, position));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void deleteMyRequest(String giftId) {

        Call<ResponseBody> call = AppController.service.deleteMyRequest(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                giftId
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void denyRequest(String giftId, String fromUserId) {

        Call<ResponseBody> call = AppController.service.denyRequest(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                giftId,
                fromUserId
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void getGift(String giftId) {

        Call<Gift> call = AppController.service.getGift(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                giftId);

        call.enqueue(new CallbackWithRetry<Gift>(call, mContext) {
            @Override
            public void onResponse(Call<Gift> call, Response<Gift> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<Gift> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void bookmark(String giftId) {

        Call<ResponseBody> call = AppController.service.bookmark(
                "application/json",
                AppController.getStoredString(Constants.Authorization),
                new BookmarkInput(giftId)
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void login(String verificationCode) {

        Call<TokenOutput> call = AppController.accountService.login(
                AppController.getStoredString(Constants.TELEPHONE),
                verificationCode,
                "password"
        );

        call.enqueue(new CallbackWithRetry<TokenOutput>(call, mContext) {
            @Override
            public void onResponse(Call<TokenOutput> call, Response<TokenOutput> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<TokenOutput> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void register(String telephone) {

        AppController.storeString(Constants.TELEPHONE, telephone);
        Call<ResponseBody> call = AppController.accountService.register(telephone);

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
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

//    public void getLocations() {
//
//        Call<List<Location>> call = AppController.service.getLocations();
//
//        call.enqueue(new CallbackWithRetry<List<Location>>(call, mContext) {
//            @Override
//            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
//                handlingOnResponse(new HandlingResponse(call, response, this));
//            }
//        });
//
//    }

    public void getCategories() {

        Call<ArrayList<Category>> call = AppController.service.getCategories(
                "0","100","1"
        );

        call.enqueue(new CallbackWithRetry<ArrayList<Category>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<Category>> call,
                                   Response<ArrayList<Category>> response) {

                handlingOnResponse(new HandlingResponse(call, response, this));

            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void registerGift(Gift gift) {
        Call<Gift> call = AppController.service.registerGift(
                "application/json",
                AppController.getStoredString(Constants.Authorization),
                gift
        );

        call.enqueue(new CallbackWithRetry<Gift>(call, mContext) {
            @Override
            public void onResponse(Call<Gift> call,
                                   Response<Gift> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<Gift> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void sendRequestGift(RequestGiftInput requestGiftInput) {
        Call<ResponseBody> call = AppController.service.sendRequestGift(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                requestGiftInput
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getUser(String userID) {
        Call<User> call = AppController.service.getUser(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                userID
        );

        call.enqueue(new CallbackWithRetry<User>(call, mContext) {
            @Override
            public void onResponse(Call<User> call,
                                   Response<User> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getSentRequestList(StartLastIndex startLastIndex) {
        Call<ArrayList<RequestModel>> call = AppController.service.getSentRequestList(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                startLastIndex.startIndex,
                startLastIndex.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<RequestModel>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<RequestModel>> call,
                                   Response<ArrayList<RequestModel>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<RequestModel>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getBookmarkList(StartLastIndex startLastIndex) {
        Call<ArrayList<Gift>> call = AppController.service.getBookmarkList(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                startLastIndex.startIndex,
                startLastIndex.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<Gift>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<Gift>> call,
                                   Response<ArrayList<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<Gift>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getRequestsToMyGifts(StartLastIndex startLastIndex) {
        Call<ArrayList<Gift>> call = AppController.service.getRequestsMyGifts(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                startLastIndex.startIndex,
                startLastIndex.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<Gift>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<Gift>> call,
                                   Response<ArrayList<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<Gift>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getRecievedRequestList(RecievedRequestListInput recievedRequestListInput) {
        Call<ArrayList<RequestModel>> call = AppController.service.getRecievedRequestList(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                recievedRequestListInput.giftId,
                recievedRequestListInput.startIndex,
                recievedRequestListInput.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<RequestModel>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<RequestModel>> call,
                                   Response<ArrayList<RequestModel>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<RequestModel>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }


    public void getRegisteredGifts(String userId, StartLastIndex startLastIndex) {
        Call<ArrayList<Gift>> call = AppController.service.getMyRegisteredGifts(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                userId,
                startLastIndex.startIndex,
                startLastIndex.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<Gift>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<Gift>> call,
                                   Response<ArrayList<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<Gift>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getDonatedGifts(String userId, StartLastIndex startLastIndex) {
        Call<ArrayList<Gift>> call = AppController.service.getMyDonatedGifts(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                userId,
                startLastIndex.startIndex,
                startLastIndex.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<Gift>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<Gift>> call,
                                   Response<ArrayList<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<Gift>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getReceivedGifts(String userId, StartLastIndex startLastIndex) {
        Call<ArrayList<Gift>> call = AppController.service.getMyReceivedGifts(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                userId,
                startLastIndex.startIndex,
                startLastIndex.lastIndex
        );

        call.enqueue(new CallbackWithRetry<ArrayList<Gift>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<Gift>> call,
                                   Response<ArrayList<Gift>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<Gift>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void logout() {
        Call<ResponseBody> call = AppController.accountService.logout(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization)
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

}
