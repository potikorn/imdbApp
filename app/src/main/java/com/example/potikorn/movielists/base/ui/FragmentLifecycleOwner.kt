package com.example.potikorn.movielists.base.ui

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry

class FragmentLifecycleOwner : LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    companion object {
        fun create() = FragmentLifecycleOwner()
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}