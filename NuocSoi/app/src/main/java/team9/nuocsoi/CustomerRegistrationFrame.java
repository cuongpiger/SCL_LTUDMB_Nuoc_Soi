package team9.nuocsoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.apache.commons.text.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collections;
import java.util.Objects;

import team9.nuocsoi.Model.Customer;
import team9.nuocsoi.Model.User;

public class CustomerRegistrationFrame extends AppCompatActivity {

    Button btnSignUp;
    TextView tvPreviousFrame, tvCopyright;
    CountryCodePicker ccpCountry;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String fullName, email, password, retype, phone, country;
    TextInputLayout tilFullName, tilEmail, tilPassword, tilRetype, tilPhone;

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
        Config.clearError(tilEmail);
        Config.clearError(tilFullName);
        Config.clearError(tilPassword);
        Config.clearError(tilRetype);
        Config.clearError(tilPhone);

        if (fullName.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilFullName, "Bạn chưa nhập tên!");
        } else if (email.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilEmail, "Bạn chưa nhập email!");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            return ReusableCodeForAll.clearFocisEditText(tilEmail, "Email chưa hợp lệ!");
        } else if (password.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilPassword, "Bạn chưa nhập mật khẩu!");
        } else if (password.length() < 8) {
            return ReusableCodeForAll.clearFocisEditText(tilPassword, "Mật khẩu phải hơn 8 kí tự!");
        } else if (password.length() > 18) {
            return ReusableCodeForAll.clearFocisEditText(tilPassword, "Mật khẩu phải dưới 18 kí tự!");
        } else if (retype.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilRetype, "Bạn chưa xác thực mật khẩu!");
        } else if (!retype.equals(password)) {
            return ReusableCodeForAll.clearFocisEditText(tilRetype, "Mật khẩu xác thực chưa đúng!");
        } else if (phone.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilPhone, "Bạn chưa nhập SĐT!");
        } else if (!phone.matches(Config.PHONE_PATTERN)) {
            return ReusableCodeForAll.clearFocisEditText(tilPhone, "SĐT chưa hợp lệ!");
        }

        return true;
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        tilFullName = findViewById(R.id.tilFullName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilRetype = findViewById(R.id.tilRetype);
        tilPhone = findViewById(R.id.tilPhone);
        ccpCountry = findViewById(R.id.ccpCountry);
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
                fullName = Objects.requireNonNull(tilFullName.getEditText()).getText().toString().trim();
                email = Objects.requireNonNull(tilEmail.getEditText()).getText().toString().trim();
                password = Objects.requireNonNull(tilPassword.getEditText()).getText().toString();
                retype = Objects.requireNonNull(tilRetype.getEditText()).getText().toString();
                phone = Objects.requireNonNull(tilPhone.getEditText()).getText().toString();
                country = ccpCountry.getSelectedCountryNameCode();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(CustomerRegistrationFrame.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Đang đăng kí tài khoản.\nBạn chờ mình xíu nha...");
                    mDialog.show();

                    // https://stackoverflow.com/questions/39866086/change-password-with-firebase-for-android
                    // https://stackoverflow.com/questions/40093781/check-if-given-email-exists
                    Customer customer = new Customer(fullName, phone, country, email, password);
                    firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.getResult().getSignInMethods().isEmpty()) {
                                /* New customer */
                                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            databaseReference = firebaseDatabase.getReference(User.class.getSimpleName()).child(userId);
                                            databaseReference.setValue(Collections.singletonMap("role", Customer.class.getSimpleName()));
                                            databaseReference = firebaseDatabase.getReference(Customer.class.getSimpleName()).child(userId);
                                            databaseReference.setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    mDialog.dismiss();
                                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
//                                                    ReusableCodeForAll.showAlert(CustomerRegistrationFrame.this, "Đăng ký thành công", "Kiểm tra email của bạn và xác nhận đăng ký giúp mình nhé.");
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrationFrame.this);
                                                                builder.setTitle("Đăng ký thành công").
                                                                        setMessage("Kiểm tra email của bạn và xác nhận đăng ký giúp mình nhé.").
                                                                        setCancelable(false).setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                        CustomerRegistrationFrame.this.finish();
                                                                        onBackPressed();
                                                                    }
                                                                });

                                                                builder.create().show();
                                                            } else {
                                                                ReusableCodeForAll.showAlert(CustomerRegistrationFrame.this, "Có tí trục trặc rồi...", task.getException().getMessage());
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        } else {
                                            mDialog.dismiss();
                                            ReusableCodeForAll.showAlert(CustomerRegistrationFrame.this, "Có tí trục trặc rồi...", task.getException().getMessage());
                                        }
                                    }
                                });
                            } else {
                                mDialog.dismiss();
                                tilEmail.setError("Email này đã được sử dụng!");
                                tilEmail.getEditText().requestFocus();
                            }
                        }
                    });
                }
            }
        });

        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                onBackPressed();
            }
        });

        tilFullName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String fname = editable.toString();
                String cname = WordUtils.capitalize(fname);
                EditText editText = tilFullName.getEditText();

                if(!fname.equals(cname)) {
                    editText.setText(cname);
                    editText.setSelection(editText.length());
                }
            }
        });
    }
}