package day01.swomfire.restaurantapp;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import model.DishInItemList;
import model.DishInReceipt;
import utils.StyleUtils;


public class OrderDetailQuantityDialogFragment extends DialogFragment {
    private static View view;
    private TextView itemQuantityText;
    private TextView lblId;
    private String[] itemPositionAndQuantity = null;

    public void setUp(View view) {
        this.view = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View quantityDialog = inflater.inflate(R.layout.fragment_dialog_item_request_quantity, null);
        builder.setView(quantityDialog);
        StyleUtils.setGradientBackground(quantityDialog, R.id.itemItemQuantityDialog,
                new int[]{view.getResources().getColor(R.color.colorDoneOrderBackground1),
                        view.getResources().getColor(R.color.colorDoneOrderBackground2)}, StyleUtils.GradientMode.TOP_BOTTOM.getMode());
        // Get current item quantity
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            itemPositionAndQuantity = bundle.getStringArray("ItemPositionAndQuantity");
        }

        View parent1 = (View) view.getParent();
        View parent2 = (View) parent1.getParent();
        lblId = parent2.findViewById(R.id.itemOrderDetailId);

        // Set Quantity for dialog
        itemQuantityText = quantityDialog.findViewById(R.id.itemItemQuantityDialogQuantity);
        itemQuantityText.setText(itemPositionAndQuantity[1]);

        // Change button edit
        Button btnChange = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityNew = Integer.valueOf(String.valueOf(itemQuantityText.getText()));
                String[] itemPositionAndNewQuantity = {itemPositionAndQuantity[0], String.valueOf(quantityNew)};
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("itemPositionAndNewQuantity", itemPositionAndNewQuantity[0] + "," + itemPositionAndNewQuantity[1]);
                editor.commit();
                OrderDetailQuantityDialogFragment.this.dismiss();
            }
        });
        // Add button edit
        Button btnAdd = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(String.valueOf(itemQuantityText.getText()));
                if (++quantity <= 10) {
                    itemQuantityText.setText(String.valueOf(quantity));

                }
            }
        });
        // Sub button edit
        Button btnSub = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogSub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(String.valueOf(itemQuantityText.getText()));
                if (--quantity >= 0) {
                    itemQuantityText.setText(String.valueOf(quantity));
                }
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment frg = null;
        frg = getActivity().getSupportFragmentManager().findFragmentByTag("ORDERING_TAB");
        final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
}
