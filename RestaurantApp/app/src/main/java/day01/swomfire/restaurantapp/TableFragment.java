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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

    private RecyclerView recyclerView;
    private TableRVAdapter tableRVAdapter;
    private List<Table> tables;
    private Spinner spinner;
    private static final String[] paths = {"All", "Free", "Ordering"};
    private static Boolean status;
    private TextView totalTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        totalTable = view.findViewById(R.id.lblNumberOfTable);

        initSpinner(view);

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
        initRecycleView(initTableList(tables, status));


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
    private void initRecycleView(List<Table> tables) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_table_list);
        tableRVAdapter = new TableRVAdapter(tables);
        GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tableRVAdapter);
    }

    private void initSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.tableFilterSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.table_spinner_item, paths);

        adapter.setDropDownViewResource(R.layout.table_spinner_row);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                status = null;
                                initRecycleView(initTableList(tables, status));

                                break;
                            case 1:
                                status = true;
                                initRecycleView(initTableList(tables, status));
                                break;
                            case 2:
                                status = false;
                                initRecycleView(initTableList(tables, status));
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    private List<Table> initTableList(List<Table> tables, Boolean status) {
        List<Table> tablesAfterFilter = new ArrayList<>();
        if (status == null) {
            tablesAfterFilter = tables;
        } else {
            for (Table table : tables) {
                if (table.isStatus() == status) {
                    tablesAfterFilter.add(table);
                }
            }
        }
        totalTable.setText(String.valueOf(tablesAfterFilter.size()));
        return tablesAfterFilter;
    }
}
