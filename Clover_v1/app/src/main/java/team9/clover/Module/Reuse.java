package team9.clover.Module;

import android.app.Activity;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import team9.clover.R;

public class Reuse {
    public static void startActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /*
    * Dùng để thay đổi các fragment
    * */
    public static void setFragment(FragmentActivity activity, Fragment fragment, FrameLayout layout, int animFrom) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (animFrom == -1) // from left
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        else if (animFrom == 1) // from right
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        fragmentTransaction.replace(layout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
