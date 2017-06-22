package ir.kindnesswall.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ir.kindnesswall.activity.ShowNotifActivity;
import ir.kindnesswall.helper.ApiRequest;
import retrofit2.Call;
import retrofit2.Response;

import static ir.kindnesswall.app.AppController.PUSH_NOTIFICATION;


/**
 * Created by Taher on 2/2/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements ApiRequest.Listener {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private String dataMessage;

    private NotificationUtils notificationUtils;
    private LocalBroadcastManager broadcaster;
    private ApiRequest apiRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
        apiRequest = new ApiRequest(this, this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        Log.d("onMessageReceived",
                "remoteMessage.getData(): " + remoteMessage.getData()
        );

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            dataMessage = remoteMessage.getData().toString();

            try {
//                NotificationData notificationData = new NotificationData(
//                        remoteMessage.getData().get("action"),
//                        remoteMessage.getData().get("user_id"),
//                        remoteMessage.getData().get("data"),
//                        remoteMessage.getData().get("class_id")
//                );

//                Log.d("onMessageReceived",
//                        "userId: " + notificationData.user_id
//                                + " ,action: " + notificationData.action
//                                + ", class_id: " + notificationData.class_id
//                                + " , data:" + notificationData.data
//                );

//                handleDataMessage(notificationData);
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
    private void handleDataMessage() {

//        doAction(notificationData);

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", dataMessage);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

//            Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
//            resultIntent.putExtra("message", notificationData.message);

            Intent resultIntent = ShowNotifActivity.createIntent(dataMessage);

            // check for image attachment

            showNotificationMessage(getApplicationContext(), "عنوان", dataMessage, resultIntent);
        } else {
            // app is in background, show the notification in notification tray
//            Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
//            resultIntent.putExtra("message", message);

            /////mitra:comment this line:
          //  doAction(notificationData);

//            Intent resultIntent = intentToAction(notificationData);
//            if(resultIntent!=null)
//                startActivity(resultIntent);


            // check for image attachment
//            if (TextUtils.isEmpty(notificationData.imageUrl)) {
//                showNotificationMessage(getApplicationContext(), "عنوان", dataMessage, resultIntent);
//            } else {
//                // image is present, show notification with image
//                showNotificationMessageWithBigImage(getApplicationContext(), notificationData.title, notificationData.result, resultIntent, notificationData.imageUrl);
//            }
        }
    }

//    private void doAction(NotificationData notificationData) {
//        if (
//                (AppController.getStoredString(UserInfo.CLASS_ID) == null) ||
//                        (AppController.getStoredString(UserInfo.USER_ID) == null) ||
//                        !notificationData.user_id.equals(AppController.getStoredString(UserInfo.USER_ID)) ||
//                        !notificationData.class_id.equals(AppController.getStoredString(UserInfo.CLASS_ID))
//                ) {
//
//            if (!notificationData.user_id.equals(Constants.BRODCAST_MESSAGE)) {
//
//                Log.d(TAG, "notif is not for these class or user: classID=" +
//                        notificationData.class_id + " ,userID=" + notificationData.user_id);
//
//                return;
//            }
//        }
//
//        Gson gson = new Gson();
//        switch (notificationData.action) {
//
//            case Push.ON_USER_LOGOUT:
//                //notificationData.data = "{\"user_id\":59,\"logout_time\":{\"fa_time\":\"1395/12/21 16:49:47\",\"en_time\":\"2017/03/11 16:49:47\",\"day_of_week\":\"Saturday\"}}";
//                UserLogout userLogout = gson.fromJson(notificationData.data, UserLogout.class);
//                Time logOutTime = userLogout.logoutTime;
//                logOutTime.save();
//                SQLite.update(Student.class)
//                        .set(Student_Table.status.eq(Constants.ABCSNT)
//                                ,Student_Table.logout_time_timeID.eq(logOutTime.timeID))
//                        .where(Student_Table.user_id.is(userLogout.userId))
//                        .execute();
//                SQLite.update(Teacher.class)
//                        .set(Teacher_Table.status.eq(Constants.ABCSNT)
//                                ,Teacher_Table.logout_time_timeID.eq(logOutTime.timeID))
//                        .where(Teacher_Table.user_id.is(userLogout.userId))
//                        .execute();
//                sendResult(Push.ON_USER_LOGOUT, UserInfo.USER_ID, userLogout.userId);
//                break;
//
////            case Push.ON_CHAT_BLOCK_STATUS_CHANGED:
////                break;
//
//            case Push.ON_FILE_DELETED:
//                File deletedFile = gson.fromJson(notificationData.data, File.class);
//                SQLite.delete().from(File.class).where(File_Table.file_id.is(deletedFile.file_id)).execute();
//                sendResult(Push.ON_FILE_DELETED,Constants.FILE_ID,deletedFile.file_id);
//
//                break;
//
//            case Push.ON_NEW_FILE:
//
////                File file = gson.fromJson(notificationData.data, File.class);
////                file.save();
//                sendResult(Push.ON_NEW_FILE , Constants.NEW_FILE, notificationData.data);
//
//                break;
//
//            case Push.ON_CHAT_BLOCK_STATUS_CHANGED:
//
//                ChatBlockStatusChange chatBlockStatusChange =
//                        gson.fromJson(notificationData.data, ChatBlockStatusChange.class);
//
//                SQLite.update(Chat.class)
//                        .set(Chat_Table.status.eq(chatBlockStatusChange.status))
//                        .where(Chat_Table.chat_id.is(chatBlockStatusChange.chatId))
//                        .execute();
//
//                sendResult(Push.ON_CHAT_BLOCK_STATUS_CHANGED);
//
//                break;
//
//            case Push.ON_NEW_STUDENT:
//
//                Student student = gson.fromJson(notificationData.data, Student.class);
//                student.save();
//                sendResult(Push.ON_NEW_STUDENT, UserInfo.USER_ID, student.user_id);
//                break;
//
//            case Push.ON_USER_STATUS_CHANGED:
//
//                StatusChanged statusChanged = gson.fromJson(notificationData.data, StatusChanged.class);
//                SQLite.update(Student.class)
//                        .set(Student_Table.status.eq(statusChanged.status))
//                        .where(Student_Table.user_id.is(statusChanged.user_id))
//                        .execute();
//
//                SQLite.update(Teacher.class)
//                        .set(Teacher_Table.status.eq(statusChanged.status))
//                        .where(Teacher_Table.user_id.is(statusChanged.user_id))
//                        .execute();
//
//
//                sendResult(Push.ON_USER_STATUS_CHANGED, UserInfo.USER_ID, statusChanged.user_id);
//                break;
//
//            case Push.ON_USER_EMOJI_CHANGED:
//
//                EmojiChanged emojiChanged = gson.fromJson(notificationData.data, EmojiChanged.class);
//                SQLite.update(Student.class)
//                        .set(Student_Table.emoji.eq(emojiChanged.emoji))
//                        .where(Student_Table.user_id.is(emojiChanged.user_id))
//                        .execute();
//
//                SQLite.update(Teacher.class)
//                        .set(Teacher_Table.emoji.eq(emojiChanged.emoji))
//                        .where(Teacher_Table.user_id.is(emojiChanged.user_id))
//                        .execute();
//
//                if (emojiChanged.user_id.equals(AppController.getStoredString(UserInfo.USER_ID)))
//                    sendResult(Push.ON_MY_EMOJI_CHANGED, Constants.EMOJI, emojiChanged.emoji);
//                else
//                    sendResult(Push.ON_USER_EMOJI_CHANGED, UserInfo.USER_ID, emojiChanged.user_id);
//
//                break;
//            case Push.ON_NEW_QUIZ_DESCRIPTIVE:
//
//                DescriptiveQuizObj descriptiveQuizObj =
//                        gson.fromJson(notificationData.data, DescriptiveQuizObj.class);
//
//                startActivity(
//                        DescriptiveQuizActivity.createIntent(this, descriptiveQuizObj)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                );
//
//                break;
//
//            case Push.ON_NEW_QUIZ_MULTIPLE_CHOICE:
//
//                MultipleChoiceQuizObj multipleChoiceQuizObj =
//                        gson.fromJson(notificationData.data, MultipleChoiceQuizObj.class);
//
//                startActivity(
//                        MultipleChoiceQuizActivity.createIntent(this, multipleChoiceQuizObj)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                );
//
//                break;
//
//            case Push.ON_NEW_QUESTION:
//
//                Question question = gson.fromJson(notificationData.data, Question.class);
//                question.time.save();
//                question.save();
//                sendResult(Push.ON_NEW_QUESTION, Constants.QUESTION_ID, question.question_id);
//                break;
//
//            case Push.ON_NEW_MESSAGE_RECEIVED:
//
//                NewMessageReceivedData newMessageReceivedData = gson.fromJson(notificationData.data, NewMessageReceivedData.class);
//                newMessageReceivedData.message.save();
//                Chat chat = SQLite.select().from(Chat.class).where(Chat_Table.chat_id.is(newMessageReceivedData.chatId)).querySingle();
//                int unseen;
//                if (!newMessageReceivedData.message.sender_id.equals(
//                        AppController.getStoredString(UserInfo.USER_ID)
//                )) {
//                    unseen = Integer.valueOf(chat.count_unseen) + 1;
//                }else {
//                    unseen = Integer.valueOf(chat.count_unseen);
//                }
//
//                SQLite.update(Chat.class)
//                        .set(
//                                Chat_Table.count_unseen.eq(unseen + ""),
//                                Chat_Table.last_message_id.eq(newMessageReceivedData.message.id)
//
//                        ).where(Chat_Table.chat_id.is(newMessageReceivedData.chatId))
//                        .execute();
//
//                sendResult(
//                        Push.ON_NEW_MESSAGE_RECEIVED,
//                        Constants.CHAT_ID,
//                        newMessageReceivedData.chatId
//                );
//                break;
//
//            case Push.ON_NEW_EVENT:
//
//                sendResult(
//                        Push.ON_NEW_EVENT,
//                        Constants.EVENT_DATA,
//                        notificationData.data
//                );
//
//                break;
//
//            case Push.ON_QUESTION_UPDATE:
//
//                Question updatedQuestion = gson.fromJson(notificationData.data, Question.class);
//                updatedQuestion.time.save();
//                updatedQuestion.save();
//                sendResult(Push.ON_QUESTION_UPDATE, Constants.QUESTION_ID, updatedQuestion.question_id);
//                break;
//
//            case Push.ON_TIME_CHANGED:
//                NewTime classTime = gson.fromJson(notificationData.data, NewTime.class);
//
//                ClassInfo classInfo = SQLite.select().from(ClassInfo.class)
//                        .where(
//                                ClassInfo_Table.class_id.is(AppController.getStoredString(UserInfo.CLASS_ID))
//                        ).querySingle();
//
//                SQLite.update(Time.class).set(
//                        en_time.eq(classTime.newTime.en_time),
//                        fa_time.eq(classTime.newTime.fa_time),
//                        day_of_week.eq(classTime.newTime.day_of_week)
//                ).where(Time_Table.timeID.is(classInfo.end_time.timeID)).execute();
//
//                sendResult(Push.ON_TIME_CHANGED);
//
//                break;
//
//            case Push.ON_CLASS_FINISHED:
//
//                apiRequest.disconnect();
//                AppController.clearClassData();
//                startActivity(ActiveClassListActivity.createIntent(this, true));
//
//                break;
//
//            default:
//                break;
//        }
//
//    }

    public void sendResult(String action, String TAG, String data) {
        Intent intent = new Intent(action);
        intent.putExtra(TAG, data);
        broadcaster.sendBroadcast(intent);
    }

    public void sendResult(String action) {
        Intent intent = new Intent(action);
        broadcaster.sendBroadcast(intent);
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

    @Override
    public void onResponse(Call call, Response response) {

    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}