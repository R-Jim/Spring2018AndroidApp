package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import day01.swomfire.restaurantapp.R;
import model.DishInItemList;
import model.DishInReceipt;

/**
 * Created by Swomfire on 15-Mar-18.
 */

public class OrderDetailRVAdapter extends RecyclerView.Adapter<OrderDetailRVAdapter.MyViewHolder> {

    private List<DishInReceipt> dishInReceipts;
    private int layoutId;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemPosition, itemName, itemQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemPosition = (TextView) itemView.findViewById(R.id.itemOrderDetailId);
            this.itemName = (TextView) itemView.findViewById(R.id.itemOrderDetailName);
            this.itemQuantity = (TextView) itemView.findViewById(R.id.itemOrderDetailQuantity);
        }
    }

    public OrderDetailRVAdapter(List<DishInReceipt> dishInReceipts, int layoutId) {
        this.dishInReceipts = dishInReceipts;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DishInReceipt dishInReceipt = dishInReceipts.get(position);
        holder.itemName.setText(dishInReceipt.getDish().getItemName());
        if (holder.itemPosition != null) {
            holder.itemPosition.setText(String.valueOf(position));
        }
        holder.itemQuantity.setText(String.valueOf(dishInReceipt.getQuantity()));
    }


    @Override
    public int getItemCount() {
        return dishInReceipts.size();
    }
}


