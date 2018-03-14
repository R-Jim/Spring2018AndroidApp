package day01.swomfire.restaurantapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class RequestOrderActivity extends AppCompatActivity {

    private static RequestOrderTableDialogFragment requestOrderTableDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order);
    }

    public void backToMenu(View view) {
        this.finish();
    }

    public void chooseTable(View view) {
        FragmentManager fm = getSupportFragmentManager();
        requestOrderTableDialogFragment = new RequestOrderTableDialogFragment();
        requestOrderTableDialogFragment.show(fm, "request_order_table_dialog_fragment");
    }
}
