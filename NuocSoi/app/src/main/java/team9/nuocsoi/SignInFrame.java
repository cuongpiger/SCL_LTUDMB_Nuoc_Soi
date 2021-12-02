package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SignInFrame extends AppCompatActivity {

    Button btnSignInEmail, btnSignInPhone, btnSignUp;
    ImageView imgBackground;
    TextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_frame);

        referWidgets();
        setupView();
        setupAnimation();
        setupEventListeners();
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
    }

    private void referWidgets() {
        imgBackground = findViewById(R.id.imgBackground);
        tvCopyright = findViewById(R.id.txtCopyright);
        btnSignInEmail = findViewById(R.id.btnSignInEmail);
        btnSignInPhone = findViewById(R.id.btnSignInPhone);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setupAnimation() {
        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        imgBackground.setAnimation(zoomin);
        imgBackground.setAnimation(zoomout);

        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgBackground.startAnimation(zoomin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgBackground.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setupEventListeners() {
        btnSignInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInEmail = new Intent(SignInFrame.this, EmailSignInFrame.class);
                signInEmail.putExtra("Home", "Email");
                startActivity(signInEmail);
//                finish();
            }
        });

        btnSignInPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInPhone = new Intent(SignInFrame.this, PhoneSignInFrame.class);
                signInPhone.putExtra("Home", "Phone");
                startActivity(signInPhone);
//                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignInFrame.this, CustomerRegistrationFrame.class);
                signUp.putExtra("Home", "SignUp");
                startActivity(signUp);
//                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}