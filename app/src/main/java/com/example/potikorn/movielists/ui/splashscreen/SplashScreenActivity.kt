package com.example.potikorn.movielists.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.MainActivity
import com.example.potikorn.movielists.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashScreenActivity : AppCompatActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        compositeDisposable.add(Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { startActivity(Intent(this, MainActivity::class.java)) }
        )
    }
}