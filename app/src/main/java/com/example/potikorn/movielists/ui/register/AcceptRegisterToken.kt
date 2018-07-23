package com.example.potikorn.movielists.ui.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.potikorn.movielists.R
import kotlinx.android.synthetic.main.activity_web_view.*

class AcceptRegisterToken : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webViewContent.loadUrl("https://www.themoviedb.org/authenticate/598850f9720d81c00575d1c564685f7b108e94b6")
    }
}