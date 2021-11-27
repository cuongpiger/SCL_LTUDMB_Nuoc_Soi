package team9.nuocsoi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ReusableCodeForAll {
    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setTitle(title).setMessage(message).show();
    }
}
