package team9.clover.Module;

import java.util.Calendar;

import team9.clover.R;

public class Config {
    public static final int TURN_SIGN_IN_DELAY = 0; // 3000
    public static final int COPYRIGHT_DELAY = 0; // 800
    public static final int LOGO_DELAY = 1000; // 1000
    public static final int BANNER_SLIDER_DELAY = 3000;
    public static final int BANNER_SLIDER_PERIOD = 3000;
    public static final int PADDING_ICON_DRAWABLE = 15;
    public static final String COPYRIGHT = String.format("Copyright %d - Team9", Calendar.getInstance().get(Calendar.YEAR));
}
