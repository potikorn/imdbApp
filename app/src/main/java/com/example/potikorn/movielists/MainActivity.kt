package com.example.potikorn.movielists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance(savedInstanceState)
    }

    private fun initInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = MainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, fragment, fragment.javaClass.name)
                .commitAllowingStateLoss()
        }
    }
}
