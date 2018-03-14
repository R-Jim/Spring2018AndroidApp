package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.ExpandableItemListAdapter;

public class ItemFragment extends Fragment {


    private ExpandableListView listView;
    private android.widget.ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ExpandableListView) view.findViewById(R.id.itemExpandableList);
        System.out.println(listView.hashCode());
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
        initData();
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
        listDataHeader.add("Item5");
        listDataHeader.add("Item6");
        listDataHeader.add("Item7");

        List<String> list1 = new ArrayList<>();
        list1.add("Pizza");

        List<String> list2 = new ArrayList<>();
        list2.add("Bugger");
        list2.add("Meat");

        List<String> list3 = new ArrayList<>();
        list3.add("Coffee");
        list3.add("cappuccino");
        list3.add("latte");

        List<String> list4 = new ArrayList<>();
        list4.add("Steak");
        list4.add("Pork");

        listHashMap.put(listDataHeader.get(0), list1);
        listHashMap.put(listDataHeader.get(1), list2);
        listHashMap.put(listDataHeader.get(2), list3);
        listHashMap.put(listDataHeader.get(3), list4);
        listHashMap.put(listDataHeader.get(4), list4);
        listHashMap.put(listDataHeader.get(5), list4);
        listHashMap.put(listDataHeader.get(6), list4);
    }


}
