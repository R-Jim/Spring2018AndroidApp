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

import java.util.ArrayList;
import java.util.List;

import adapter.OrderDetailRVAdapter;
import data.model.Item;
import data.remote.RmaAPIService;
import model.DishInReceipt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;


public class OrderDetailOrderedTabFragment extends Fragment {
    private List<DishInReceipt> dishInReceipts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_detail_ordered_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadOrderedList();
    }

    private void loadOrderedList() {
/*        RmaAPIService rmaAPIService = RmaAPIUtils.getAPIService();
        rmaAPIService.getReceipt(receiptId).enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {
                if (response.isSuccessful()) {
                    dishInReceipts = response.body();
                    initRecycleView(dishInReceipts);
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<Receipt> call, Throwable t) {
                System.out.println("Failed to load Order Request list");

            }
        });*/
    }

    private void initRecycleView(List<DishInReceipt> dishInReceipts) {
        RecyclerView recyclerView = getActivity().findViewById(R.id.orderDetailOrderedRV);
        OrderDetailRVAdapter orderDetailOrderingRVAdapter = new OrderDetailRVAdapter(dishInReceipts, R.layout.item_order_detail_ordered_list_row);
        GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderDetailOrderingRVAdapter);
    }
}
