package com.example.potikorn.movielists.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.dao.user.UserDao
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.viewmodel.UserViewModel
import com.example.potikorn.movielists.ui.viewmodel.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ImdbApplication.appComponent.inject(this)
        btnRegister.setOnClickListener {
            userViewModel.createUserWithFirebase(
                UserDao(
                    displayName = "BB",
                    email = etEmail.text.trim().toString(),
                    password = etPassword.text.trim().toString()
                )
            )
        }
        initViewModel()
    }

    private fun initViewModel() {
        userViewModel.liveUserViewModel.observe(this, Observer {
            it?.let {
                showToast(R.string.msg_user_has_signed_in)
                Log.i(TAG, "${it.isAnonymous}")
                Log.i(TAG, "${it.displayName}")
                Log.i(TAG, "${it.email}")
                Log.i(TAG, it.uid)
            }
        })
    }
}