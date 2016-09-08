package com.ijustyce.fastandroiddev.example.fragment;

import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.example.BR;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.event.ContactEvent;
import com.ijustyce.fastandroiddev.example.model.Contact;
import com.ijustyce.fastandroiddev.irecyclerview.BindingInfo;

/**
 * Created by yc on 16-9-6.
 */

public class FragmentContactList extends BaseListFragment <Contact> {
    @Override
    public Class getType() {
        return null;
    }

    @Override
    public BindingInfo getBindingInfos() {
        return new BindingInfo(R.layout.item_contact, BR.user).add(BR.handler, new ContactEvent());
    }

    @Override
    public boolean getMoreData() {
        data.clear();
        for (int i =0; i < 10; i++){
           Contact contact = new Contact();
            contact.name.set("name " + i);
            contact.phone.set("15397123143");
            contact.head.set("http://static.comicvine.com/uploads/scale_super" +
                    "/11118/111184089/4055576-5144526196-55258.jpg");
            data.add(contact);
        }
        mIRecyclerView.notifyDataSetChanged();
        return false;
    }
}
