package com.android.example.btremote.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.android.base.ui.XActivity;
import com.android.example.btremote.R;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

public class OurTeamActivity extends XActivity implements View.OnClickListener {

    private TextView tv_msg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public int getActivityTitle() { return R.string.our_team; }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)

                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

        tv_msg = findViewById(R.id.tv_msg);

        tv_msg.setText("我们的团队成员有" + "\n" + "项目经理：张鑫成" + "\n" + "开发团队成员：刘本初     王继航     侯卓甲     张辰昕"
         + "\n" + "公司CEO：张迪" + "\n" + "如果您对此项目满意的话，欢迎您在设置-支持我们中进行打赏。您的每一份支持，就是我们继续下去的最大动力！");
    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_menu:
                break;
        }

    }
}