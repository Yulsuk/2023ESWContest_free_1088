package com.example.greenhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SelectOperationActivity extends AppCompatActivity {
    //static boolean operGHO = false, operWS = false,  operV = false, operHW = false, operW = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_operation);

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
        SelectOperationRequest registerRequest = new SelectOperationRequest("F", "F", "F", "F", "F", "F", "F", responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
        queue.add(registerRequest);

        Switch greenhouseOpenOrClose = (Switch)findViewById(R.id.switchGreenHouseSide);
        Switch waterSprayerOperOrStop = (Switch)findViewById(R.id.switchWaterSpray);
        Switch heatingWireOperOrStop = (Switch)findViewById(R.id.switchHeatingWire);
        Switch ventilatorOperOrStop = (Switch)findViewById(R.id.switchVentilator);
        Switch wiperOperOrStop = (Switch)findViewById(R.id.switchWiper);
        Switch waterSprayerOutsideOperOrStop = (Switch)findViewById(R.id.switchWaterSprayOutside);

        //greenhouseOpenOrClose.setChecked(operGHO);
        greenhouseOpenOrClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String greenhouseOpen = "F";
                String greenhouseClose = "F";
                if(isChecked){
                    greenhouseOpen = "T";
                    greenhouseClose = "F";
                    //operGHO = true;

                }
                else{
                    greenhouseOpen = "F";
                    greenhouseClose = "T";
                    //operGHO = false;
                }
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
                SelectOperationRequest registerRequest = new SelectOperationRequest(greenhouseOpen, greenhouseClose, "0", "0", "0", "0", "0",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
                queue.add(registerRequest);

            }
        });

        //waterSprayerOperOrStop.setChecked(operWS);
        waterSprayerOperOrStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String waterSprayerOper = "F";
                if(isChecked){
                    waterSprayerOper = "T";
                    //operWS = true;
                }
                else{
                    waterSprayerOper = "F";
                    //operWS = false;
                }
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
                SelectOperationRequest registerRequest = new SelectOperationRequest("0", "0", waterSprayerOper, "0","0","0","0",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
                queue.add(registerRequest);
            }
        });

        //heatingWireOperOrStop.setChecked(operHW);
        heatingWireOperOrStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String heatingWireOper = "F";
                if(isChecked){
                    heatingWireOper = "T";
                    //operHW = true;
                }
                else{
                    heatingWireOper = "F";
                    //operHW = false;
                }
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
                SelectOperationRequest registerRequest = new SelectOperationRequest("0", "0", "0", "0",heatingWireOper,"0","0",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
                queue.add(registerRequest);
            }
        });

        //ventilatorOperOrStop.setChecked(operV);
        ventilatorOperOrStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String ventilatorOper = "F";
                if(isChecked){
                    ventilatorOper = "T";
                    //operV = true;
                }
                else{
                    ventilatorOper = "F";
                    //operV = false;
                }
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
                SelectOperationRequest registerRequest = new SelectOperationRequest("0", "0", "0", ventilatorOper,"0","0","0",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
                queue.add(registerRequest);
            }
        });

        //wiperOperOrStop.setChecked(operW);
        wiperOperOrStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String wiperOper = "F";
                if(isChecked){
                    wiperOper = "T";
                    //operW = true;
                }
                else{
                    wiperOper = "F";
                    //operW = false;
                }
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
                SelectOperationRequest registerRequest = new SelectOperationRequest("0", "0", "0", "0","0",wiperOper,"0", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
                queue.add(registerRequest);
            }
        });

        waterSprayerOutsideOperOrStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String operateWaterSprayerOutside = "F";
                if(isChecked){
                    operateWaterSprayerOutside = "T";
                    //operWS = true;
                }
                else{
                    operateWaterSprayerOutside = "F";
                    //operWS = false;
                }
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
                SelectOperationRequest registerRequest = new SelectOperationRequest("0", "0", "0", "0","0","0",operateWaterSprayerOutside,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectOperationActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}