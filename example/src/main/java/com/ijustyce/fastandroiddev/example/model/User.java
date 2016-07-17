package com.ijustyce.fastandroiddev.example.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ijustyce.fastandroiddev.example.BR;

/**
 * Created by yc on 15-12-31.
 */
public class User extends BaseObservable {

    private String name;
    private int age;

    public User() {
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
