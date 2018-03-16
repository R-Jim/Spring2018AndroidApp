package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomRVAdapter;
import adapter.ItemRequestRVAdapter;
import adapter.TableRVAdapter;
import data.model.OrderRequest;
import data.remote.APIService;
import model.Table;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.APIUtils;


public class TableFragment extends Fragment {

    private List<OrderRequest> requestList;
    private RecyclerView recyclerView;
    private TableRVAdapter tableRVAdapter;
    private List<Table> tables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTableList();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_table_list);
        tableRVAdapter = new TableRVAdapter(tables);
        GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tableRVAdapter);


    }

/*    public void loadRequestList() {
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
    }*/

    private void initTableList() {
        tables = new ArrayList<>();
        tables.add(
                new Table(1, true, null)
        );
        tables.add(
                new Table(2, false, null)
        );
        tables.add(
                new Table(3, true, null)
        );
    }
}
