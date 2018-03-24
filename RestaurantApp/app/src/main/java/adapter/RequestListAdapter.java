package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import data.model.Item;
import data.model.OrderRequest;
import data.model.Request;
import day01.swomfire.restaurantapp.R;

/**
 * Created by elpsychris on 03/13/2018.
 */

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        public TextView tvTableId;
        public TextView tvDishName;
        public TextView tvDishDiscr;
        public RelativeLayout viewBackground, viewForeground;

        public RequestViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_request_item);
            tvTableId = (TextView) itemView.findViewById(R.id.tv_table_id);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);
            tvDishDiscr = (TextView) itemView.findViewById(R.id.tv_description);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

    // Data for the RV
    public List<Request> requestList;

    public RequestListAdapter(List requestList) {
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
        holder.tvTableId.setText(requestList.get(position).getTableId() + "");
    }


    @Override
    public int getItemCount() {
        return requestList == null ? 0 : requestList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void removeItem(int pos) {
        if (requestList == null) {
            return;
        }

        requestList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void restoredItem(Request request, int pos) {
        requestList.add(pos, request);

        notifyItemInserted(pos);
    }


}
