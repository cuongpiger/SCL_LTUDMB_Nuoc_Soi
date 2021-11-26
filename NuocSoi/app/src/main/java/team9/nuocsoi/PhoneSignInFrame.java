package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PhoneSignInFrame extends AppCompatActivity {

    TextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_sign_in_frame);

        referWidgets();
        setupView();
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
    }

    private void setupView() {
        Config.setCopyright(tvCopyright);
    }
}