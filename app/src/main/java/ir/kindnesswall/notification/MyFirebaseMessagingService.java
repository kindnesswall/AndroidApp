package ir.kindnesswall.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Map;

import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.activity.ShowNotifActivity;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.bottombar.BottomBar;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.model.NotificationData;
import ir.kindnesswall.model.PushSampleModel;

import static ir.kindnesswall.app.AppController.PUSH_NOTIFICATION;


/**
 * Created by Taher on 2/2/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessaging";//.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
                Map<String, String> params = remoteMessage.getData();

//                if (params.get("type").equals("message")) {



                    if (remoteMessage.getData().get("userId").equals(AppController.getStoredString(Constants.USER_ID))
                            || remoteMessage.getData().get("userId").equals("0")){

                        PushSampleModel pushSampleModel = new Gson().fromJson(remoteMessage.getData().get("data"), PushSampleModel.class);

                        NotificationData notificationData = new NotificationData(

                                pushSampleModel.message,
                                pushSampleModel.title,
                                pushSampleModel.imageUrl

                        );

                        handleDataMessage(notificationData);
                    }

//                }

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            //app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

//            Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
//            resultIntent.putExtra("message", message);
            Intent resultIntent = ShowNotifActivity.createIntent(message);

            // check for image attachment

            showNotificationMessage(getApplicationContext(), "عنوان", message, resultIntent);
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    //TODO: handle JSON messages.
    private void handleDataMessage(NotificationData notificationData) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push);

        contentView.setTextViewText(R.id.title, notificationData.title);
        contentView.setTextViewText(R.id.message_tv, notificationData.message);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_profile)
                .setContent(contentView);

        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

            Intent notificationIntent = new Intent(this, BottomBar.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            notification.contentIntent = contentIntent;
        }


        mNotificationManager.notify(2, notification);

        Picasso
                .with(this)
                .load(notificationData.imageUrl)
                .into(contentView, R.id.notification_imageview, 2, notification);
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent, imageUrl);
    }

}