package com.ijustyce.fastandroiddev.example.event;

import android.view.View;

import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;
import com.ijustyce.fastandroiddev.example.model.CallHistory;
import com.ijustyce.fastandroiddev.example.model.Contact;

/**
 * Created by yc on 16-9-6.
 */

public class ContactEvent {
    public void onClick(View view, Contact contact){
        if (CommonTool.isNull(view, contact)) return;
        ToastUtil.show("id is " + contact.name);
    }

    public void onClick(View view, CallHistory callHistory){
        if (CommonTool.isNull(view, callHistory)) return;
        ToastUtil.show("id is " + callHistory.name);
    }
}