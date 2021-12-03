package team9.nuocsoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.validator.routines.EmailValidator;

import team9.nuocsoi.Model.Customer;
import team9.nuocsoi.Model.User;
import team9.nuocsoi.Module.Config;
import team9.nuocsoi.Module.ReusableCodeForAll;

public class EmailSignInFrame extends AppCompatActivity {

    TextView tvCopyright, tvPreviousFrame, tvForgetPassword;
    TextInputLayout tilEmail, tilPassword;
    Button btnSignInEmail;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_sign_in_frame);
        getSupportActionBar().hide();

        referWidgets();
        setupView();
        setupEventListeners();
        setupFirebase();
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        btnSignInEmail = findViewById(R.id.btnSignInEmail);
    }

    private boolean isValid(String email, String password) {
        ReusableCodeForAll.clearError(tilEmail);
        ReusableCodeForAll.clearError(tilPassword);

        if (!EmailValidator.getInstance().isValid(email)) {
            return ReusableCodeForAll.clearFocisEditText(tilEmail, getString(R.string.email_invalid));
        } else if (password.isEmpty()) {
            return ReusableCodeForAll.clearFocisEditText(tilPassword, getString(R.string.password_empty));
        } else if (password.length() < 8) {
            return ReusableCodeForAll.clearFocisEditText(tilPassword, getString(R.string.password_short));
        } else if (password.length() > 18) {
            return ReusableCodeForAll.clearFocisEditText(tilPassword, getString(R.string.password_long));
        }

        return true;
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
    }

    private void setupFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void setupEventListeners() {
        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnSignInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tilEmail.getEditText().getText().toString().trim();
                String password = tilPassword.getEditText().getText().toString();

                if (isValid(email, password)) {
                    final ProgressDialog mDialog = new ProgressDialog(EmailSignInFrame.this);
                    ReusableCodeForAll.showProgressDialog(mDialog, getString(R.string.sign_in_dialog));

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mDialog.dismiss();
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    firebaseDatabase.getReference(User.class.getSimpleName()).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User user = snapshot.getValue(User.class);
                                            if (user.getRole().equals(Customer.class.getSimpleName())) {
                                                finishAffinity();
                                                Intent intent = new Intent(EmailSignInFrame.this, CustomerHomeFrame.class);
                                                intent.putExtra("user", user);
                                                startActivity(intent);
                                                Toast.makeText(EmailSignInFrame.this, getString(R.string.sign_in_success), Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {
                                    new AlertDialog.Builder(EmailSignInFrame.this)
                                            .setTitle(getString(R.string.email_unverified))
                                            .setMessage(getString(R.string.email_verify_question))
                                            .setCancelable(false)
                                            .setPositiveButton(getString(R.string.btn_accept), new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseAuth.getInstance().signOut();
                                                            Toast.makeText(EmailSignInFrame.this, getString(R.string.email_verify_resend), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            })
                                            .setNegativeButton(getString(R.string.btn_refuse), new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    FirebaseAuth.getInstance().signOut();
                                                    dialog.cancel();
                                                    dialog.dismiss();
                                                }
                                            }).create().show();
                                }
                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.showAlert(EmailSignInFrame.this, getString(R.string.sign_in_failure), getString(R.string.sign_in_failure_dialog));
                            }
                        }
                    });
                }
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailSignInFrame.this, UserForgetPasswordFrame.class));
                finish();
            }
        });
    }
}