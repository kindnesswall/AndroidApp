package ir.kindnesswall.helper;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.kindnesswall.activity.LoginActivity;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.constants.RequestName;
import ir.kindnesswall.model.GetGiftPathQuery;
import ir.kindnesswall.model.api.Category;
import ir.kindnesswall.model.api.ErrorOutput;
import ir.kindnesswall.model.api.Gift;
import ir.kindnesswall.model.api.RequestModel;
import ir.kindnesswall.model.api.StartLastIndex;
import ir.kindnesswall.model.api.StatusOutput;
import ir.kindnesswall.model.api.TeamMember;
import ir.kindnesswall.model.api.User;
import ir.kindnesswall.model.api.input.BookmarkInput;
import ir.kindnesswall.model.api.input.LogoutInput;
import ir.kindnesswall.model.api.input.RecievedRequestListInput;
import ir.kindnesswall.model.api.input.ReportInput;
import ir.kindnesswall.model.api.input.RequestGiftInput;
import ir.kindnesswall.model.api.input.SetDeviceInput;
import ir.kindnesswall.model.api.output.AppInfoOutput;
import ir.kindnesswall.model.api.output.RegisterOutput;
import ir.kindnesswall.model.api.output.StatisticsOutput;
import ir.kindnesswall.model.api.output.TokenOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ApiRequest {

    private Listener listener;
    private AdapterListener adapterListener;
    private AdapterTagListener adapterTagListener;
    private ActivityListener activityListener;
    private TagListener tagListener;

    private Context mContext;

    public ApiRequest(Context context, TagListener tagListener) {
        this.tagListener = tagListener;
        mContext = context;
    }

    public ApiRequest(Context context, AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
        mContext = context;
    }

    public ApiRequest(Context context, AdapterTagListener adapterTagListener) {
        this.adapterTagListener = adapterTagListener;
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

    private void handlingOnResponse(HandlingResponse handlingResponse) {
        handlingOnResponse(handlingResponse, "");
    }

    private void handlingOnResponse(HandlingResponse handlingResponse, String TAG) {

        if (!handlingResponse.response.isSuccessful()) {
            ErrorOutput errorOutput = null;
            if (handlingResponse.response.errorBody()!=null){

                try {
                    errorOutput = (new Gson()).fromJson(
                            handlingResponse.response.errorBody().string(), ErrorOutput.class
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (handlingResponse.response.code()==401){
                AppController.clearInfo();
                mContext.startActivity(LoginActivity.createIntent());
            } else if (handlingResponse.response.code()==400
                    && errorOutput!=null
		            && errorOutput.description != null
                    && errorOutput.description.equals(
                    		"incorrect_user_pass"
            )){

                listener.onFailure(handlingResponse.call, new Throwable("incorrect_user_pass"));

            }else if(ConnectionDetector.isConnectedToInternet()) {
                if ((mContext instanceof Activity) && ((Activity) mContext).hasWindowFocus()) {
                    ConnectionDetector.ShowServerProblemDialog(mContext, handlingResponse.callbackWithRetry);
                } else {
                    Toasti.showS("مشکل ارتباط با سرور");
                }
            } else
                ConnectionDetector.ShowNetwrokConnectionProblemDialog(mContext, handlingResponse.callbackWithRetry);
            return;
        }

        if ((handlingResponse.response).body() instanceof StatusOutput) {
            ((StatusOutput) (handlingResponse.response).body()).tag = TAG;
        }

        if (listener != null) {
            listener.onResponse(handlingResponse.call, handlingResponse.response);
        } else if (adapterListener != null) {
            adapterListener.onResponse(handlingResponse.call, handlingResponse.response,
                    handlingResponse.position);
        } else if (tagListener != null) {
            tagListener.onResponse(
                    handlingResponse.call,
                    handlingResponse.response, TAG);
        } else if (adapterTagListener != null) {
            adapterTagListener.onResponse(
                    handlingResponse.call,
                    handlingResponse.response,
                    handlingResponse.position,
                    TAG
            );
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

    public void getGifts(final GetGiftPathQuery getGiftPathQuery, final int position) {

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
                handlingOnResponse(new HandlingResponse(call, response, this,position));
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
                handlingOnResponse(new HandlingResponse(call, response, this),
                        RequestName.AcceptRequest);
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
                handlingOnResponse(
                        new HandlingResponse(call, response, this, position),
                        RequestName.DeleteMyRequest);
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
                handlingOnResponse(new HandlingResponse(call, response, this), RequestName.DeleteMyRequest);
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
                handlingOnResponse(new HandlingResponse(call, response, this), RequestName.DenyRequest);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void deleteGift(String giftId) {

        Call<ResponseBody> call = AppController.service.deleteGift(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                giftId);

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this), RequestName.DeleteGift);
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
                handlingOnResponse(new HandlingResponse(call, response, this), RequestName.Bookmark);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void login(String verificationCode, String deviceId) {

        Call<TokenOutput> call = AppController.accountService.login(
                AppController.getStoredString(Constants.TELEPHONE),
                verificationCode,
                AppController.getStoredString(Constants.FIREBASE_REG_TOKEN),
                deviceId,
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
        Call<RegisterOutput> call = AppController.accountService.register(telephone);

        call.enqueue(new CallbackWithRetry<RegisterOutput>(call, mContext) {
            @Override
            public void onResponse(Call<RegisterOutput> call, Response<RegisterOutput> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<RegisterOutput> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

    }

    public void getCategories() {

        Call<ArrayList<Category>> call = AppController.service.getCategories(
                "0", "100", "1"
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

    public void editGift(Gift gift) {
        Call<ResponseBody> call = AppController.service.editGift(
                "application/json",
                AppController.getStoredString(Constants.Authorization),
                gift.giftId,
                gift
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
                handlingOnResponse(new HandlingResponse(call, response, this), RequestName.SendRequestGift);
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

    public void getStatistics() {
        Call<StatisticsOutput> call = AppController.service.getStatistics(
                Constants.JSON_TYPE
        );

        call.enqueue(new CallbackWithRetry<StatisticsOutput>(call, mContext) {
            @Override
            public void onResponse(Call<StatisticsOutput> call,
                                   Response<StatisticsOutput> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<StatisticsOutput> call, Throwable t) {
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

    public void reportGift(ReportInput reportInput) {
        Call<ResponseBody> call = AppController.service.reportGift(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                reportInput
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this), RequestName.ReportGift);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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

    public void getTeamMembers() {
        Call<ArrayList<TeamMember>> call = AppController.service.getTeamMembers();

        call.enqueue(new CallbackWithRetry<ArrayList<TeamMember>>(call, mContext) {
            @Override
            public void onResponse(Call<ArrayList<TeamMember>> call,
                                   Response<ArrayList<TeamMember>> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ArrayList<TeamMember>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getAppInfo() {//final ProgressView progressView) {
        Call<AppInfoOutput> result = AppController.service.getAppInfo(
                DeviceInfo.getAppVersionCode());
        //TODO : add call ArrayList
        //callArrayList.add(result);
        result.enqueue(new CallbackWithRetry<AppInfoOutput>(result, mContext) {
            @Override
            public void onResponse(Call<AppInfoOutput> call, Response<AppInfoOutput> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void retry() {
                //Todo : has problem in progress view visibility
//				if (progressView != null)
//					progressView.setVisibility(View.VISIBLE);
                super.retry();

            }

            @Override
            public void onFailure(Call<AppInfoOutput> call, Throwable t) {
                super.onFailure(call, t);

//				if (t instanceof IOException) {//network problem
//					progressView.setVisibility(View.VISIBLE);
//					startActivity(NoInternetActivity.createIntentUpdateCallFailure(this));
//				} else {
//					Toasti.showS(mContext, getString(R.string.onFailuer));
//				}
            }
        });
    }

    public void setDevice(SetDeviceInput device) {
        Call<ResponseBody> result =
                AppController.accountService.setDevice(
                        Constants.JSON_TYPE,
                        device
                );

        result.enqueue(new CallbackWithRetry<ResponseBody>(result, mContext) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handlingOnResponse(new HandlingResponse(call, response, this));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasti.showS("مشکل ارتباط با سرور");
            }
        });
    }

    public void logout() {
        Call<ResponseBody> call = AppController.accountService.logout(
                Constants.JSON_TYPE,
                AppController.getStoredString(Constants.Authorization),
                new LogoutInput(
                        AppController.getStoredString(Constants.FIREBASE_REG_TOKEN)
                )
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


    public interface Listener {
        void onResponse(Call call, Response response);

        void onFailure(Call call, Throwable t);
    }

    public interface TagListener {
        void onResponse(Call call, Response response, String tag);

        void onFailure(Call call, Throwable t, String tag);
    }

    public interface AdapterTagListener {
        void onResponse(Call call, Response response, int position, String tag);

        void onFailure(Call call, Throwable t, String tag);
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
        public String tag;

        public HandlingResponse(
                Call call,
                Response response,
                CallbackWithRetry callbackWithRetry,
                String tag
        ) {
            this.call = call;
            this.response = response;
            this.callbackWithRetry = callbackWithRetry;
            this.tag = tag;
        }

        public HandlingResponse(
                Call call,
                Response response,
                CallbackWithRetry callbackWithRetry,
                int position,
                String tag
        ) {
            this.call = call;
            this.response = response;
            this.callbackWithRetry = callbackWithRetry;
            this.position = position;
            this.tag = tag;
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

}
