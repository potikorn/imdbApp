package com.example.potikorn.movielists.base.ui

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.potikorn.movielists.ImdbApplication
import com.example.potikorn.movielists.di.AppComponent
import com.example.potikorn.movielists.extensions.inflate

abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun layoutToInflate(): Int

    protected abstract fun createLifecycleOwner(): FragmentLifecycleOwner
    protected abstract fun doInjection(appComponent: AppComponent)
    protected abstract fun startView()
    protected abstract fun stopView()
    protected abstract fun destroyView()
    protected abstract fun setupInstance()
    protected abstract fun setupView()
    protected abstract fun initialize()

    lateinit var lifecycleOwner: FragmentLifecycleOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutToInflate() == 0) throw NotSetLayoutException()
        doInjection(ImdbApplication.appComponent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = container?.inflate(layoutToInflate())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleOwner = createLifecycleOwner()
        setupInstance()
        setupView()
        if (savedInstanceState == null) initialize()
        lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        startView()
        lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onStop() {
        super.onStop()
        stopView()
        lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onPause() {
        super.onPause()
        lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onResume() {
        super.onResume()
        lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyView()
        lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}