package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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


import adapter.TableRVAdapter;
import data.remote.RmaAPIService;
import model.Status;
import data.model.Table;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;


public class TableFragment extends Fragment {


    private List<Table> tables;
    private static final String[] paths = {"All", "Free", "Ordering"};
    private TextView totalTable;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        totalTable = view.findViewById(R.id.lblNumberOfTable);


        initSpinner(view);
        tables = new ArrayList<>();

        loadRequestList();

    }

    public void loadRequestList() {
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getTableList().enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                if (response.isSuccessful()) {
                    tables = response.body();
                    initRecycleView(initTableList(tables, null));
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                System.out.println("Failed to load item list");
            }
        });
    }

    private void initRecycleView(List<Table> tables) {
        RecyclerView recyclerView = getActivity().findViewById(R.id.rv_table_list);
        TableRVAdapter tableRVAdapter = new TableRVAdapter(tables, TableRVAdapter.RVViewMode.TABLE_TAB.getViewMode());
        GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(gLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tableRVAdapter);
    }

    private void initSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.tableFilterSpinner);
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
                                initRecycleView(initTableList(tables, null));

                                break;
                            case 1:
                                initRecycleView(initTableList(tables, Status.AVAIABLE));
                                break;
                            case 2:
                                initRecycleView(initTableList(tables, Status.OCCUPIED));
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    private List<Table> initTableList(List<Table> tables, Status status) {
        List<Table> tablesAfterFilter = new ArrayList<>();
        if (status == null) {
            tablesAfterFilter = tables;
        } else {
            for (Table table : tables) {
                if (table.getStatusByStatusSeqId().getStatusId().equals(status.getStatusId())) {
                    tablesAfterFilter.add(table);
                }
            }
        }
        totalTable.setText(String.valueOf(tablesAfterFilter.size()));
        return tablesAfterFilter;
    }
}
