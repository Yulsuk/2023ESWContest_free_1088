package com.example.greenhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MonitoringTempAndHumActivity extends AppCompatActivity {
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_temp_and_hum);

        Button tempAndHumRefresh = (Button)findViewById(R.id.buttonTempAndHumRefresh);

        String URL = "http://220.69.240.29/raspToApp.php";

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        LineChart lineChartTempAndHum = (LineChart) findViewById(R.id.tempAndHumChart);

        ArrayList<Entry> entryChartTemp = new ArrayList<>();
        ArrayList<Entry> entryChartHum = new ArrayList<>();

        LineData tempAndHumData = new LineData();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("raspToApp");
                    entryChartTemp.clear();
                    entryChartHum.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        entryChartTemp.add(new Entry(i, Float.parseFloat(jsonObject.getString("temperature"))));
                        entryChartHum.add(new Entry(i, Float.parseFloat(jsonObject.getString("humidity"))));
                    }

                    LineDataSet lineDataSetTemp = new LineDataSet(entryChartTemp, "Temp");
                    LineDataSet lineDataSetHum = new LineDataSet(entryChartHum, "Hum");
                    lineDataSetTemp.setColor(Color.RED);
                    lineDataSetHum.setColor(Color.BLUE);
                    lineDataSetTemp.setDrawCircles(false);
                    lineDataSetHum.setDrawCircles(false);
                    tempAndHumData.addDataSet(lineDataSetTemp);
                    tempAndHumData.addDataSet(lineDataSetHum);

                    lineChartTempAndHum.setData(tempAndHumData);
                    lineChartTempAndHum.notifyDataSetChanged();
                    lineChartTempAndHum.invalidate();
                    lineChartTempAndHum.setTouchEnabled(false);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴
                Toast.makeText(getApplicationContext(), "에러:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                return param;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);

        tempAndHumRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//인텐트 종료
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent intent = getIntent(); //인텐트
                startActivity(intent); //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기
            }
        });
    }
}