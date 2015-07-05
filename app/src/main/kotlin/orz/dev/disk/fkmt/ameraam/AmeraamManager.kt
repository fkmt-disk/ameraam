package orz.dev.disk.fkmt.ameraam

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log

import java.util.Calendar

public class AmeraamManager private constructor() {

    init {
        throw Error("ダメ。ゼッタイ。")
    }

    companion object {

        private val TAG = javaClass<AmeraamManager>().getSimpleName()

        private val MILLIS_OF_DAY = 24 * 60 * 60 * 1000.toLong()

        private val REQUEST_CODE = 1

        fun setAmeraam(context: Context, hour: Int, minute: Int) {
            Log.d(TAG, "setAmeraam: hour=" + hour + ", minute=" + minute)

            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            val trigger = cal.getTimeInMillis()
            val cycle = MILLIS_OF_DAY

            Log.d(TAG, "setAmeraam: trigger=" + cal + ", cycle=" + cycle + " ms")

            getAlarmManager(context).setRepeating(AlarmManager.RTC_WAKEUP, trigger, cycle, makePendingIntent(context))
        }

        fun cancelAmeraam(context: Context) {
            Log.d(TAG, "cancelAmeraam")
            getAlarmManager(context).cancel(makePendingIntent(context))
        }

        private fun getAlarmManager(context: Context): AlarmManager {
            return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }

        private fun makePendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, javaClass<AmeraamReceiver>())
            intent.setAction("ameraam")
            return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun getPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        }

        fun saveArea(context: Context, area: String) {
            val pref = getPreference(context)
            val editor = pref.edit()
            editor.putString("area", area)
            editor.commit()
        }

        fun loadArea(context: Context): String {
            return getPreference(context).getString("area", "")
        }
    }

}
