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
import Adapter.ExpandableListAdapter;


public class TableFragment extends Fragment {

    private ExpandableListView listView;
    private android.widget.ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ExpandableListView) view.findViewById(R.id.tableExpandableList);

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
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listHashMap);
        listView.setAdapter(listAdapter);

    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("ABC");
        listDataHeader.add("ADC");
        listDataHeader.add("ASwC");
        listDataHeader.add("Asadsa");

        List<String> list1 = new ArrayList<>();
        list1.add("1");

        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");

        List<String> list3 = new ArrayList<>();
        list3.add("1");
        list3.add("3");
        list3.add("4");

        List<String> list4 = new ArrayList<>();
        list4.add("2");
        list4.add("24");

        listHashMap.put(listDataHeader.get(0), list1);
        listHashMap.put(listDataHeader.get(1), list2);
        listHashMap.put(listDataHeader.get(2), list3);
        listHashMap.put(listDataHeader.get(3), list4);
    }
}
