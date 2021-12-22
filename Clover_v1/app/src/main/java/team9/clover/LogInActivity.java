package team9.clover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import team9.clover.Fragment.SignInFragment;
import team9.clover.Fragment.SignUpFragment;
import team9.clover.Module.Reuse;

public class LogInActivity extends AppCompatActivity {

    ImageButton mClose;
    FrameLayout mContainer;
    public static String currentFragment = SignInFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        refer();
        Reuse.setFragment(LogInActivity.this, new SignInFragment(), mContainer, 0);

    }

    @Override
    public void onBackPressed() {
        if (currentFragment.equals(SignUpFragment.class.getSimpleName())) {
            Reuse.setFragment(LogInActivity.this, new SignInFragment(), mContainer, -1);
        } else if (currentFragment.equals(SignInFragment.class.getSimpleName())){
            currentFragment = null;
            Toast.makeText(this, getString(R.string.on_back_press), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    private void refer() {
        mClose = findViewById(R.id.ibClose);
        mContainer = findViewById(R.id.flContainer);
    }
}