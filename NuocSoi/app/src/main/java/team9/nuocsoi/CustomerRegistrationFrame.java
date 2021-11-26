package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.apache.commons.text.WordUtils;

import java.util.Objects;

import team9.nuocsoi.Model.User;

public class CustomerRegistrationFrame extends AppCompatActivity {

    Button btnSignUp;
    TextView tvPreviousFrame, tvCopyright;
    EditText edtPhone;
    CountryCodePicker ccpCountry;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String fullName, email, password, retype, phone, country;
    TextInputLayout tilFullName, tilEmail, tilPassword, tilRetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_registration_frame);

        referWidgets();
        setupView();
        setupEventListeners();
        setupFirebase();
    }

    private void setupFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private boolean isValid() {
//        edtEmail.setErrorEnabled();


        return false;
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        tilFullName = findViewById(R.id.tilFullName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilRetype = findViewById(R.id.tilRetype);
        ccpCountry = findViewById(R.id.ccpCountry);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
        ccpCountry.setCountryForNameCode(Config.COUNTRY_NAME_CODE);
    }

    private void setupEventListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = WordUtils.capitalize(Objects.requireNonNull(tilFullName.getEditText()).getText().toString().trim());
                email = Objects.requireNonNull(tilEmail.getEditText()).getText().toString().trim();
                password = Objects.requireNonNull(tilPassword.getEditText()).getText().toString();
                retype = Objects.requireNonNull(tilRetype.getEditText()).getText().toString();
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