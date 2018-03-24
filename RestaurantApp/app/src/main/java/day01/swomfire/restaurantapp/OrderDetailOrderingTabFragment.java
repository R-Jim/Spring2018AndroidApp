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
import data.model.Item;
import data.model.RequestDetail;
import data.remote.RmaAPIService;
import model.DishInReceipt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;


public class OrderDetailOrderingTabFragment extends Fragment {

    private static List<RequestDetail> requestDetails;

    public static List<RequestDetail> getRequestDetails() {
        return requestDetails;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_detail_ordering_tab, container, false);
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
        rmaAPIService.getRequestDetailsByReceiptSeq(receiptId).enqueue(new Callback<List<RequestDetail>>() {
            @Override
            public void onResponse(Call<List<RequestDetail>> call, Response<List<RequestDetail>> response) {
                if (response.isSuccessful()) {
                    requestDetails = response.body();
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                    initRecycleView(requestDetails);
                }
            }

            @Override
            public void onFailure(Call<List<RequestDetail>> call, Throwable t) {
                System.out.println("Failed to load Order Request list");

            }
        });
    }


    private void initRecycleView(List<RequestDetail> requestDetails) {
        List<DishInReceipt> dishInReceipts = new ArrayList<>();
        for (RequestDetail requestDetail : requestDetails) {
            if (!requestDetail.getChangeable() && requestDetail.getQuantity() > 0) {
                addDishToReceipt(requestDetail, dishInReceipts);
            }
        }
        initRV(dishInReceipts, R.layout.item_order_detail_ordering_not_changable_list_row, R.id.orderDetail);

        dishInReceipts = new ArrayList<>();
        for (RequestDetail requestDetail : requestDetails) {
            if (requestDetail.getChangeable() && requestDetail.getQuantity() > 0) {

                DishInReceipt dishInReceipt = new DishInReceipt();


                List<Item> itemList = MainActivity.getItemList();
                for (Item item : itemList) {
                    if (item.getSeqId().equals((long) requestDetail.getItemSeq())) {
                        dishInReceipt.setDish(item);
                        break;
                    }
                }
                dishInReceipt.setRequestDetailId(requestDetail.getSeq());
                dishInReceipt.setQuantity(requestDetail.getQuantity());
                dishInReceipts.add(dishInReceipt);
            }
        }
        initRV(dishInReceipts, R.layout.item_order_detail_ordering_list_row, R.id.orderDetailChangable);
    }

    private void initRV(List<DishInReceipt> dishInReceipts, int layoutId, int RVId) {
        if (getActivity() != null) {
            RecyclerView recyclerView = getActivity().findViewById(RVId);
            OrderDetailRVAdapter orderDetailOrderingRVAdapter = new OrderDetailRVAdapter(dishInReceipts, layoutId);
            GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
            if (recyclerView != null) {
                recyclerView.setLayoutManager(gLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(orderDetailOrderingRVAdapter);
            }
        }
    }

    private void addDishToReceipt(RequestDetail requestDetail, List<DishInReceipt> dishInReceipts) {
        DishInReceipt dishInReceipt = new DishInReceipt();


        List<Item> itemList = MainActivity.getItemList();
        for (Item item : itemList) {
            if (item.getSeqId().equals((long) requestDetail.getItemSeq())) {
                dishInReceipt.setDish(item);
            }
        }

        boolean existed = false;
        dishInReceipt.setQuantity(requestDetail.getQuantity());
        if (!dishInReceipts.isEmpty()) {
            for (DishInReceipt inReceipt : dishInReceipts) {
                if (inReceipt.getDish().getSeqId().equals(dishInReceipt.getDish().getSeqId())) {
                    dishInReceipt = inReceipt;
                    dishInReceipt.setQuantity(requestDetail.getQuantity() + inReceipt.getQuantity());
                    existed = true;
                    break;
                }
            }
        }
        if (!existed) {
            dishInReceipt.setQuantity(requestDetail.getQuantity());
            dishInReceipts.add(dishInReceipt);
        }
    }
}
