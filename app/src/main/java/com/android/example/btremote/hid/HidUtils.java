package com.android.example.btremote.hid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.text.TextUtils;

import com.android.example.btremote.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

public class HidUtils {
    public static String SelectedDeviceMac = "";
    public static boolean _connected = false;
    public static boolean IsRegister = false;
    public static boolean isKM = true;
    public static int type = 1;
    public static int i = 0;
    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothProfile bluetoothProfile;
    public static BluetoothDevice BtDevice;
    public static BluetoothHidDevice HidDevice;

    public static void RegisterApp(Context context) throws IOException {
        if (IsRegister) {

        } else {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, mProfileServiceListener, BluetoothProfile.HID_DEVICE);
            FileInputStream inputStream = context.openFileInput("type.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            type = Integer.parseInt(String.valueOf(stringBuilder));
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }
    }

    public static boolean Pair(String deviceAddress) {
        if (BluetoothAdapter.checkBluetoothAddress(deviceAddress)) {
            try {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (BtDevice == null) {
                    BtDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
                }
                if (BtDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    BtDevice.createBond();
                    return false;
                } else if (BtDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    return true;
                } else if (BtDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static boolean IsConnected() {
        try {
            return HidUtils._connected;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static void IsConnected(boolean _connected) {
        HidUtils._connected = _connected;
    }

    public static boolean connect(String deviceAddress) {
        if (TextUtils.isEmpty(deviceAddress)) {
            ToastUtils.showShort("获取mac地址失败");
            return false;
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtils.showShort("当前设备不支持蓝牙HID");
            return false;
        }
        if (BtDevice == null) {
            BtDevice = mBluetoothAdapter.getRemoteDevice(deviceAddress);
        }
        boolean ret = HidDevice.connect(BtDevice);
        HidConstants.BtDevice = BtDevice;
        HidConstants.HidDevice = HidDevice;
        return ret;
    }

    public static boolean connect(BluetoothDevice device) {
        boolean ret = HidDevice.connect(device);
        HidConstants.BtDevice = device;
        HidConstants.HidDevice = HidDevice;
        return ret;
    }

    public static void reConnect( Activity context) {
        if (TextUtils.isEmpty(SelectedDeviceMac)) {
            return;
        }
        try {
            if (HidUtils.HidDevice != null) {
                if (HidUtils.BtDevice == null) {
                    HidUtils.BtDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(HidUtils.SelectedDeviceMac);
                }
                int state = HidUtils.HidDevice.getConnectionState(HidUtils.BtDevice);
                if (state == BluetoothProfile.STATE_DISCONNECTED) {
                    if (TextUtils.isEmpty(HidUtils.SelectedDeviceMac)) {
                    } else {
                        if (HidUtils.Pair(HidUtils.SelectedDeviceMac)) {
                            HidUtils.RegisterApp(context.getApplicationContext());
                            UtilCls.DelayTask(new Runnable() {
                                @Override
                                public void run() {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HidUtils.connect(HidUtils.SelectedDeviceMac);
                                        }
                                    });
                                }
                            }, 500, true);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public static BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceDisconnected(int profile) {
        }

        @SuppressLint("NewApi")
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            bluetoothProfile = proxy;
            if (profile == BluetoothProfile.HID_DEVICE) {
                HidDevice = (BluetoothHidDevice) proxy;
                HidConstants.HidDevice = HidDevice;
                isKM = type != 0;
                BluetoothHidDeviceAppSdpSettings sdp = null;
                if(isKM) {
                    sdp = new BluetoothHidDeviceAppSdpSettings(HidConstants.NAME_KM, HidConstants.DESCRIPTION_KM, HidConstants.PROVIDER_KM, BluetoothHidDevice.SUBCLASS1_COMBO, HidConstants.Descriptor);
                }else {
                    if(i == 10) {
                        sdp = new BluetoothHidDeviceAppSdpSettings(HidConstants.NAME_C_Xbox, HidConstants.DESCRIPTION_C_Xbox, HidConstants.PROVIDER_C_Xbox, BluetoothHidDevice.SUBCLASS2_GAMEPAD, HidConstants.C_Descriptor_Xbox);
                    }else if(i == 0) {
                        sdp = new BluetoothHidDeviceAppSdpSettings(HidConstants.NAME_C, HidConstants.DESCRIPTION_C, HidConstants.PROVIDER_C, BluetoothHidDevice.SUBCLASS2_GAMEPAD, HidConstants.C_Descriptor);
                    }
                }
                HidDevice.registerApp(sdp, null, null, Executors.newCachedThreadPool(), mCallback);
            }
        }
    };

    public static final BluetoothHidDevice.Callback mCallback = new BluetoothHidDevice.Callback() {
        @Override
        public void onAppStatusChanged(BluetoothDevice pluggedDevice, boolean registered) {
            IsRegister = registered;
        }

        @Override
        public void onConnectionStateChanged(BluetoothDevice device, int state) {
            if (state == BluetoothProfile.STATE_DISCONNECTED) {
                HidUtils.IsConnected(false);
                EventBus.getDefault().post(new HidEvent(HidEvent.tcpType.onDisConnected));
            } else if (state == BluetoothProfile.STATE_CONNECTED) {
                HidUtils.IsConnected(true);
                EventBus.getDefault().post(new HidEvent(HidEvent.tcpType.onConnected));
            } else if (state == BluetoothProfile.STATE_CONNECTING) {
                EventBus.getDefault().post(new HidEvent(HidEvent.tcpType.onConnecting));
            }
        }
    };
}
