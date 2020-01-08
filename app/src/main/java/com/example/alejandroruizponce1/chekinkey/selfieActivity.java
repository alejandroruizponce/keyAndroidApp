package com.example.alejandroruizponce1.chekinkey;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class selfieActivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    private Button repeatSelfie;
    private Button captureButton;
    private ImageView capture;
    private UserProfile profile = UserProfile.getInstance();
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    private Dialog MyDialog;
    private String idBiomatching = "";
    private TextView titleDialog;
    private TextView messageDialog;
    private ImageView bioImage;
    private Button bioButton;
    private LottieAnimationView lottieBiomatching;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);
        cameraKitView = findViewById(R.id.camera);
        cameraKitView.setFacing(CameraKit.FACING_FRONT);
        repeatSelfie = findViewById(R.id.repeatSelfie);
        repeatSelfie.setVisibility(View.GONE);
        captureButton = findViewById(R.id.captureButton);
        capture = findViewById(R.id.capture);
        capture.setVisibility(View.GONE);


        //Biomatching Dialog

        MyDialog = new Dialog(this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.biomatching_dialog);
        MyDialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
        MyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        titleDialog = MyDialog.findViewById(R.id.sexTitleDialog);
        messageDialog = MyDialog.findViewById(R.id.bioMessageDialog);
        bioImage = MyDialog.findViewById(R.id.openImageView);
        bioButton = MyDialog.findViewById(R.id.dialogBioButton);
        lottieBiomatching = MyDialog.findViewById(R.id.lottieAnimationCheck);

        bioButton.setVisibility(View.GONE);
        bioImage.setImageAlpha(0);


        repeatSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.selfieScanned = "";

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeintransition);

                captureButton.setText("CAPTURAR");
                captureButton.clearAnimation();
                captureButton.startAnimation(animFadeIn);
                captureButton.setEnabled(true);

                capture.setVisibility(View.GONE);



            }
        });
    }

    public void capturePicture(View v) {
        if (profile.selfieScanned == "") {
            cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {

                    System.out.println("TOMO FOTO PULSANDO CAPTURAR");

                    profile.selfieScanned = Base64.encodeToString(capturedImage, Base64.DEFAULT);

                    Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeintransition);
                    repeatSelfie.setVisibility(View.VISIBLE);
                    animFadeIn.reset();
                    repeatSelfie.clearAnimation();
                    repeatSelfie.startAnimation(animFadeIn);


                    captureButton.setText(getString(R.string.continuar));
                    captureButton.clearAnimation();
                    captureButton.startAnimation(animFadeIn);

                    Drawable d = Drawable.createFromStream(new ByteArrayInputStream(capturedImage), null);
                    Bitmap image = drawableToBitmap(d);
                    image = flipImage(image,2);
                    d = new BitmapDrawable(getResources(), image);

                    capture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    capture.setImageDrawable(d);

                    capture.setVisibility(View.VISIBLE);

                    sendSelfie(profile.selfieScanned);



                }

            });
        } else {

            showDialog();
            biomatchingRequest(profile.documentID, profile.selfieID);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getBiomatching(idBiomatching);
                }
            }, 3500);



        }


    }

    public Bitmap flipImage(Bitmap src, int type) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        // if vertical
        if(type == FLIP_VERTICAL) {
            // y = y * -1
            matrix.preScale(1.0f, -1.0f);
        }
        // if horizonal
        else if(type == FLIP_HORIZONTAL) {
            // x = x * -1
            matrix.preScale(-1.0f, 1.0f);
            // unknown type
        } else {
            return null;
        }

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public  void sendSelfie(final String selfie) {
        System.out.println("PETICION PARA SUBIR EL SELFIE");
        String url = "https://test3.chekin.io/api/v1/tools/biomatch/person/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("picture_file", selfie);

        //System.out.println("Los parametros son:" + params);

        JSONObject parameters = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            profile.selfieID = response.getString("id");

                            System.out.println("La respuesta a la subida del selfie es: " + response.toString());



                        } catch(JSONException e){
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error


                        System.out.println("ERROR AL SUBIR EL SELFIE ==" + error.toString());


                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",  "Token 0fbe68a9ba96cbb48f186e3b5b677c58f94520db");
                params.put("Content-Type", "application/json");


                System.out.println("Las headers son:" + params);
                return params;
            }

        };

        requestQueue.add(jsonObjectRequest);

    }

    public void biomatchingRequest(String idDocument, String idPicture) {
        System.out.println("PETICION PARA HACER EL BIOMATCHING");
        String url = "https://test3.chekin.io/api/v1/tools/biomatch/compare/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("identification_picture", idDocument);
        params.put("person_picture", idPicture);
        System.out.println("Los parametros son:" + params);

        JSONObject parameters = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            idBiomatching = response.getString("id");
                            System.out.println("La respuesta a la solicitud de biomatching es: " + response.toString());



                        } catch(JSONException e){
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("ERROR AL HACER SOLICITUD DE BIOTMATCHING ==" + error.toString());

                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                System.out.println(obj);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }


                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",  "Token 0fbe68a9ba96cbb48f186e3b5b677c58f94520db");
                params.put("Content-Type", "application/json");


                System.out.println("Las headers son:" + params);
                return params;
            }

        };

        requestQueue.add(jsonObjectRequest);

    }

    public void getBiomatching(String idBio) {
        System.out.println("PETICION PARA HACER EL BIOMATCHING");
        String url = "https://test3.chekin.io/api/v1/tools/biomatch/compare/" + idBio;



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean matched = response.getBoolean("is_match");
                            System.out.println("La respuesta al GET de biomatching es: " + response.toString());

                            if (matched == true) {
                                profile.identityVerified = true;
                                lottieBiomatching.setVisibility(View.GONE);
                                titleDialog.setText("¡IDENTIDAD VERIFICADA!");
                                messageDialog.setText("Fantástico. Tu selfie y tu foto en el documento coinciden.");
                                bioImage.setImageAlpha(255);
                                bioImage.setImageResource(R.drawable.comparingpics);
                                bioButton.setVisibility(View.VISIBLE);
                                bioButton.setText("CONTINUAR");
                                bioButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MyDialog.dismiss();
                                        Intent intent_next=new Intent(selfieActivity.this, signatureActivity.class);
                                        startActivity(intent_next);
                                        selfieActivity.this.finish();
                                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                                    }
                                });
                            } else {
                                profile.identityVerified = false;
                                lottieBiomatching.setVisibility(View.GONE);
                                titleDialog.setText("¡UPS!");
                                messageDialog.setText(getString(R.string.failcomparing));
                                bioImage.setImageAlpha(255);
                                bioImage.setImageResource(R.drawable.upsicon);
                                bioButton.setVisibility(View.VISIBLE);
                                bioButton.setText(getString(R.string.repeat));
                                bioButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MyDialog.dismiss();
                                        Intent intent_next=new Intent(selfieActivity.this, cameraDocumentActivity.class);
                                        startActivity(intent_next);
                                        selfieActivity.this.finish();
                                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                                    }
                                });
                            }



                        } catch(JSONException e){
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("ERROR AL HACER GET DE BIOTMATCHING ==" + error.toString());

                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                System.out.println(obj);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }


                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",  "Token 0fbe68a9ba96cbb48f186e3b5b677c58f94520db");
                params.put("Content-Type", "application/json");


                System.out.println("Las headers son:" + params);
                return params;
            }

        };

        requestQueue.add(jsonObjectRequest);

    }

    public void showDialog(){


        MyDialog.show();
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
