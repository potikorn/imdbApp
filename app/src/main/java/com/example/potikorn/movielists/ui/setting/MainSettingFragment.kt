package com.example.potikorn.movielists.ui.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.potikorn.movielists.R
import com.example.potikorn.movielists.base.ui.BaseFragment
import com.example.potikorn.movielists.base.ui.FragmentLifecycleOwner
import com.example.potikorn.movielists.data.AppSettings
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.ui.login.LoginActivity
import com.example.potikorn.movielists.ui.setting.favorite.FavoriteFragment
import kotlinx.android.synthetic.main.fragment_main_setting.*
import javax.inject.Inject

class MainSettingFragment : BaseFragment() {

    @Inject
    lateinit var user: User

    @Inject
    lateinit var setting: AppSettings

    override fun layoutToInflate(): Int = R.layout.fragment_main_setting

    override fun createLifecycleOwner(): FragmentLifecycleOwner = FragmentLifecycleOwner.create()

    override fun doInjection(appComponent: AppComponent) = appComponent.inject(this)

    override fun startView() {}

    override fun stopView() {}

    override fun destroyView() {}

    override fun setupInstance() {}

    override fun setupView() {
        tvChangeLangLabel.apply {
            text = setting.getLang()
            setOnClickListener { createOptionsDialog() }
        }
        tvFavorite.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.activity_left_push_in,
                    R.anim.activity_left_push_out,
                    R.anim.activity_right_push_in,
                    R.anim.activity_right_push_out
                )
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

    private fun createOptionsDialog() {
        val optionsLabel = resources.getStringArray(R.array.choose_language)
        val optionsValue = resources.getStringArray(R.array.language)
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setCancelable(true)
        builder?.setTitle(getString(R.string.title_select_your_language))
        builder?.setItems(optionsLabel) { _, which ->
            when (optionsLabel[which]) {
                optionsLabel[0] -> {
                    setting.setLang(optionsValue[0])
                    tvChangeLangLabel.text = optionsValue[0]
                }
                optionsLabel[1] -> {
                    setting.setLang(optionsValue[1])
                    tvChangeLangLabel.text = optionsValue[1]
                }
            }
        }
        builder?.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder?.show()
    }

    companion object {
        fun newInstance(bundle: Bundle? = null): MainSettingFragment {
            val mainSettingFragment = MainSettingFragment()
            mainSettingFragment.arguments = bundle
            return mainSettingFragment
        }
    }
}