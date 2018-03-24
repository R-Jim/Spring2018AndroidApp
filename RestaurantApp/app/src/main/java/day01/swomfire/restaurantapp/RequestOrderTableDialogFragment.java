package day01.swomfire.restaurantapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import adapter.TableRVAdapter;
import data.model.Table;
import data.remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;

public class RequestOrderTableDialogFragment extends DialogFragment {

    private List<Table> tables;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View quantityDialog = inflater.inflate(R.layout.fragment_dialog_item_request_table, null);
        builder.setView(quantityDialog);
        loadRequestList(quantityDialog);
        return builder.create();
    }


    public void loadRequestList(View view) {
        RmaAPIService mService = RmaAPIUtils.getAPIService();
        mService.getTableList(LoginActivity.token).enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                if (response.isSuccessful()) {
                    tables = response.body();
                    initRV(view);
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                System.out.println("Failed to load table list");
            }
        });
    }

    private void initRV(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.requestOrderChooseTable);
        TableRVAdapter tableRVAdapter = new TableRVAdapter(tables, TableRVAdapter.RVViewMode.REQUEST_ORDER_CHOOSE_TABLE.getViewMode());
        if (getActivity() != null) {
            GridLayoutManager gLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
            recyclerView.setLayoutManager(gLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(tableRVAdapter);
        }
    }
}
