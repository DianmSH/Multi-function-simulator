package com.android.example.btremote.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.base.router.Router;
import com.android.base.ui.XFragment;
import com.android.example.btremote.R;
import com.android.example.btremote.ui.activity.AboutUsActivity;
import com.android.example.btremote.ui.activity.OurTeamActivity;
import com.android.base.SharedPreferencesUtil;
import com.android.example.btremote.ui.views.dialog.AlertDialog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import www.bigkoo.pickerview.builder.OptionsPickerBuilder;
import www.bigkoo.pickerview.listener.OnOptionsSelectListener;
import www.bigkoo.pickerview.view.OptionsPickerView;


public class SettingFragment extends XFragment implements View.OnClickListener {

    private TextView tv_vibration;
    private TextView tvType;
    private int type = 1;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void bindUI(View rootView) {
        tv_vibration = rootView.findViewById(R.id.tv_vibrate);
        rootView.findViewById(R.id.tv_privacy_policy).setOnClickListener(this);
        rootView.findViewById(R.id.tv_about_us).setOnClickListener(this);
        rootView.findViewById(R.id.tv_problem_feekback).setOnClickListener(this);
        rootView.findViewById(R.id.llt_tv_vibration).setOnClickListener(this);
        rootView.findViewById(R.id.llt_tv_type).setOnClickListener(this);


    }

    @Override
    public void initData() {
        int vibrate = (int) SharedPreferencesUtil.getData("vibrate", 50);
        if (vibrate == 200) {
            tv_vibration.setText("强");
        } else if (vibrate == 100) {
            tv_vibration.setText("中");
        } else if (vibrate == 50) {
            tv_vibration.setText("弱");
        } else if (vibrate == -1) {
            tv_vibration.setText("关闭");
        }

        tvType = getActivity().findViewById(R.id.tv_type);
        try {
            readFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(type == 1) {
            tvType.setText("键鼠");
        }else if(type == 0){
            tvType.setText("手柄");
        }else {
            tvType.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llt_tv_vibration:
                List<String> datas = new ArrayList<>();
                datas.add("强");
                datas.add("中");
                datas.add("弱");
                datas.add("关闭");

                OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tv_vibration.setText(datas.get(options1));
                        if (options1 == 0) {
                            SharedPreferencesUtil.putData("vibrate", 200);
                        } else if (options1 == 1) {
                            SharedPreferencesUtil.putData("vibrate", 100);
                        } else if (options1 == 2) {
                            SharedPreferencesUtil.putData("vibrate", 50);
                        } else if (options1 == 3) {
                            SharedPreferencesUtil.putData("vibrate", -1);
                        }
                    }
                })
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setTitleText("请选择按键振动强度")
                        .setBgColor(getResources().getColor(R.color.bg_window))
                        .setTitleBgColor(getResources().getColor(R.color.bg_item))
                        .setTitleColor(getResources().getColor(R.color.main_text))
                        .setCancelColor(getResources().getColor(R.color.main_text))
                        .setSubmitColor(getResources().getColor(R.color.main_text))
                        .setTextColorCenter(getResources().getColor(R.color.main_text))
                        .build();

                pvNoLinkOptions.setNPicker(datas, null, null);
                pvNoLinkOptions.setSelectOptions(0, 0, 0);
                pvNoLinkOptions.show();
                break;
            case R.id.llt_tv_type:
                List<String> datass = new ArrayList<>();
                datass.add("键鼠");
                datass.add("手柄");

                OptionsPickerView pvNoLinkOptionss = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //tv_vibration.setText(datass.get(options1));

                        String str = tvType.getText().toString();
                        if (options1 == 0 && !("键鼠").equals(str)) {
                            new AlertDialog(getActivity())
                                    .init()
                                    .setMsg("注:需要取消蓝牙配对再重连并重启本设备才可切换类型. 是否确认?")
                                    .setNegativeButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {}
                                    })
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String filename = "type.txt";
                                            FileOutputStream outputStream;
                                            tvType.setText("键鼠");
                                            try {
                                                outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                                                String s = "1";
                                                outputStream.write(s.getBytes());
                                                outputStream.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            getActivity().finish();
                                        }
                                    }).show();

                        } else if (options1 == 1 && !("手柄").equals(str)) {
                            new AlertDialog(getActivity())
                                    .init()
                                    .setMsg("注:需要取消蓝牙配对再重连并重启本设备才可切换类型. 是否确认?")
                                    .setNegativeButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {}
                                    })
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String filename = "type.txt";
                                            FileOutputStream outputStream;
                                            tvType.setText("手柄");
                                            try {
                                                outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                                                String s = "0";
                                                outputStream.write(s.getBytes());
                                                outputStream.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            getActivity().finish();
                                        }
                                    }).show();
                        }
                    }
                })
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setTitleText("请选择要使用的设备类型")
                        .setBgColor(getResources().getColor(R.color.bg_window))
                        .setTitleBgColor(getResources().getColor(R.color.bg_item))
                        .setTitleColor(getResources().getColor(R.color.main_text))
                        .setCancelColor(getResources().getColor(R.color.main_text))
                        .setSubmitColor(getResources().getColor(R.color.main_text))
                        .setTextColorCenter(getResources().getColor(R.color.main_text))
                        .build();

                pvNoLinkOptionss.setNPicker(datass, null, null);
                pvNoLinkOptionss.setSelectOptions(0, 0, 0);
                pvNoLinkOptionss.show();
                break;
            case R.id.tv_privacy_policy:
                Router.newIntent(getActivity()).to(OurTeamActivity.class).launch();
                break;
            case R.id.tv_problem_feekback:
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:"));
                sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "20271055@bjtu.edu.cn");
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                try {
                    Intent chooser = Intent.createChooser(sendIntent, "Send mail...");
                    context.startActivity(chooser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_about_us:
                Router.newIntent(getActivity()).to(AboutUsActivity.class).launch();
                break;
        }
    }
    public void readFile() throws IOException {
        FileInputStream inputStream = getActivity().openFileInput("type.txt");
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
