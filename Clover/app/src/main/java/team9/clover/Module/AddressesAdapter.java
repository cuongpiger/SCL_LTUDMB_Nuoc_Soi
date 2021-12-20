package team9.clover.Module;

import static team9.clover.DeliveryActivity.SELECT_ADDRESS;
import static team9.clover.Fragment.MyAccountFragment.MANAGE_ADDRESS;
import static team9.clover.MyAddressActivity.refreshItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.AddressesMOdel;
import team9.clover.R;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    List<AddressesMOdel> addressesMOdelList;
    int preSelectedPosition;
    int MODE;

    public AddressesAdapter(List<AddressesMOdel> addressesMOdelList, int MODE) {
        this.addressesMOdelList = addressesMOdelList;
        this.MODE = MODE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressesMOdel item = addressesMOdelList.get(position);
        String name = item.getFullname(),
                address = item.getAddress(),
                phone = item.getPhone();
        Boolean selected = item.getSelected();

        holder.setData(name, address, phone, selected, position);
    }

    @Override
    public int getItemCount() {
        return addressesMOdelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView fullname, address, phone;
        ImageView icon_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            icon_view = itemView.findViewById(R.id.icon_view);
        }

        private void setData(String username, String userAddress, String userPhone, Boolean selected, int position) {
            fullname.setText(username);
            address.setText(userAddress);
            phone.setText(userPhone);

            if (MODE == SELECT_ADDRESS) {
                icon_view.setImageResource(R.drawable.empty_start);

                if (selected) {
                    icon_view.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                } else  {
                    icon_view.setVisibility(View.GONE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (preSelectedPosition != position){
                            addressesMOdelList.get(position).setSelected(true);
                            addressesMOdelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, position);
                            preSelectedPosition = position;
                        }
                    }
                });
            } else if (MODE == MANAGE_ADDRESS) {

            }
         }
    }
}
