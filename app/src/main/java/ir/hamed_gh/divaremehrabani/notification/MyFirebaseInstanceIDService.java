package ir.hamed_gh.divaremehrabani.notification;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import retrofit2.Call;
import retrofit2.Response;

import static ir.hamed_gh.divaremehrabani.app.AppController.REGISTRATION_COMPLETE;

/**
 * Created by Taher on 2/2/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService implements ApiRequest.Listener {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    ApiRequest apiRequest;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        AppController.storeString(Constants.FIREBASE_REG_TOKEN, refreshedToken);
        Log.d(Constants.FIREBASE_REG_TOKEN, refreshedToken);
//        Toasti.showS(getAppContext() , "GET REGID" + Constants.FIREBASE_REG_TOKEN);
        // sending reg id to server
        apiRequest = new ApiRequest(this, this);
//        String deviceID = DeviceInfo.getDeviceID(this);
//        apiRequest.setDevice(new SetDeviceInput(refreshedToken, deviceID));

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    @Override
    public void onResponse(Call call, Response response) {
//        if (response.body() instanceof SetDeviceOutput) {
//            SetDeviceOutput output = ((SetDeviceOutput) response.body());
//            switch (output.status) {
//                case "1":
////                    Toasti.showS(this, "Token is added.");
//                    break;
//
//                default:
////                    Toasti.showS(this, "wrong result");
//                    break;
//            }
//        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}