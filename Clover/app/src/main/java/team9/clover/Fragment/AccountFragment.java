package team9.clover.Fragment;

import static team9.clover.Model.DatabaseModel.firebaseUser;
import static team9.clover.Model.DatabaseModel.masterOrder;
import static team9.clover.Model.DatabaseModel.masterUser;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import team9.clover.MainActivity;
import team9.clover.Model.DatabaseModel;
import team9.clover.R;


public class AccountFragment extends Fragment {

    public static final int ID = 10;
    public static final String NAME = "Account";

    ActionBar actionBar;
    MaterialTextView mEmail, mFullName, mAddress, mPhone;
    ImageButton mEdit1, mEdit2;

    public AccountFragment(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        refer(view);
        setData();
        setEvent();

        return view;
    }

    private void setEvent() {
        mEdit1.setOnClickListener(v -> {
            showEditForm();
        });

        mEdit2.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(getContext(), R.style.ThemeOverlay_App_MaterialAlertDialog).setTitle("Đặt lại mật khẩu")
                    .setMessage("Bạn có muốn đặt lại mật khẩu?")
                    .setCancelable(true)
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseModel.resetPassword(firebaseUser.getEmail());
                            Toast.makeText(getContext(), "Mail đặt lại mật khẩu đã được gửi.", Toast.LENGTH_LONG).show();
                            dialogInterface.dismiss();
                        }
                    }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                    }
            }).create().show();
        });
    }

    private void showEditForm() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_address);

        final TextInputLayout mName, mPhoneNumber, mCity, mDistrict, mVillage, mStreet;
        final MaterialButton mSave;

        mName = dialog.findViewById(R.id.tilFullName);
        mPhoneNumber = dialog.findViewById(R.id.tilPhone);
        mCity = dialog.findViewById(R.id.tilCity);
        mDistrict = dialog.findViewById(R.id.tilDistrict);
        mVillage = dialog.findViewById(R.id.tilVillage);
        mStreet = dialog.findViewById(R.id.tilStreet);
        mSave = dialog.findViewById(R.id.mbSave);

        mName.getEditText().setText(masterUser.getFullName());
        mPhoneNumber.getEditText().setText(masterUser.getPhone());
        mCity.getEditText().setText(masterUser.getAddress().get(3));
        mDistrict.getEditText().setText(masterUser.getAddress().get(2));
        mVillage.getEditText().setText(masterUser.getAddress().get(1));
        mStreet.getEditText().setText(masterUser.getAddress().get(0));

        mSave.setOnClickListener(v -> {
            masterUser.setFullName(mName.getEditText().getText().toString());
            masterUser.setPhone(mPhoneNumber.getEditText().getText().toString());
            masterUser.updateAddress(
                    mStreet.getEditText().getText().toString(),
                            mVillage.getEditText().getText().toString(),
                            mDistrict.getEditText().getText().toString(),
                            mCity.getEditText().getText().toString());

            Toast.makeText(getContext(), "Cập nhật thông tin thành công.", Toast.LENGTH_LONG).show();
            dialog.dismiss();

            mFullName.setText(masterUser.getFullName());
            mPhone.setText(masterUser.getPhone());
            mAddress.setText(String.join(", ", masterUser.getAddress()));
        });

        dialog.show();
    }

    private void setData() {
        mEmail.setText(firebaseUser.getEmail());
        mFullName.setText(masterUser.getFullName());
        mAddress.setText(String.join(", ", masterUser.getAddress()));
        mPhone.setText(masterUser.getPhone());
    }

    private void refer(View view) {
        mEmail = view.findViewById(R.id.mtvEmail);
        mFullName = view.findViewById(R.id.mtvFullName);
        mAddress = view.findViewById(R.id.mtvAddress);
        mPhone = view.findViewById(R.id.mtvPhone);
        mEdit1 = view.findViewById(R.id.ibEdit1);
        mEdit2 = view.findViewById(R.id.ibEdit2);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActionBar();
    }

    private void setActionBar() {
        actionBar.setTitle("Tài khoản");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        MainActivity.toggle.setDrawerIndicatorEnabled(true); // hiển thị hamburger
        MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        MainActivity.actionBarLogo.setVisibility(View.GONE);
        MainActivity.displayActionBarMenu(false);
        MainActivity.displayCategory(false);
    }
}