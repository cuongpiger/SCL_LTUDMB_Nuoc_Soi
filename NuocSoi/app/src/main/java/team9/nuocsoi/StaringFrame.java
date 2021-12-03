package team9.nuocsoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team9.nuocsoi.Model.Customer;
import team9.nuocsoi.Model.User;
import team9.nuocsoi.Module.Config;


public class StaringFrame extends AppCompatActivity {

    ImageView imgLogo;
    TextView tvCopyright;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_frame);
        getSupportActionBar().hide();

        keepInLogin();
        referWidgets();
        setupAnimation();
        turnSignInFrame();
    }

    private void referWidgets() {
        imgLogo = findViewById(R.id.imgLogo);
        tvCopyright = findViewById(R.id.tvCopyright);
    }

    private void setupAnimation() {
        imgLogo.animate().alpha(0f).setDuration(0);
        tvCopyright.animate().alpha(0f).setDuration(0);

        imgLogo.animate().alpha(1f).setDuration(Config.LOGO_DELAY).setListener(new AnimatorListenerAdapter() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onAnimationEnd(Animator animator) {
                tvCopyright.setText(Config.COPYRIGHT);
                tvCopyright.animate().alpha(1f).setDuration((Config.COPYRIGHT_DELAY));
            }
        });
    }

    private void turnSignInFrame() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, Config.TURN_SIGN_IN_DELAY);
    }

    private void keepInLogin() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();;
        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            FirebaseDatabase.getInstance()
                    .getReference(User.class.getSimpleName()).child(firebaseUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if (user.getRole().equals(Customer.class.getSimpleName())) {
                                intent = new Intent(StaringFrame.this, CustomerHomeFrame.class);
                                intent.putExtra("user", user);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        } else {
            intent = new Intent(StaringFrame.this, SignInFrame.class);
        }
    }
}