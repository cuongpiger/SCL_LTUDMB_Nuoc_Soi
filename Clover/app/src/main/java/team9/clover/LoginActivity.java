package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import team9.clover.Fragment.SignInFragment;
import team9.clover.Module.Config;

public class LoginActivity extends AppCompatActivity {

    FrameLayout floLogin;
    ImageButton ibtClose;
    public static boolean onResetPasswordFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slideout_from_left);
        setContentView(R.layout.activity_login);

        referWidgets();
        setEvents();
        setDefaultFragment(new SignInFragment());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onResetPasswordFragment) {
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slideout_from_left);
    }

    private void referWidgets() {
        floLogin = findViewById(R.id.floLogin);
        ibtClose = findViewById(R.id.ibtClose);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setEvents() {
        ibtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                startActivity(mainIntent);
            }
        });
    }
}