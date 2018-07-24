package com.example.potikorn.movielists.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.dao.GuestDao
import com.example.potikorn.movielists.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel(),
    BaseSubscriber.SubscribeCallback<GuestDao> {

    val isLoading = MutableLiveData<Boolean>()
    val liveGuestUserData = MutableLiveData<GuestDao>()
    val error = MutableLiveData<String>()

    fun requestGuestSession() {
        isLoading.value = true
        userRepository.getGuestSession(this)
    }

    override fun onSuccess(body: GuestDao?) {
        isLoading.value = false
        liveGuestUserData.value = body
    }

    override fun onUnSuccess(message: String?) {
        isLoading.value = false
        error.value = message
    }

    override fun onObservableError(message: String?) {
        isLoading.value = false
        error.value = message
    }

    override fun onUnAuthorized() {}
}