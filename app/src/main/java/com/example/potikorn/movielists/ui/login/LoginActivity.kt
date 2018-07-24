package com.example.potikorn.movielists.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.extensions.addFragment
import com.example.potikorn.movielists.ui.login.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ImdbApplication.appComponent.inject(this)
        setupView()
    }

    private fun setupView() {
        addFragment(LoginFragment.newInstance(), R.id.frameContainer)
    }
}