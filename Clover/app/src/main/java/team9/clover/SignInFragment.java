package team9.clover;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import org.apache.commons.validator.routines.EmailValidator;


public class SignInFragment extends Fragment {

    TextInputLayout tilEmail, tilPassword;
    MaterialTextView mtvSignUp, mtvForgetPassword;
    MaterialButton mbtSignIn;
    ProgressBar pgbSignIn;
    FrameLayout floLogin;
    FirebaseAuth firebaseAuth;
    String email, password;

    public SignInFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setValues();
        setFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        referWidgets(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEvents();
    }

    private void setValues() {
        email = "";
        password = "";
    }

    private boolean emailValid() {
        email = tilEmail.getEditText().getText().toString();
        tilEmail.setErrorEnabled(true);
        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.email_empty)); email = "";
        } else if (!EmailValidator.getInstance().isValid(email)) {
            tilEmail.setError(getString(R.string.email_invalid)); email = "";
        } else {
            tilEmail.setErrorEnabled(false);
            tilEmail.setError("");
        }

        return !email.isEmpty();
    }

    private boolean passwordValid() {
        password = tilPassword.getEditText().getText().toString();
        tilPassword.setErrorEnabled(true);
        if (password.isEmpty()) {
            tilPassword.setError(getString(R.string.password_empty)); password = "";
        } else if (password.length() < 8) {
            tilPassword.setError(getString(R.string.password_short)); password = "";
        } else if (password.length() > 18) {
            tilPassword.setError(getString(R.string.password_long)); password = "";
        } else {
            tilPassword.setErrorEnabled(false);
            tilPassword.setError("");
        }

        return !password.isEmpty();
    }

    private void referWidgets(View view) {
        tilEmail = view.findViewById(R.id.tilEmail);
        tilPassword = view.findViewById(R.id.tilPassword);
        mtvSignUp = view.findViewById(R.id.mtvSignUp);
        mtvForgetPassword = view.findViewById(R.id.mtvForgetPassword);
        pgbSignIn = view.findViewById(R.id.pgbSignIn);
        mbtSignIn = view.findViewById(R.id.mbtSignIn);
        floLogin = getActivity().findViewById(R.id.floLogin);
    }

    private void setEvents() {
        mbtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailValid() && passwordValid()) {
                    signIn();
                }
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
                emailValid();
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
                passwordValid();
            }
        });

        mtvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtvSignUp.setTypeface(null, Typeface.BOLD);
                setFragment(new SignUpFragment());
            }
        });
    }

    private void signIn() {
        pgbSignIn.setVisibility(View.VISIBLE);
        mbtSignIn.setClickable(false);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
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

    private void errorDialog() {
        pgbSignIn.setVisibility(View.INVISIBLE);
        mbtSignIn.setClickable(true);
        new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog).setTitle(getString(R.string.error_title))
                .setMessage(getString(R.string.sign_in_failure))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }
}