package team9.clover;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import team9.clover.Module.Reusable;


public class SignUpFragment extends Fragment {

    TextInputLayout tilFullName, tilEmail, tilPassword, tiiRetype;
    MaterialTextView mtvSignIn;
    FrameLayout floLogin;

    public SignUpFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mtvSignIn = view.findViewById(R.id.mtvSignIn);
        floLogin = getActivity().findViewById(R.id.floLogin);
    }

    private void setEvents() {
        mtvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}