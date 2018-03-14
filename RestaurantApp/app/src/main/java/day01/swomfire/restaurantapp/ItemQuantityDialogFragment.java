package day01.swomfire.restaurantapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;


public class ItemQuantityDialogFragment extends DialogFragment {
    private View view;
    private LinearLayout linearLayout;
    private TextView currentItemQuantityText;
    private TextView itemQuantityText;

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
        View quantityDialog = inflater.inflate(R.layout.item_quantity_dialog_fragment, null);
        Drawable background = linearLayout.getBackground();
        int color = 0;
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }
        quantityDialog.setBackgroundColor(color);
        builder.setView(quantityDialog);

        // Get current item quantity
        currentItemQuantityText = view.findViewById(R.id.lblItemItemQuantity);

        // Set Quantity for dialog
        itemQuantityText = quantityDialog.findViewById(R.id.itemItemQuantityDialogQuantity);
        itemQuantityText.setText(currentItemQuantityText.getText());

        // Change button edit
        Button btnChange = quantityDialog.findViewById(R.id.btnItemItemQuantityDialogChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItemQuantityText.setText(itemQuantityText.getText());
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
