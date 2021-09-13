package com.example.oblig1.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.oblig1.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private String token;

    public Result<LoggedInUser> login(String username, String password, Context ctx) {


        try {
            StringRequest stringRequest = new StringRequest(


                    Request.Method.GET,
                    "http://192.168.1.161:8080/api/auth/login?uid=" + username + "&pwd=" + password,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            token = response;
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("volley", "Error: " + error.getMessage());
                            error.printStackTrace();
                        }
                    });

            VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);

            LoggedInUser user = new LoggedInUser(token, username);

            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}