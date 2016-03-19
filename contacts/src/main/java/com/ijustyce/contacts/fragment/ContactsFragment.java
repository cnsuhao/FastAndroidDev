package com.ijustyce.contacts.fragment;

import android.content.Context;
import android.widget.BaseAdapter;

import com.ijustyce.contacts.adapter.FriendsAdapter;
import com.ijustyce.contacts.http.HttpContacts;
import com.ijustyce.contacts.model.FriendsModel;
import com.ijustyce.fastandroiddev.base.BaseListFragment;

import java.util.List;

/**
 * Created by yc on 16-2-8. 联系人列表
 */
public class ContactsFragment extends BaseListFragment <FriendsModel>{

    FriendsAdapter adapter;
    @Override
    public boolean getMoreData() {

        return HttpContacts.getMoreContacts("token", pageNo, mContext, httpListener);
    }

    @Override
    public Class getType() {
        return null;
    }

    //    @Override
//    public void doSuccess(JSONObject result, String taskId) {
//        super.doSuccess(result, taskId);
//        try {
//            JSONArray array = result == null ? null : result.getJSONArray("array");
//            int size = array == null ? 0 : array.length();
//            for (int i = 0; i < size; i ++){
//                FriendsModel tmp = IJson.fromJson(array.getJSONObject(i).toString(), FriendsModel.class);
//                data.add(tmp);
//            }
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public BaseAdapter buildAdapter(Context mContext, List<FriendsModel> data) {

        if (adapter == null){
            adapter = new FriendsAdapter(mContext, data);
        }
        return adapter;
    }
}
