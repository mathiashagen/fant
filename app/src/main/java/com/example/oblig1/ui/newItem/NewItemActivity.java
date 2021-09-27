package com.example.oblig1.ui.newItem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.oblig1.R;
import com.example.oblig1.data.LoginRepository;
import com.example.oblig1.data.VolleyMultipartRequest;
import com.example.oblig1.data.VolleySingleton;
import com.example.oblig1.ui.itemList.ItemsForSaleAdapter;
import com.example.oblig1.ui.itemList.ItemsForSaleListActivity;
import com.example.oblig1.ui.itemList.NewItemAdapter;
import com.example.oblig1.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewItemActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    ArrayList<Bitmap> photos;
    NewItemAdapter adapter;
    RecyclerView rvNewItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        LoginRepository loginRepository = LoginRepository.getInstance();

        rvNewItem = (RecyclerView) findViewById(R.id.recyclerview_newitem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_newitem);
        setSupportActionBar(toolbar);

        EditText titleEditText = (EditText) findViewById(R.id.title_additem);
        EditText priceEditText = (EditText) findViewById(R.id.price_additem);
        EditText descriptionEditText = (EditText) findViewById(R.id.description_additem);
        Button loginButton = (Button) findViewById(R.id.login_newitem);
        Button addItemButton = (Button) findViewById(R.id.button_additem);
        Button removeImagesButton = (Button) findViewById(R.id.removeimages_button);

        if (loginRepository.isLoggedIn() == false) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NewItemActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            loginButton.setText(loginRepository.getUser().getUserId());
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginRepository.logout();
                    Intent intent = new Intent(NewItemActivity.this, ItemsForSaleListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        photos = new ArrayList<>();
        Button photoButton = (Button) this.findViewById(R.id.addImage);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        removeImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos = new ArrayList<>();
                adapter = new NewItemAdapter(photos);
                rvNewItem.setAdapter(adapter);
                rvNewItem.setLayoutManager(new LinearLayoutManager(NewItemActivity.this));
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + loginRepository.getUser().getToken());

                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest("http://192.168.1.161:8080/api/fant/create", headers,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("GotError",""+error.getMessage());
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("title", titleEditText.getText().toString());
                        params.put("price", priceEditText.getText().toString());
                        params.put("description", descriptionEditText.getText().toString());
                        return params;
                    }

                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        long imagename = System.currentTimeMillis();
                        params.put("files", new DataPart(imagename + ".png", getFileDataFromDrawable(photos.get(0))));
                        return params;
                    }
                };
                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photos.add(photo);
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new NewItemAdapter(photos);
        rvNewItem.setAdapter(adapter);
        rvNewItem.setLayoutManager(new LinearLayoutManager(this));
    }
}
