package com.example.potikorn.movielists.ui.setting

import android.os.Bundle
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.di.AppComponent
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment() {

    override fun layoutToInflate(): Int = R.layout.fragment_setting

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {
        etText.setText("NEW TEXT")
    }

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
        etText.setText("Default")
    }

    override fun initialize() {}

    companion object {
        fun newInstance(bundle: Bundle? = null): SettingFragment {
            val settingFragment = SettingFragment()
            val args = Bundle()
            settingFragment.arguments = args
            return settingFragment
        }
    }
}