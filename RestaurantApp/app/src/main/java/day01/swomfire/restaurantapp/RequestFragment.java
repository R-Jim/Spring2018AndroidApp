package day01.swomfire.restaurantapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.RequestListAdapter;
import adapter.SwipeTouchHelper;
import data.model.OrderDetail;
import data.model.OrderRequest;
import data.model.Request;
import data.remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;

public class RequestFragment extends Fragment implements SwipeTouchHelper.RecyclerItemTouchHelperListener {
    private RecyclerView rv;
    private List<Request> requestList;

    private RmaAPIService mService;
    private RequestListAdapter adapter;
    private FrameLayout cardFrameLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mService = RmaAPIUtils.getAPIService();

        return inflater.inflate(R.layout.fragment_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardFrameLayout = getView().findViewById(R.id.card_framelayout);
        rv = getView().findViewById(R.id.rv_request_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

//        initList();

        RequestListAdapter adapter = new RequestListAdapter(requestList);
        this.adapter = adapter;
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        loadRequestList();

        SwipeTouchHelper itemTouchHelperCallback = new SwipeTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);


    }

    public void loadRequestList() {
        mService.getRequestOrderList().enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                if (response.isSuccessful()) {
                    requestList = response.body();

                    adapter = new RequestListAdapter(requestList);
                    rv.setAdapter(adapter);
                    System.out.println("Loaded list");
                    Log.d(this.getClass().getSimpleName(), "GET loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                System.out.println("Failed to load Order Request list");

            }
        });
    }

//    private void initList() {
//        requestList = new ArrayList<>();
//        OrderRequest orderRequest = new OrderRequest();
//        orderRequest.setTableId((long) 4);
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setItemSeq((long) 1);
//        orderDetail.setQuantity(3);
//        orderDetails.add(orderDetail);
//        orderRequest.setOrderDetailList(orderDetails);
//        requestList.add(orderRequest);
//    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (adapter == null) return;
        if (viewHolder instanceof RequestListAdapter.RequestViewHolder) {
            String table = requestList.get(position).getTableId() + "";

            // backup removed item
            final Request delRequest = requestList.get(viewHolder.getAdapterPosition());
            final int delPos = viewHolder.getAdapterPosition();

            adapter.removeItem(delPos);

            // show undo
            Snackbar snackbar = Snackbar.make(cardFrameLayout, "Request from " + table + " has been served!",
                    Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.restoredItem(delRequest, delPos);
                }
            });

            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    if (event == DISMISS_EVENT_TIMEOUT) {
                        onRequestDismissed(delRequest, delPos);
                    }
                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    }

    private void onRequestDismissed(Request request, int pos) {
        if (request == null) {
            return;
        }
        List<Request> dismissList = new ArrayList<>();
        dismissList.add(request);

        mService.sendDismissRequest(dismissList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("Request has been dismissed successfully");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to send dismiss request! Please try again later", Toast.LENGTH_SHORT).show();
                adapter.restoredItem(request, pos);
            }
        });
    }

}
