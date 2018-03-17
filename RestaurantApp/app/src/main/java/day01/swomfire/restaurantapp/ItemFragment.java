package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ExpandableItemListAdapter;
import data.model.Category;
import data.model.Item;
import data.remote.APIService;
import model.DishInItemList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.APIUtils;


public class ItemFragment extends Fragment {


    private ExpandableListView listView;
    private ExpandableItemListAdapter listAdapter;
    private static HashMap<Category, List<DishInItemList>> listHashMap;
    private List<Item> itemListFromDb;
    private List<Category> categoryListFromDb;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        loadDishList();
    }


    private void loadDishList() {
        APIService mService = APIUtils.getAPIService();

        mService.getItemList().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    itemListFromDb = response.body();
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                    loadCategoryList();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                System.out.println("Failed to load item list");
            }
        });

    }

    private void loadCategoryList() {
        APIService mService = APIUtils.getAPIService();

        mService.getCategoryList().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryListFromDb = response.body();
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                    initData();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println("Failed to load item list");
            }
        });

    }

    private void initData() {
        if (listHashMap == null) {
            listHashMap = new HashMap<>();
            for (Category category : categoryListFromDb) {
                listHashMap.put(category, null);
            }
            for (Item item : itemListFromDb) {
                if (item.getAvailable()) {
                    for (Map.Entry<Category, List<DishInItemList>> entry : listHashMap.entrySet()) {
                        if (entry.getKey().getCategoryId().equals(item.getCategory().getCategoryId())) {
                            List<DishInItemList> dishInItemLists = entry.getValue();
                            if (dishInItemLists == null) {
                                dishInItemLists = new ArrayList<>();
                            }
                            DishInItemList dishInItemList = createDish(item);
                            dishInItemLists.add(dishInItemList);
                            listHashMap.put(entry.getKey(), dishInItemLists);
                        }
                    }
                }
            }
        } else {
            HashMap<Category, List<DishInItemList>> listHashMapBackUp = new HashMap<>();
            for (Category category : categoryListFromDb) {
                listHashMapBackUp.put(category, null);
            }
            for (Item item : itemListFromDb) {
                if (item.getAvailable()) {
                    for (Map.Entry<Category, List<DishInItemList>> entry : listHashMapBackUp.entrySet()) {
                        if (entry.getKey().getCategoryId().equals(item.getCategory().getCategoryId())) {
                            List<DishInItemList> dishInItemLists = entry.getValue();
                            if (dishInItemLists == null) {
                                dishInItemLists = new ArrayList<>();
                            }
                            DishInItemList dishInItemList = createDish(item);
                            for (Map.Entry<Category, List<DishInItemList>> entry1 : listHashMap.entrySet()) {
                                for (DishInItemList dishInItemList1 : entry1.getValue()){
                                    if (dishInItemList.getDish().getItemId().equals(dishInItemList1.getDish().getItemId())){
                                        dishInItemList.setSelected(dishInItemList1.isSelected());
                                        dishInItemList.setQuantity(dishInItemList1.getQuantity());
                                    }
                                }
                            }

                            dishInItemLists.add(dishInItemList);
                            listHashMapBackUp.put(entry.getKey(), dishInItemLists);
                        }
                    }
                }
            }
            listHashMap = listHashMapBackUp;
        }


        initExpandableList(view);
    }


    private void initExpandableList(View view) {
        listView = (ExpandableListView) view.findViewById(R.id.itemExpandableList);
        //Only allow 1 expanded group
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int prevGrp = -1;

            @Override
            public void onGroupExpand(int i) {
                if (i != prevGrp) {
                    listView.collapseGroup(prevGrp);
                    prevGrp = i;
                }
            }
        });
        if (listHashMap == null) {
            loadDishList();
        } else {
            int quantity = 0;
            for (Map.Entry<Category, List<DishInItemList>> entry : listHashMap.entrySet()) {
                for (DishInItemList dishInItemList : entry.getValue()) {
                    if (dishInItemList.isSelected()) {
                        quantity += dishInItemList.getQuantity();
                    }
                }
            }
            TextView lblQuantity = view.findViewById(R.id.lblNumberOfDishRequested);
            lblQuantity.setText(String.valueOf(quantity));
        }
        listAdapter = new ExpandableItemListAdapter(getActivity(), categoryListFromDb, listHashMap);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (!parent.isGroupExpanded(groupPosition)) {
                    parent.expandGroup(groupPosition);
                } else {
                    parent.collapseGroup(groupPosition);
                }
                parent.setSelectedGroup(groupPosition);

                return true;
            }
        });
    }

    private DishInItemList createDish(Item item) {
        return new DishInItemList(item, 1, false);
    }

    public static HashMap<Category, List<DishInItemList>> getListHashMap() {
        return listHashMap;
    }

}
