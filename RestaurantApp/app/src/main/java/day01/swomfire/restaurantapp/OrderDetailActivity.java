package day01.swomfire.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import data.model.Receipt;
import data.remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ParseUtils;
import utils.RmaAPIUtils;

public class OrderDetailActivity extends AppCompatActivity {
    public final String ORDERED_TAB = "ORDERED_TAB";
    public final String ORDERING_TAB = "ORDERING_TAB";
    private static String tableId;
    private static Integer receiptId;

    public static void setTableId(String id) {
        tableId = id;
    }

    public static void setReceiptIdId(Integer id) {
        receiptId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


        TextView lblTableId = findViewById(R.id.orderDetailTableId);
        lblTableId.setText(tableId);

        if (receiptId != null) {
            TextView lblReceiptId = findViewById(R.id.orderDetailReceiptId);
            lblReceiptId.setText(String.valueOf(receiptId));
        }
        loadOrderDetail();
    }

    private void setUpTab() {
        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        View tab1 = View.inflate(getBaseContext(), R.layout.tabwidget_order_detail_ordering_indicator, null);
        View tab2 = View.inflate(getBaseContext(), R.layout.tabwidget_order_detail_ordered_indicator, null);
        tabHost.addTab(tabHost.newTabSpec(ORDERING_TAB)
                        .setIndicator(tab1),
                OrderDetailOrderingTabFragment.class,
                null);
        tabHost.addTab(tabHost.newTabSpec(ORDERED_TAB)
                        .setIndicator(tab2),
                OrderDetailOrderedTabFragment.class,
                null);

        tabHost.setOnTabChangedListener(tabId -> {
            for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                View view = tabHost.getTabWidget().getChildAt(i);
                TextView tv = view.findViewById(R.id.tvTabTitle);
                tv.setTextColor(getColor(R.color.colorComTabTextUnselected));
                view.setBackgroundColor(getColor(R.color.colorPrimary));
                View viewBar = view.findViewById(R.id.vTopBar);
                viewBar.setBackgroundColor(getColor(R.color.colorComTabTextUnselected));
            }
            View view = tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
            TextView tv = view.findViewById(R.id.tvTabTitle);
            tv.setTextColor(getColor(R.color.colorComTabTextSelected));
            view.setBackgroundColor(getColor(R.color.colorComTabTextUnselected));
            View viewBar = view.findViewById(R.id.vTopBar);
            viewBar.setBackgroundColor(getColor(R.color.colorComTabTextSelected));
        });
    }

/*    private void setNewTab(Context ctx, FragmentTabHost tabHost, String tag, String title) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(ctx, title));
        tabHost.addTab(tabSpec, ComTabFragment.class, null);
    }*/

    /*   private View getTabIndicator(Context ctx, String title) {
           View view = View.inflate(this, R.layout.tab_layout_order_detail, null);
           TextView tvTitle = view.findViewById(R.id.tvTabTitle);
           tvTitle.setText(title);
           return view;
       }*/
    private void loadOrderDetail() {
        RmaAPIService rmaAPIService = RmaAPIUtils.getAPIService();
        if (receiptId != null) {
            rmaAPIService.getReceiptByReceiptId(receiptId).enqueue(new Callback<Receipt>() {
                @Override
                public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                    if (response.isSuccessful()) {
                        Receipt receipt = response.body();
                        TextView lblTotal = findViewById(R.id.orderDetailTotal);
                        lblTotal.setText(String.valueOf((receipt.getTotal() != null) ? receipt.getTotal() : "0"));
                        TextView lblDate = findViewById(R.id.orderDetailDate);
                        Date date = new Date(receipt.getIssueDate());
                        lblDate.setText(String.valueOf(ParseUtils.parseDateToStringFormat(date)));
                        setUpTab();

                        Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                    }
                }

                @Override
                public void onFailure(Call<Receipt> call, Throwable t) {
                    System.out.println("Failed to load Order Request list");

                }
            });
        } else {
            rmaAPIService.getReceiptByTableId(Integer.parseInt(tableId)).enqueue(new Callback<Receipt>() {
                @Override
                public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                    if (response.isSuccessful()) {
                        Receipt receipt = response.body();
                        TextView lblTotal = findViewById(R.id.orderDetailTotal);
                        lblTotal.setText(String.valueOf((receipt.getTotal() != null) ? receipt.getTotal() : "0"));
                        TextView lblDate = findViewById(R.id.orderDetailDate);
                        Date date = new Date(receipt.getIssueDate());
                        lblDate.setText(String.valueOf(ParseUtils.parseDateToStringFormat(date)));
                        receiptId = receipt.getSeqId();
                        TextView lblReceiptId = findViewById(R.id.orderDetailReceiptId);
                        lblReceiptId.setText(String.valueOf(receipt.getSeqId()));
                        setUpTab();
                        Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                    }
                }

                @Override
                public void onFailure(Call<Receipt> call, Throwable t) {
                    System.out.println("Failed to load Order Request list");

                }
            });
        }
    }

    public void requestItemQuantityChange(View view) {
        FragmentManager fm = getSupportFragmentManager();
        OrderDetailQuantityDialogFragment orderDetailQuantityDialogFragment = new OrderDetailQuantityDialogFragment();
        View parent = (View) view.getParent().getParent();
        TextView id = parent.findViewById(R.id.itemOrderDetailId);
        TextView quantity = view.findViewById(R.id.itemOrderDetailQuantity);
        Bundle bundle = new Bundle();
        String[] ItemPositionAndQuantity = {id.getText().toString(), quantity.getText().toString()};
        bundle.putStringArray("ItemPositionAndQuantity", ItemPositionAndQuantity);
        orderDetailQuantityDialogFragment.setArguments(bundle);
        orderDetailQuantityDialogFragment.show(fm, "fragment_dialog_order_detail_quantity");
    }

    public void addNewRequestToTable(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("tableId", tableId);
        setResult(3, i);
        tableId = null;
        receiptId = null;
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tableId = null;
        receiptId = null;
    }
}
