package com.ijustyce.xingche.activity;

import android.view.View;
import android.widget.TextView;

import com.ijustyce.fastandroiddev.base.BaseTabActivity;
import com.ijustyce.fastandroiddev.umenglib.UpdateTool;
import com.ijustyce.xingche.AppApplication;
import com.ijustyce.xingche.R;
import com.ijustyce.xingche.fragment.MeFragment;
import com.ijustyce.xingche.fragment.NewRecordFragment;
import com.ijustyce.xingche.fragment.RecordListFragment;

import butterknife.Bind;

public class MainActivity extends BaseTabActivity {

    @Bind(R.id.right)
    TextView right;
    @Override
    public void addFragment() {

        mFragmentList.add(new NewRecordFragment());
        mFragmentList.add(new RecordListFragment());
        mFragmentList.add(new MeFragment());
    }

    @Override
    public void onPageSelect(int position) {
        super.onPageSelect(position);
        if (position != 1){
            right.setVisibility(View.INVISIBLE);
        }else{
            right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterCreate() {

        if (!AppApplication.isLogin()){
            newActivity(LoginActivity.class);
        }
        addTab(R.layout.tab_jiaowu, R.id.radioButton);
        addTab(R.layout.tab_kebiao, R.id.radioButton);
        addTab(R.layout.tab_me, R.id.radioButton);

        right.setText("搜索");
        UpdateTool.update(this);
        onPageSelect(0);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newActivity(RecordListActivity.class);
            }
        });
    }
}
