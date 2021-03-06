package team9.nuocsoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import team9.nuocsoi.Model.User;
import team9.nuocsoi.Module.Config;
import team9.nuocsoi.Module.ReusableCodeForAll;

public class CustomerVerifyPhoneFrame extends AppCompatActivity {

    Button btnVerify, btnResend;
    TextView tvCopyright, tvPreviousFrame;
    CountryCodePicker ccpCountry;
    TextInputLayout tilPhone, tilOtp;
    PinView pvOtp;
    String verificationId, userId;
    User customer;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verifiedCallback;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_verify_phone_frame);
        getSupportActionBar().hide();

        referWidgets();
        getValues();
        setupView();
        setupFirebase();
        setupEventListeners();
        setupPhoneVerification();

        startVerificationPhoneNumber(customer.takePhoneNumberWithPlus());
        countDownTimer();
    }

    private void setupPhoneVerification() {
        verifiedCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verify(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) { }

            @Override
            public void onCodeSent(String id, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(id, forceResendingToken);
                resendingToken = forceResendingToken;
                verificationId = id;
                progressDialog.dismiss();
            }
        };
    }

    private void verify(PhoneAuthCredential credential) {
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                firebaseDatabase.getReference(User.class.getSimpleName()).child(userId).setValue(customer);
                firebaseAuth.signOut();
                progressDialog.dismiss();
                finishAffinity();
                Toast.makeText(CustomerVerifyPhoneFrame.this, getString(R.string.toast_sign_up_complete), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CustomerVerifyPhoneFrame.this, SignInFrame.class);
                startActivity(intent);
            }
        });
    }

    private void verifyPhoneNumber(String otpCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);
        verify(credential);
    }

    private void resendVerification(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(CustomerVerifyPhoneFrame.this)
                .setCallbacks(verifiedCallback)
                .setForceResendingToken(resendingToken)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void referWidgets() {
        btnVerify = findViewById(R.id.btnVerify);
        btnResend = findViewById(R.id.btnResend);
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        tilOtp = findViewById(R.id.tilOtp);
        pvOtp = findViewById(R.id.pvOtp);
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
        tilPhone.getEditText().setEnabled(false);
        tilPhone.getEditText().setTextColor(Color.BLACK);
        ccpCountry.setCountryForPhoneCode(Integer.parseInt(customer.getCountry()));
        ccpCountry.setEnabled(false);
        ccpCountry.setCcpClickable(false);
        btnResend.setEnabled(false);
        btnResend.setAlpha(0.5f);

        progressDialog = new ProgressDialog(CustomerVerifyPhoneFrame.this);
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void startVerificationPhoneNumber(String phoneNumber) {
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS).setActivity(CustomerVerifyPhoneFrame.this).setCallbacks(verifiedCallback).build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
        ReusableCodeForAll.showProgressDialog(progressDialog, getString(R.string.check_robot));
    }

    private void countDownTimer() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                btnResend.setText(String.format(getString(R.string.btn_resend_otp_second), l/1000));
            }

            @Override
            public void onFinish() {
                btnResend.setEnabled(true);
                btnResend.setAlpha(1.0f);
                btnResend.setText(getString(R.string.btn_resend_otp));
            }
        }.start();
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
                String otpCode = pvOtp.getText().toString();
                if (otpCode.isEmpty() && otpCode.length() < Config.OTP_LENGTH) {
                    ReusableCodeForAll.clearFocisEditText(tilOtp, getString(R.string.otp_empty));
                } else {
                    ReusableCodeForAll.showProgressDialog(progressDialog, getString(R.string.sign_up_verify_phone));
                    verifyPhoneNumber(otpCode);
                }
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerification(customer.takePhoneNumberWithPlus());
                btnResend.setEnabled(false);
                btnResend.setAlpha(0.5f);
                countDownTimer();
                ReusableCodeForAll.showProgressDialog(progressDialog, getString(R.string.check_robot));
            }
        });
    }
}