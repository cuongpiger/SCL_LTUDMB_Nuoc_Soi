package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import team9.nuocsoi.Model.User;

public class CustomerVerifyPhoneFrame extends AppCompatActivity {

    Button btnVerify, btnResend;
    TextView tvCopyright, tvPreviousFrame;
    CountryCodePicker ccpCountry;
    TextInputLayout tilPhone, tilOtp;
    String verifiedId, userId;
    User customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_verify_phone_frame);

        referWidgets();
        getValues();
        setupView();
        setupEventListeners();
    }

    private void referWidgets() {
        btnVerify = findViewById(R.id.btnVerify);
        btnResend = findViewById(R.id.btnResend);
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        tilOtp = findViewById(R.id.tilOtp);
        tilPhone = findViewById(R.id.tilPhone);
        ccpCountry = findViewById(R.id.ccpCountry);
    }

    private void getValues() {
        customer = (User) getIntent().getSerializableExtra("user");
        userId = getIntent().getStringExtra("userId");
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
        tilPhone.getEditText().setText(customer.getPhone());
        ccpCountry.setCountryForPhoneCode(Integer.parseInt(customer.getCountry()));
        btnResend.setEnabled(false);
        btnResend.setAlpha(0.5f);
    }

    private void setupEventListeners() {
        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                onBackPressed();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}