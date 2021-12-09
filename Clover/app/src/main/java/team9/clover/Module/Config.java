package team9.clover.Module;

import java.util.Calendar;

import team9.clover.R;

public class Config {
    public static final int TURN_SIGN_IN_DELAY = 3000; // 3000
    public static final int COPYRIGHT_DELAY = 800; // 800
    public static final int LOGO_DELAY = 1000; // 1000
    public static final String COPYRIGHT = String.format("Copyright %d - Team9", Calendar.getInstance().get(Calendar.YEAR));
}
