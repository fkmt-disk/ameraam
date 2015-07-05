package orz.dev.disk.fkmt.ameraam;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

public final class AmeraamManager {

    private static final String TAG = AmeraamManager.class.getSimpleName();

    private static final long MILLIS_OF_DAY = 24 * 60 * 60 * 1000;

    private static final int REQUEST_CODE = 1;

    static void setAmeraam(Context context, int hour, int minute) {
        Log.d(TAG, "setAmeraam: hour=" + hour + ", minute=" + minute);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        long trigger = cal.getTimeInMillis();
        long cycle = MILLIS_OF_DAY;

        Log.d(TAG, "setAmeraam: trigger=" + cal + ", cycle=" + cycle + " ms");

        getAlarmManager(context).setRepeating(AlarmManager.RTC_WAKEUP, trigger, cycle, makePendingIntent(context));
    }

    static void cancelAmeraam(Context context) {
        Log.d(TAG, "cancelAmeraam");
        getAlarmManager(context).cancel(makePendingIntent(context));
    }

    private static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    private static PendingIntent makePendingIntent(Context context) {
        Intent intent = new Intent(context, AmeraamReceiver.class);
        intent.setAction("ameraam");
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    static void saveArea(Context context, String area) {
        SharedPreferences pref = getPreference(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("area", area);
        editor.commit();
    }

    static String loadArea(Context context) {
        return getPreference(context).getString("area", "");
    }

    private AmeraamManager() {
        throw new Error("ダメ。ゼッタイ。");
    }

}
