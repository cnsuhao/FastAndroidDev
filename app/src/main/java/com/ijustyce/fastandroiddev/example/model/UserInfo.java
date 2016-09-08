package com.ijustyce.fastandroiddev.example.model;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.ijustyce.fastandroiddev.base.BaseViewModel;

/**
 * Created by yc on 16-9-6.
 */

public class UserInfo extends BaseViewModel{
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> headerUrl = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> nickName = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
}
