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
    public enum RVViewType {
        TABLE_FREE(0), TABLE_ORDERING(1), TABLE_CHOOSE_FREE(2), TABLE_CHOOSE_ORDERING(3);

        private int viewType;

        RVViewType(int viewType) {
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }

    public enum RVViewMode {
        TABLE_TAB(0), REQUEST_ORDER_CHOOSE_TABLE(1);
        private int viewMode;

        RVViewMode(int viewMode) {
            this.viewMode = viewMode;
        }

        public int getViewMode() {
            return viewMode;
        }
    }


    private List<Table> tables;
    private int viewMode;

    @Override
    public int getItemViewType(int position) {
        if (viewMode == RVViewMode.TABLE_TAB.getViewMode()) {
            if (tables.get(position).getStatusByStatusSeqId().getStatusId().equals(Status.AVAIABLE.getStatusId())) {
                return RVViewType.TABLE_FREE.getViewType();
            } else {
                return RVViewType.TABLE_ORDERING.getViewType();
            }
        } else {
            if (tables.get(position).getStatusByStatusSeqId().getStatusId().equals(Status.AVAIABLE.getStatusId())) {
                return RVViewType.TABLE_CHOOSE_FREE.getViewType();
            } else {
                return RVViewType.TABLE_CHOOSE_ORDERING.getViewType();
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tableNumber;

        public MyViewHolder(View tableView) {
            super(tableView);
            tableNumber = (TextView) itemView.findViewById(R.id.listTableNumber);

        }
    }

    public TableRVAdapter(List<Table> tables, int viewMode) {
        this.tables = tables;
        this.viewMode = viewMode;
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
            case 2:
                tableView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.table_list_row_choosing_free, parent, false);
                break;
            case 3:
                tableView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.table_list_row_choosing_ordering, parent, false);
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


