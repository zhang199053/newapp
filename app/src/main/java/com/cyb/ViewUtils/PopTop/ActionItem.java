package com.cyb.ViewUtils.PopTop;

import android.content.Context;

/**
 * 功能描述：弹窗内部子类项（绘制标题和图标）
 */
public class ActionItem {
    // 定义文本对象
    public CharSequence id;
    // 定义文本对象
    public CharSequence name;
    // 定义文本对象
    public CharSequence date;
    // 定义文本对象
    public CharSequence sort;

    public ActionItem() {


    }

    public ActionItem(CharSequence id, CharSequence name, CharSequence date, CharSequence sort) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.sort = sort;

    }

    public CharSequence getId() {
        return id;
    }

    public void setId(CharSequence id) {
        this.id = id;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public CharSequence getDate() {
        return date;
    }

    public void setDate(CharSequence date) {
        this.date = date;
    }

    public CharSequence getSort() {
        return sort;
    }

    public void setSort(CharSequence sort) {
        this.sort = sort;
    }
}
