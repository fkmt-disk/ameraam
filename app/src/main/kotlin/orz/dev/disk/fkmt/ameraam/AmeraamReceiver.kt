package orz.dev.disk.fkmt.ameraam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver
import android.util.Log
import kotlin.platform.platformStatic

public class AmeraamReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        val ameraam = Intent(context, javaClass<AmeraamService>())
        WakefulBroadcastReceiver.startWakefulService(context, ameraam)
    }

    companion object {
        private val TAG = javaClass<AmeraamReceiver>().getSimpleName()
    }

}
