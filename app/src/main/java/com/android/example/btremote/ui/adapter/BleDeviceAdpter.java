package com.android.example.btremote.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.base.adapter.SingleAdapter;
import com.android.base.adapter.SuperViewHolder;
import com.android.example.btremote.R;
import com.android.example.btremote.bean.MBluetoothDevice;

public class BleDeviceAdpter extends SingleAdapter<MBluetoothDevice> {
    public BleDeviceAdpter(Context context) {
        super(context, R.layout.item_ble_device);
    }

    @Override
    protected void bindData(SuperViewHolder holder, MBluetoothDevice item, int position) {
        super.bindData(holder, item, position);

        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_mac = holder.getView(R.id.tv_mac);
        TextView tv_ispair = holder.getView(R.id.tv_ispair);
        String name = item.mBluetoothDevice.getName();
        if (TextUtils.isEmpty(name)) {
            tv_name.setText("无法识别名称");
            tv_name.setTextColor(getContext().getColor(R.color.red_text));
        } else {
            tv_name.setTextColor(getContext().getColor(R.color.main_text));
            tv_name.setText(name);
        }
        tv_mac.setText(item.mBluetoothDevice.getAddress());
        if (item.type == 1) {
            tv_ispair.setVisibility(View.VISIBLE);
        } else {
            tv_ispair.setVisibility(View.GONE);
        }

        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(item);
                }

            }
        });
    }

    private OnClickListener mOnClickListener;

    public void setmOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnClickListener {
        void onClick(MBluetoothDevice item);
    }
}
