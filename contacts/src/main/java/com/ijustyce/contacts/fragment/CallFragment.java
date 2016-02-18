package com.ijustyce.contacts.fragment;

import android.view.View;
import android.widget.TextView;

import com.ijustyce.contacts.R;
import com.ijustyce.fastandroiddev.base.BaseFragment;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yc on 16-2-8. 拨号界面
 */
public class CallFragment extends BaseFragment {

    @Bind(R.id.phoneNum)
    TextView phoneNum;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_call;
    }

    @OnClick({R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5,
            R.id.num6, R.id.num7, R.id.num8, R.id.num9, R.id.call, R.id.delete })
    public void onClick(View view){

        if (view == null){
            return;
        }

        String text = phoneNum.getText().toString();

        switch (view.getId()) {

            case R.id.num0:
                text += getResString(R.string.num0);
                break;

            case R.id.num1:
                text += getResString(R.string.num1);
                break;

            case R.id.num2:
                text += getResString(R.string.num2);
                break;

            case R.id.num3:
                text += getResString(R.string.num3);
                break;

            case R.id.num4:
                text += getResString(R.string.num4);
                break;

            case R.id.num5:
                text += getResString(R.string.num5);
                break;

            case R.id.num6:
                text += getResString(R.string.num6);
                break;

            case R.id.num7:
                text += getResString(R.string.num7);
                break;

            case R.id.num8:
                text += getResString(R.string.num8);
                break;

            case R.id.num9:
                text += getResString(R.string.num9);
                break;

            case R.id.delete:
                text = text.substring(0, text.length() > 1 ? text.length() -1 : 0);
                break;

            case R.id.call:
                CommonTool.toCallPhoneActivity(mContext, text);
                break;
        }

        phoneNum.setText(text);
    }
}
