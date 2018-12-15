package com.umeng.soexample.morerecycleview.view;

/**
 * 文件描述：
 * 作者：鲁华丰
 * 创建时间：2018/12/15
 */

public interface IView<T> {
    void seccess(T success);
    void error(T error);

}
