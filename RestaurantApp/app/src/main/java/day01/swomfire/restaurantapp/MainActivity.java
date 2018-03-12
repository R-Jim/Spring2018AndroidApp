package day01.swomfire.restaurantapp;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import Service.TabHostService;
import Service.TabHostServiceImpl;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;
    private ExpandableListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        listView = findViewById(R.id.tableExpandableList);


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

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenu().add("View statistic");
        popup.getMenu().add("Dashboard");
        popup.getMenu().add("Bla bla");
        getMenuInflater().inflate(R.menu.menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);

        //Get the SearchView and set the Searchable config
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //Assumes cur activity is the searchable activity
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        List demo = new ArrayList();
        demo.add("Bun bo");
        demo.add("Bun cha");
        demo.add("Pho");
        demo.add("23");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("Text submitted: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("Text changed: " + newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                System.out.println("Setting item selected");
                break;
            case R.id.action_search:
                System.out.println("Search item selected");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
