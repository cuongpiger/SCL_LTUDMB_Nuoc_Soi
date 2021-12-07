package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import team9.clover.Module.Config;

public class LoginActivity extends AppCompatActivity {

    FrameLayout floLogin;
    ImageButton ibtClose;
    TextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referWidgets();
        setView();
        setEvents();
        setFragment(new SignInFragment());
    }

    private void referWidgets() {
        floLogin = findViewById(R.id.floLogin);
        tvCopyright = findViewById(R.id.tvCopyright);
        ibtClose = findViewById(R.id.ibtClose);
    }

    private void setView() {
        tvCopyright.setText(Config.COPYRIGHT);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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