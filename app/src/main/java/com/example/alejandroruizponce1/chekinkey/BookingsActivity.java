package com.example.alejandroruizponce1.chekinkey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class BookingsActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private Button logoutButton;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private UserProfile profile = UserProfile.getInstance();

    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_next=new Intent(BookingsActivity.this, MainActivity.class);
                startActivity(intent_next);
                BookingsActivity.this.finish();
                overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter(this);

        System.out.println("El codigo del telefono es: " + profile.codePhone);
        getBookings("34", "616506980");




    }

    public  void getBookings(String prefix, String number) {
        System.out.println("PETICION PARA CONSEGUIR LAS RESERVAS");
        String url = "https://test3.chekin.io/api/v1/tools/chekin_online/reservations/" + prefix + number +"/";
        System.out.println("La URL es: " + url );


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println("Las reservas obtenidas son:");
                        for(int i=0;i<response.length();i++) {
                            // Get current json object
                            try {
                                JSONObject booking = response.getJSONObject(i);
                                mCardAdapter.addCardItem(new CardItem(booking.getString("accommodation_name"), ""));
                                System.out.println("Reserva "+ i + booking);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        profile.myBookings = response;



                        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

                        mViewPager.setAdapter(mCardAdapter);
                        mViewPager.setPageTransformer(false, mCardShadowTransformer);
                        mViewPager.setOffscreenPageLimit(3);

                        mCardShadowTransformer.enableScaling(true);




                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error


                        System.out.println("ERROR AL OBTENER LAS RESERVAS ==" + error.toString());


                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Api-Key", "81f23bc4a4a4676ba78a8edae1fcc92c59d367b6");
                params.put("Content-Type", "application/json");


                System.out.println("Las headers son:" + params);
                return params;
            }

        };

        requestQueue.add(jsonArrayRequest);

    }


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


}

