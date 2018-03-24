package day01.swomfire.restaurantapp;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import adapter.ExpandableItemListAdapter;
import model.DishInItemList;
import utils.StyleUtils;

public class ItemQuantityDialogFragment extends DialogFragment {
    private int quantityOld;
    private TextView currentItemQuantityText;
    private TextView itemQuantityText;
    private TextView lblId;
    private static View view1;

    public void setUp(View view) {
        view1 = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View quantityDialog = inflater.inflate(R.layout.fragment_dialog_item_quantity, null);
        View view = view1.findViewById(R.id.itemItem);
        GradientDrawable background = (GradientDrawable) view.getBackground().getConstantState().newDrawable();
        quantityDialog.setBackground(background);
        builder.setView(quantityDialog);

        // Get current item quantity
        currentItemQuantityText = view.findViewById(R.id.lblItemItemQuantity);
        quantityOld = Integer.parseInt(String.valueOf(currentItemQuantityText.getText()));

        lblId = view.findViewById(R.id.lblListItemId);

        // Set Quantity for dialog
        itemQuantityText = quantityDialog.findViewById(R.id.itemItemQuantityDialogQuantity);
        itemQuantityText.setText(currentItemQuantityText.getText());

        // Change button edit
        Button btnChange = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogChange);
        btnChange.setOnClickListener(view1 -> {
            currentItemQuantityText.setText(itemQuantityText.getText());
            DishInItemList dishInItemList = ExpandableItemListAdapter.findDish(String.valueOf(lblId.getText()));
            int quantityNew = Integer.valueOf(String.valueOf(itemQuantityText.getText()));
            dishInItemList.setQuantity(quantityNew);

            if (dishInItemList.isSelected()) {
                TextView lblNumberOfDishRequested = getActivity().findViewById(R.id.lblNumberOfDishRequested);
                String quantityStr = String.valueOf(lblNumberOfDishRequested.getText());
                int quantity = Integer.parseInt(quantityStr);
                quantity += (quantityNew - quantityOld);
                lblNumberOfDishRequested.setText(String.valueOf(quantity));
            }
            ItemQuantityDialogFragment.this.dismiss();
        });
        // Add button edit
        Button btnAdd = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogAdd);
        btnAdd.setOnClickListener(view12 -> {
            int quantity = Integer.parseInt(String.valueOf(itemQuantityText.getText()));
            if (++quantity <= 10) {
                itemQuantityText.setText(String.valueOf(quantity));

            }
        });
        // Sub button edit
        Button btnSub = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogSub);
        btnSub.setOnClickListener(view13 -> {
            int quantity = Integer.parseInt(String.valueOf(itemQuantityText.getText()));
            if (--quantity >= 1) {
                itemQuantityText.setText(String.valueOf(quantity));
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
