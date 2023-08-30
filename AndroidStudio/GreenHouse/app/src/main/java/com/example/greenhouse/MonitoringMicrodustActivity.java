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


public class MonitoringMicrodustActivity extends AppCompatActivity {
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_microdust);

        Button tempAndHumRefresh = (Button)findViewById(R.id.buttonMicrodustRefresh);

        String URL = "http://220.69.240.29/raspToAppMicrodust.php";

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        LineChart lineChartMicrodust = (LineChart) findViewById(R.id.microdustChart);

        ArrayList<Entry> entryChart1_0 = new ArrayList<>();
        ArrayList<Entry> entryChart2_5 = new ArrayList<>();
        ArrayList<Entry> entryChart10_0 = new ArrayList<>();

        LineData microdustData = new LineData();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("raspToAppMicrodust");
                    entryChart1_0.clear();
                    entryChart2_5.clear();
                    entryChart10_0.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        entryChart1_0.add(new Entry(i, Float.parseFloat(jsonObject.getString("pm1_0"))));
                        entryChart2_5.add(new Entry(i, Float.parseFloat(jsonObject.getString("pm2_5"))));
                        entryChart10_0.add(new Entry(i, Float.parseFloat(jsonObject.getString("pm10_0"))));
                    }

                    LineDataSet lineDataSet1_0 = new LineDataSet(entryChart1_0, "pm1_0");
                    LineDataSet lineDataSet2_5 = new LineDataSet(entryChart2_5, "pm2_5");
                    LineDataSet lineDataSet10_0 = new LineDataSet(entryChart10_0, "pm10_0");
                    lineDataSet1_0.setColor(Color.BLUE);
                    lineDataSet2_5.setColor(Color.GREEN);
                    lineDataSet10_0.setColor(Color.RED);
                    lineDataSet1_0.setDrawCircles(false);
                    lineDataSet2_5.setDrawCircles(false);
                    lineDataSet10_0.setDrawCircles(false);
                    microdustData.addDataSet(lineDataSet1_0);
                    microdustData.addDataSet(lineDataSet2_5);
                    microdustData.addDataSet(lineDataSet10_0);

                    lineChartMicrodust.setData(microdustData);
                    lineChartMicrodust.notifyDataSetChanged();
                    lineChartMicrodust.invalidate();
                    lineChartMicrodust.setTouchEnabled(false);
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