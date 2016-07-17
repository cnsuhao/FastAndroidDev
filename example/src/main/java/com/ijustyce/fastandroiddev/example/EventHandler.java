package com.ijustyce.fastandroiddev.example;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yc on 16-1-26.
 */
public class EventHandler {

    public void onNameClick(View view){

        String s = "your name is ";
        if (view instanceof  TextView){
            s += ((TextView) view).getText().toString();
        }
        Snackbar.make(view, s, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
