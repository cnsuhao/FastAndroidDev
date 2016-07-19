package com.ijustyce.fastandroiddev.example.EventHandler;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.ijustyce.fastandroiddev.base.BaseActivity;
import com.ijustyce.fastandroiddev.example.RecyclerViewActivity;

/**
 * Created by yc on 16-1-26.
 */
public class ShowMVVMEvent {

    private BaseActivity mActivity;

    public ShowMVVMEvent(BaseActivity mActivity){
        this.mActivity = mActivity;
    }

    public void onNameClick(View view){

        String s = "your name is ";
        if (view instanceof  TextView){
            s += ((TextView) view).getText().toString();
        }
        Snackbar.make(view, s, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void gotoListView(View view){
        Snackbar.make(view, "转到listview", Snackbar.LENGTH_SHORT).show();
        mActivity.newActivity(RecyclerViewActivity.class);
    }

    public void onAgeClick(View view){

        Snackbar.make(view, "你小于25岁", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void onBigAgeClick(View view){

        Snackbar.make(view, "你大于25岁", Snackbar.LENGTH_LONG)
                .setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }
}
