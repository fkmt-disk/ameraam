package orz.dev.disk.fkmt.ameraam;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.io.IOException;

public final class AmeraamActivity extends Activity {

    private static final String TAG = AmeraamActivity.class.getSimpleName();

    private static final int FLAG_DISMISS_LOCK =
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.ameraam);

        getWindow().addFlags(FLAG_DISMISS_LOCK);

        final MediaPlayer player = playSound();

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // onCreateではwigth/heightがとれないので、ここで。
        shakeUmbrella();
    }

    private void shakeUmbrella() {
        ImageView umbrella = (ImageView)findViewById(R.id.umbrella);

        ScaleAnimation scale = new ScaleAnimation(1, 0.9f, 1, 0.9f, umbrella.getPivotX(), umbrella.getPivotY());
        scale.setDuration(1000);
        scale.setRepeatCount(Animation.INFINITE);

        umbrella.setAnimation(scale);
    }

    private MediaPlayer playSound() {
        MediaPlayer player = MediaPlayer.create(this, R.raw.chime);
        player.setLooping(true);
        player.start();
        return player;
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        getWindow().clearFlags(FLAG_DISMISS_LOCK);
    }

}
