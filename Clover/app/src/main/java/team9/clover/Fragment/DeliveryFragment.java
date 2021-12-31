package team9.clover.Fragment;

import static team9.clover.Model.DatabaseModel.masterOrder;
import static team9.clover.Model.DatabaseModel.masterUser;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

import team9.clover.MainActivity;
import team9.clover.Model.CartItemModel;
import team9.clover.Model.DatabaseModel;
import team9.clover.Module.Reuse;
import team9.clover.R;

public class DeliveryFragment extends Fragment {

    public static final int ID = 7;
    public static final String NAME = "Delivery";
    
    ActionBar actionBar;
    MaterialTextView mNoProducts, mOriTotal, mDelivery, mTotal;
    MaterialTextView mFullName, mPhone, mAddress;
    ImageButton mEdit;
    MaterialButton mCheck, mGoHone;
    ConstraintLayout mSuccess;


    public DeliveryFragment(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);

        refer(view);
        setData();
        setEvent();

        return view;
    }

    private void setEvent() {
        mEdit.setOnClickListener(v -> {
            showChooseForm();
        });

        /*
        * User nhấn vào button Hoàn tất để xác nhận mua hàng
        * */
        mCheck.setOnClickListener(v -> {
            masterOrder.addDate(Reuse.getCurrentDate()); // thêm ngày hiện tại làm ngày đặt hàng
            masterOrder.setFullName(mFullName.getText().toString().trim());
            masterOrder.setPhone(mPhone.getText().toString().trim());
            masterOrder.setAddress(mAddress.getText().toString().trim());
            masterOrder.setId(masterUser.getOrder());
            masterOrder.setOrder(new Timestamp(new Date()));

            ProgressDialog pd = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
            pd.setMessage("Đang xử lý...");
            pd.show();
            DatabaseModel.updateMasterOrder().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DatabaseModel.addNewOrder().addOnSuccessListener(task1 -> {
                            pd.dismiss();
                            actionBar.setDisplayHomeAsUpEnabled(false);
                            mSuccess.setVisibility(View.VISIBLE);
                            masterUser.setOrder(task1.getId());
                            DatabaseModel.updateMasterUser();
                            DatabaseModel.masterCart = new ArrayList<>();
                        });
                    }
                }
            });
        });

        mGoHone.setOnClickListener(v -> {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Intent intent = new Intent("broadcast");
            intent.putExtra(SpecificProductFragment.NAME, 0);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        });
    }

    private void showChooseForm() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_address);

//        final Spinner spinner = dialog.findViewById(R.id.spSize);
//        final EditText editText = dialog.findViewById(R.id.etQuantity);
//        final MaterialButton button = dialog.findViewById(R.id.mbConfirm);
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, productModel.getSize());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSize = spinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                quantity = Integer.parseInt(editText.getText().toString());
//                if (quantity > 0) {
//                    dialog.dismiss();
//                    Toast.makeText(getContext(), "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_LONG).show();
//                    isChanged = true;
//
//                    if (!Reuse.updateMasterCart(productModel.getId(), selectedSize, (long) quantity, null)) {
//                        CartItemModel newCart = new CartItemModel(
//                                productModel.getId(),
//                                productModel.getTitle(),
//                                productModel.getImage().get(0),
//                                productModel.getPrice(), selectedSize, (long) quantity);
//
//                        Reuse.updateMasterCart(productModel.getId(), selectedSize, (long) quantity, newCart);
//                    }
//                } else {
//                    editText.setError("> 0");
//                }
//            }
//        });

        dialog.show();
    }

    private void setData() {
        mNoProducts.setText(masterOrder.getNoProducts() + " sản phẩm");
        mTotal.setText(Reuse.vietnameseCurrency(masterOrder.getTotal()));
        mOriTotal.setText(mTotal.getText());
        mFullName.setText(masterUser.getFullName());
        mPhone.setText(masterUser.getPhone());
        mAddress.setText(String.join(", ", masterUser.getAddress()));
    }

    private void refer(View view) {
        mNoProducts = view.findViewById(R.id.mtvNoProduct);
        mOriTotal = view.findViewById(R.id.mtvTotal);
        mDelivery = view.findViewById(R.id.mtvDelivery);
        mTotal = view.findViewById(R.id.mtvTotalPrice);
        mFullName = view.findViewById(R.id.mtvFullName);
        mPhone = view.findViewById(R.id.mtvPhone);
        mAddress = view.findViewById(R.id.mtvAddress);
        mEdit = view.findViewById(R.id.ibEdit);
        mCheck = view.findViewById(R.id.mbCheck);
        mSuccess = view.findViewById(R.id.clSuccess);
        mGoHone = view.findViewById(R.id.mbGoHome);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActionBar();
    }

    private void setActionBar() {
        actionBar.setTitle("Thanh toán");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        MainActivity.toggle.setDrawerIndicatorEnabled(false);
        MainActivity.actionBarLogo.setVisibility(View.GONE);
        MainActivity.displayActionBarMenu(false);
        MainActivity.displayCategory(false);
    }
}