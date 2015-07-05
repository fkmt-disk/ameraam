package orz.dev.disk.fkmt.ameraam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public final class AmeraamReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = AmeraamReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Intent ameraam = new Intent(context, AmeraamService.class);
        startWakefulService(context, ameraam);
    }

}
