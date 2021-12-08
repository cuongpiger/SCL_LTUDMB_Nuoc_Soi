package team9.clover.Fragment;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.validator.routines.EmailValidator;

import team9.clover.R;

public class ResetPasswordFragment extends Fragment {

    TextInputLayout tilEmail;
    MaterialButton mbtResetPassword;
    MaterialTextView mtvSignIn;
    ImageView ivSending;
    TextView tvSending;
    ProgressBar pgbSending;
    FrameLayout floLogin;
    FirebaseAuth firebaseAuth;
    String email;

    public ResetPasswordFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
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
        mbtResetPassword = view.findViewById(R.id.mbtResetPassword);
        mtvSignIn = view.findViewById(R.id.mtvSignIn);
        ivSending = view.findViewById(R.id.ivSending);
        tvSending = view.findViewById(R.id.tvSending);
        pgbSending = view.findViewById(R.id.pgbSending);
        floLogin = getActivity().findViewById(R.id.floLogin);
    }

    private void setFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setEvents() {
        tilEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ivSending.setVisibility(View.GONE);
                tvSending.setVisibility(View.GONE);
                pgbSending.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                emailValid();
            }
        });

        mbtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailValid()) {
                    resetPassword();
                }
            }
        });

        mtvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtvSignIn.setTypeface(null, Typeface.BOLD);
                setFragment(new SignInFragment());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(floLogin.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void resetPassword() {
        setProgressBarSending();
        mbtResetPassword.setClickable(false);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ivSending.setImageResource(R.drawable.ic_baseline_mark_email_read_24);
                    ivSending.setColorFilter(ContextCompat.getColor(getContext(), R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                    tvSending.setText(R.string.recovery_email_sent);
                    tvSending.setTextColor(getResources().getColor(R.color.green));
                    pgbSending.setVisibility(View.GONE);
                    mbtResetPassword.setClickable(true);
                    Toast.makeText(getContext(), "Reset password done", Toast.LENGTH_SHORT).show();
                } else {
                    errorDialog();
                }
            }
        });
    }

    private void setProgressBarSending() {
        ivSending.setImageResource(R.drawable.ic_baseline_attach_email_24);
        ivSending.setColorFilter(ContextCompat.getColor(getContext(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
        tvSending.setText(R.string.recovery_email_sending);
        tvSending.setTextColor(getResources().getColor(R.color.red));

        ivSending.setVisibility(View.VISIBLE);
        tvSending.setVisibility(View.VISIBLE);
        pgbSending.setVisibility(View.VISIBLE);
    }

    private void errorDialog() {
        ivSending.setVisibility(View.GONE);
        tvSending.setVisibility(View.GONE);
        pgbSending.setVisibility(View.GONE);
        mbtResetPassword.setClickable(true);
        new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle(getString(R.string.error_title))
                .setMessage(getString(R.string.recovery_email_failure))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
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
}