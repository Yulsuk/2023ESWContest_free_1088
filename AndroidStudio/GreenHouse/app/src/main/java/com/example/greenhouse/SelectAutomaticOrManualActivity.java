package com.example.greenhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SelectAutomaticOrManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_automatic_or_manual);

        Button automaticBtn = (Button) findViewById(R.id.buttonAutomatic);
        Button manualBtn = (Button) findViewById(R.id.buttonManual);


        automaticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String controlVal = "T";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("접속성공");
                            if (success) {
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                ControlValRequest registerRequest = new ControlValRequest(controlVal, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectAutomaticOrManualActivity.this);
                queue.add(registerRequest);
                Intent intent = new Intent(SelectAutomaticOrManualActivity.this, SelectCorpsActivity.class);
                startActivity(intent);
            }
        });

        manualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String controlVal = "F";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("접속성공");
                            if (success) {
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                ControlValRequest registerRequest = new ControlValRequest(controlVal, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectAutomaticOrManualActivity.this);
                queue.add(registerRequest);
                Intent intent = new Intent(SelectAutomaticOrManualActivity.this, SelectOperationActivity.class);
                startActivity(intent);
            }
        });
    }
}
