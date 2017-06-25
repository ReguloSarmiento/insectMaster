package com.regulo.dev.insects.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.regulo.dev.insects.ListBugsFragment;
import com.regulo.dev.insects.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    public static final String EXTRA_INSECTS = "insectList";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Schedule alarm on BOOT_COMPLETED
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            scheduleAlarm(context);
        }
    }

    /* Schedule the alarm based on user preferences */
    public static void scheduleAlarm(Context context) {

        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        String keyReminder = context.getString(R.string.pref_key_reminder);
        String keyAlarm = context.getString(R.string.pref_key_alarm);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean enabled = preferences.getBoolean(keyReminder, false);


        //Intent to trigger
        Intent intent = new Intent(context, ReminderService.class);

       intent.putParcelableArrayListExtra(EXTRA_INSECTS, ListBugsFragment.mInsectsList);



        PendingIntent operation = PendingIntent
                .getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (enabled) {
            //Gather the time preference
            Calendar startTime = Calendar.getInstance();

            String alarmPref = preferences.getString(keyAlarm, "12:00");
            String[] split = alarmPref.split(":");
            startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
            startTime.set(Calendar.MINUTE, Integer.parseInt(split[1]));

//            try {
//                String alarmPref = preferences.getString(keyAlarm, "12:00");
//                //SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
//                //startTime.setTime(format.parse(alarmPref));
//            } catch (ParseException e) {
//                Log.w(TAG, "Unable to determine alarm start time", e);
//                return;
//            }

            //Start at the preferred time
            //If that time has passed today, set for tomorrow
            if (Calendar.getInstance().after(startTime)) {
                startTime.add(Calendar.DATE, 1);
            }

            Log.d(TAG, "Scheduling quiz reminder alarm");
            manager.setExact(AlarmManager.RTC, startTime.getTimeInMillis(), operation);
        } else {
            Log.d(TAG, "Disabling quiz reminder alarm");
            manager.cancel(operation);
        }
    }



}
