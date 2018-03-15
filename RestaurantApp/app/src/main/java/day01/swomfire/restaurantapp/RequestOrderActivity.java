package day01.swomfire.restaurantapp;

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

    private static RequestOrderTableDialogFragment requestOrderTableDialogFragment;
    private HashMap<String, List<DishInItemList>> listHashMap;
    private RecyclerView recyclerView;
    private ItemRequestRVAdapter itemRequestRVAdapter;
    private TextView lblNewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order);
        int newQuantity = 0;
        List<DishInItemList> dishInItemLists1 = new ArrayList<>();
        List<DishInItemList> dishInItemLists2 = new ArrayList<>();
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

        lblNewRequest = (TextView) findViewById(R.id.lblItemRequestRowNewQuantity);
        lblNewRequest.setText(String.valueOf(newQuantity));

        initRecyclerView(R.id.requestItemRecyclerView1, dishInItemLists1);
        initRecyclerView(R.id.requestItemRecyclerView2, dishInItemLists2);

    }

    public void backToMenu(View view) {
        this.finish();
    }

    public void chooseTable(View view) {
        FragmentManager fm = getSupportFragmentManager();
        requestOrderTableDialogFragment = new RequestOrderTableDialogFragment();
        requestOrderTableDialogFragment.show(fm, "request_order_table_dialog_fragment");
    }

    private void initRecyclerView(int id, List<DishInItemList> dishInItemLists) {

        recyclerView = (RecyclerView) findViewById(id);
        itemRequestRVAdapter = new ItemRequestRVAdapter(dishInItemLists);
        RecyclerView.LayoutManager iLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(iLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemRequestRVAdapter);
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
}
