package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import data.model.Item;
import data.model.OrderRequest;
import data.model.Request;
import day01.swomfire.restaurantapp.MainActivity;
import day01.swomfire.restaurantapp.R;

/**
 * Created by elpsychris on 03/13/2018.
 */

public class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.RequestViewHolder> {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tvTableId;
        TextView tvDishName;
        TextView tvDishDiscr;
        TextView tvReceiptId;

        public RequestViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_request_item);
            tvTableId = itemView.findViewById(R.id.tv_table_id);
            tvDishName = itemView.findViewById(R.id.tv_dish_name);
            tvDishDiscr = itemView.findViewById(R.id.tv_description);
            tvReceiptId = itemView.findViewById(R.id.tv_receipt_id);
        }
    }

    // Data for the RV
    private List<Request> requestList;

    public CustomRVAdapter(List requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_request_list, parent, false);
        RequestViewHolder rvh = new RequestViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.tvTableId.setText(String.valueOf(requestList.get(position).getTableId()));

        List<Item> itemList = MainActivity.getItemList();
        String name = "";
        for (Item item : itemList) {
            if (item.getSeqId().equals((long) requestList.get(position).getItemSeq())) {
                name = item.getItemName();
                break;
            }
        }
        holder.tvDishName.setText((name.length() < 12) ? name : name.substring(0, 12) + "...");
        holder.tvReceiptId.setText(String.valueOf(requestList.get(position).getReceiptSeq()));
        //holder.tvDishDiscr.setText(requestList.get(position).getTableId() + "");
    }


    @Override
    public int getItemCount() {
        return requestList == null ? 0 : requestList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
