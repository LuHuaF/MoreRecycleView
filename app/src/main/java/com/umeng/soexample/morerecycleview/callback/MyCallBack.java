package com.umeng.soexample.morerecycleview.callback;

/**
 * 文件描述：
 * 作者：鲁华丰
 * 创建时间：2018/12/15
 */
public interface MyCallBack<T> {
    void setSuccess(T success);
    void setRrror(T error);
}
