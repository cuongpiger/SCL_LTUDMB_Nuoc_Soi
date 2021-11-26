package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EmailSignInFrame extends AppCompatActivity {

    TextView tvCopyright, tvPreviousFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_sign_in_frame);

        referWidgets();
        setupView();
        setupEventListeners();
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
    }

    private void setupEventListeners() {
        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}