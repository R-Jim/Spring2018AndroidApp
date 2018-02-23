package day01.swomfire.restaurantapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.widget.TabHost;

import Service.TabHostService;
import Service.TabHostServiceImpl;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TabHostService tabHostService = new TabHostServiceImpl(this);

        //get tabHost
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //
        tabHostService.tabInitializer(tabHost);
        //
        tabHost.setOnTabChangedListener(
                new TabHost.OnTabChangeListener() {

                    @Override
                    public void onTabChanged(String tabId) {
                        tabHostService.tabIconReset(tabHost);
                        tabHostService.tabChooseIndicator(tabHost, tabId);
                    }
                }
        );
    }
}
