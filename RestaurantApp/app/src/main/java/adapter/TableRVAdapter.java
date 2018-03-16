package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import day01.swomfire.restaurantapp.R;
import model.Table;

/**
 * Created by Swomfire on 15-Mar-18.
 */

public class TableRVAdapter extends RecyclerView.Adapter<TableRVAdapter.MyViewHolder> {
    public static enum viewType {
        TABLE_FREE(0), TABLE_ORDERING(1);

        private int viewType;

        viewType(int viewType) {
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }


    private List<Table> tables;

    @Override
    public int getItemViewType(int position) {
        if (tables.get(position).isStatus()) {
            return viewType.TABLE_FREE.getViewType();
        } else {
            return viewType.TABLE_ORDERING.getViewType();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        private TextView itemRequestId, itemRequestHeader, itemRequestQuantity;

        public MyViewHolder(View tableView) {
            super(tableView);
/*            itemRequestId = (TextView) itemView.findViewById(R.id.itemRequestId);
            itemRequestHeader = (TextView) itemView.findViewById(R.id.lblItemRequestRowHeader);
            itemRequestQuantity = (TextView) itemView.findViewById(R.id.lblItemRequestRowQuantity);*/
        }
    }

    public TableRVAdapter(List<Table> tables) {
        this.tables = tables;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tableView = null;
        switch (viewType) {
            case 0:
                tableView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.table_list_row_free, parent, false);
                break;
            case 1:
                tableView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.table_list_row_ordering, parent, false);
                break;
        }

        return new MyViewHolder(tableView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
/*        DishInItemList dishInItemList = dishInItemLists.get(position);
        holder.itemRequestId.setText(String.valueOf(position));
        holder.itemRequestHeader.setText(dishInItemList.getDish().getName());
        holder.itemRequestQuantity.setText(String.valueOf(dishInItemList.getQuantity()));*/
    }


    @Override
    public int getItemCount() {
        return (tables != null) ? tables.size() : -1;
    }
}


