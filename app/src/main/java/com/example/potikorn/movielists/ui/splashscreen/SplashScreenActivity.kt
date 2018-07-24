package com.example.potikorn.movielists.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.MainActivity
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.ui.login.LoginActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var userData: User

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImdbApplication.appComponent.inject(this)
        setContentView(R.layout.activty_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        startDelay()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun startDelay() {
        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isLogin() }
        )
    }

    private fun isLogin() {
        if (userData.isFirstTime()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            when (!userData.isLogin()) {
                true -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                else -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}