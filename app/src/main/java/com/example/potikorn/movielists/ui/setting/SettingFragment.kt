package com.example.potikorn.movielists.ui.setting

import android.os.Bundle
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.di.AppComponent
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

    override fun setupView() {}

    override fun initialize() {
        fragmentManager?.beginTransaction()
            ?.add(R.id.frameMainSettingContainer, MainSettingFragment.newInstance())?.commit()
    }

    companion object {
        fun newInstance(bundle: Bundle? = null): SettingFragment {
            val settingFragment = SettingFragment()
            settingFragment.arguments = bundle
            return settingFragment
        }
    }
}