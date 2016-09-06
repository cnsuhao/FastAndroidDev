package com.ijustyce.fastandroiddev.example.model;

import android.databinding.ObservableField;

import com.ijustyce.fastandroiddev.base.BaseViewModel;

/**
 * Created by yc on 16-9-6.
 */

public class CallHistory extends BaseViewModel {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> contactId = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();
}
