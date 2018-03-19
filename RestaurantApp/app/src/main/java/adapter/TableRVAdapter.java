package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import day01.swomfire.restaurantapp.R;
import model.Status;
import data.model.Table;

/**
 * Created by Swomfire on 15-Mar-18.
 */

public class TableRVAdapter extends RecyclerView.Adapter<TableRVAdapter.MyViewHolder> {
    public enum viewType {
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
        if (tables.get(position).getStatusByStatusSeqId().getStatusId().equals(Status.AVAIABLE.getStatusId())) {
            return viewType.TABLE_FREE.getViewType();
        } else {
            return viewType.TABLE_ORDERING.getViewType();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tableNumber;

        public MyViewHolder(View tableView) {
            super(tableView);
            tableNumber = (TextView) tableView.findViewById(R.id.listTableNumber);

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
        Table table = tables.get(position);
        holder.tableNumber.setText(String.valueOf(table.getTableId()));
    }


    @Override
    public int getItemCount() {
        return (tables != null) ? tables.size() : -1;
    }
}


