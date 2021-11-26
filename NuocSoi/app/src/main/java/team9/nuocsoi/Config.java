package team9.nuocsoi;

import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Config {
//    public static final int TURN_SIGN_IN_DELAY = 3000; // 3000
//    public static final int COPYRIGHT_DELAY = 800; // 800
//    public static final int LOGO_DELAY = 1000; // 1000
    public static final String COPYRIGHT = String.format("Copyright %d - Team9", Calendar.getInstance().get(Calendar.YEAR));
    public static final String COUNTRY_NAME_CODE = "VN";
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    public static final String FIREBASE_URL = "";

    public static final int IS_CUSTOMER = 0;

    public static final int TURN_SIGN_IN_DELAY = 0; // 3000
    public static final int COPYRIGHT_DELAY = 0; // 800
    public static final int LOGO_DELAY = 0; // 1000

//    public static DatabaseReference setupFirebase(FirebaseDatabase firebaseDatabase, String collection) {
//        firebaseDatabase.get
//    }
}
