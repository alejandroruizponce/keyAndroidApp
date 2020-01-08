package com.example.alejandroruizponce1.chekinkey;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {


    private UserProfile profile = UserProfile.getInstance();
    private static final int CONTENT_VIEW_ID = 10101010;
    private ImageButton backButton;
    private Dialog MyDialog;
    private Button buttonConfirm;
    private TextView messageDialog;
    private TextView titleDialog;
    private ImageView imageDialog;

    private FrameLayout frame;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);
        backButton.setEnabled(false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    getSupportFragmentManager().popBackStack();
                    backButton.setVisibility(View.GONE);
                    backButton.setEnabled(false);
                    profile.phone = "";
                    profile.codePhone = "";
                }
            }
        });


    }


    public  void sendVerification(final String phone, final String codePhone) {
        String url = "https://test3.chekin.io/api/v1/auth/phone/generate/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("phone_number", codePhone+phone);

        System.out.println("Los parametros son:" + params);

        JSONObject parameters = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Respuesta del envio del movil: " + response.toString());
                            profile.setPk(response.getString("pk"));


                        if (profile.phone == "") {
                               FragmentManager manager = getSupportFragmentManager();
                               FragmentTransaction ft = manager.beginTransaction();

                                Fragment fragmentVCode = new verificationCode_fragment();
                                ft.setCustomAnimations(R.anim.fadeintransition,
                                        R.anim.fadeout, R.anim.fadeintransition, R.anim.fadeout);
                                ft.replace(R.id.fragmentVerification, fragmentVCode);
                                verification_fragment fragment = (verification_fragment) manager.findFragmentById(R.id.fragmentVerification);
                                ft.addToBackStack("");
                                ft.commit();

                                backButton.setVisibility(View.VISIBLE);
                                backButton.setEnabled(true);

                                profile.codePhone = codePhone;
                                profile.phone = phone;
                            }

                        } catch(JSONException e){
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

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

        requestQueue.add(jsonObjectRequest);

    }


    public  void validateVerificationCode(final String codePhone) {

        frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Fragment newFragment = new verifyng_fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fadeintransition,
                R.anim.fadeout);
        ft.addToBackStack("verificationCode");
        ft.add(CONTENT_VIEW_ID, newFragment).commit();


        String url = "https://test3.chekin.io/api/v1/auth/phone/validate/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("otp", codePhone);
        params.put("pk", profile.getPk());

        System.out.println("Los parametros son:" + params);

        JSONObject parameters = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Respuesta de la validación del código: " + response.toString());
                            profile.token = response.getString("token");
                            int status = response.getInt("status");
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.spin_kit).setAlpha(0);
                                    showDialog();
                                }
                            }, 1500);


                        } catch(JSONException e){
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.spin_kit).setAlpha(0);
                                showDialog();
                            }
                        }, 1500);

                        System.out.println("NO ES EL CODIGO CORRECTO DE VERIFICACION");

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

        requestQueue.add(jsonObjectRequest);

    }





    public void showDialog(){

        MyDialog = new Dialog(this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.custom_dialog);
        MyDialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
        MyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        buttonConfirm = (Button)MyDialog.findViewById(R.id.continueButton);
        buttonConfirm.setEnabled(true);

        if (profile.token != ""){
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.cancel();
                    startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                }
            });

        } else {
            titleDialog = MyDialog.findViewById(R.id.sexTitleDialog);
            titleDialog.setText("¡UPS!");
            messageDialog = MyDialog.findViewById(R.id.bioMessageDialog);
            messageDialog.setText(getString(R.string.failvalidation));
            imageDialog = MyDialog.findViewById(R.id.imageDialog);
            imageDialog.setImageResource(R.drawable.upsicon);
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.cancel();
                    ((ViewGroup) frame.getParent()).removeView(frame);
                    profile.phone = "";
                    profile.codePhone = "";
                    profile.token = "";
                    profile.pk = "";
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }

        MyDialog.show();
    }


}