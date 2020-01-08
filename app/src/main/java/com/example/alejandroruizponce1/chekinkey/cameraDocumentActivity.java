package com.example.alejandroruizponce1.chekinkey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.camerakit.CameraKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class cameraDocumentActivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    private Button scanButton;
    private ImageView overlayScan;
    private LottieAnimationView animationCheck;
    private TextView messageScanned;
    private UserProfile profile = UserProfile.getInstance();
    private MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_document);
        cameraKitView = findViewById(R.id.camera);
        overlayScan = findViewById(R.id.overlayImageView);
        scanButton = findViewById(R.id.scanButton);
        animationCheck = findViewById(R.id.lottieAnimationCheck);
        animationCheck.setVisibility(View.GONE);
        messageScanned = findViewById(R.id.messageScanned);
        messageScanned.setVisibility(View.GONE);

        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        System.out.println("EL SDK VERSION DE ESTE MOVIL ES: " + sdkVersion);


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        cameraKitView.onPause();
                        profile.documentScanned = Base64.encodeToString(capturedImage, Base64.DEFAULT);

                        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeintransition);
                        overlayScan.setBackgroundColor(0xB300429A);
                        animFadeIn.reset();
                        overlayScan.clearAnimation();
                        overlayScan.startAnimation(animFadeIn);

                        animationCheck.setVisibility(View.VISIBLE);
                        animationCheck.playAnimation();

                        messageScanned.setVisibility(View.VISIBLE);
                        messageScanned.clearAnimation();
                        messageScanned.startAnimation(animFadeIn);


                        sendDocumentScanned(profile.documentScanned);



                    }

                });
             }
        });


    }

    public  void sendDocumentScanned(final String documentPicture) {
        System.out.println("PETICION PARA SUBIR EL DOCUMENTO PARA ESCANEAR");
        String url = "https://test3.chekin.io/api/v1/tools/biomatch/identification/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("picture_file", documentPicture);

        //System.out.println("Los parametros son:" + params);

        JSONObject parameters = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            profile.documentID = response.getString("id");
                            System.out.println("La respuesta a la subida del documento es: " + response.toString());

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    cameraKitView.onStop();
                                    Intent intent_next=new Intent(cameraDocumentActivity.this, selfieActivity.class);
                                    startActivity(intent_next);
                                    cameraDocumentActivity.this.finish();
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
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
                        System.out.println("ERROR AL SUBIR EL DOCUMENTO ESCANEADO ==" + error.toString());

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token 0fbe68a9ba96cbb48f186e3b5b677c58f94520db");
                params.put("Content-Type", "application/json");


                System.out.println("Las headers son:" + params);
                return params;
            }

        };

        requestQueue.add(jsonObjectRequest);

    }


    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
