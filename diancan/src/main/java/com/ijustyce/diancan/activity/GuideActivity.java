package com.ijustyce.diancan.activity;

import android.content.Intent;

import com.ijustyce.fastandroiddev.base.BaseGuideActivity;
import com.ijustyce.fastandroiddev.umenglib.UpdateTool;
import com.ijustyce.diancan.IApplication;
import com.ijustyce.diancan.fragment.GuideFour;
import com.ijustyce.diancan.fragment.GuideOne;
import com.ijustyce.diancan.fragment.GuideThree;
import com.ijustyce.diancan.fragment.GuideTwo;

/**
 * Created by yc on 16-3-14.    引导页
 */
public class GuideActivity extends BaseGuideActivity {

    @Override
    public void afterCreate(){

        if (IApplication.isLogin()){

            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        UpdateTool.update(this);
    }

    @Override
    public void addFragment() {

        mFragmentList.add(new GuideOne());
        mFragmentList.add(new GuideTwo());
        mFragmentList.add(new GuideThree());
        mFragmentList.add(new GuideFour());
    }
}
