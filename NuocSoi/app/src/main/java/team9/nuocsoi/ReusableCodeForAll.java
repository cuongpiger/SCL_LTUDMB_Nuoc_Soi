package team9.nuocsoi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.textfield.TextInputLayout;

public class ReusableCodeForAll {
    public static void showAlert(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.btn_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setTitle(title).setMessage(message).show();
    }

    public static boolean clearFocisEditText(TextInputLayout textInputLayout, String message) {
        textInputLayout.setError(message);
        textInputLayout.getEditText().requestFocus();
        return false;
    }

    public static void showProgressDialog(ProgressDialog mDialog, String message) {
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        mDialog.show();
    }

    public static String genPhoneNumber(String country, String phone) {
        return "+" + country + phone;
    }

    public static void clearError(TextInputLayout textInputLayout) {
        textInputLayout.setErrorEnabled(false);
        textInputLayout.setError("");
    }
}
