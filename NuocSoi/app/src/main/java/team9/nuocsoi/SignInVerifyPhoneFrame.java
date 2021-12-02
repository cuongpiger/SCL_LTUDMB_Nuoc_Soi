package team9.nuocsoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import team9.nuocsoi.Model.User;

public class SignInVerifyPhoneFrame extends AppCompatActivity {

    Button btnVerify, btnResend;
    TextView tvCopyright, tvPreviousFrame;
    CountryCodePicker ccpCountry;
    TextInputLayout tilPhone, tilOtp;
    PinView pvOtp;
    String verificationId, userId, country, phone;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verifiedCallback;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_verify_phone_frame);

        referWidgets();
        getValues();
        setupView();
        setupFirebase();
        setupEventListeners();
        setupPhoneVerification();

        startVerificationPhoneNumber(ReusableCodeForAll.genPhoneNumber(country, phone));
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
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkUserVerifyEmail();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ReusableCodeForAll.clearFocisEditText(tilOtp, "Mã OTP không chính xác!");
            }
        });
    }

    private void checkUserVerifyEmail() {
        firebaseDatabase.getReference(String.format("/%s/%s/userId", Config.PHONE_VERIFICATION, firebaseAuth.getCurrentUser().getUid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String userId = snapshot.getValue().toString();
                            firebaseDatabase.getReference(String.format("/%s/%s/email", User.class.getSimpleName(), userId))
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                String email = snapshot.getValue().toString();
                                                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                                        if (task.isSuccessful()) {
                                                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                                                Toast.makeText(SignInVerifyPhoneFrame.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(SignInVerifyPhoneFrame.this, "Chưa xác nhận email", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
                .setActivity(SignInVerifyPhoneFrame.this)
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
        country = getIntent().getStringExtra("country");
        phone = getIntent().getStringExtra("phone");
        userId = getIntent().getStringExtra("userId");
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
        tilPhone.getEditText().setText(phone);
        tilPhone.getEditText().setEnabled(false);
        tilPhone.getEditText().setTextColor(Color.BLACK);
        ccpCountry.setCountryForPhoneCode(Integer.parseInt(country));
        ccpCountry.setEnabled(false);
        ccpCountry.setCcpClickable(false);
        btnResend.setEnabled(false);
        btnResend.setAlpha(0.5f);

        progressDialog = new ProgressDialog(SignInVerifyPhoneFrame.this);
    }

    private void setupFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void startVerificationPhoneNumber(String phoneNumber) {
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS).setActivity(SignInVerifyPhoneFrame.this).setCallbacks(verifiedCallback).build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
        ReusableCodeForAll.showProgressDialog(progressDialog, "Đang xác thực người máy, bạn đợi tí nha...");
    }

    private void countDownTimer() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                btnResend.setText(String.format("Gửi lại sau %d giây", l/1000));
            }

            @Override
            public void onFinish() {
                btnResend.setEnabled(true);
                btnResend.setAlpha(1.0f);
                btnResend.setText("Gửi lại mã OTP");
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
//                String otpCode = tilOtp.getEditText().getText().toString().trim();
                String otpCode = pvOtp.getText().toString();
                if (otpCode.isEmpty() && otpCode.length() < Config.OTP_LENGTH) {
                    ReusableCodeForAll.clearFocisEditText(tilOtp, "Bạn chưa nhập mã OTP!");
                } else {
                    verifyPhoneNumber(otpCode);
                }
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerification(ReusableCodeForAll.genPhoneNumber(country, phone));
                btnResend.setEnabled(false);
                btnResend.setAlpha(0.5f);
                countDownTimer();
                ReusableCodeForAll.showProgressDialog(progressDialog, "Đang xác thực người máy, bạn đợi tí nha...");
            }
        });
    }
}