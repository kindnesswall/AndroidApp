package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.edit_text.EditTextIranSans;
import ir.kindnesswall.notification.NotificationUtils;

/**
 * Created by Taher on 2/11/2017.
 */

public class ShowNotifActivity extends AppCompatActivity {
    String notifData;
    EditTextIranSans tvNotif;
    private ArrayList<String> notiflist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifs);
        extractDataFromBundle();
        tvNotif = (EditTextIranSans) findViewById(R.id.et_show_notifs);

        notiflist = AppController.getStoredNotifs("NOTIFICATIONS");

        String list = notifData +
                System.getProperty("line.separator") +
                System.getProperty("line.separator");

        for (int i = notiflist.size()-1; i >= 0; i--) {
            list += notiflist.get(i) +
                    System.getProperty("line.separator") +
                    System.getProperty("line.separator");
        }
        tvNotif.setText(list);

        NotificationUtils.clearNotifications(this);

    }

    public static Intent createIntent(String data) {
        Intent intent = new Intent(AppController.getAppContext(), ShowNotifActivity.class);
        intent.putExtra(Constants.NOTIF_DATA, data);
        return intent;
    }

    private void extractDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            notifData = bundle.getString(Constants.NOTIF_DATA);
        }
    }

}
