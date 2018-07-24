package com.example.potikorn.movielists.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.MainActivity
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.register.RegisterActivity
import com.example.potikorn.movielists.ui.viewmodel.UserViewModel
import com.example.potikorn.movielists.ui.viewmodel.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    @Inject
    lateinit var userPref: User

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ImdbApplication.appComponent.inject(this)
        setupView()
        initViewModel()
    }

    private fun setupView() {
        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnLoginAsGuest.setOnClickListener { userViewModel.requestGuestSession() }
        tvNotLogin.setOnClickListener {
            userPref.setFirstTime(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initViewModel() {
        userViewModel.isLoading.observe(this, Observer { })
        userViewModel.error.observe(this, Observer { showToast(it) })
        userViewModel.liveGuestUserData.observe(this, Observer {
            userPref.apply {
                setFirstTime(false)
                setLogin(true)
                setSessionId(it?.guestSessionId)
                setSessionExpired(it?.expireAt)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
    }
}