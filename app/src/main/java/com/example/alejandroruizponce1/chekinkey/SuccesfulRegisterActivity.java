package com.example.alejandroruizponce1.chekinkey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccesfulRegisterActivity extends AppCompatActivity {

    private Button getKeysButton;
    private TextView congratulationsMessage;
    private UserProfile profile = UserProfile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesful_register);

        congratulationsMessage = findViewById(R.id.congratulationsTextView);
        congratulationsMessage.setText("¡ENHORABUENA," + profile.name.toUpperCase() + "!");

        getKeysButton = findViewById(R.id.buttonGetKeys);
        getKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_next=new Intent(SuccesfulRegisterActivity.this, BookingsActivity.class);
                startActivity(intent_next);
                SuccesfulRegisterActivity.this.finish();
                overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
            }
        });

    }
}
