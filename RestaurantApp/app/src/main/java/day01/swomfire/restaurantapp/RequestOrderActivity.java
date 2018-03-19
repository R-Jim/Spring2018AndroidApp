package day01.swomfire.restaurantapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ExpandableItemListAdapter;
import adapter.ItemRequestRVAdapter;
import data.model.Category;
import data.model.Table;
import model.DishInItemList;
import utils.StyleUtils;

public class RequestOrderActivity extends AppCompatActivity {

    private RequestOrderItemQuantityDialogFragment requestOrderItemQuantityDialogFragment;
    private RequestOrderTableDialogFragment requestOrderTableDialogFragment;
    private static List<DishInItemList> dishInItemLists;
    private static RecyclerView recyclerView;
    private static ItemRequestRVAdapter itemRequestRVAdapter;
    private static HashMap<Category, List<DishInItemList>> listHashMap;
    private static TextView lblNewRequest;
    private static String tableId;

    private static void initRecyclerView(int id, List<DishInItemList> dishInItemLists, Activity activity) {

        recyclerView = (RecyclerView) activity.findViewById(id);
        itemRequestRVAdapter = new ItemRequestRVAdapter(dishInItemLists);
        GridLayoutManager gLayoutManager = new GridLayoutManager(activity.getApplicationContext(), 2);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemRequestRVAdapter);
    }


    public static void setTableId(String id) {
        tableId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order);

        StyleUtils.setGradientBackground(findViewById(android.R.id.content), R.id.requestOrderLayout,
                new int[]{getColor(R.color.colorDoneOrderBackground1),
                        getColor(R.color.colorDoneOrderBackground2)}, StyleUtils.GradientMode.TOP_BOTTOM.getMode());

        initRecycleListView(this);
    }

    public static void initRecycleListView(Activity activity) {
        int newQuantity = 0;
        dishInItemLists = new ArrayList<>();
        listHashMap = ExpandableItemListAdapter.getListHashMap();
        for (Map.Entry<Category, List<DishInItemList>> entry : listHashMap.entrySet()) {
            for (DishInItemList dishInItemList : entry.getValue()) {
                if (dishInItemList.isSelected()) {
                    dishInItemLists.add(dishInItemList);
                    newQuantity += dishInItemList.getQuantity();
                }
            }
        }
        if (tableId != null) {
            TextView requestOrderTable = activity.findViewById(R.id.requestOrderTableId);
            requestOrderTable.setText(tableId);
        }

        lblNewRequest = (TextView) activity.findViewById(R.id.lblItemRequestRowNewQuantity);
        lblNewRequest.setText(String.valueOf(newQuantity));

        initRecyclerView(R.id.requestItemRecyclerView, dishInItemLists, activity);
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
        for (Map.Entry<Category, List<DishInItemList>> entry : listHashMap.entrySet()) {
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
        tableId = null;
        this.finish();
    }

    public void requestItemQuantityChange(View view) {
        FragmentManager fm = getSupportFragmentManager();
        requestOrderItemQuantityDialogFragment = new RequestOrderItemQuantityDialogFragment();
        requestOrderItemQuantityDialogFragment.setUp(view);
        requestOrderItemQuantityDialogFragment.show(fm, "fragment_dialog_item_request_quantity");


    }

    public static DishInItemList getDishInRequestItemList(int position) {
        return dishInItemLists.get(position);
    }

    public void chooseRequestTable(View view) {
        requestOrderTableDialogFragment.dismiss();
        TextView table = view.findViewById(R.id.listTableNumber);
        TextView requestOrderTable = findViewById(R.id.requestOrderTableId);
        tableId = String.valueOf(table.getText());
        requestOrderTable.setText(tableId);

        //TODO: reload table's list receipt if has one

    }

    public void confirmRequest(View view) {
        if (tableId != null) {

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Please choose a table first", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
