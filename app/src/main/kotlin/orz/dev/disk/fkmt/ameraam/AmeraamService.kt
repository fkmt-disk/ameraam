package orz.dev.disk.fkmt.ameraam

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver
import android.util.Log

import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

public class AmeraamService : IntentService(AmeraamService.TAG) {

    override fun onHandleIntent(intent: Intent) {
        Log.d(TAG, "onHandleIntent")
        try {
            onHandleImpl()
        } catch (e: Exception) {
            Log.e(TAG, e.getMessage(), e)
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent)
        }
    }

    throws(Exception::class)
    private fun onHandleImpl() {
        Log.d(TAG, "onHandleImpl")

        val context = getApplicationContext()

        val area = AmeraamManager.loadArea(context)
        Log.d(TAG, "area=$area")

        if (isRainToday(area)) {
            launchAmeraam(context)
        } else {
            Log.d(TAG, "雨振らない！")
        }
    }

    private fun launchAmeraam(context: Context) {
        Log.d(TAG, "launchAmeraam")
        val ameraam = Intent(context, javaClass<AmeraamActivity>())
        ameraam.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(ameraam)
    }

    companion object {

        private val TAG = javaClass<AmeraamService>().getSimpleName()

        throws(Exception::class)
        private fun isRainToday(area: String): Boolean {
            Log.d(TAG, "isRainToday")
            val url = URL("http://weather.livedoor.com/forecast/webservice/json/v1?city=$area")

            val con = url.openConnection() as HttpURLConnection
            val str = loadText(con.getInputStream())

            val json = JSONObject(str)
            val forecasts = json.getJSONArray("forecasts")

            for (i in 0..forecasts.length() - 1) {
                val item = forecasts.getJSONObject(i)
                if ("今日" == item.getString("dateLabel")) {
                    val telop = item.getString("telop")
                    Log.d(TAG, "telop=" + telop)
                    return telop.contains("雨")
                }
            }

            return false
        }

        throws(IOException::class)
        private fun loadText(`is`: InputStream): String {
            val reader = BufferedReader(InputStreamReader(`is`))
            val buffer = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                buffer.append(line)
                line = reader.readLine()
            }
            reader.close()
            return buffer.toString()
        }

    }

}
