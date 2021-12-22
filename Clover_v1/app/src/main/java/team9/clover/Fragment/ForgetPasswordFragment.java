package team9.clover.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.textview.MaterialTextView;

import team9.clover.LogInActivity;
import team9.clover.Module.Reuse;
import team9.clover.R;


public class ForgetPasswordFragment extends Fragment {

    FrameLayout mContainer;
    MaterialTextView mSignInFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInActivity.currentFragment = ForgetPasswordFragment.class.getSimpleName();
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
    }

    private void setEvents() {
        mSignInFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignInFragment.setTypeface(null, Typeface.BOLD); // bold text
                Reuse.setFragment(getActivity(), new SignInFragment(), mContainer, -1);
            }
        });
    }
}