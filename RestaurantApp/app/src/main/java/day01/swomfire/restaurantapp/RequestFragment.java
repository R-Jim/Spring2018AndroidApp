package day01.swomfire.restaurantapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomRVAdapter;
import data.model.OrderRequest;
import data.model.Request;
import data.remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;

public class RequestFragment extends Fragment {
    private RecyclerView rv;
    private List<Request> requestList;

    private RmaAPIService mService;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = RmaAPIUtils.getAPIService();

        rv = getView().findViewById(R.id.rv_request_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        loadRequestList();
        CustomRVAdapter adapter = new CustomRVAdapter(requestList);
        rv.setAdapter(adapter);
    }

    public void loadRequestList() {
        mService.getRequestList().enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                if (response.isSuccessful()) {
                    requestList = response.body();

                    CustomRVAdapter adapter = new CustomRVAdapter(requestList);
                    rv.setAdapter(adapter);
                    System.out.println("Loaded list");
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Fail to connect to server", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

}
