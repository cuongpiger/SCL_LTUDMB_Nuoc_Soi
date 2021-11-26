package team9.nuocsoi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

public class PhoneSignInFrame extends AppCompatActivity {

    TextView tvCopyright, tvPreviousFrame;
    CountryCodePicker ccpCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_sign_in_frame);

        referWidgets();
        setupView();
        setupEventListeners();
    }

    private void referWidgets() {
        tvCopyright = findViewById(R.id.tvCopyright);
        tvPreviousFrame = findViewById(R.id.tvPreviousFrame);
        ccpCountry = findViewById(R.id.ccpCountry);
    }

    private void setupView() {
        tvCopyright.setText(Config.COPYRIGHT);
        ccpCountry.setCountryForNameCode(Config.COUNTRY_NAME_CODE);
    }

    private void setupEventListeners() {
        tvPreviousFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}