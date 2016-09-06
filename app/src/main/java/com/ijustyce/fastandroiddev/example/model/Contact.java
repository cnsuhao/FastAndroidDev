package com.ijustyce.fastandroiddev.example.model;

import android.database.Observable;
import android.databinding.ObservableField;

import com.ijustyce.fastandroiddev.base.BaseViewModel;

/**
 * Created by yc on 16-9-6.
 */

public class Contact extends BaseViewModel {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> id = new ObservableField<>();
    public final ObservableField<String> head = new ObservableField<>();
}
