package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageView imgLogo;
    TextView txtCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogo = findViewById(R.id.imgLogo);
        txtCopyright = findViewById(R.id.txtCopyright);

        setupStartApp();
        turnMainMenuPage();
    }

    private void turnMainMenuPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        }, Config.TURN_MAIN_MENU_DELAY);
    }

    private void setupStartApp() {
        imgLogo.animate().alpha(0f).setDuration(0);
        txtCopyright.animate().alpha(0f).setDuration(0);

        imgLogo.animate().alpha(1f).setDuration(Config.LOGO_DELAY).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                txtCopyright.setText(String.format("Copyright %d - Team9", Calendar.getInstance().get(Calendar.YEAR)));
                txtCopyright.animate().alpha(1f).setDuration((Config.COPYRIGHT_DELAY));

                Log.d("db", "dg");
            }
        });
    }
}