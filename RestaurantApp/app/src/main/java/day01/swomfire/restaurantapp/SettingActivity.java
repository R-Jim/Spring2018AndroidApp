package day01.swomfire.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ToggleButton;

public class SettingActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private boolean isNotiOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LoginActivity.token != null) {
            setContentView(R.layout.activity_setting);
            setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.setting_preference), Context.MODE_PRIVATE);
            this.isNotiOn = sharedPreferences.getBoolean(getString(R.string.setting_preference_noti), true);


            toggleButton = findViewById(R.id.tb_noti);
            toggleButton.setChecked(isNotiOn);
        } else {
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void onAccountSettingClick(View view) {
    }

    public void onLogoutClick(View view) {
        LoginActivity.token = null;
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onNotiChangeClick(View view) {
        if (toggleButton == null) {
            return;
        }
        isNotiOn = !isNotiOn;
        toggleButton.setChecked(isNotiOn);
    }
}
