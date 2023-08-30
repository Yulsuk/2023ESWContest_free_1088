package com.example.greenhouse;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SelectOperationRequest extends StringRequest {
    final static private String URL = "http://220.69.240.29/appToRasp.php"; // "http:// 퍼블릭 DSN 주소/Login.php";
    private Map<String, String> parameters;


    public SelectOperationRequest(String operateGreenhouseOpen, String operateGreenhouseClose, String operateWaterSprayer,
                                  String operateVentilator, String operateHeatingWire, String operateWiper, String operateWaterSprayerOutside,
                                  Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("operateGreenhouseOpen", operateGreenhouseOpen);
        parameters.put("operateGreenhouseClose", operateGreenhouseClose);
        parameters.put("operateWaterSprayer", operateWaterSprayer);
        parameters.put("operateVentilator", operateVentilator);
        parameters.put("operateHeatingWire", operateHeatingWire);
        parameters.put("operateWiper", operateWiper);
        parameters.put("operateWaterSprayerOutside", operateWaterSprayerOutside);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}