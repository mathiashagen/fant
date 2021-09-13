package com.example.oblig1.ui.createUser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.oblig1.R;
import com.example.oblig1.data.CreateUserDataSource;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateUserDataSource createUserDataSource = new CreateUserDataSource();

        final EditText usernameEditText = findViewById(R.id.userid);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button createUserButton = findViewById(R.id.createNewUser);

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserDataSource.createUser(usernameEditText.toString(), passwordEditText.toString(), CreateUserActivity.this);
            }
        });
    }
}
