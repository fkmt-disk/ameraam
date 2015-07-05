package orz.dev.disk.fkmt.ameraam;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// なんで標準APIで用意しないのか、、、めんどくさすぎる。。。
public final class KeyValueAdapter extends ArrayAdapter<Pair<String, String>> {

    public KeyValueAdapter(Context context, int resource, List<Pair<String, String>> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView)super.getView(position, convertView, parent);
        v.setText(getItem(position).second);
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView)super.getDropDownView(position, convertView, parent);
        v.setText(getItem(position).second);
        return v;
    }

    public static List<Pair<String, String>> makeListFrom(Resources res) {
        List<Pair<String, String>> list = new ArrayList<>();
        TypedArray array = res.obtainTypedArray(R.array.area_data);
        for (int i = 0; i < array.length(); i++) {
            int resId = array.getResourceId(i, -1);
            if (resId != -1) {
                String[] item = res.getStringArray(resId);
                list.add(new Pair<String, String>(item[0], item[1]));
            }
        }
        return list;
    }

    public static Pair<String, String> getItemFrom(Spinner spinner, int position) {
        return (Pair<String, String>)spinner.getAdapter().getItem(position);
    }

}
