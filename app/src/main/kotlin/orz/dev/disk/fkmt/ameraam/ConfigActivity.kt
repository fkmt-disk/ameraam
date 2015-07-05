package orz.dev.disk.fkmt.ameraam

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast


public class ConfigActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        setContentView(R.layout.config)

        val context = getApplicationContext()

        initAreaSpinner()

        findViewById(R.id.set).setOnClickListener { v ->
            Log.d(TAG, "onclick SET button")

            val picker = getView<TimePicker>(R.id.timePicker)
            AmeraamManager.setAmeraam(context, picker.getCurrentHour()!!, picker.getCurrentMinute()!!)

            val spinner = getView<Spinner>(R.id.area)
            val position = spinner.getSelectedItemPosition()
            AmeraamManager.saveArea(context, KeyValueAdapter.getItemFrom(spinner, position).first)

            makeTextToast("セットした！").show()
        }

        findViewById(R.id.cancel).setOnClickListener { v ->
            Log.d(TAG, "onclick CANCEL button")
            AmeraamManager.cancelAmeraam(context)
            makeTextToast("キャンセルした！").show()
        }

        findViewById(R.id.quit).setOnClickListener { v ->
            Log.d(TAG, "onclick QUIT button")
            finish()
        }
    }

    private fun initAreaSpinner() {
        val items = KeyValueAdapter.makeListFrom(getResources())

        val adapter = KeyValueAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val s = getView<Spinner>(R.id.area)
        s.setAdapter(adapter)
    }

    private fun makeTextToast(text: String): Toast {
        return Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
    }

    private fun <T> getView(id: Int): T {
        return findViewById(id) as T
    }

    companion object {
        private val TAG = javaClass<ConfigActivity>().getSimpleName()
    }

}
