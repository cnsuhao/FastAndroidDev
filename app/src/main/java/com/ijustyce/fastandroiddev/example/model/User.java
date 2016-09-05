package com.ijustyce.fastandroiddev.example.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ijustyce.fastandroiddev.base.BaseViewModel;
import com.ijustyce.fastandroiddev.example.BR;

/**
 * Created by yc on 15-12-31.
 */
public class User extends BaseViewModel {

    private String name;
    private int age;
    public String header = "http://gravatar.oschina.net/avatar/39a701a70b4a8d4a25d93e31edce21e3?s=300&d=mm";

    public User() {
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setHeader(String header){
        this.header = header;
        notifyPropertyChanged(BR.handler);
    }

    @Bindable
    public String getHeader(){
        return header;
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
