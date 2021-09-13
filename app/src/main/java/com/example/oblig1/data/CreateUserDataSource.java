package com.example.oblig1.data;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.oblig1.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUserDataSource {
    public void createUser(String username, String password, Context ctx) {
        StringRequest jsonObjectRequest = new StringRequest(

                Request.Method.POST,
                "http://192.168.1.161:8080/api/auth/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast toast = Toast.makeText(ctx, "User created successfully", Toast.LENGTH_LONG);
                        toast.show();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Toast toast = Toast.makeText(ctx, "Cant create user", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", username);
                params.put("pwd", password);
                return params;
            }

        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}
