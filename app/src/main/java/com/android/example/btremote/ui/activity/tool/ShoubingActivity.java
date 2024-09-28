package com.android.example.btremote.ui.activity.tool;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.example.btremote.R;
import com.android.example.btremote.hid.HidConstants;
import com.android.example.btremote.utils.RockerView;
import com.android.example.btremote.utils.VibrateUtil;

public class ShoubingActivity extends AppCompatActivity implements SensorEventListener{
    private boolean XUp = true; // X 抬起
    private boolean YUp = true; // Y 抬起
    private boolean AUp = true; // A 抬起
    private boolean BUp = true; // B 抬起
    private int i = 0; // 判断是否为Xbox
    private SensorManager sm; // 陀螺仪
    private double Rx;
    private double Ry;
    private double Rz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoubing);

        ImageView close = findViewById(R.id.close);
        RockerView rock = findViewById(R.id.rock);
//        TextView tv_area = findViewById(R.id.tv_area);

        //X Y A B
        ImageButton btn_X = findViewById(R.id.btn_X);
        ImageButton btn_Y = findViewById(R.id.btn_Y);
        ImageButton btn_A = findViewById(R.id.btn_A);
        ImageButton btn_B = findViewById(R.id.btn_B);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 获取传感器管理器
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 调用方法获得需要的传感器
        Sensor mSensorOrientation = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // 注册监听器
        // SENSOR_DELAY_FASTEST最灵敏
        // SENSOR_DELAY_GAME 游戏的时候，不过一般用这个就够了
        // SENSOR_DELAY_NORMAL 比较慢。
        // SENSOR_DELAY_UI 最慢的
        sm.registerListener(this, mSensorOrientation, android.hardware.SensorManager.SENSOR_DELAY_UI);

        btn_X.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    XUp = false;
                    HidConstants.XBtnDown(i);
                    btn_X.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    XUp = true;
                    HidConstants.XBtnUp(i);
                    btn_X.setBackgroundResource(R.drawable.btn_bg);
//                   System.out.println(111);
                }
                return false;
            }
        });

        btn_Y.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    YUp = false;
                    HidConstants.YBtnDown(i);
                    btn_Y.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    YUp = true;
                    HidConstants.YBtnUp(i);
                    btn_Y.setBackgroundResource(R.drawable.btn_bg);
                }
                return false;
            }
        });

        btn_A.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    AUp = false;
                    HidConstants.ABtnDown(i);
                    btn_A.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    AUp = true;
                    HidConstants.ABtnUp(i);
                    btn_A.setBackgroundResource(R.drawable.btn_bg);
                }
                return false;
            }
        });

        btn_B.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BUp = false;
                    HidConstants.BBtnDown(i);
                    btn_B.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    BUp = true;
                    HidConstants.BBtnUp(i);
                    btn_B.setBackgroundResource(R.drawable.btn_bg);
                }
                return false;
            }
        });

        //tv_area.setText(String.valueOf(0));
        rock.setOnAngleChangeListener(new RockerView.OnAngleChangeListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void angle(double angle, double x, double y) {
                //计算斜边，由于是直角三角形，可知：勾股数组程a² + b² = c²
                //angle 移动角度 0~90 第一象限  90～180 第二象限 180～270 第三象限 270～360 第四象限
                double X1 = x - 230;
                double Y1 = y - 230;

                if (X1 > 230) X1 = 230;
                else if (X1 < -230) X1 = -230;

                if (Y1 > 230) Y1 = 230;
                else if (Y1 < -230) Y1 = -230;

//                System.out.println(X1);
//                System.out.println(Y1);
                if(i == 10) {
                    HidConstants.RSInfo_Xbox(X1,Y1,Rx,Ry,Rz,!XUp,!YUp,!AUp,!BUp);
                }else if(i == 0) {
                    HidConstants.RSInfo(X1,Y1,Rx,Ry,!XUp,!YUp,!AUp,!BUp);
                }
            }

            @Override
            public void onFinish() {
            }
        });
    }

    // 该方法在传感器的值发生改变的时候调用
    @Override
    public void onSensorChanged(SensorEvent event) {
        Rx = (double) Math.toDegrees(event.values[0]);
        Ry = (double) Math.toDegrees(event.values[1]);
        Rz = (double) Math.toDegrees(event.values[2]);
    }

    // 当传感器的进度发生改变时会回调
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    // 在activity变为不可见的时候，传感器依然在工作，这样很耗电，所以我们根据需求可以在onPause方法里面停掉传感器的工作
    @Override
    public void onPause() {
        sm.unregisterListener(this);
        super.onPause();
    }
}