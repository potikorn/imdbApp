package com.example.potikorn.movielists.ui.login.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.example.potikorn.movielists.MainActivity
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.dao.user.UserDao
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.extensions.showToast
import com.example.potikorn.movielists.ui.register.RegisterActivity
import com.example.potikorn.movielists.ui.viewmodel.UserViewModel
import com.example.potikorn.movielists.ui.viewmodel.UserViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory
    @Inject
    lateinit var userPref: User
    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun layoutToInflate(): Int = R.layout.fragment_login

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
        btnLogin.setOnClickListener {
            userViewModel.signInWithFirebase(
                UserDao(
                    email = tiEtEmail.text.trim().toString(),
                    password = tiEtPassword.text.trim().toString()
                )
            )
        }
        btnLoginAsGuest.setOnClickListener { userViewModel.requestGuestSession() }
        tvCreateAccount.setOnClickListener {
            startActivity(Intent(context, RegisterActivity::class.java))
        }
        tvNotLogin.setOnClickListener {
            userPref.setFirstTime(false)
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }

    override fun initialize() {
        initViewModel()
    }

    private fun initViewModel() {
        userViewModel.isLoading.observe(this, Observer { })
        userViewModel.error.observe(this, Observer { activity?.showToast(it) })
        userViewModel.liveGuestUserData.observe(this, Observer {
            userPref.apply {
                setFirstTime(false)
                setLogin(true)
                setSessionId(it?.guestSessionId)
                setSessionExpired(it?.expireAt)
            }
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        })
        userViewModel.liveUserViewModel.observe(this, Observer {
            it?.let {
                userPref.apply {
                    setFirstTime(false)
                    setLogin(true)
                    setUserId(it.uid)
                }
                startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }
        })
    }

    companion object {
        fun newInstance(): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}