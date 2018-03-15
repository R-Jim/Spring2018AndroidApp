package day01.swomfire.restaurantapp;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

public class ItemQuantityDialogFragment extends DialogFragment {
    private static View view;
    private static LinearLayout linearLayout;
    private int quantityOld;
    private TextView currentItemQuantityText;
    private TextView itemQuantityText;
    private TextView lblId;

    public void setUp(View view, LinearLayout linearLayout) {
        this.view = view;
        this.linearLayout = linearLayout;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View quantityDialog = inflater.inflate(R.layout.fragment_dialog_item_quan, null);
        Drawable background = linearLayout.getBackground();
        int color = 0;
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }
        quantityDialog.setBackgroundColor(color);
        builder.setView(quantityDialog);

        // Get current item quantity
        currentItemQuantityText = view.findViewById(R.id.lblItemItemQuantity);
        quantityOld = Integer.parseInt(String.valueOf(currentItemQuantityText.getText()));

        View parent1 = (View) view.getParent();
        View parent2 = (View) parent1.getParent();
        lblId = parent2.findViewById(R.id.lblListItemId);

        // Set Quantity for dialog
        itemQuantityText = quantityDialog.findViewById(R.id.itemItemQuantityDialogQuantity);
        itemQuantityText.setText(currentItemQuantityText.getText());

        // Change button edit
        Button btnChange = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                MainActivity.closeDialog();
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
                if (--quantity >= 1) {
                    itemQuantityText.setText(String.valueOf(quantity));
                }
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
