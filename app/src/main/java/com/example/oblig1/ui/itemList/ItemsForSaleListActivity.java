package com.example.oblig1.ui.itemList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.oblig1.R;
import com.example.oblig1.data.LoginRepository;
import com.example.oblig1.data.VolleySingleton;
import com.example.oblig1.data.model.ItemForSale;
import com.example.oblig1.ui.login.LoginActivity;
import com.example.oblig1.ui.newItem.NewItemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ItemsForSaleListActivity extends AppCompatActivity {
    ArrayList<ItemForSale> itemForSaleArrayList;
    ItemsForSaleAdapter adapter;
    RecyclerView rvItemsForSale;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemsforsale);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_itemsforsale);
        setSupportActionBar(toolbar);

        rvItemsForSale = (RecyclerView) findViewById(R.id.rvItemsForSale);
        final FloatingActionButton fab = findViewById(R.id.fab);

        searchView = (SearchView) findViewById(R.id.itemsearch);

        LoginRepository loginRepository = LoginRepository.getInstance();

        Button loginButton = (Button) findViewById(R.id.login_itemsforsale);

        if (loginRepository.isLoggedIn() == false) {
            fab.hide();
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ItemsForSaleListActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            fab.show();
            loginButton.setText(loginRepository.getUser().getUserId());
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginRepository.logout();
                    Intent intent = new Intent(ItemsForSaleListActivity.this, ItemsForSaleListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsForSaleListActivity.this, NewItemActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ItemForSale> resultList = new ArrayList<>();
                for (ItemForSale i : itemForSaleArrayList) {
                    if (i.getTitle() != null && i.getTitle().contains(newText.toLowerCase(Locale.ROOT))) {
                        resultList.add(i);
                    }
                }
                adapter = new ItemsForSaleAdapter(resultList, ItemsForSaleListActivity.this);
                rvItemsForSale.setAdapter(adapter);
                rvItemsForSale.setLayoutManager(new LinearLayoutManager(ItemsForSaleListActivity.this));
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemForSaleArrayList = new ArrayList<>();
        adapter = new ItemsForSaleAdapter(itemForSaleArrayList, ItemsForSaleListActivity.this);
        rvItemsForSale.setAdapter(adapter);
        rvItemsForSale.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            "http://192.168.1.161:8080/api/fant",
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            ArrayList<String> photosList = new ArrayList<>();
                            JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray("photos");
                            if (jsonArray != null) {
                                int len = jsonArray.length();
                                for (int j=0;j<len;j++){
                                    JSONObject jo = (JSONObject) jsonArray.get(j);
                                    photosList.add(jo.getString("subpath").toString());
                                }
                            }
                            ItemForSale itemForSale = new ItemForSale(jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("price"), photosList, jsonObject.getString("id"));
                            itemForSaleArrayList.add(itemForSale);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                }
            });
        VolleySingleton.getInstance(ItemsForSaleListActivity.this).addToRequestQueue(jsonArrayRequest);
    }
}
