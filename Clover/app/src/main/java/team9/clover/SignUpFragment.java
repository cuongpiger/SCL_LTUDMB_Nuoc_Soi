package team9.clover;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.text.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collections;


public class SignUpFragment extends Fragment {

    TextInputLayout tilFullName, tilEmail, tilPassword, tilRetype;
    MaterialButton mbtSignUp;
    MaterialTextView mtvSignIn;
    FrameLayout floLogin;
    ProgressBar pgbSignUp;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    public SignUpFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        referWidgets(view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvents();
    }

    private void referWidgets(View view) {
        tilEmail = view.findViewById(R.id.tilEmail);
        tilFullName = view.findViewById(R.id.tilFullName);
        tilPassword = view.findViewById(R.id.tilPassword);
        tilRetype = view.findViewById(R.id.tilRetype);
        mtvSignIn = view.findViewById(R.id.mtvSignIn);
        mbtSignUp = view.findViewById(R.id.mbtSignUp);
        pgbSignUp = view.findViewById(R.id.pgbSignUp);
        floLogin = getActivity().findViewById(R.id.floLogin);
    }

    private void setEvents() {
        mtvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        tilEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = tilEmail.getEditText().getText().toString();
                tilEmail.setErrorEnabled(true);
                if (email.isEmpty()) {
                    tilEmail.setError(getString(R.string.email_empty));
                } else if (!EmailValidator.getInstance().isValid(email)) {
                    tilEmail.setError(getString(R.string.email_invalid));
                } else {
                    tilEmail.setErrorEnabled(false);
                    tilEmail.setError("");
                }
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
                String fullName = tilFullName.getEditText().getText().toString();
                tilFullName.setErrorEnabled(true);
                if (fullName.isEmpty()) {
                    tilFullName.setError(getString(R.string.name_empty));
                } else {
                    tilFullName.setErrorEnabled(false);
                    tilFullName.setError("");
                }
            }
        });

        tilPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = tilPassword.getEditText().getText().toString();
                tilPassword.setErrorEnabled(true);
                if (password.isEmpty()) {
                    tilPassword.setError(getString(R.string.password_empty));
                } else if (password.length() < 8) {
                    tilPassword.setError(getString(R.string.password_short));
                } else if (password.length() > 18) {
                    tilPassword.setError(getString(R.string.password_long));
                } else {
                    tilPassword.setErrorEnabled(false);
                    tilPassword.setError("");
                }
            }
        });

        tilRetype.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = tilPassword.getEditText().getText().toString();
                String retype = tilRetype.getEditText().getText().toString();
                tilRetype.setErrorEnabled(true);
                if (retype.isEmpty()) {
                    tilRetype.setError(getString(R.string.retype_empty));
                } else if (!retype.equals(password)) {
                    tilRetype.setError(getString(R.string.retype_invalid));
                } else {
                    tilRetype.setErrorEnabled(false);
                    tilRetype.setError("");
                }
            }
        });

        mbtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(tilEmail.isErrorEnabled() && tilFullName.isErrorEnabled() && tilPassword.isErrorEnabled() && tilRetype.isErrorEnabled())) {
                    signUp();
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void addNewUser() {
        firebaseFirestore.collection("User")
                .add(Collections.singletonMap("fullName", WordUtils.capitalize(tilFullName.getEditText().getText().toString().trim())))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                            getActivity().finish();
                            startActivity(mainIntent);
                        } else {
                            errorDialog();
                        }
                    }
                });
    }

    private void signUp() {
        pgbSignUp.setVisibility(View.VISIBLE);
        mbtSignUp.setClickable(false);
        String email = tilEmail.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addNewUser();
                } else {
                    errorDialog();
                }
            }
        });
    }

    private void errorDialog() {
        pgbSignUp.setVisibility(View.INVISIBLE);
        mbtSignUp.setClickable(true);
        new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog).setTitle(getString(R.string.error_title))
                .setMessage(getString(R.string.sign_up_failure))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
}