package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import adapter.CustomRVAdapter;
import data.model.OrderRequest;
import data.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.APIUtils;


public class TableFragment extends Fragment {

    private RecyclerView rv;
    private android.widget.ExpandableListAdapter listAdapter;
    private List<OrderRequest> requestList;

    private APIService mService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = APIUtils.getAPIService();

        rv = getView().findViewById(R.id.rv_request_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        loadRequestList();

        //Only allow 1 expanded group
//        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int prevGrp = -1;
//            @Override
//            public void onGroupExpand(int i) {
//                if (i != prevGrp) {
//                    listView.collapseGroup(prevGrp);
//                    prevGrp = i;
//                }
//            }
//        });
//        initData();


    }

    public void loadRequestList() {
        mService.getRequestList().enqueue(new Callback<List<OrderRequest>>() {
            @Override
            public void onResponse(Call<List<OrderRequest>> call, Response<List<OrderRequest>> response) {
                if (response.isSuccessful()) {
                    requestList = response.body();

                    CustomRVAdapter adapter = new CustomRVAdapter(requestList);
                    rv.setAdapter(adapter);
                    System.out.println("Loaded list");
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<OrderRequest>> call, Throwable t) {
                System.out.println("Failed to load item list");
            }
        });
    }

//    private void initData() {
//
//
//        listDataHeader.add("ABC");
//        listDataHeader.add("ADC");
//        listDataHeader.add("ASwC");
//        listDataHeader.add("Asadsa");
//
//        List<String> list1 = new ArrayList<>();
//        list1.add("1");
//
//        List<String> list2 = new ArrayList<>();
//        list2.add("1");
//        list2.add("2");
//
//        List<String> list3 = new ArrayList<>();
//        list3.add("1");
//        list3.add("3");
//        list3.add("4");
//
//        List<String> list4 = new ArrayList<>();
//        list4.add("2");
//        list4.add("24");
//
//        listHashMap.put(listDataHeader.get(0), list1);
//        listHashMap.put(listDataHeader.get(1), list2);
//        listHashMap.put(listDataHeader.get(2), list3);
//        listHashMap.put(listDataHeader.get(3), list4);
//    }
}
