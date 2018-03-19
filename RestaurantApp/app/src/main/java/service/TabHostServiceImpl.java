package service;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import day01.swomfire.restaurantapp.ItemFragment;
import day01.swomfire.restaurantapp.R;
import day01.swomfire.restaurantapp.RequestFragment;
import day01.swomfire.restaurantapp.TableFragment;

/**
 * Created by Swomfire on 23-Feb-18.
 */

public class TabHostServiceImpl implements TabHostService {

    private Context context;


    public TabHostServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void tabIconReset(FragmentTabHost tabHost) {

        // Hide top bar
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View ivTopBar = tabHost.getTabWidget().getChildAt(i).findViewById(R.id.vTopBar);
            ivTopBar.setVisibility(View.INVISIBLE);
        }

        // Change icon to inactive ones
        ImageView iv;

        iv = tabHost.getTabWidget().getChildAt(0).findViewById(R.id.tab_icon);
        iv.setImageResource(R.drawable.ic_tab_assignment_24dp);
        iv = tabHost.getTabWidget().getChildAt(1).findViewById(R.id.tab_icon);
        iv.setImageResource(R.drawable.ic_tab_table_24dp_inactive);
        iv = tabHost.getTabWidget().getChildAt(2).findViewById(R.id.tab_icon);
        iv.setImageResource(R.drawable.ic_tab_dish_24dp);
    }

    @Override
    public void tabInitializer(FragmentTabHost tabHost) {
        View tab1 = View.inflate(this.context, R.layout.tabwidget_req_list_indicator, null);
        View tab2 = View.inflate(this.context, R.layout.tabwidget_table_list_indicator, null);
        View tab3 = View.inflate(this.context, R.layout.tabwidget_dish_list_indicator, null);


        tabHost.addTab(tabHost.newTabSpec(REQUEST_TAB_ID)
                        .setIndicator(tab1),
                RequestFragment.class,
                null);
        tabHost.addTab(tabHost.newTabSpec(TABLE_TAB_ID)
                        .setIndicator(tab2),
                TableFragment.class,
                null);
        tabHost.addTab(tabHost.newTabSpec(DISH_TAB_ID)
                        .setIndicator(tab3),
                ItemFragment.class,
                null);

    }

    @Override
    public void tabChooseIndicator(FragmentTabHost tabHost, String switchCase) {
        View ivTopBar = tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.vTopBar);
        ivTopBar.setVisibility(View.VISIBLE);

        switch (switchCase) {
            case REQUEST_TAB_ID:
                ImageView imageView = tabHost.getTabWidget().getChildAt(0).findViewById(R.id.tab_icon);
                imageView.setImageResource(R.drawable.ic_tab_assignment_24dp_active);

                break;
            case TABLE_TAB_ID:
                imageView = tabHost.getTabWidget().getChildAt(1).findViewById(R.id.tab_icon);
                imageView.setImageResource(R.drawable.ic_tab_table_24dp_active);
                break;
            case DISH_TAB_ID:
                imageView = tabHost.getTabWidget().getChildAt(2).findViewById(R.id.tab_icon);
                imageView.setImageResource(R.drawable.ic_tab_dish_24dp_active);
                break;
        }
    }
}
