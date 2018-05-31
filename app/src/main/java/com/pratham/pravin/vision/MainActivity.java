package com.pratham.pravin.vision;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pratham.pravin.vision.gps.EventBusMSG;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gps_text)
    TextView gps_text;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        } else {
            showTimeDialog();
        }

    }

    private void showTimeDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Note down the time taken in getting location...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Subscribe
    public void onEvent(Short msg) {
        if (msg == EventBusMSG.UPDATE_TRACK) {
            gps_text.setText(getDate(VisionApplication.location.getTime(), "dd/MM/yyyy hh:mm:ss.SSS"));
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @OnClick(R.id.google)
    public void startGoogleAPI() {
        startActivity(new Intent(MainActivity.this, com.pratham.pravin.vision.google.MainActivity.class));
    }

    @OnClick(R.id.microsoft)
    public void startMicrosoftAPI() {
        startActivity(new Intent(MainActivity.this, com.pratham.pravin.vision.microsoft.activity.MainActivity.class));
    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @param dateFormat   Date format
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        VisionApplication.getInstance().getLocation(MainActivity.this);
                        showTimeDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }
}