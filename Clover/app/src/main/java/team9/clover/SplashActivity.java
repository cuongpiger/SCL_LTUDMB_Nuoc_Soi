package team9.clover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team9.clover.Module.Config;

public class SplashActivity extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvCopyright;
    Intent intent;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        referWidgets();
        setAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(intent);
            }
        }, Config.TURN_SIGN_IN_DELAY);
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        ivLogo = findViewById(R.id.ivLogo);
    }

    private void setAnimation() {
        final Animation splashScreen = AnimationUtils.loadAnimation(this, R.anim.splash_screen);
        splashScreen.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvCopyright.setText(Config.COPYRIGHT);
                tvCopyright.animate().alpha(1f).setDuration((Config.COPYRIGHT_DELAY));
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        tvCopyright.animate().alpha(0f).setDuration(0);
        ivLogo.startAnimation(splashScreen);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slideout_from_left);
    }
}