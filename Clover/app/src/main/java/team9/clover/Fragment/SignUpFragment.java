package team9.clover.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import team9.clover.LogInActivity;
import team9.clover.MainActivity;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.UserModel;
import team9.clover.Module.Reuse;
import team9.clover.R;

public class SignUpFragment extends Fragment {

    FrameLayout mContainer;
    MaterialTextView mSignInFragment;
    TextInputLayout mEmail, mFullName, mPassword;
    ProgressBar mCircle;
    MaterialButton mSignUp;

    String email = "", fullName = "", password = "";

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInActivity.currentFragment = SignUpFragment.class.getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        refer(view);
        setEvents();

        return view;
    }

    private void refer(View view) {
        mContainer = view.findViewById(R.id.flContainer);
        mSignInFragment = view.findViewById(R.id.mtvSignIn);
        mEmail = view.findViewById(R.id.tilEmail);
        mFullName = view.findViewById(R.id.tilFullName);
        mPassword = view.findViewById(R.id.tilPassword);
        mCircle = view.findViewById(R.id.pbCircle);
        mSignUp = view.findViewById(R.id.mbResetPassword);
    }

    private void setEvents() {
        /*
         * X??? l?? s??? ki???n user nh???n n??t quay v??? trang fragment ????ng nh???p
         * */
        mSignInFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignInFragment.setTypeface(null, Typeface.BOLD); // bold text
                Reuse.setFragment(getActivity().getSupportFragmentManager(), new SignInFragment(), mContainer, -1);
            }
        });

        /*
         * X??? l?? s??? ki???n user nh???p email ????? ????ng k??
         * */
        mEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = Reuse.emailValid(mEmail, getActivity());
            }
        });

        /*
         * X??? l?? s??? ki???n user nh???p t??n m??nh v??o ????? ????ng k??
         * */
        mFullName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                fullName = Reuse.fullNameValid(mFullName, getActivity());
            }
        });

        /*
         * X??? l?? s??? ki???n user nh???p password ????? ????ng k??
         * */
        mPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = Reuse.passwordValid(mPassword, getActivity());
            }
        });

        /*
         * X??? l?? s??? ki???n user nh???n nut ????ng k?? ng?????i d??ng m???i
         * */
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = Reuse.emailValid(mEmail, getActivity());
                fullName = Reuse.fullNameValid(mFullName, getActivity());
                password = Reuse.passwordValid(mPassword, getActivity());
                if (email.isEmpty() || fullName.isEmpty() || password.isEmpty()) return;
                addNewUser();
            }
        });
    }

    /*
     * ????ng k?? new user v?? th??m th??ng tin user v??o FireStore
     * */
    private void addNewUser() {
        mSignUp.setClickable(false);
        mCircle.setVisibility(View.VISIBLE);

        UserModel newUser = new UserModel(fullName, email);
        DatabaseModel.signUp(email, password).addOnCompleteListener(new OnCompleteListener() { // ?????ng k?? t??i kho???n
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    DatabaseModel.addNewUser(newUser).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                getActivity().finish();
                                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                                Reuse.startActivity(getActivity());

                            } else {
                                errorDialog();
                            }
                        }
                    });
                } else {
                    errorDialog();
                }
            }
        });
    }

    /*
     * Hi???n th??? dialog c???nh b???o cho user l?? ????ng k?? kh??ng th??nh c??ng
     * */
    private void errorDialog() {
        mSignUp.setClickable(true);
        mCircle.setVisibility(View.INVISIBLE);

        new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog).setTitle("????ng k?? th???t b???i")
                .setMessage("B???n vui l??ng ki???m tra l???i gi??p m??nh nha.")
                .setCancelable(false)
                .setPositiveButton("????ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
}