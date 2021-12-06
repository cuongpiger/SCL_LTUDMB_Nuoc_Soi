package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

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
        setFragment(new SignInFragment());
    }

    /*
    * Refer to all widget components
    * */
    private void referWidgets() {
        floLogin = findViewById(R.id.floLogin);
        tvCopyright = findViewById(R.id.tvCopyright);
        ibtClose = findViewById(R.id.ibtClose);
    }

    private void setView() {
        tvCopyright.setText(String.format(getString(R.string.copyright), Calendar.getInstance().get(Calendar.YEAR)));
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }
}