package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EmailSignInFrame extends AppCompatActivity {

    TextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_sign_in_frame);

        referWidgets();
        setupView();
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
    }

}