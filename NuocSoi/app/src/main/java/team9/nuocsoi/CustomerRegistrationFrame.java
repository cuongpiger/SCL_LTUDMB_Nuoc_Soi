package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerRegistrationFrame extends AppCompatActivity {

    Button btnSignUp;
    TextView tvPreviousFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration_frame);

        referWidgets();
        setupEventListeners();
    }

    private void referWidgets() {
        btnSignUp = findViewById(R.id.btnSignUp);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
    }

    private void setupEventListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerRegistrationFrame.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}