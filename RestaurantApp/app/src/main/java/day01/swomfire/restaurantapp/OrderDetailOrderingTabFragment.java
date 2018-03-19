package day01.swomfire.restaurantapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.OrderDetailRVAdapter;
import data.model.Item;
import model.DishInReceipt;


public class OrderDetailOrderingTabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_detail_ordering_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<DishInReceipt> dishInReceipts = new ArrayList<>();
        Item item = new Item();
        item.setItemName("Pizza");
        dishInReceipts.add(
                new DishInReceipt(
                        item, 1
                )
        );
        Item item1 = new Item();
        item1.setItemName("Baked Bean");
        dishInReceipts.add(
                new DishInReceipt(
                        item1, 5
                )
        );

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            String itemPositionAndQuantity = pref.getString("itemPositionAndNewQuantity", null);
            editor.clear();
            editor.commit();
            if (itemPositionAndQuantity != null) {
                String[] positionAndQuantity = itemPositionAndQuantity.split(",");
                int position = Integer.parseInt(positionAndQuantity[0]);
                int quantity = Integer.parseInt(positionAndQuantity[1]);
                if (quantity > 0) {
                    DishInReceipt dishInReceipt = dishInReceipts.get(position);
                    dishInReceipt.setQuantity(quantity);
                } else {
                    dishInReceipts.remove(position);
                }

            }
        }

        initRecycleView(dishInReceipts);
    }

    private void initRecycleView(List<DishInReceipt> dishInReceipts) {
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.orderDetail);
        OrderDetailRVAdapter orderDetailOrderingRVAdapter = new OrderDetailRVAdapter(dishInReceipts, R.layout.item_order_detail_ordering_list_row);
        GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderDetailOrderingRVAdapter);
    }


}
