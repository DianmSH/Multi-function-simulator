package com.android.example.btremote.hid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class HidConstants {

    public final static String NAME_KM= "KM";
    public final static String NAME_C= "C";
    public final static String NAME_C_Xbox= "C_Xbox";
    public final static String DESCRIPTION_KM = "description_KM";
    public final static String DESCRIPTION_C = "description_C";
    public final static String DESCRIPTION_C_Xbox = "description_C_Xbox";
    public final static String PROVIDER_KM = "provider_KM";
    public final static String PROVIDER_C = "provider_C";
    public final static String PROVIDER_C_Xbox = "provider_C_Xbox";
    public static BluetoothHidDevice HidDevice;
    public static BluetoothDevice BtDevice;
    public static byte ModifierByte = 0x00;
    public static byte KeyByte = 0x00;

    public final static byte[] Descriptor = {
            (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x02, (byte) 0xa1, (byte) 0x01, (byte) 0x09, (byte) 0x01, (byte) 0xa1, (byte) 0x00,
            (byte) 0x85, (byte) 0x01, (byte) 0x05, (byte) 0x09, (byte) 0x19, (byte) 0x01, (byte) 0x29, (byte) 0x03, (byte) 0x15, (byte) 0x00, (byte) 0x25, (byte) 0x01,
            (byte) 0x95, (byte) 0x03, (byte) 0x75, (byte) 0x01, (byte) 0x81, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x05, (byte) 0x81, (byte) 0x03,
            (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x30, (byte) 0x09, (byte) 0x31, (byte) 0x09, (byte) 0x38, (byte) 0x15, (byte) 0x81, (byte) 0x25, (byte) 0x7f,
            (byte) 0x75, (byte) 0x08, (byte) 0x95, (byte) 0x03, (byte) 0x81, (byte) 0x06, (byte) 0xc0, (byte) 0xc0, (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x06,
            (byte) 0xa1, (byte) 0x01, (byte) 0x85, (byte) 0x02, (byte) 0x05, (byte) 0x07, (byte) 0x19, (byte) 0xE0, (byte) 0x29, (byte) 0xE7, (byte) 0x15, (byte) 0x00,
            (byte) 0x25, (byte) 0x01, (byte) 0x75, (byte) 0x01, (byte) 0x95, (byte) 0x08, (byte) 0x81, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x08,
            (byte) 0x15, (byte) 0x00, (byte) 0x25, (byte) 0x65, (byte) 0x19, (byte) 0x00, (byte) 0x29, (byte) 0x65, (byte) 0x81, (byte) 0x00, (byte) 0x05, (byte) 0x08,
            (byte) 0x95, (byte) 0x05, (byte) 0x75, (byte) 0x01, (byte) 0x19, (byte) 0x01, (byte) 0x29, (byte) 0x05,
            (byte) 0x91, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x03, (byte) 0x91, (byte) 0x03,
            (byte) 0xc0
    };

    public final static byte[] C_Descriptor = {
            (byte)0x05, (byte)0x01, // Usage Page (Generic Desktop Ctrls)
            (byte)0x09, (byte)0x05, // Usage (Game Pad)
            (byte)0xa1, (byte)0x01, // Collection (Application)
            (byte)0x85, (byte)0x04, // Report ID (4)
            (byte)0x05, (byte)0x09, // Usage Page (Button)
            (byte)0x19, (byte)0x01, // Usage Minimum (Button 1)
            (byte)0x29, (byte)0x10, // Usage Maximum (Button 16)
            (byte)0x15, (byte)0x00, // Logical Minimum (0)
            (byte)0x25, (byte)0x01, // Logical Maximum (1)
            (byte)0x75, (byte)0x01, // Report Size (1)
            (byte)0x95, (byte)0x10, // Report Count (16)
            (byte)0x81, (byte)0x02, // Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
            (byte)0x05, (byte)0x01, // Usage Page (Generic Desktop Ctrls)
            (byte)0x15, (byte)0x81, // Logical Minimum (-127)
            (byte)0x25, (byte)0x7f, // Logical Maximum (127)
            (byte)0x09, (byte)0x30, // Usage (X)
            (byte)0x09, (byte)0x31, // Usage (Y)
            (byte)0x09, (byte)0x32, // Usage (Z)
            (byte)0x09, (byte)0x35, // Usage (Rz)
            (byte)0x75, (byte)0x08, // Report Size (8)
            (byte)0x95, (byte)0x04, // Report Count (4)
            (byte)0x81, (byte)0x02, // Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
            (byte)0xc0, // End Col
    };

    public final static byte[] C_Descriptor_Xbox = {
            (byte)0x05, (byte)0x01,                     //0       GLOBAL_USAGE_PAGE(Generic Desktop Controls)
            (byte)0x09, (byte)0x05,                     //2       LOCAL_USAGE(Game Pad)
            (byte)0xA1, (byte)0x01,                     //4       MAIN_COLLECTION(Applicatior)
            (byte)0xA1, (byte)0x00,                     //6       MAIN_COLLECTION(Physical)
            (byte)0x09, (byte)0x30,                     //8       LOCAL_USAGE(X)
            (byte)0x09, (byte)0x31,                     //10      LOCAL_USAGE(Y)
            (byte)0x15, (byte)0x00,                     //12      GLOBAL_LOGICAL_MINIMUM(0)
            (byte)0x26, (byte)0xFF, (byte)0xFF,         //14      GLOBAL_LOCAL_MAXIMUM(-1)
            (byte)0x35, (byte)0x00,                     //17      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0xFF, (byte)0xFF,         //19      GLOBAL_PHYSICAL_MAXIMUM(65535)
            (byte)0x95, (byte)0x02,                     //22      GLOBAL_REPORT_COUNT(2)
            (byte)0x75, (byte)0x10,                     //24      GLOBAL_REPORT_SIZE(16)            4 byte
            (byte)0x81, (byte)0x02,                     //26      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //28      MAIN_COLLECTION_END
            (byte)0xA1, (byte)0x00,                     //29      MAIN_COLLECTION(Physical)
            (byte)0x09, (byte)0x33,                     //31      LOCAL_USAGE(Rx)
            (byte)0x09, (byte)0x34,                     //33      LOCAL_USAGE(Ry)
            (byte)0x15, (byte)0x00,                     //35      GLOBAL_LOGICAL_MINIMUM(0)
            (byte)0x26, (byte)0xFF, (byte)0xFF,         //37      GLOBAL_LOCAL_MAXIMUM(-1)
            (byte)0x35, (byte)0x00,                     //40      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0xFF, (byte)0xFF,         //42      GLOBAL_PHYSICAL_MAXIMUM(65535)
            (byte)0x95, (byte)0x02,                     //45      GLOBAL_REPORT_COUNT(2)
            (byte)0x75, (byte)0x10,                     //47      GLOBAL_REPORT_SIZE(16)            4 byte
            (byte)0x81, (byte)0x02,                     //49      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //51      MAIN_COLLECTION_END
            (byte)0xA1, (byte)0x00,                     //52      MAIN_COLLECTION(Physical)
            (byte)0x09, (byte)0x32,                     //54      LOCAL_USAGE(Z)
            (byte)0x15, (byte)0x00,                     //56      GLOBAL_LOGICAL_MINIMUM(0)
            (byte)0x26, (byte)0xFF, (byte)0xFF,         //r58      GLOBAL_LOCAL_MAXIMUM(-1)
            (byte)0x35, (byte)0x00,                     //61      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0xFF, (byte)0xFF,         //63      GLOBAL_PHYSICAL_MAXIMUM(65535)
            (byte)0x95, (byte)0x01,                     //66      GLOBAL_REPORT_COUNT(1)
            (byte)0x75, (byte)0x10,                     //68      GLOBAL_REPORT_SIZE(16)            2 byte
            (byte)0x81, (byte)0x02,                     //70      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //72      MAIN_COLLECTION_END
            (byte)0x05, (byte)0x09,                     //73      GLOBAL_USAGE_PAGE(Button)
            (byte)0x19, (byte)0x01,                     //75      LOCAL_USAGE_MINIMUM(1)
            (byte)0x29, (byte)0x0A,                     //77      LOCAL_USAGE_MAXIMUM(10)
            (byte)0x95, (byte)0x0A,                     //79      GLOBAL_REPORT_COUNT(10)
            (byte)0x75, (byte)0x01,                     //81      GLOBAL_REPORT_SIZE(1)             10 bit
            (byte)0x81, (byte)0x02,                     //83      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0x05, (byte)0x01,                     //85      GLOBAL_USAGE_PAGE(Generic Desktop Controls)
            (byte)0x09, (byte)0x39,                     //87      LOCAL_USAGE(Hat switch)
            (byte)0x15, (byte)0x01,                     //89      GLOBAL_LOGICAL_MINIMUM(1)
            (byte)0x25, (byte)0x08,                     //91      GLOBAL_LOCAL_MAXIMUM(8)
            (byte)0x35, (byte)0x00,                     //93      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0x3B, (byte)0x10,         //95      GLOBAL_PHYSICAL_MAXIMUM(4155)
            (byte)0x66, (byte)0x0E, (byte)0x00,         //98      GLOBAL_REPORT_UNIT(14)
            (byte)0x75, (byte)0x04,                     //101     GLOBAL_REPORT_SIZE(4)
            (byte)0x95, (byte)0x01,                     //103     GLOBAL_REPORT_COUNT(1)            4 bit
            (byte)0x81, (byte)0x42,                     //105     MAIN_INPUT(data var absolute NoWrap linear PreferredState NullState NonVolatile )
            (byte)0x75, (byte)0x02,                     //107     GLOBAL_REPORT_SIZE(2)
            (byte)0x95, (byte)0x01,                     //109     GLOBAL_REPORT_COUNT(1)            2 bit
            (byte)0x81, (byte)0x03,                     //111     MAIN_INPUT(const var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0x75, (byte)0x08,                     //113     GLOBAL_REPORT_SIZE(8)
            (byte)0x95, (byte)0x02,                     //115     GLOBAL_REPORT_COUNT(2)            2 byte
            (byte)0x81, (byte)0x03,                     //117     MAIN_INPUT(const var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //119     MAIN_COLLECTION_END
    };


    private static Handler handler;
    private static ExecutorService singleThreadExecutor;

    public static void reportTrans() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.myLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        HidReport mHidReport = (HidReport) msg.obj;
                        postReport(mHidReport);
                    }
                };
                Looper.loop();
            }
        });
    }

    private static void postReport(HidReport report) {
        report.SendState = HidReport.State.Sending;
        Log.e("postReport", "ID:" + report.ReportId + "\t\tDATA:" + BytesUtils.toHexStringForLog(report.ReportData));
        boolean ret = HidDevice.sendReport(BtDevice, report.ReportId, report.ReportData);
        if (!ret) {
            report.SendState = HidReport.State.Failded;
        } else {
            report.SendState = HidReport.State.Sended;
        }
    }

    public static void exit() {
        if (handler != null) {
            handler.getLooper().quit();
            handler = null;
        }

        if (singleThreadExecutor != null && !singleThreadExecutor.isShutdown()) {
            singleThreadExecutor.shutdown();
            singleThreadExecutor = null;
        }
    }

    public static void CleanKbd() {
        SendKeyReport(new byte[]{0, 0});
    }

    protected static void addInputReport(final HidReport inputReport) {
        if (handler == null || singleThreadExecutor == null) {
            reportTrans();
        }
        if (inputReport != null && handler != null) {
            Message msg = new Message();
            msg.obj = inputReport;
            handler.sendMessage(msg);
        }
    }


    private static HidReport MouseReport = new HidReport(HidReport.DeviceType.Mouse, (byte) 0x01, new byte[]{0, 0, 0, 0});
    private static HidReport ControllerReport = new HidReport(HidReport.DeviceType.Controller, (byte) 0x04, new byte[]{0, 0, 0, 0, 0, 0});
    private static HidReport ControllerReport_Xbox = new HidReport(HidReport.DeviceType.Controller, (byte) 0x00, new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    //0-3:X Y   4-7:Rx Ry   8-9:Z   10-11.2:10个按键A B X Y      11.3-13:0

    public static void XBtnDown(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] |= 4;
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] |= 4;
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void XBtnUp(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] &= (~4);
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] &= (~4);
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void YBtnDown(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] |= 8;
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] |= 8;
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void YBtnUp(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] &= (~8);
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] &= (~8);
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void ABtnDown(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] |= 1;
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] |= 1;
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void ABtnUp(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] &= (~1);
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] &= (~1);
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void BBtnDown(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[i] |= 2;
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[i] |= 2;
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void BBtnUp(int i){
        if(i == 0) {
            HidConstants.ControllerReport.ReportData[0] &= (~2);
            SendControllerReport(HidConstants.ControllerReport.ReportData);
        }else if(i == 10){
            HidConstants.ControllerReport_Xbox.ReportData[0] &= (~2);
            SendControllerReport_Xbox(HidConstants.ControllerReport_Xbox.ReportData);
        }
    }
    public static void RSInfo_Xbox(double X, double Y, double Rx, double Ry, double Z,
                                    boolean X_flag, boolean Y_flag,
                                    boolean A_flag, boolean B_flag){
        double T1 = 460;
        double T2 = 400;
        X += 230;
        Y += 230;
        Rx += 200;
        Ry += 200;
        Z += 200;
        if(Rx < 0) Rx = 0; else if(Rx > 400) Rx = 400;
        if(Ry < 0) Ry = 0; else if(Ry > 400) Ry = 400;
        if(Z < 0) Z = 0; else if(Z > 400) Z = 400;
//        if(ControllerReport_Xbox.SendState.equals(HidReport.State.Sending)){
//            return;
//        }
        ByteBuffer bufferX = ByteBuffer.allocate(2),bufferY = ByteBuffer.allocate(2),
                bufferRx = ByteBuffer.allocate(2),bufferRy = ByteBuffer.allocate(2),
                bufferZ = ByteBuffer.allocate(2);
        X = X / T1 * 65535;
        bufferX.putShort((short) X);
        Y = Y / T1 * 65535;
        bufferY.putShort((short)Y);
        Rx = Rx / T2 * 65535;
        bufferRx.putShort((short)Rx);
        Ry = Ry / T2 * 65535;
        bufferRy.putShort((short)Ry);
        Z = Z / T2 * 65535;
        bufferZ.putShort((short)Z);
        byte[] bytes_X = bufferX.array(), bytes_Y = bufferY.array(), bytes_Rx = bufferRx.array(),
                bytes_Ry = bufferRy.array(), bytes_Z = bufferZ.array();
        if(X_flag){
            ControllerReport_Xbox.ReportData[10] |= 4;
        }else {
            ControllerReport_Xbox.ReportData[10] = (byte) (ControllerReport_Xbox.ReportData[10] & (~4));
        }
        if(Y_flag){
            ControllerReport_Xbox.ReportData[10] |= 8;
        }else {
            ControllerReport_Xbox.ReportData[10] = (byte) (ControllerReport_Xbox.ReportData[10] & (~8));
        }
        if(A_flag){
            ControllerReport_Xbox.ReportData[10] |= 1;
        }else {
            ControllerReport_Xbox.ReportData[10] = (byte) (ControllerReport_Xbox.ReportData[10] & (~1));
        }
        if(B_flag){
            ControllerReport_Xbox.ReportData[10] |= 2;
        }else {
            ControllerReport_Xbox.ReportData[10] = (byte) (ControllerReport_Xbox.ReportData[10] & (~2));
        }
        ControllerReport_Xbox.ReportData[0] = bytes_X[0];
        ControllerReport_Xbox.ReportData[1] = bytes_X[1];
        ControllerReport_Xbox.ReportData[2] = bytes_Y[0];
        ControllerReport_Xbox.ReportData[3] = bytes_Y[1];
        ControllerReport_Xbox.ReportData[4] = bytes_Rx[0];
        ControllerReport_Xbox.ReportData[5] = bytes_Rx[1];
        ControllerReport_Xbox.ReportData[6] = bytes_Ry[0];
        ControllerReport_Xbox.ReportData[7] = bytes_Ry[1];
        ControllerReport_Xbox.ReportData[8] = bytes_Z[0];
        ControllerReport_Xbox.ReportData[9] = bytes_Z[1];

        addInputReport(ControllerReport_Xbox);
    }

    public static void RSInfo(double X, double Y, double Z, double Rz,
                              boolean X_flag, boolean Y_flag,
                              boolean A_flag, boolean B_flag){
        double T1 = 230;
        double T2 = 200;
//        if(ControllerReport.SendState.equals(HidReport.State.Sending)){
//            return;
//        }
        if(Z < -200) Z = -200; else if(Z > 200) Z = 200;
        if(Rz < -200) Rz = -200; else if(Rz > 200) Rz = 200;

        X = X / T1 * 127;
        Y = Y / T1 * 127;
        Z = Z / T1 * 127;
        Rz = Rz / T2 * 127;


        if(X_flag){
            ControllerReport.ReportData[0] |= 4;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~4));
        }
        if(Y_flag){
            ControllerReport.ReportData[0] |= 8;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~8));
        }
        if(A_flag){
            ControllerReport.ReportData[0] |= 1;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~1));
        }
        if(B_flag){
            ControllerReport.ReportData[0] |= 2;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~2));
        }

        ControllerReport.ReportData[2] = (byte) X;
        ControllerReport.ReportData[3] = (byte) Y;
        ControllerReport.ReportData[4] = (byte) Z;
        ControllerReport.ReportData[5] = (byte) Rz;

        addInputReport(ControllerReport);
    }

    public static void MouseMove(int dx, int dy, int wheel, final boolean leftButton, final boolean rightButton, final boolean middleButton) {
        if (MouseReport.SendState.equals(HidReport.State.Sending)) {
            return;
        }
        if (dx > 127) dx = 127;
        if (dx < -127) dx = -127;
        if (dy > 127) dy = 127;
        if (dy < -127) dy = -127;
        if (wheel > 127) wheel = 127;
        if (wheel < -127) wheel = -127;
        if (leftButton) {
            MouseReport.ReportData[0] |= 1;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~1));
        }
        if (rightButton) {
            MouseReport.ReportData[0] |= 2;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~2));
        }
        if (middleButton) {
            MouseReport.ReportData[0] |= 4;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~4));
        }
        MouseReport.ReportData[1] = (byte) dx;
        MouseReport.ReportData[2] = (byte) dy;
        MouseReport.ReportData[3] = (byte) wheel;

        addInputReport(MouseReport);
    }

    public static void LeftBtnDown() {
        HidConstants.MouseReport.ReportData[0] |= 1;
        SendMouseReport(HidConstants.MouseReport.ReportData);
    }

    public static void LeftBtnUp() {
        HidConstants.MouseReport.ReportData[0] &= (~1);
        SendMouseReport(HidConstants.MouseReport.ReportData);
    }

    public static void LeftBtnClick() {
        LeftBtnDown();
        UtilCls.DelayTask(new Runnable() {
            @Override
            public void run() {
                LeftBtnUp();
            }
        }, 20, true);
    }

    public static TimerTask LeftBtnClickAsync(int delay) {
        return UtilCls.DelayTask(new Runnable() {
            @Override
            public void run() {
                LeftBtnClick();
            }
        }, delay, true);
    }

    public static void RightBtnDown() {
        HidConstants.MouseReport.ReportData[0] |= 2;
        SendMouseReport(HidConstants.MouseReport.ReportData);
    }

    public static void RightBtnUp() {
        HidConstants.MouseReport.ReportData[0] &= (~2);
        SendMouseReport(HidConstants.MouseReport.ReportData);
    }

    public static void MidBtnDown() {
        HidConstants.MouseReport.ReportData[0] |= 4;
        SendMouseReport(HidConstants.MouseReport.ReportData);
    }

    public static void MidBtnUp() {
        HidConstants.MouseReport.ReportData[0] &= (~4);
        SendMouseReport(HidConstants.MouseReport.ReportData);
    }

    public static byte ModifierDown(byte UsageId) {
        synchronized (HidConstants.class) {
            ModifierByte |= UsageId;
        }
        return ModifierByte;
    }

    public static byte ModifierUp(byte UsageId) {
        UsageId = (byte) (~((byte) (UsageId)));
        synchronized (HidConstants.class) {
            ModifierByte = (byte) (ModifierByte & UsageId);
        }
        return ModifierByte;
    }

    public static void KbdKeyDown(String usageStr) {
        if (!TextUtils.isEmpty(usageStr)) {
            if (usageStr.startsWith("M")) {
                usageStr = usageStr.replace("M", "");
                synchronized (HidConstants.class) {
                    byte mod = ModifierDown((byte) Integer.parseInt(usageStr));
                    SendKeyReport(new byte[]{mod, KeyByte});
                }
            } else {
                byte key = (byte) Integer.parseInt(usageStr);
                synchronized (HidConstants.class) {
                    KeyByte = key;
                    SendKeyReport(new byte[]{ModifierByte, KeyByte});
                }
            }
        }
    }

    public static void KbdKeyUp(String usageStr) {
        if (!TextUtils.isEmpty(usageStr)) {
            if (usageStr.startsWith("M")) {
                usageStr = usageStr.replace("M", "");
                synchronized (HidConstants.class) {
                    byte mod = ModifierUp((byte) Integer.parseInt(usageStr));
                    SendKeyReport(new byte[]{mod, KeyByte});
                }
            } else {
                synchronized (HidConstants.class) {
                    KeyByte = 0;
                    SendKeyReport(new byte[]{ModifierByte, KeyByte});
                }
            }
        }
    }

    public static void SendKeyReport(byte[] reportData) {
        HidReport report = new HidReport(HidReport.DeviceType.Keyboard, (byte) 0x02, reportData);
        addInputReport(report);
    }
    public static void SendMouseReport(byte[] reportData) {
        HidReport report = new HidReport(HidReport.DeviceType.Mouse, (byte) 0x01, reportData);
        addInputReport(report);
    }
    public static void SendControllerReport_Xbox(byte[] reportData){
        HidReport report = new HidReport(HidReport.DeviceType.Controller, (byte) 0x00, reportData);
        addInputReport(report);
    }
    public static void SendControllerReport(byte[] reportData){
        HidReport report = new HidReport(HidReport.DeviceType.Controller, (byte) 0x04, reportData);
        addInputReport(report);
    }
}
