package Service;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.widget.ImageView;
import android.widget.TabHost;

import day01.swomfire.restaurantapp.EmployeeFragment;
import day01.swomfire.restaurantapp.ItemFragment;
import day01.swomfire.restaurantapp.R;
import day01.swomfire.restaurantapp.TableFragment;

/**
 * Created by Swomfire on 23-Feb-18.
 */

public class TabHostServiceImpl implements TabHostService {

    private Context context;
    private ImageView imageView;
    private TabHost.TabSpec tab;

    public TabHostServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void tabIconReset(FragmentTabHost tabHost) {
        imageView = (ImageView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.table_tab_icon));
        imageView = (ImageView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.item_tab_icon));
        imageView = (ImageView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.icon);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.employee_tab_icon));
    }

    @Override
    public void tabInitializer(FragmentTabHost tabHost) {
        tab = tabHost.newTabSpec("tableTab").setIndicator("", context.getResources().getDrawable(R.drawable.table_tab_icon_selected));
        tabHost.addTab(tab, TableFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("itemTab").setIndicator("", context.getResources().getDrawable(R.drawable.item_tab_icon)),
                ItemFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("employeeTab").setIndicator("", context.getResources().getDrawable(R.drawable.employee_tab_icon)),
                EmployeeFragment.class, null);
    }

    @Override
    public void tabChooseIndicator(FragmentTabHost tabHost, String switchCase) {
        switch (switchCase) {
            case "tableTab":
                imageView = (ImageView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.table_tab_icon_selected));
                break;
            case "itemTab":
                imageView = (ImageView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.item_tab_icon_selected));
                break;
            case "employeeTab":
                imageView = (ImageView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.icon);
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.employee_tab_icon_selected));
                break;
        }
    }
}
