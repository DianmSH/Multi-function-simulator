package com.android.example.btremote.ui.activity.tool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.example.btremote.R;
import com.android.example.btremote.bean.KeyBean;
import com.android.example.btremote.hid.HidConstants;
import com.android.example.btremote.hid.KeyConfigs;
import com.android.example.btremote.ui.activity.SelectActivity;
import com.android.example.btremote.utils.VibrateUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiyActivity extends AppCompatActivity {


    Boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        float buttonX = -1, buttonY = -1;

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diy);

        ArrayList<String> loadedDataArrayList = new ArrayList<>();
        float[] loadedXArray = new float[1000];
        float[] loadedYArray = new float[1000];

        //读取保存的按钮
        try {
            FileInputStream inputStream = openFileInput("data_list.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            String savedArray = stringBuilder.toString();
            loadedDataArrayList = new ArrayList<>(Arrays.asList(savedArray.split(",")));
            if(loadedDataArrayList.get(0).equals("")){
                loadedDataArrayList.remove(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = openFileInput("X_list.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            String savedArray = stringBuilder.toString();
            ArrayList<String> loadedXArrayList = new ArrayList<>(Arrays.asList(savedArray.split(",")));

            for (int i = 0; i < loadedXArrayList.size(); i++) {
                loadedXArray[i] = Float.parseFloat(loadedXArrayList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream inputStream = openFileInput("Y_list.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            String savedArray = stringBuilder.toString();
            ArrayList<String> loadedYArrayList = new ArrayList<>(Arrays.asList(savedArray.split(",")));

            for (int i = 0; i < loadedYArrayList.size(); i++) {
                loadedYArray[i] = Float.parseFloat(loadedYArrayList.get(i));
            }
            System.out.println("Xarry"+loadedXArray);
            System.out.println("Yarry"+loadedYArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FrameLayout diyLayout = findViewById(R.id.diyLayout);
        Button editButton = findViewById(R.id.editButton);
        Button addButton = findViewById(R.id.addButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button hengButton = findViewById(R.id.hengButton);
        addButton.setEnabled(false);
        addButton.setVisibility(View.GONE);

        //跳转事件
        Intent addIntent = new Intent(this, SelectActivity.class);
       //Intent delteteIntent = new Intent(this, DeleteActivity.class);

        //所有数据
        ArrayList<String> dataList= new ArrayList<String>();
        ArrayList<Float> xList = new ArrayList<Float>();
        ArrayList<Float> yList = new ArrayList<Float>();


        //获取select页面传回来的数据
        ArrayList<String> newDataList = this.getIntent().getStringArrayListExtra("dataList");
        float[] newXArray = this.getIntent().getFloatArrayExtra("xArray");
        float[] newYArray = this.getIntent().getFloatArrayExtra("yArray");
        System.out.println(211);
        if(newDataList!=null&&newXArray!=null&&newYArray!=null){
            //判断组件数据
            //传回来的数据赋值到本地
            dataList = newDataList;
            loadButton(dataList,newXArray,newYArray,diyLayout,addIntent);

        }else {
            //将数据传到select页面
            dataList = loadedDataArrayList;
            newXArray = loadedXArray;
            newYArray = loadedYArray;
            loadButton(loadedDataArrayList, loadedXArray, loadedYArray, diyLayout, addIntent);

        }


        AtomicBoolean flag = new AtomicBoolean(true);
        hengButton.setOnClickListener(view -> {
            if(flag.get()) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                flag.set(false);
            }
            else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                flag.set(true);
            }
        });

        //编辑按钮的绑定事件
        editButton.setOnClickListener(v -> {
            if(isEdit) {
                isEdit = false;
                saveButton.setText("保存");
                editButton.setText("编辑");
                addButton.setEnabled(false);
                addButton.setVisibility(View.GONE);
                hengButton.setEnabled(true);
                hengButton.setVisibility(View.VISIBLE);
            } else {
                isEdit = true;
                saveButton.setText("删除");
                editButton.setText("退出编辑");
                addButton.setEnabled(true);
                addButton.setVisibility(View.VISIBLE);
                hengButton.setEnabled(false);
                hengButton.setVisibility(View.GONE);
            }
        });

        //添加按钮的绑定事件
        addButton.setOnClickListener(v -> {
            addIntent.putExtra("buttonType","add");
            startActivity(addIntent);
            finish();
        });

        //保存按钮的绑定事件
        ArrayList<String> finalDataList = dataList;
        float[] finalNewXArray = newXArray;
        float[] finalNewYArray = newYArray;
        saveButton.setOnClickListener(v -> {
            if(isEdit){
                addIntent.putExtra("buttonType","delete");
                startActivity(addIntent);
                finish();
            }else{
                if(true){
                    //ArrayList<String> arrayList = new ArrayList<>();
                    String dataArray = TextUtils.join(",", finalDataList);
                    String filename = "data_list.txt";
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(dataArray.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ArrayList<String> newXArrayList = new ArrayList<>();
                    for (float f : finalNewXArray) {
                        newXArrayList.add(Float.toString(f));
                    }
                    String XArray = TextUtils.join(",", newXArrayList);
                    System.out.println("Xarry:   "+XArray);
                    filename = "X_list.txt";
                    //FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(XArray.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    ArrayList<String> newYArrayList = new ArrayList<>();
                    for (float f : finalNewYArray) {
                        newYArrayList.add(Float.toString(f));
                    }
                    String YArray = TextUtils.join(",", newYArrayList);
                    filename = "Y_list.txt";
                    System.out.println("Yarry:  "+YArray);
                    //FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(YArray.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        });
    }

    private void loadButton(ArrayList<String> dataList,float[] newXArray,float[] newYArray,FrameLayout diyLayout,Intent addIntent){
        int flag = 0;
        if(dataList != null){
            List<KeyBean> Beans = KeyConfigs.getKeys();
            for (String value: dataList) {
                View vid = findViewById(Integer.parseInt(value));
                for(KeyBean bean:Beans){
                    if(bean.vid == Integer.parseInt(value)){
                        vid.setTag(bean.key);
                        vid.setPadding(10, 0, 10, 0);//内边距
                        vid.setX(newXArray[flag]);
                        vid.setY(newYArray[flag]);
                        int finalFlag = flag;
                        vid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.setOnTouchListener(new View.OnTouchListener() {
                                    private float lastX, lastY;
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if(isEdit){
                                            switch (event.getAction()) {
                                                case MotionEvent.ACTION_DOWN:
                                                    // 记录手指按下时的坐标
                                                    lastX = event.getRawX();
                                                    lastY = event.getRawY();
                                                    v.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                                                    System.out.println(lastX);
                                                    break;
                                                case MotionEvent.ACTION_MOVE:
                                                    // 计算手指移动的距离
                                                    float deltaX = event.getRawX() - lastX;
                                                    float deltaY = event.getRawY() - lastY;

                                                    // 更新按钮的位置
                                                    v.setX(v.getX() + deltaX);
                                                    v.setY(v.getY() + deltaY);

                                                    // 更新lastX和lastY
                                                    lastX = event.getRawX();
                                                    lastY = event.getRawY();
                                                    break;
                                                case MotionEvent.ACTION_UP:
                                                    // 清除按钮的触摸事件监听器
                                                    newXArray[finalFlag] = v.getX();
                                                    newYArray[finalFlag] = v.getY();
                                                    v.setBackgroundResource(R.drawable.shape_key_sel_c5);
                                                    //v.setOnTouchListener(null);
                                                    break;
                                            }
                                        }else {
                                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                HidConstants.KbdKeyDown(v.getTag().toString());
                                                System.out.println(v.getTag().toString());
                                                v.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                                                VibrateUtil.vibrate();
                                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                                HidConstants.KbdKeyUp(v.getTag().toString());
                                                v.setBackgroundResource(R.drawable.shape_key_sel_c5);
                                            }
                                        }
                                        return false;
                                    }
                                });
                            }
                        });

                       /* vid.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                // 设置按钮可拖动
                                v.setOnTouchListener(new View.OnTouchListener() {
                                    private float lastX, lastY;
                                    boolean isFirst = true;
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {

                                        return true;
                                    }
                                });
                                return true;
                            }
                        });
*/
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200, 200);
                        LinearLayout parentLayout = (LinearLayout)vid.getParent();
                        parentLayout.removeView(vid);
                        diyLayout.addView(vid, params);
                        flag++;
                    }
                }
            }
            //将数据传到select页面
            addIntent.putStringArrayListExtra("dataList", dataList);
            addIntent.putExtra("xArray",newXArray);
            addIntent.putExtra("yArray",newYArray);

        }

    }
    private void deleteButton(){

    }
}