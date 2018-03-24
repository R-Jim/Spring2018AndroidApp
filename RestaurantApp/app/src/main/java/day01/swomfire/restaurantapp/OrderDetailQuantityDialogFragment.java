package day01.swomfire.restaurantapp;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import data.model.Request;
import data.remote.RmaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RmaAPIUtils;
import utils.StyleUtils;


public class OrderDetailQuantityDialogFragment extends DialogFragment {
    private TextView itemQuantityText;
    private String[] itemPositionAndQuantity = null;
    private Integer requestDetailId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View quantityDialog = inflater.inflate(R.layout.fragment_dialog_item_request_quantity, null);
        builder.setView(quantityDialog);
        StyleUtils.setGradientBackground(quantityDialog, R.id.itemItemQuantityDialog,
                new int[]{getContext().getResources().getColor(R.color.colorOrderDetailDialogBackground1),
                        getContext().getResources().getColor(R.color.colorOrderDetailDialogBackground2)}, StyleUtils.GradientMode.TOP_BOTTOM.getMode());
        // Get current item quantity
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            itemPositionAndQuantity = bundle.getStringArray("ItemPositionAndQuantity");
        }


        // Set Quantity for dialog
        itemQuantityText = quantityDialog.findViewById(R.id.itemItemQuantityDialogQuantity);
        itemQuantityText.setText(itemPositionAndQuantity[1]);
        // Change button edit

        Button btnChange = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogChange);
        btnChange.setOnClickListener(view -> {
            int quantityNew = Integer.valueOf(String.valueOf(itemQuantityText.getText()));
            requestDetailId = Integer.parseInt(itemPositionAndQuantity[0]);
            List<Request> requestDetails = OrderDetailOrderingTabFragment.getRequestDetails();
            Request requestDetail = null;
            for (Request detail : requestDetails) {
                if (detail.getSeq().equals(requestDetailId)) {
                    requestDetail = detail;
                    if (quantityNew > 0) {
                        requestDetail.setQuantity(quantityNew);
                    } else {
                        requestDetail.setChangeable(false);
                    }
                    break;
                }
            }

            sendRequestDetailToServer(requestDetail);

            OrderDetailQuantityDialogFragment.this.dismiss();
        });
        // Add button edit
        Button btnAdd = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogAdd);
        btnAdd.setOnClickListener(view -> {
            int quantity = Integer.parseInt(String.valueOf(itemQuantityText.getText()));
            if (++quantity <= 10) {
                itemQuantityText.setText(String.valueOf(quantity));

            }
        });
        // Sub button edit
        Button btnSub = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogSub);
        btnSub.setOnClickListener(view -> {
            int quantity = Integer.parseInt(String.valueOf(itemQuantityText.getText()));
            if (--quantity >= 0) {
                itemQuantityText.setText(String.valueOf(quantity));
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() != null) {
            Fragment frg = getActivity().getSupportFragmentManager().findFragmentByTag("ORDERING_TAB");
            final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }
    }

    private void sendRequestDetailToServer(Request requestDetail) {

        RmaAPIService rmaAPIService = RmaAPIUtils.getAPIService();
        rmaAPIService.sendRequestDetail(LoginActivity.token, requestDetail).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        if (getActivity() != null) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Request change success", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        if (getActivity() != null) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Request change success", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                if (getActivity() != null) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Fail to connect to server", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
