package com.example.greenhouse;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TempAndHumRequest extends StringRequest {
    final static private String URL = "http://220.69.240.29/raspToApp.php"; // "http:// 퍼블릭 DSN 주소/Login.php";
    private Map<String, String> parameters;


    public TempAndHumRequest(String temperature, String humidity,
                                  Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.get(temperature);
        parameters.get(humidity);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}