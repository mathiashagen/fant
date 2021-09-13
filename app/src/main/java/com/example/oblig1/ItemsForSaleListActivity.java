package com.example.oblig1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.oblig1.data.VolleySingleton;
import com.example.oblig1.data.model.ItemForSale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsForSaleListActivity extends AppCompatActivity {
    ArrayList<ItemForSale> itemForSaleArrayList;
    ItemsForSaleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemsforsale);

        RecyclerView rvItemsForSale = (RecyclerView) findViewById(R.id.rvItemsForSale);

        //Initialize list
        itemForSaleArrayList = new ArrayList<>();
        adapter = new ItemsForSaleAdapter(itemForSaleArrayList);
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
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ItemForSale itemForSale = new ItemForSale(jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("price"));
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
