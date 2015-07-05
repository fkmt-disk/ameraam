package orz.dev.disk.fkmt.ameraam;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class AmeraamService extends IntentService {

    private static final String TAG = AmeraamService.class.getSimpleName();

    public AmeraamService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        try {
            onHandleImpl();
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        finally {
            AmeraamReceiver.completeWakefulIntent(intent);
        }
    }

    private void onHandleImpl() throws Exception {
        Log.d(TAG, "onHandleImpl");

        Context context = getApplicationContext();

        String area = AmeraamManager.loadArea(context);
        Log.d(TAG, "area=" + area);

        if (isRainToday(area)) {
            launchAmeraam(context);
        }
        else {
            Log.d(TAG, "雨振らない！");
        }
    }

    private void launchAmeraam(Context context) {
        Log.d(TAG, "launchAmeraam");
        Intent ameraam = new Intent(context, AmeraamActivity.class);
        ameraam.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(ameraam);
    }

    private static boolean isRainToday(String area) throws Exception {
        Log.d(TAG, "isRainToday");
        URL url = new URL("http://weather.livedoor.com/forecast/webservice/json/v1?city=" + area);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        String str = loadText(con.getInputStream());

        JSONObject json = new JSONObject(str);
        JSONArray forecasts = json.getJSONArray("forecasts");

        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject item = forecasts.getJSONObject(i);
            if ("今日".equals(item.getString("dateLabel"))) {
                String telop = item.getString("telop");
                Log.d(TAG, "telop=" + telop);
                return telop.contains("雨");
            }
        }

        return false;
    }

    private static String loadText(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        return buffer.toString();
    }

}
