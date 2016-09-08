package com.ijustyce.fastandroiddev.example.fragment;

import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.baseLib.utils.DateUtil;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.BR;
import com.ijustyce.fastandroiddev.example.event.ContactEvent;
import com.ijustyce.fastandroiddev.example.model.CallHistory;
import com.ijustyce.fastandroiddev.irecyclerview.BindingInfo;

/**
 * Created by yc on 16-9-6.
 */

public class FragmentCallHistory extends BaseListFragment<CallHistory> {
    @Override
    public Class getType() {
        return null;
    }

    @Override
    public BindingInfo getBindingInfos() {
        return new BindingInfo(R.layout.item_call_history, BR.call).add(BR.handler, new ContactEvent());
    }

    @Override
    public boolean getMoreData() {
        data.clear();
        for (int i = 0; i < 10; i++) {
            CallHistory callHistory = new CallHistory();
            callHistory.name.set("name " + i);
            callHistory.phone.set("15397123143");
            callHistory.contactId.set("i");
            callHistory.time.set(DateUtil.getDateString("yyyy-MM-dd HH:mm"));
            callHistory.type.set("来电");
            data.add(callHistory);
        }
        mIRecyclerView.notifyDataSetChanged();
        return false;
    }
}
