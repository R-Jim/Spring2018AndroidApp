package day01.swomfire.restaurantapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ExpandableItemListAdapter;
import data.model.Category;
import data.model.Item;
import data.remote.RmaAPIService;
import model.DishInItemList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;


public class ItemFragment extends Fragment {


    private ExpandableListView listView;
    private HashMap<Category, List<DishInItemList>> listHashMap;
    private List<Item> itemListFromDb;
    private List<Category> categoryListFromDb;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        loadDishList();
    }


    private void loadDishList() {
        RmaAPIService mService = RmaAPIUtils.getAPIService();

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
                loadCategoryList();
            }
        });

    }

    private void loadCategoryList() {
        RmaAPIService mService = RmaAPIUtils.getAPIService();

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
                initData();
            }
        });

    }

    private void initData() {
        if (listHashMap == null) {
            listHashMap = new HashMap<>();
            addItemAndCategoryToHashmap(listHashMap);
        } else {
            HashMap<Category, List<DishInItemList>> listHashMapBackUp = new HashMap<>();
            addItemAndCategoryToHashmap(listHashMapBackUp);
            listHashMap = listHashMapBackUp;
        }


        initExpandableList(view);
    }

    private void addItemAndCategoryToHashmap(HashMap<Category, List<DishInItemList>> listHashMap) {
        if (categoryListFromDb != null) {
            for (Category category : categoryListFromDb) {
                listHashMap.put(category, null);
            }
        }
        if (itemListFromDb != null) {
            for (Item item : itemListFromDb) {
                if (item.getAvailable()) {
                    for (Map.Entry<Category, List<DishInItemList>> entry : listHashMap.entrySet()) {
                        if (entry.getKey().getCategoryId().equals(item.getCategory().getCategoryId())) {
                            List<DishInItemList> dishInItemLists = entry.getValue();
                            if (dishInItemLists == null) {
                                dishInItemLists = new ArrayList<>();
                            }
                            DishInItemList dishInItemList = createDish(item);
                            for (Map.Entry<Category, List<DishInItemList>> entry1 : this.listHashMap.entrySet()) {
                                if (entry1.getValue() != null) {
                                    for (DishInItemList dishInItemList1 : entry1.getValue()) {
                                        if (dishInItemList.getDish().getItemId().equals(dishInItemList1.getDish().getItemId())) {
                                            dishInItemList.setSelected(dishInItemList1.isSelected());
                                            dishInItemList.setQuantity(dishInItemList1.getQuantity());
                                        }
                                    }
                                }
                            }

                            dishInItemLists.add(dishInItemList);
                            listHashMap.put(entry.getKey(), dishInItemLists);
                        }
                    }
                }
            }
        }
    }


    private void initExpandableList(View view) {
        listView = view.findViewById(R.id.itemExpandableList);
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
                if (entry.getValue() != null) {
                    for (DishInItemList dishInItemList : entry.getValue()) {
                        if (dishInItemList.isSelected()) {
                            quantity += dishInItemList.getQuantity();
                        }
                    }
                }
            }
            TextView lblQuantity = view.findViewById(R.id.lblNumberOfDishRequested);
            lblQuantity.setText(String.valueOf(quantity));
        }
        ExpandableItemListAdapter listAdapter = new ExpandableItemListAdapter(getActivity(), categoryListFromDb, listHashMap);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {

            if (!parent.isGroupExpanded(groupPosition)) {
                parent.expandGroup(groupPosition);
            } else {
                parent.collapseGroup(groupPosition);
            }
            parent.setSelectedGroup(groupPosition);

            return true;
        });
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        HashMap<String, List<DishInItemList>> stringListHashMap = DishInItemList.convertHashmapToStringKey(listHashMap);
        Gson gson = new Gson();
        String json = gson.toJson(stringListHashMap);
        prefsEditor.putString("listDishInListStringHashMap", json);
        json = gson.toJson(categoryListFromDb);
        prefsEditor.putString("listCategory", json);
        prefsEditor.commit();
    }

    private DishInItemList createDish(Item item) {
        return new DishInItemList(item, 1, false);
    }


}
