package com.example.potikorn.movielists.ui.setting

import android.content.Intent
import android.os.Bundle
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.ui.login.LoginActivity
import com.example.potikorn.movielists.ui.setting.favorite.FavoriteFragment
import kotlinx.android.synthetic.main.fragment_main_setting.*
import javax.inject.Inject

class MainSettingFragment : BaseFragment() {

    @Inject
    lateinit var user: User

    override fun layoutToInflate(): Int = R.layout.fragment_main_setting

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
        tvFavorite.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.activity_left_push_in, R.anim.activity_left_push_out)
                ?.addToBackStack(MainSettingFragment::class.java.simpleName)
                ?.add(R.id.frameMainSettingContainer, FavoriteFragment.newInstance())?.commit()
        }
        tvLogout.setOnClickListener {
            user.apply { clear() }
            startActivity(
                Intent(context, LoginActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
    }

    override fun initialize() {}

    companion object {
        fun newInstance(bundle: Bundle? = null): MainSettingFragment {
            val mainSettingFragment = MainSettingFragment()
            mainSettingFragment.arguments = bundle
            return mainSettingFragment
        }
    }
}