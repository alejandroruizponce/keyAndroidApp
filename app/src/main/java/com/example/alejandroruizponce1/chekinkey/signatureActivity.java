package com.example.alejandroruizponce1.chekinkey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class signatureActivity extends AppCompatActivity {

    private Button sendButton;
    private SignatureView signatureView;
    private CheckBox checkbox;
    private Button repeatSign;
    private UserProfile profile = UserProfile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        sendButton = findViewById(R.id.signatureButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!signatureView.isBitmapEmpty() && checkbox.isChecked()) {
                    Bitmap signature = signatureView.getSignatureBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    signature.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    profile.signaturePicture = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Intent intent_next=new Intent(signatureActivity.this, SuccesfulRegisterActivity.class);
                    startActivity(intent_next);
                    signatureActivity.this.finish();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);


                }
            }
        });

        signatureView = findViewById(R.id.signature_view);
        checkbox = findViewById(R.id.checkBoxTC);
        repeatSign = findViewById(R.id.repeatSignatureButton);
        repeatSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

    }
}
