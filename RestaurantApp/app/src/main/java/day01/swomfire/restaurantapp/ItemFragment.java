package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import model.Dish;
import model.DishInItemList;


public class ItemFragment extends Fragment {


    private ExpandableListView listView;
    private ExpandableItemListAdapter listAdapter;
    private List<String> listDataHeader;
    private static HashMap<String, List<DishInItemList>> listHashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            initData();
        } else {
            int quantity = 0;
            for (Map.Entry<String, List<DishInItemList>> entry : listHashMap.entrySet()) {
                for (DishInItemList dishInItemList : entry.getValue()) {
                    if (dishInItemList.isSelected()) {
                        quantity += dishInItemList.getQuantity();
                    }
                }
            }
            TextView lblQuantity = view.findViewById(R.id.lblNumberOfDishRequested);
            lblQuantity.setText(String.valueOf(quantity));
        }
        listAdapter = new ExpandableItemListAdapter(getActivity(), listDataHeader, listHashMap);
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

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Item1");
        listDataHeader.add("Item2");
        listDataHeader.add("Item3");
        listDataHeader.add("Item4");

        List<DishInItemList> list1 = new ArrayList<>();

        list1.add(createDish("Pizza"));

        List<DishInItemList> list2 = new ArrayList<>();


        list2.add(createDish("Bugger"));
        list2.add(createDish("Meat"));

        List<DishInItemList> list3 = new ArrayList<>();
        list3.add(createDish("Coffee"));
        list3.add(createDish("cappuccino"));
        list3.add(createDish("latte"));

        List<DishInItemList> list4 = new ArrayList<>();
        list4.add(createDish("Steak"));
        list4.add(createDish("Pork"));

        listHashMap.put(listDataHeader.get(0), list1);
        listHashMap.put(listDataHeader.get(1), list2);
        listHashMap.put(listDataHeader.get(2), list3);
        listHashMap.put(listDataHeader.get(3), list4);

    }

    private DishInItemList createDish(String name) {
        Dish dish = new Dish();
        dish.setName(name);
        return new DishInItemList(dish, 1, false);
    }

    public static HashMap<String, List<DishInItemList>> getListHashMap() {
        return listHashMap;
    }

}
