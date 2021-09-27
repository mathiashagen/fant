package com.example.oblig1.ui.itemDetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oblig1.R;
import com.example.oblig1.data.LoginRepository;
import com.example.oblig1.data.VolleySingleton;
import com.example.oblig1.data.model.ItemForSale;
import com.example.oblig1.ui.itemList.ItemsForSaleListActivity;
import com.example.oblig1.ui.login.LoginActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;
import java.util.Map;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        LoginRepository loginRepository = LoginRepository.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_newitem);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        ItemForSale itemForSale = (ItemForSale) i.getSerializableExtra("itemForSale");

        final SliderView sliderView = findViewById(R.id.imageSlider);
        final TextView titleTextView = findViewById(R.id.title_itemdetail);
        final TextView priceTextView = findViewById(R.id.price_itemdetail);
        final TextView descriptionTextView = findViewById(R.id.description_itemdetail);
        final Button buyButton = findViewById(R.id.buybutton_itemdetail);

        titleTextView.setText(itemForSale.getTitle());
        priceTextView.setText(itemForSale.getPrice());
        descriptionTextView.setText(itemForSale.getDescription());

        Button loginButton = (Button) findViewById(R.id.login_newitem);

        ItemDetailSliderAdapter adapter = new ItemDetailSliderAdapter(this, itemForSale.getPhotos());
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        if (!loginRepository.isLoggedIn()) {
            buyButton.setVisibility(View.GONE);
            loginButton.setText("Login");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ItemDetailActivity.this, LoginActivity.class);
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
                    Intent intent = new Intent(ItemDetailActivity.this, ItemsForSaleListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.PUT,
                        "http://192.168.1.161:8080/api/fant/buy/" + itemForSale.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast toast = Toast.makeText(ItemDetailActivity.this, "Item bought!", Toast.LENGTH_LONG);
                                toast.show();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(ItemDetailActivity.this, "Something went wrong", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("Authorization", "Bearer " + loginRepository.getUser().getToken());
                                params.put("Content-Type", "application/json");

                                return params;
                            }
                        };
                VolleySingleton.getInstance(ItemDetailActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }
}
