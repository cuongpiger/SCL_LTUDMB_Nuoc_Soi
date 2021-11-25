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

import java.util.Calendar;

public class StaringApp extends AppCompatActivity {

    ImageView imgLogo;
    TextView txtCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_page);

        referWidgets();
        setupAnimation();
        turnSignInPage();
    }

    private void referWidgets() {
        imgLogo = findViewById(R.id.imgLogo);
        txtCopyright = findViewById(R.id.txtCopyright);
    }

    private void setupAnimation() {
        imgLogo.animate().alpha(0f).setDuration(0);
        txtCopyright.animate().alpha(0f).setDuration(0);

        imgLogo.animate().alpha(1f).setDuration(Config.LOGO_DELAY).setListener(new AnimatorListenerAdapter() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onAnimationEnd(Animator animator) {
                txtCopyright.setText(String.format(Config.COPYRIGHT, Calendar.getInstance().get(Calendar.YEAR)));
                txtCopyright.animate().alpha(1f).setDuration((Config.COPYRIGHT_DELAY));
            }
        });
    }

    private void turnSignInPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StaringApp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        }, Config.TURN_SIGN_IN_DELAY);
    }
}