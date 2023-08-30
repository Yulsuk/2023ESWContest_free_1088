package com.example.greenhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class SelectTimeActivity extends AppCompatActivity {
    static String selectedStartTime, selectedEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        TimePicker timePickerStart = (TimePicker)findViewById(R.id.timePickerStart);
        TimePicker timePickerEnd = (TimePicker)findViewById(R.id.timePickerEnd);
        Button timeSetButton = (Button)findViewById(R.id.buttonTimeSet);
        TextView selectedTime = (TextView)findViewById(R.id.textViewSelectedTime);

        timePickerStart.setIs24HourView(true);
        timePickerEnd.setIs24HourView(true);

        selectedTime.setText("선택된 시간 : " + selectedStartTime + " ~ " + selectedEndTime);

        timeSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String timeStartHour, timeStartMinute;
                String timeEndHour, timeEndMinute;
                String timeStart, timeEnd;
                if(timePickerStart.getHour() < 10){
                    timeStartHour = '0'+ Integer.toString(timePickerStart.getHour());
                }
                else{
                    timeStartHour = Integer.toString(timePickerStart.getHour());
                }

                if(timePickerStart.getMinute() < 10){
                    timeStartMinute = '0'+ Integer.toString(timePickerStart.getMinute());
                }
                else{
                    timeStartMinute = Integer.toString(timePickerStart.getMinute());
                }

                if(timePickerEnd.getHour() < 10){
                    timeEndHour = '0'+ Integer.toString(timePickerEnd.getHour());
                }
                else{
                    timeEndHour = Integer.toString(timePickerEnd.getHour());
                }

                if(timePickerEnd.getMinute() < 10){
                    timeEndMinute = '0'+ Integer.toString(timePickerEnd.getMinute());
                }else{
                    timeEndMinute = Integer.toString(timePickerEnd.getMinute());
                }

                timeStart = timeStartHour + timeStartMinute + "00";
                timeEnd = timeEndHour + timeEndMinute + "00";
                selectedStartTime = timeStart;
                selectedEndTime = timeEnd;

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

                SelectTimeRequest registerRequest = new SelectTimeRequest(timeStart, timeEnd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelectTimeActivity.this);
                queue.add(registerRequest);

                selectedTime.setText("선택된 시간 : " + selectedStartTime + " ~ " + selectedEndTime);
            }
        });
    }
}