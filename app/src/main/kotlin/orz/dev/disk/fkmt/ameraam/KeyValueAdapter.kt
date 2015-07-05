package orz.dev.disk.fkmt.ameraam

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import java.util.ArrayList

// なんで標準APIで用意しないのか、、、めんどくさすぎる。。。
public class KeyValueAdapter(
        context: Context,
        resource: Int,
        list: List<Pair<String, String>>) :
    ArrayAdapter<Pair<String, String>>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v = super.getView(position, convertView, parent) as TextView
        v.setText(getItem(position).second)
        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v = super.getDropDownView(position, convertView, parent) as TextView
        v.setText(getItem(position).second)
        return v
    }

    companion object {

        public fun makeListFrom(res: Resources): List<Pair<String, String>> {
            val list = ArrayList<Pair<String, String>>()
            val array = res.obtainTypedArray(R.array.area_data)
            for (i in 0..array.length() - 1) {
                val resId = array.getResourceId(i, -1)
                if (resId != -1) {
                    val item = res.getStringArray(resId)
                    list.add(Pair(item[0], item[1]))
                }
            }
            return list
        }

        public fun getItemFrom(spinner: Spinner, position: Int): Pair<String, String> {
            return spinner.getAdapter().getItem(position) as Pair<String, String>
        }
    }

}
