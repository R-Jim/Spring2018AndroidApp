package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.OrderDetailRVAdapter;
import data.remote.RmaAPIService;
import model.DishInReceipt;
import data.model.ReceiptDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;


public class OrderDetailOrderedTabFragment extends Fragment {
    private List<ReceiptDetail> receiptDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_detail_ordered_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View parent = (View) view.getParent().getParent();
        TextView lblReceiptId = parent.findViewById(R.id.orderDetailReceiptId);
        loadOrderedList(Integer.parseInt(String.valueOf(lblReceiptId.getText())));
    }

    private void loadOrderedList(Integer receiptId) {
        RmaAPIService rmaAPIService = RmaAPIUtils.getAPIService();
        rmaAPIService.getReceiptDetailsByReceiptId(receiptId).enqueue(new Callback<List<ReceiptDetail>>() {
            @Override
            public void onResponse(Call<List<ReceiptDetail>> call, Response<List<ReceiptDetail>> response) {
                if (response.isSuccessful()) {
                    receiptDetails = response.body();
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                    initRecycleView(receiptDetails);
                }
            }

            @Override
            public void onFailure(Call<List<ReceiptDetail>> call, Throwable t) {
                System.out.println("Failed to load Order Request list");

            }
        });
    }

    private void initRecycleView(List<ReceiptDetail> receiptDetails) {
        RecyclerView recyclerView = getActivity().findViewById(R.id.orderDetailOrderedRV);
        List<DishInReceipt> dishInReceipts = parseList(receiptDetails);
        OrderDetailRVAdapter orderDetailOrderingRVAdapter = new OrderDetailRVAdapter(dishInReceipts, R.layout.item_order_detail_ordered_list_row);
        GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderDetailOrderingRVAdapter);
    }

    private List<DishInReceipt> parseList(List<ReceiptDetail> receiptDetails) {
        List<DishInReceipt> dishInReceipts = new ArrayList<>();
        for (ReceiptDetail receiptDetail : receiptDetails) {
            DishInReceipt dishInReceipt = new DishInReceipt();
            dishInReceipt.setDish(receiptDetail.getItemByItemSeqId());
            dishInReceipt.setQuantity(receiptDetail.getQuantity().intValue());
            dishInReceipts.add(dishInReceipt);
        }
        return dishInReceipts;
    }
}
