package com.regulo.dev.insects.reminders;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.regulo.dev.insects.QuizActivity;
import com.regulo.dev.insects.R;
import com.regulo.dev.insects.data.Insects;

import java.util.Collections;


public class ReminderService extends IntentService {

    private static final String TAG = ReminderService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;
    public static final String EXTRA_INSECTS = "insectList";
    public static final String EXTRA_ANSWER = "selectedInsect";

    public ReminderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Quiz reminder event triggered");

        Insects insects = (Insects)intent.getParcelableArrayListExtra(EXTRA_INSECTS);

        //Present a notification to the user
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Create action intent
        Intent action = new Intent(this, QuizActivity.class);
        action.putParcelableArrayListExtra(EXTRA_INSECTS, insects);
        Collections.shuffle(insects.getInsectsList());
        action.putExtra(EXTRA_ANSWER, insects.getInsectsList().get(2));

        PendingIntent operation =
                PendingIntent.getActivity(this, 0, action, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_bug_empty)
                .setContentIntent(operation)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);

    }

}
