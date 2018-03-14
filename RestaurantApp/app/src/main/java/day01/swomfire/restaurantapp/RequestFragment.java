package day01.swomfire.restaurantapp;

import android.content.Context;
import android.net.Uri;
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

public class RequestFragment extends Fragment {
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

}
