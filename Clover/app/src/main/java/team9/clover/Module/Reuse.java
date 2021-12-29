package team9.clover.Module;

import android.app.Activity;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.CartItemModel;
import team9.clover.Model.DatabaseModel;
import team9.clover.R;

public class Reuse {

    /*
     * Thiết lập animation khi start một activity
     * */
    public static void startActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /*
     * Dùng để thay đổi các fragment
     * */
    public static void setFragment(FragmentManager fragmentManager, Fragment fragment, FrameLayout layout, int animFrom) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (animFrom == -1) // from left
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
        else if (animFrom == 1) // from right
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);

        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.add(layout.getId(), fragment);
        fragmentTransaction.commit();
    }

    public static void setFragment(FragmentManager manager, Fragment fragment, String name, int layoutId, int animStyle) {
        FragmentTransaction transaction = manager.beginTransaction();

        if (animStyle == 0) transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        else transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

        transaction.replace(layoutId, fragment);
        transaction.addToBackStack(name);
        transaction.commit();
    }

    /*
     * Kiểm tra email user nhập vào có hợp lệ không
     * */
    public static String emailValid(TextInputLayout til, Activity activity) {
        String email = til.getEditText().getText().toString();
        til.setErrorEnabled(true);
        if (email.isEmpty()) {
            til.setError(activity.getString(R.string.email_empty)); email = "";
        } else if (!EmailValidator.getInstance().isValid(email)) {
            til.setError(activity.getString(R.string.email_invalid)); email = "";
        } else {
            til.setErrorEnabled(false);
            til.setError("");
        }

        return email;
    }

    /*
     * Kiểm tra tên user nhập vào có hợp lệ không
     * */
    public static String fullNameValid(TextInputLayout til, Activity activity) {
        String fullName = til.getEditText().getText().toString();
        til.setErrorEnabled(true);
        if (fullName.isEmpty()) {
            til.setError(activity.getString(R.string.name_empty)); fullName = "";
        } else {
            til.setErrorEnabled(false);
            til.setError("");
        }

        return fullName;
    }

    /*
     * Kiểm tra password user nhập vào có hợp lệ không
     * */
    public static String passwordValid(TextInputLayout til, Activity activity) {
        String password = til.getEditText().getText().toString();
        til.setErrorEnabled(true);
        if (password.isEmpty()) {
            til.setError(activity.getString(R.string.password_empty)); password = "";
        } else if (password.length() < 8) {
            til.setError(activity.getString(R.string.password_short)); password = "";
        } else if (password.length() > 18) {
            til.setError(activity.getString(R.string.password_long)); password = "";
        } else {
            til.setErrorEnabled(false);
            til.setError("");
        }

        return password;
    }

    public static String getLastFragmentName(FragmentManager manager) {
        int index = manager.getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(index);
        return backEntry.getName();
    }

    public static String vietnameseCurrency(long price) {
        String m = Long.toString(price);
        String res = "";
        int j = m.length() % 3;
        j = 3 - j + 1;

        for (int i = 0; i < m.length(); ++i) {
            res += m.charAt(i);
            if ((i + j) % 3 == 0 && i + 1 != m.length()) {
                res += ".";
            }
        }

        return res + " đ";
    }

    public static List<String> getCartIds() {
        List<String> res = new ArrayList<>();
        for (CartItemModel cart : DatabaseModel.masterCart) {
            res.add(cart.getId());
        }

        return res;
    }

    public static boolean updateMasterCart(String id, String size, long quantity, CartItemModel newCart) {
        if (newCart != null) {
            DatabaseModel.masterCart.add(newCart);
            return true;
        }

        for (CartItemModel cart : DatabaseModel.masterCart) {
            if (cart.getId().equals(id)) {
                cart.addCart(size, quantity);
                return true;
            }
        }

        return false;
    }
}
