package team9.clover.Module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.AddressesMOdel;
import team9.clover.R;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    List<AddressesMOdel> addressesMOdelList;

    public AddressesAdapter(List<AddressesMOdel> addressesMOdelList) {
        this.addressesMOdelList = addressesMOdelList;
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

        holder.setData(name, address, phone);
    }

    @Override
    public int getItemCount() {
        return addressesMOdelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView fullname, address, phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
        }

        private void setData(String username, String userAddress, String userPhone) {
            fullname.setText(username);
            address.setText(userAddress);
            phone.setText(userPhone);
        }
    }
}
