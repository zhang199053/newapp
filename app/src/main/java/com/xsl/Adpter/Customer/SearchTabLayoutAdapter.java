package com.xsl.Adpter.Customer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Des：
 *
 * @author zhouyuru
 * @date 2018/4/8
 */

public class SearchTabLayoutAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] tiles;
    private Fragment[] mFragments ;

    public SearchTabLayoutAdapter(FragmentManager fm, Context mContext, String[] tiles, Fragment[] mFragments) {
        super(fm);
        this.mContext = mContext;
        this.tiles = tiles;
        this.mFragments = mFragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return tiles[position];
    }
}
