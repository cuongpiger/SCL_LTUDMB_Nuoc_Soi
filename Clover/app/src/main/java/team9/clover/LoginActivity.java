package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class LoginActivity extends AppCompatActivity {

    FrameLayout floLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referWidgets();
        setView(new SignInFragment());
    }

    /*
    * Refer to all widget components
    * */
    private void referWidgets() {
        floLogin = findViewById(R.id.floLogin);
    }

    private void setView(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }
}