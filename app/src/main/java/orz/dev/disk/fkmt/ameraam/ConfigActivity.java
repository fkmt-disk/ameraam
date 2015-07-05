package orz.dev.disk.fkmt.ameraam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;


public final class ConfigActivity extends Activity {

    private static final String TAG = ConfigActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.config);

        final Context context = getApplicationContext();

        initAreaSpinner();

        findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick SET button");

                TimePicker picker = getView(R.id.timePicker);
                AmeraamManager.setAmeraam(context, picker.getCurrentHour(), picker.getCurrentMinute());

                Spinner spinner = getView(R.id.area);
                int position = spinner.getSelectedItemPosition();
                AmeraamManager.saveArea(context, KeyValueAdapter.getItemFrom(spinner, position).first);

                makeTextToast("セットした！").show();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick CANCEL button");
                AmeraamManager.cancelAmeraam(context);
                makeTextToast("キャンセルした！").show();
            }
        });

        findViewById(R.id.quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick QUIT button");
                finish();
            }
        });
    }

    private void initAreaSpinner() {
        List<Pair<String, String>> items = KeyValueAdapter.makeListFrom(getResources());

        KeyValueAdapter adapter = new KeyValueAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner s = getView(R.id.area);
        s.setAdapter(adapter);
    }

    private Toast makeTextToast(String text) {
        return Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
    }

    private <T> T getView(int id) {
        return (T)findViewById(id);
    }
}
