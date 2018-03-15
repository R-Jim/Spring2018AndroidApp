package day01.swomfire.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ItemRequestRVAdapter;
import model.DishInItemList;

public class RequestOrderActivity extends AppCompatActivity {

    private static RequestOrderItemQuantityDialogFragment requestOrderItemQuantityDialogFragment;
    private static RequestOrderTableDialogFragment requestOrderTableDialogFragment;
    private static List<DishInItemList> dishInItemLists1;
    private static List<DishInItemList> dishInItemLists2;
    private static RecyclerView recyclerView;
    private static ItemRequestRVAdapter itemRequestRVAdapter;
    private static HashMap<String, List<DishInItemList>> listHashMap;
    private static TextView lblNewRequest;

    private static void initRecyclerView(int id, List<DishInItemList> dishInItemLists, int listCode, Activity activity) {

        recyclerView = (RecyclerView) activity.findViewById(id);
        itemRequestRVAdapter = new ItemRequestRVAdapter(dishInItemLists, listCode);
        RecyclerView.LayoutManager iLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(iLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemRequestRVAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order);

        initRecycleListView(this);
    }

    private static void initRecycleListView(Activity activity) {
        int newQuantity = 0;
        dishInItemLists1 = new ArrayList<>();
        dishInItemLists2 = new ArrayList<>();
        boolean swap = false;
        listHashMap = ItemFragment.getListHashMap();
        for (Map.Entry<String, List<DishInItemList>> entry : listHashMap.entrySet()) {
            for (DishInItemList dishInItemList : entry.getValue()) {
                if (dishInItemList.isSelected()) {
                    if (!swap) {
                        dishInItemLists1.add(dishInItemList);
                        swap = true;
                    } else {
                        dishInItemLists2.add(dishInItemList);
                        swap = false;
                    }
                    newQuantity += dishInItemList.getQuantity();
                }
            }
        }

        lblNewRequest = (TextView) activity.findViewById(R.id.lblItemRequestRowNewQuantity);
        lblNewRequest.setText(String.valueOf(newQuantity));

        initRecyclerView(R.id.requestItemRecyclerView1, dishInItemLists1, 1, activity);
        initRecyclerView(R.id.requestItemRecyclerView2, dishInItemLists2, 2, activity);
    }

    public void backToMenu(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("restartItemFragment", "TRUE");
        setResult(5, i);
        this.finish();
    }

    public void chooseTable(View view) {
        FragmentManager fm = getSupportFragmentManager();
        requestOrderTableDialogFragment = new RequestOrderTableDialogFragment();
        requestOrderTableDialogFragment.show(fm, "request_order_table_dialog_fragment");
    }


    public void cancelRequest(View view) {
        for (Map.Entry<String, List<DishInItemList>> entry : listHashMap.entrySet()) {
            for (DishInItemList dishInItemList : entry.getValue()) {
                if (dishInItemList.isSelected()) {
                    dishInItemList.setSelected(false);
                    dishInItemList.setQuantity(1);
                }
            }
        }
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("restartItemFragment", "TRUE");
        setResult(5, i);
        this.finish();
    }

    public void requestItemQuantityChange(View view) {
        FragmentManager fm = getSupportFragmentManager();
        requestOrderItemQuantityDialogFragment = new RequestOrderItemQuantityDialogFragment();
        requestOrderItemQuantityDialogFragment.setUp(view);
        requestOrderItemQuantityDialogFragment.show(fm, "fragment_dialog_item_request_quantity");
    }

    public static DishInItemList getDishInRequestItemList(int listCode, int position) {
        if (listCode == 1) {
            return dishInItemLists1.get(position);
        } else if (listCode == 2) {
            return dishInItemLists2.get(position);
        }
        return null;
    }

    public static void closeDialog(Activity activity) {
        requestOrderItemQuantityDialogFragment.dismiss();
        initRecycleListView(activity);
    }
}
