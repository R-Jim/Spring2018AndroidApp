package day01.swomfire.restaurantapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {
    public final String ORDERED_TAB = "ORDERED_TAB";
    public final String ORDERING_TAB = "ORDERING_TAB";
    private OrderDetailQuantityDialogFragment orderDetailQuantityDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
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

                View viewBar = view.findViewById(R.id.vTopBar);
                viewBar.setBackgroundColor(getColor(R.color.colorComTabTextUnselected));
            }
            View view = tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
            TextView tv = view.findViewById(R.id.tvTabTitle);
            tv.setTextColor(getColor(R.color.colorComTabTextSelected));
            View viewBar = view.findViewById(R.id.vTopBar);
            viewBar.setBackgroundColor(getColor(R.color.colorComTabTextSelected));
        });

    }

    private void setNewTab(Context ctx, FragmentTabHost tabHost, String tag, String title) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(ctx, title));
        tabHost.addTab(tabSpec, ComTabFragment.class, null);
    }

    private View getTabIndicator(Context ctx, String title) {
        View view = View.inflate(this, R.layout.tab_layout_order_detail, null);
        TextView tvTitle = view.findViewById(R.id.tvTabTitle);
        tvTitle.setText(title);
        return view;
    }

    public void requestItemQuantityChange(View view) {
        FragmentManager fm = getSupportFragmentManager();
        orderDetailQuantityDialogFragment = new OrderDetailQuantityDialogFragment();
        orderDetailQuantityDialogFragment.setUp(view);
        View parent = (View) view.getParent().getParent();
        TextView id = parent.findViewById(R.id.itemOrderDetailId);
        TextView quantity = view.findViewById(R.id.itemOrderDetailQuantity);
        Bundle bundle = new Bundle();
        String[] ItemPositionAndQuantity = {id.getText().toString(), quantity.getText().toString()};
        bundle.putStringArray("ItemPositionAndQuantity", ItemPositionAndQuantity);
        orderDetailQuantityDialogFragment.setArguments(bundle);
        orderDetailQuantityDialogFragment.show(fm, "fragment_dialog_order_detail_quantity");
    }

}
