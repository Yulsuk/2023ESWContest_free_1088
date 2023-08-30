package com.example.greenhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Button selectAOMBtn = (Button)findViewById(R.id.buttonSelectAutomaticOrManual);
        Button monitoringTempAndHumBtn = (Button)findViewById(R.id.buttonMonitoringTempAndHum);
        Button selectDetectTimeBtn = (Button)findViewById(R.id.buttonSelectTime);
        Button watchDetectedImageBtn = (Button)findViewById(R.id.buttonWatchDetectedImage);
        Button monitoringMicrodust = (Button)findViewById(R.id.buttonMonitoringMicrodust);

        selectAOMBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectAutomaticOrManualActivity.class);
                startActivity(intent);
            }
        });
        monitoringTempAndHumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MonitoringTempAndHumActivity.class);
                startActivity(intent);
            }
        });
        selectDetectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectTimeActivity.class);
                startActivity(intent);
            }
        });

        watchDetectedImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://220.69.240.29/viewImg.php"));
                startActivity(intent);
            }
        });

        monitoringMicrodust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MonitoringMicrodustActivity.class);
                startActivity(intent);
            }
        });
    }
}