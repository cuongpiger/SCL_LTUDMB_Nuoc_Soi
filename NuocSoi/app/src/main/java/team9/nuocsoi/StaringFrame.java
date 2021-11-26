package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;


public class StaringFrame extends AppCompatActivity {

    ImageView imgLogo;
    TextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_frame);

        referWidgets();
        setupAnimation();
        turnSignInFrame();
    }

    private void referWidgets() {
        imgLogo = findViewById(R.id.imgLogo);
        tvCopyright = findViewById(R.id.tvCopyright);
    }

    private void setupAnimation() {
        imgLogo.animate().alpha(0f).setDuration(0);
        tvCopyright.animate().alpha(0f).setDuration(0);

        imgLogo.animate().alpha(1f).setDuration(Config.LOGO_DELAY).setListener(new AnimatorListenerAdapter() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onAnimationEnd(Animator animator) {
                Config.setCopyright(tvCopyright);
                tvCopyright.animate().alpha(1f).setDuration((Config.COPYRIGHT_DELAY));
            }
        });
    }

    private void turnSignInFrame() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StaringFrame.this, SignInFrame.class);
                startActivity(intent);
                finish();
            }
        }, Config.TURN_SIGN_IN_DELAY);
    }
}