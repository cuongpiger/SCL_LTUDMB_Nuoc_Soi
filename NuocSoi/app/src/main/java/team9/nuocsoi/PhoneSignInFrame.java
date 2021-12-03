package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class PhoneSignInFrame extends AppCompatActivity {

    TextView tvCopyright, tvPreviousFrame;
    CountryCodePicker ccpCountry;
    TextInputLayout tilPhone;
    Button btnSignInPhone;
    CheckBox cvKeepSignIn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_sign_in_frame);

        referWidgets();
        setupView();
        setupEventListeners();
        setupFirebase();
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        ccpCountry = findViewById(R.id.ccpCountry);
        tilPhone = findViewById(R.id.tilPhone);
        btnSignInPhone = findViewById(R.id.btnSignInPhone);
        cvKeepSignIn = findViewById(R.id.cbKeepSignIn);
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
        ccpCountry.setCountryForNameCode(Config.COUNTRY_NAME_CODE);
    }

    private boolean isValid(String phone) {
        if (phone.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilPhone, getString(R.string.phone_empty));
        } else if (!phone.matches(Config.PHONE_PATTERN)) {
            return ReusableCodeForAll.clearFocisEditText(tilPhone, getString(R.string.phone_unmatched));
        }

        return true;
    }

    private void setupEventListeners() {
        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnSignInPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = tilPhone.getEditText().getText().toString();
                String country = ccpCountry.getSelectedCountryCode();
                if (isValid(phone)) {
                    Intent intent = new Intent(PhoneSignInFrame.this, SignInVerifyPhoneFrame.class);
                    intent.putExtra("country", country);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
            }
        });
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }
}