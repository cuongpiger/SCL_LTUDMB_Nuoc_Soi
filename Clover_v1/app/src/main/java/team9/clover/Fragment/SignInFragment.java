package team9.clover.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.textview.MaterialTextView;

import team9.clover.LogInActivity;
import team9.clover.Module.Reuse;
import team9.clover.R;

public class SignInFragment extends Fragment {

    FrameLayout mContainer;
    MaterialTextView mSignUpFragment, mForgetPassword;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInActivity.currentFragment = SignInFragment.class.getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        refer(view);
        setEvents();

        return view;
    }

    /*
    * Tham chiếu đến các component của activity
    * */
    private void refer(View view) {
        mContainer = view.findViewById(R.id.flContainer);
        mForgetPassword = view.findViewById(R.id.mtvForgetPassword);
        mSignUpFragment = view.findViewById(R.id.mtvSignUp);
    }

    private void setEvents() {
        /*
        * Xử lí sự kiện user nhấn nút đăng ký tài khoản
        * */
        mSignUpFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignUpFragment.setTypeface(null, Typeface.BOLD); // bold text
                Reuse.setFragment(getActivity(), new SignUpFragment(), mContainer, 1);
            }
        });

        /*
        * Xử lí sự kiện user nhấn nút forget password
        * */
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mForgetPassword.setTypeface(null, Typeface.BOLD); // bold text
                Reuse.setFragment(getActivity(), new ForgetPasswordFragment(), mContainer, 1);
            }
        });
    }
}