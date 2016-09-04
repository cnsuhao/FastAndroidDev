package com.ijustyce.fastandroiddev.net;

import java.util.List;

/**
 * Created by yangchun on 16/3/8.   网络请求 自动解析基类
 */
public abstract class IResponseList<T> {

    public abstract List<T> getData();
}