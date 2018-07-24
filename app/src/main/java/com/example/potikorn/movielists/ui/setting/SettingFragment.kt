package com.example.potikorn.movielists.ui.setting

import android.content.Intent
import android.os.Bundle
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject

class SettingFragment : BaseFragment() {

    @Inject
    lateinit var user: User

    override fun layoutToInflate(): Int = R.layout.fragment_setting

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
        tvLogout.setOnClickListener {
            user.apply {
                setLogin(false)
                setSessionExpired("")
                setSessionId("")
            }
            startActivity(
                Intent(context, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
    }

    override fun initialize() {}

    companion object {
        fun newInstance(bundle: Bundle? = null): SettingFragment {
            val settingFragment = SettingFragment()
            settingFragment.arguments = bundle
            return settingFragment
        }
    }
}