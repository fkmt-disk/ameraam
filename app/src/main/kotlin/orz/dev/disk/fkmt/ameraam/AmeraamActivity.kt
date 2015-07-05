package orz.dev.disk.fkmt.ameraam

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.widget.ImageView

import java.io.IOException

public class AmeraamActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        setContentView(R.layout.ameraam)

        getWindow().addFlags(FLAG_DISMISS_LOCK)

        val player = playSound()

        findViewById(R.id.ok).setOnClickListener { v ->
            player.stop()
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // onCreateではwigth/heightがとれないので、ここで。
        shakeUmbrella()
    }

    private fun shakeUmbrella() {
        val umbrella = findViewById(R.id.umbrella) as ImageView

        val scale = ScaleAnimation(1f, 0.8f, 1f, 0.8f, umbrella.getPivotX(), umbrella.getPivotY())
        scale.setDuration(1500)
        scale.setRepeatCount(Animation.INFINITE)

        umbrella.setAnimation(scale)
    }

    private fun playSound(): MediaPlayer {
        val player = MediaPlayer.create(this, R.raw.chime)
        player.setLooping(true)
        player.start()
        return player
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
        getWindow().clearFlags(FLAG_DISMISS_LOCK)
    }

    companion object {

        private val TAG = javaClass<AmeraamActivity>().getSimpleName()

        private val FLAG_DISMISS_LOCK =
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD

    }

}
