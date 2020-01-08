package com.example.alejandroruizponce1.chekinkey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.media.VolumeShaper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.omnitec.omnilock.sdk.api.OMLockAPI;
import com.omnitec.omnilock.sdk.callback.OMLockCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;


public class BookingExpandedActivity extends Activity {

    private ImageView imageHotel;
    private ImageView unlockRoomImage;
    private Dialog MyDialog;
    private Button buttonConfirm;
    private ImageButton backButton;
    private Boolean roomUnlocked = false;
    private OmnitecAPI omnilock = new OmnitecAPI();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_expanded);





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }




        /*

        if(mOMLockAPI.isConnected(mKey.getLockMac())) {
        // In connected state, call unlock interface directly
            if(mKey.isAdmin())
                // Admin unlock
                mOMLockAPI.adminUnlock(null, openid, mKey.getLockVersion(),
                        mKey.getAdminPs(), mKey.getUnlockKey(), mKey.getLockFlagPos(),
                        System.currentTimeMillis(), mKey.getAesKeystr(), mKey.getTimezoneRawOffset());
            else
                // User unlock
                mOMLockAPI.userUnlock(null, openid, mKey.getLockVersion(),
                        mKey.getStartDate(), mKey.getEndDate(), mKey.getUnlockKey(),
                        mKey.getLockFlagPos(), mKey.getAesKeystr(), mKey.getTimezoneRawOffset());
        } else {
            // Connect lock
            mOMLockAPI.connect(mKey.getLockMac());
            bleSession.setOperation(VolumeShaper.Operation.CLICK_UNLOCK);
            bleSession.setLockmac(mKey.getLockMac());
        }*/


            MyDialog = new Dialog(this);
            MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            MyDialog.setContentView(R.layout.unlock_dialog);
            MyDialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
            MyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            imageHotel = findViewById(R.id.imageHotelExpanded);
            imageHotel.setColorFilter(Color.parseColor("#992D508E"), PorterDuff.Mode.SRC_ATOP);

            buttonConfirm = MyDialog.findViewById(R.id.dialogUnlockButton);
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.dismiss();

                }
            });

            backButton = findViewById(R.id.backBookingsButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_next=new Intent(BookingExpandedActivity.this, BookingsActivity.class);
                    startActivity(intent_next);
                    BookingExpandedActivity.this.finish();
                    overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);

                }
            });

            unlockRoomImage = findViewById(R.id.unlockRoomImage);
            unlockRoomImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();

                }
            });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   omnilock.init(this);

                    //
                } else {
                    System.out.println("PERMISO NO CONCEDIDO PARA OMNITEC BLE");
                    // Alert the user that this application requires the location permission to perform the scan.
                }
            }
        }
    }




    public void showDialog(){


        MyDialog.show();
    }





}
