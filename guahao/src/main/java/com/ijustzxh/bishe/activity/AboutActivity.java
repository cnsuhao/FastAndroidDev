package com.ijustzxh.bishe.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustzxh.bishe.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zxh on 2016/1/19 0019.
 */
public class AboutActivity extends AppBaseActivity {

    @Bind(R.id.version)
    TextView version;
    @Override
    public int getLayoutId() {
        return R.layout.person_about;
    }

    @Override
    public void afterCreate() {

        version.setText(getResText(R.string.version).replace("#version#", CommonTool.getVersionName(mContext)));
    }

    @OnClick({R.id.back, R.id.aboutLayout2})
    public void viewClick(View view) {

        if (view == null){
            return;
        }

        switch (view.getId()) {

            case R.id.back:
                backPress();
                break;

            case R.id.aboutLayout2:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:17764576160"));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
}
