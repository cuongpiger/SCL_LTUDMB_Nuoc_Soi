package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class MainMenu extends AppCompatActivity {

    Button btnSignInEmail, btnSignInPhone, btnSignUp;
    ImageView imgBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        imgBackground = findViewById(R.id.imgBackground);
        btnSignInEmail = findViewById(R.id.btnSignInEmail);
        btnSignInPhone = findViewById(R.id.btnSignInPhone);
        btnSignUp = findViewById(R.id.btnSignUp);

        setupAtStart();
//        setupEventListeners();
    }

//    private void setupEventListeners() {
//        btnSignInEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent signInEmail = new Intent(MainMenu.this, ChooseOne.class);
//                signInEmail.putExtra("Home", "Email");
//                startActivity(signInEmail);
//                finish();
//            }
//        });
//
//        btnSignInPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent signInPhone = new Intent(MainMenu.this, ChooseOne.class);
//                signInPhone.putExtra("Home", "Phone");
//                startActivity(signInPhone);
//                finish();
//            }
//        });
//
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent signUp = new Intent(MainMenu.this, ChooseOne.class);
//                signUp.putExtra("Home", "SignUp");
//                startActivity(signUp);
//                finish();
//            }
//        });
//    }

    private void setupAtStart() {
        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        System.gc();
//    }
}