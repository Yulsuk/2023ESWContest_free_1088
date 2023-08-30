package com.example.greenhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SelectCorpsActivity extends AppCompatActivity {
    static int selectedCorp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_corps);

        TextView selectedCorpsTextView = (TextView)findViewById(R.id.textViewSelectedCorps);
        ImageView cucumber = (ImageView)findViewById(R.id.imageViewCucumber);
        ImageView pepper = (ImageView)findViewById(R.id.imageViewPepper);
        ImageView strewberry = (ImageView)findViewById(R.id.imageViewStrewberry);
        ImageView tomato = (ImageView)findViewById(R.id.imageViewTomato);

        if(selectedCorp == 0){
            selectedCorpsTextView.setText("작물을 선택하시오.");
        }
        else if(selectedCorp == 1){
            selectedCorpsTextView.setText("선택된 작물 : 오이");
        }
        else if(selectedCorp == 2){
            selectedCorpsTextView.setText("선택된 작물 : 고추");
        }
        else if(selectedCorp == 3){
            selectedCorpsTextView.setText("선택된 작물 : 딸기");
        }
        else if(selectedCorp == 4){
            selectedCorpsTextView.setText("선택된 작물 : 토마토");
        }

        cucumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCorps = "1";
                selectedCorpsTextView.setText("선택된 작물 : 오이");
                selectedCorp = 1;

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

                SelectCorpsRequest registerRequest = new SelectCorpsRequest(selectedCorps, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectCorpsActivity.this);
                queue.add(registerRequest);
            }
        });

        pepper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCorps = "2";
                selectedCorpsTextView.setText("선택된 작물 : 고추");
                selectedCorp = 2;

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

                SelectCorpsRequest registerRequest = new SelectCorpsRequest(selectedCorps, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectCorpsActivity.this);
                queue.add(registerRequest);
            }
        });

        strewberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCorps = "3";
                selectedCorpsTextView.setText("선택된 작물 : 딸기");
                selectedCorp = 3;

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

                SelectCorpsRequest registerRequest = new SelectCorpsRequest(selectedCorps, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectCorpsActivity.this);
                queue.add(registerRequest);
            }
        });

        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCorps = "4";
                selectedCorpsTextView.setText("선택된 작물 : 토마토");
                selectedCorp = 4;

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

                SelectCorpsRequest registerRequest = new SelectCorpsRequest(selectedCorps, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectCorpsActivity.this);
                queue.add(registerRequest);
            }
        });

    }


}