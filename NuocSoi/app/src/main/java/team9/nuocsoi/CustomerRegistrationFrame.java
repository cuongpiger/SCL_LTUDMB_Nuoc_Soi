package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.apache.commons.text.WordUtils;

import java.util.Objects;

public class CustomerRegistrationFrame extends AppCompatActivity {

    Button btnSignUp;
    TextView tvPreviousFrame, tvCopyright;
    TextInputEditText edtFullName, edtEmail, edtPassword, edtRetype;
    EditText edtPhone;
    CountryCodePicker ccpCountry;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String fullName, email, password, retype, phone, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration_frame);

        referWidgets();
        setupView();
        setupEventListeners();

    }

    private void getWidgetValues() {
        fullName = WordUtils.capitalize(Objects.requireNonNull(edtFullName.getText()).toString().trim());
        email = Objects.requireNonNull(edtEmail.getText()).toString().trim();
        password = Objects.requireNonNull(edtPassword.getText()).toString();
        retype = Objects.requireNonNull(edtRetype.getText()).toString();
        phone = edtPhone.getText().toString();
    }

    private void referWidgets() {
        btnSignUp = findViewById(R.id.btnSignUp);
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRetype = findViewById(R.id.edtRetype);
        edtPhone = findViewById(R.id.edtPhone);
        ccpCountry = findViewById(R.id.ccpCountry);
    }

    private void setupView() {
        Config.setCopyright(tvCopyright);
    }

    private void setupEventListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerRegistrationFrame.this, fullName, Toast.LENGTH_SHORT).show();

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