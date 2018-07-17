package com.example.potikorn.movielists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.ui.MovieViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance(savedInstanceState)
    }

    private fun initInstance(savedInstanceState: Bundle?) {
            vpMain.apply {
                setPagingEnabled(false)
                offscreenPageLimit = 2
                adapter = MovieViewPagerAdapter(supportFragmentManager)
            }
            initBottomNavigation()
    }

    private fun initBottomNavigation() {
        bottom_nav_view.selectedItemId = R.id.item_home
        bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    vpMain.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.item_search -> {
                    vpMain.setCurrentItem(1, false)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    vpMain.setCurrentItem(2, false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }
}
