package day01.swomfire.restaurantapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import android.widget.TabWidget;


import adapter.ExpandableItemListAdapter;
import model.DishInItemList;
import service.TabHostService;
import service.TabHostServiceImpl;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;
    //    private ExpandableListView listView;
    private RecyclerView rvReqList;
    private ExpandableListView listView;
    private TabWidget tabWidget;

    private static ItemQuantityDialogFragment itemQuantityDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabWidget = findViewById(android.R.id.tabs);

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
////        listView = findViewById(R.id.tableExpandableList);

        initTabWidget();
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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);

        // Get the SearchView and set the Searchable config
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes cur activity is the searchable activity
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));


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
            case R.id.action_add:
                System.out.println("Setting item selected");
                break;
            case R.id.action_search:
                System.out.println("Search item selected");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTabWidget() {
        final TabHostService tabHostService = new TabHostServiceImpl(this);
        // get tabHost
        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHostService.tabInitializer(tabHost);

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

    public void itemQuantityChange(View view) {
        FragmentManager fm = getSupportFragmentManager();
        itemQuantityDialogFragment = new ItemQuantityDialogFragment();
        LinearLayout thisItemTab = findViewById(R.id.itemItem);
        itemQuantityDialogFragment.setUp(view, thisItemTab);
        itemQuantityDialogFragment.show(fm, "fragment_dialog_item_quan");
    }

    public static void closeDialog() {
        itemQuantityDialogFragment.dismiss();
    }

    public void toDoneActivity(View view) {
        Intent intent = new Intent(this, RequestOrderActivity.class);
        startActivity(intent);
    }

    public void selectItemToRequest(View view) {
        View view1 = (View) view.getParent();
        View view2 = (View) view1.getParent();
        TextView lblId = (TextView) view2.findViewById(R.id.lblListItemId);
        DishInItemList dishInItemList = ExpandableItemListAdapter.findDish(String.valueOf(lblId.getText()));

        CheckBox thisBox = (CheckBox) view.findViewById(R.id.itemCheckbox);
        dishInItemList.setSelected(thisBox.isChecked());
        TextView lblNumberOfDishRequested = findViewById(R.id.lblNumberOfDishRequested);
        String quantityStr = String.valueOf(lblNumberOfDishRequested.getText());
        int quantity = Integer.parseInt(quantityStr);
        if (dishInItemList.isSelected()) {
            quantity += dishInItemList.getQuantity();
        } else {
            quantity -= dishInItemList.getQuantity();
        }
        lblNumberOfDishRequested.setText(String.valueOf(quantity));
    }

}
