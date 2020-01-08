package com.example.alejandroruizponce1.chekinkey;

import android.content.Context;
import android.util.Log;

import com.omnitec.omnilock.sdk.api.OMLockAPI;
import com.omnitec.omnilock.sdk.callback.OMLockCallback;
import com.omnitec.omnilock.sdk.entity.Error;
import com.omnitec.omnilock.sdk.scanner.OmniLockBluetoothDevice;

import java.sql.SQLOutput;

public class OmnitecAPI implements OMLockCallback{


    public OMLockAPI mOMLockAPI;

    public void init(Context mContext) {
        mOMLockAPI = new OMLockAPI(
                mContext, this
        );

       // mOMLockAPI.requestBleEnable(BookingExpandedActivity.this);
        mOMLockAPI.startBleService(mContext);
        mOMLockAPI.startBTDeviceScan();
    }

    @Override
    public void onFoundDevice(OmniLockBluetoothDevice omniLockBluetoothDevice) {

        Log.d("onFoundDevice", "Device found: " + omniLockBluetoothDevice.getName());
        mOMLockAPI.connect(omniLockBluetoothDevice.getAddress());
    }

    @Override
    public void onDeviceConnected(OmniLockBluetoothDevice omniLockBluetoothDevice) {
        System.out.println("CERRADURA CONECTADA!!!!!");
    }

    @Override
    public void onDeviceDisconnected(OmniLockBluetoothDevice omniLockBluetoothDevice) {

    }

    @Override
    public void onGetDeviceVersion(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, int i2, int i3, int i4, Error error) {

    }

    @Override
    public void onAddAdmin(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, String s1, String s2, String s3, String s4, String s5, long l, String s6, int i, String s7, String s8, String s9, Error error) {

    }

    @Override
    public void onResetEKey(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, Error error) {

    }

    @Override
    public void onSetDeviceName(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, Error error) {

    }

    @Override
    public void onSetAdminKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, Error error) {

    }

    @Override
    public void onSetDeletePassword(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, Error error) {

    }

    @Override
    public void onUnlock(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, long l, Error error) {

    }

    @Override
    public void onSetDeviceTime(OmniLockBluetoothDevice omniLockBluetoothDevice, Error error) {

    }

    @Override
    public void onGetDeviceTime(OmniLockBluetoothDevice omniLockBluetoothDevice, long l, Error error) {

    }

    @Override
    public void onResetKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, long l, Error error) {

    }

    @Override
    public void onSetMaxNumberOfKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, Error error) {

    }

    @Override
    public void onResetKeyboardPasscodeProgress(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, Error error) {

    }

    @Override
    public void onResetLock(OmniLockBluetoothDevice omniLockBluetoothDevice, Error error) {

    }

    @Override
    public void onAddKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, String s, long l, long l1, Error error) {

    }

    @Override
    public void onModifyKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, String s, String s1, Error error) {

    }

    @Override
    public void onDeleteOneKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, String s, Error error) {

    }

    @Override
    public void onDeleteAllKeyboardPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, Error error) {

    }

    @Override
    public void onGetOperateLog(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, Error error) {

    }

    @Override
    public void onSearchDeviceFeature(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, Error error) {

    }

    @Override
    public void onSearchAutoDeviceTime(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, int i2, int i3, Error error) {

    }

    @Override
    public void onModifyAutoDeviceTime(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, Error error) {

    }

    @Override
    public void onReadDeviceInfo(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, String s1, String s2, String s3, String s4) {

    }

    @Override
    public void onEnterDFUMode(OmniLockBluetoothDevice omniLockBluetoothDevice, Error error) {

    }

    @Override
    public void onGetDeviceSwitchState(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, Error error) {

    }

    @Override
    public void onLock(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, int i2, long l, Error error) {

    }

    @Override
    public void onScreenPasscodeOperate(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, Error error) {

    }

    @Override
    public void onRecoveryData(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, Error error) {

    }

    @Override
    public void onSearchPasscode(OmniLockBluetoothDevice omniLockBluetoothDevice, String s, Error error) {

    }

    @Override
    public void onSearchPasscodeParam(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, String s, long l, Error error) {

    }

    @Override
    public void onOperateRemoteUnlockSwitch(OmniLockBluetoothDevice omniLockBluetoothDevice, int i, int i1, int i2, int i3, Error error) {

    }
}
