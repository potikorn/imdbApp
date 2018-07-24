package com.example.potikorn.movielists.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.MainActivity
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.user.UserDao
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.viewmodel.UserViewModel
import com.example.potikorn.movielists.ui.viewmodel.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory
    @Inject
    lateinit var userPref: User
    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ImdbApplication.appComponent.inject(this)
        btnRegister.setOnClickListener {
            when (tiEtPassword.text.trim().toString() != tiEtRePassword.text.trim().toString()) {
                true -> showToast(R.string.msg_password_not_match)
                else -> {
                    userViewModel.createUserWithFirebase(
                        UserDao(
                            email = tiEtEmail.text.trim().toString(),
                            password = tiEtPassword.text.trim().toString()
                        )
                    )
                }
            }
        }
        initViewModel()
    }

    private fun initViewModel() {
        userViewModel.error.observe(this, Observer { showToast(it) })
        userViewModel.liveUserViewModel.observe(this, Observer {
            it?.let {
                userPref.apply {
                    setLogin(true)
                    setUserId(it.uid)
                }
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
            }
        })
    }
}