package day01.swomfire.restaurantapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import adapter.ExpandableItemListAdapter;
import adapter.RequestListAdapter;
import adapter.SwipeTouchHelper;
import data.model.OrderRequest;
import java.util.ArrayList;
import java.util.List;
import adapter.ExpandableItemListAdapter;
import adapter.TableRVAdapter;
import data.model.Item;
import data.model.Request;
import data.model.Table;
import data.remote.RmaAPIService;

import model.DishInItemList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.TabHostService;
import service.TabHostServiceImpl;
import utils.RmaAPIUtils;

public class MainActivity extends AppCompatActivity {
    public final String FB_TOPIC_REQUESTLIST = "RequestList";
    private List<OrderRequest> requestList;
    private FragmentTabHost tabHost;
    private static List<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDishList();
        // Subscribe to topic with API
//        FirebaseMessaging.getInstance().subscribeToTopic(FB_TOPIC_REQUESTLIST);
        setSupportActionBar(findViewById(R.id.my_toolbar));
        initTabWidget();
    }

    public static List<Item> getItemList() {
        return itemList;
    }

    public void loadDishList() {
        RmaAPIService mService = RmaAPIUtils.getAPIService();

        mService.getItemList().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    itemList = response.body();
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Fail to connect to server", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(MainActivity.this, v);
        popup.setOnMenuItemClickListener(item -> {
            Intent intent;
            if (item.getTitle().equals(getResources().getString(R.string.popup_setting))) {
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
            if (item.getTitle().equals(getResources().getString(R.string.popup_logout))) {
//                    Waiting for implementation
            }

            return true;
        });

        getMenuInflater().inflate(R.menu.menu, popup.getMenu());
        popup.show();
    }


    private ArrayList<String> SUGGESTIONS = new ArrayList<>();
    private SimpleCursorAdapter mAdapter;
    private SearchView searchView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);

        // Get the SearchView and set the Searchable config
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes cur activity is the searchable activity
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        ///

        final String[] from = new String[]{"name"};
        final int[] to = new int[]{R.id.searchText};
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.search_list_item,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(mAdapter);

        searchView.setOnSuggestionListener(
                new SearchView.OnSuggestionListener() {
                    @Override
                    public boolean onSuggestionClick(int position) {
                        String text = mAdapter.getCursor().getString(1);
                        searchByTab(text);
                        searchView.setIconified(true);
                        return false;
                    }

                    @Override
                    public boolean onSuggestionSelect(int position) {
                        // Your code here
                        return true;
                    }
                }
        );
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()

        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByTab(query);
                searchView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }

        });
        searchView.setOnSearchClickListener(v ->
                loadSuggestion());
        return true;
    }

    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "name"});
        for (int i = 0; i < SUGGESTIONS.size(); i++) {
            if (SUGGESTIONS.get(i).toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{i, SUGGESTIONS.get(i)});
        }
        mAdapter.changeCursor(c);
    }

    private void loadSuggestion() {
        SUGGESTIONS = new ArrayList<>();
        int currentTab = tabHost.getCurrentTab();
        switch (currentTab) {
            case 0:
                RecyclerView recyclerView = findViewById(R.id.rv_request_list);
                if (recyclerView != null) {
                    int count = recyclerView.getAdapter().getItemCount();
                    for (int i = 0; i < count; i++) {
                        RequestListAdapter customRVAdapter = (RequestListAdapter) recyclerView.getAdapter();
                        Request request = customRVAdapter.getRequest(i);
                        if (itemList != null) {
                            for (Item item : itemList) {
                                if (item.getSeqId().equals((long) request.getItemSeq())) {
                                    SUGGESTIONS.add(item.getItemName());
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 1:
                recyclerView = findViewById(R.id.rv_table_list);
                if (recyclerView != null) {
                    int count = recyclerView.getAdapter().getItemCount();
                    for (int i = 0; i < count; i++) {
                        TableRVAdapter tableRVAdapter = (TableRVAdapter) recyclerView.getAdapter();
                        Table table = tableRVAdapter.getTable(i);
                        SUGGESTIONS.add(table.getTableId());
                    }
                }
                break;
            case 2:
                if (itemList != null) {
                    for (Item item : itemList) {
                        SUGGESTIONS.add(item.getItemName());
                    }
                }
                break;
        }
    }

    private void searchByTab(String searchValue) {
        int currentTab = tabHost.getCurrentTab();
        switch (currentTab) {
            case 0:
                RecyclerView recyclerView = findViewById(R.id.rv_request_list);
                if (recyclerView != null) {
                    int count = recyclerView.getAdapter().getItemCount();
                    for (int i = 0; i < count; i++) {
                        RequestListAdapter customRVAdapter = (RequestListAdapter) recyclerView.getAdapter();
                        Request request = customRVAdapter.getRequest(i);
                        if (itemList != null) {
                            for (Item item : itemList) {
                                if (item.getSeqId().equals((long) request.getItemSeq()) && item.getItemName().equals(searchValue)) {
                                    recyclerView.scrollToPosition(i);
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 1:
                recyclerView = findViewById(R.id.rv_table_list);
                if (recyclerView != null) {
                    int count = recyclerView.getAdapter().getItemCount();
                    for (int i = 0; i < count; i++) {
                        TableRVAdapter tableRVAdapter = (TableRVAdapter) recyclerView.getAdapter();
                        Table table = tableRVAdapter.getTable(i);
                        if (searchValue.equals(table.getTableId())) {
                            recyclerView.scrollToPosition(i);
                            break;
                        }
                    }
                }
                break;
            case 2:
                ExpandableListView expandableListView = findViewById(R.id.itemExpandableList);
                ExpandableListAdapter expandableListAdapter = expandableListView.getExpandableListAdapter();
                if (expandableListAdapter != null) {
                    int size = expandableListAdapter.getGroupCount();
                    for (int i = 0; i < size; i++) {
                        int entry = expandableListAdapter.getChildrenCount(i);
                        for (int j = 0; j < entry; j++) {
                            DishInItemList dishInItemList = (DishInItemList) expandableListAdapter.getChild(i, j);
                            if (dishInItemList.getDish().getItemName().toLowerCase().contains(searchValue.toLowerCase())) {
                                expandableListView.expandGroup(i);
                                expandableListView.setSelectedGroup(i);
                                break;
                            }
                        }
                    }
                }
                break;
        }
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
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHostService.tabInitializer(tabHost);

        tabHost.setOnTabChangedListener(
                tabId -> {
                    tabHostService.tabIconReset(tabHost);
                    tabHostService.tabChooseIndicator(tabHost, tabId);
                    searchView.setIconified(true);
                }
        );
    }

    public void itemQuantityChange(View view) {
        FragmentManager fm = getSupportFragmentManager();
        ItemQuantityDialogFragment itemQuantityDialogFragment = new ItemQuantityDialogFragment();
        itemQuantityDialogFragment.setUp((View) view.getParent());
        itemQuantityDialogFragment.show(fm, "fragment_dialog_item_quantity");
    }

    public void toDoneActivity(View view) {
        Intent intent = new Intent(this, RequestOrderActivity.class);
        startActivityForResult(intent, 2);
    }

    public void selectItemToRequest(View view) {
        View view1 = (View) view.getParent();
        View view2 = (View) view1.getParent();
        TextView lblId = view2.findViewById(R.id.lblListItemId);
        DishInItemList dishInItemList = ExpandableItemListAdapter.findDish(String.valueOf(lblId.getText()));

        CheckBox thisBox = view.findViewById(R.id.itemCheckbox);
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

    public void onRequestCardClick(View view) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        startActivityForResult(intent, 2);
        TextView tableId = view.findViewById(R.id.tv_table_id);
        TextView receiptId = view.findViewById(R.id.tv_receipt_id);
        OrderDetailActivity.setTableId(String.valueOf(tableId.getText()));
        OrderDetailActivity.setReceiptIdId(Integer.parseInt(String.valueOf(receiptId.getText())));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (resultCode == 5) {
                String message = data.getStringExtra("restartItemFragment");
                if (message != null) {
                    if (tabHost.getCurrentTabTag().equals("DISH_LIST_TAB")) {
                        Fragment frg = getSupportFragmentManager().findFragmentByTag("DISH_LIST_TAB");
                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
                    }
                }
            } else if (resultCode == 3) {
                String message = data.getStringExtra("tableId");
                if (message != null) {
                    tabHost.setCurrentTab(2);
                    RequestOrderActivity.setTableId(message);
                }
            }
        }
    }

    public void addNewRequestToTable(View view) {
        tabHost.setCurrentTab(2);
        view = (View) view.getParent().getParent().getParent();
        TextView tableId = view.findViewById(R.id.listTableNumber);
        RequestOrderActivity.setTableId(String.valueOf(tableId.getText()));
    }

    public void toOrderDetail(View view) {
        Intent intent = new Intent(MainActivity.this, OrderDetailActivity.class);
        view = (View) view.getParent().getParent().getParent();
        TextView tableId = view.findViewById(R.id.listTableNumber);
        OrderDetailActivity.setTableId(String.valueOf(tableId.getText()));
        startActivityForResult(intent, 2);
    }



}
