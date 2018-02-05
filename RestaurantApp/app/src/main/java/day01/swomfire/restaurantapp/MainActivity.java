package day01.swomfire.restaurantapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost tabHost;
    private TabHost.TabSpec tab;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabInitializer();
        tabHost.setOnTabChangedListener(
                new TabHost.OnTabChangeListener() {

                    @Override
                    public void onTabChanged(String tabId) {
                        tabIconReset();
                        if (tabId.equals("tableTab")) {
                            iv = (ImageView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
                            iv.setImageDrawable(getResources().getDrawable(R.drawable.table_tab_icon_selected));
                        } else if (tabId.equals("itemTab")) {
                            iv = (ImageView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
                            iv.setImageDrawable(getResources().getDrawable(R.drawable.item_tab_icon_selected));
                        } else if (tabId.equals("employeeTab")) {
                            iv = (ImageView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.icon);
                            iv.setImageDrawable(getResources().getDrawable(R.drawable.employee_tab_icon_selected));
                        }
                    }
                }
        );
    }

    private void tabIconReset() {
        iv = (ImageView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.table_tab_icon));
        iv = (ImageView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.item_tab_icon));
        iv = (ImageView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.icon);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.employee_tab_icon));
    }

    private void tabInitializer() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tab = tabHost.newTabSpec("tableTab").setIndicator("", getResources().getDrawable(R.drawable.table_tab_icon_selected));
        tabHost.addTab(tab, TableFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("itemTab").setIndicator("", getResources().getDrawable(R.drawable.item_tab_icon)),
                ItemFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("employeeTab").setIndicator("", getResources().getDrawable(R.drawable.employee_tab_icon)),
                EmployeeFragment.class, null);
    }

}
