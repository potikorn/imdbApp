package com.example.potikorn.movielists.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.potikorn.movielists.ui.movielist.MovieListFragment
import com.example.potikorn.movielists.ui.search.SearchFragment
import com.example.potikorn.movielists.ui.setting.SettingFragment

class MovieViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieListFragment.newInstance()
            1 -> SearchFragment.newInstance()
            else -> SettingFragment.newInstance()
        }

    override fun getCount(): Int = 3
}