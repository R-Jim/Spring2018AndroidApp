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

/**
 * Created by Swomfire on 15-Mar-18.
 */

public class ItemRequestRVAdapter extends RecyclerView.Adapter<ItemRequestRVAdapter.MyViewHolder> {

    private List<DishInItemList> dishInItemLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemRequestId, itemRequestHeader, itemRequestQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemRequestId = (TextView) itemView.findViewById(R.id.itemRequestId);
            itemRequestHeader = (TextView) itemView.findViewById(R.id.lblItemRequestRowHeader);
            itemRequestQuantity = (TextView) itemView.findViewById(R.id.lblItemRequestRowQuantity);
        }
    }

    public ItemRequestRVAdapter(List<DishInItemList> dishInItemLists) {
        this.dishInItemLists = dishInItemLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DishInItemList dishInItemList = dishInItemLists.get(position);
        holder.itemRequestId.setText(String.valueOf(position));
        holder.itemRequestHeader.setText(dishInItemList.getDish().getName());
        holder.itemRequestQuantity.setText(String.valueOf(dishInItemList.getQuantity()));
    }


    @Override
    public int getItemCount() {
        return dishInItemLists.size();
    }
}


