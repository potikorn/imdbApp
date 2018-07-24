package com.example.potikorn.movielists.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.dao.GuestDao
import com.example.potikorn.movielists.dao.user.UserDao
import com.example.potikorn.movielists.repository.UserContract
import com.example.potikorn.movielists.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel(),
    BaseSubscriber.SubscribeCallback<GuestDao>,
    UserContract.AuthStateListener {

    val isLoading = MutableLiveData<Boolean>()
    val liveGuestUserData = MutableLiveData<GuestDao>()
    val liveUserViewModel = MutableLiveData<FirebaseUser>()
    val error = MutableLiveData<String>()

    fun requestGuestSession() {
        isLoading.value = true
        userRepository.getGuestSession(this)
    }

    fun createUserWithFirebase(userDao: UserDao) {
        isLoading.value = true
        userRepository.createUser(userDao, this)
    }

    override fun onAuthSuccess(user: FirebaseUser) {
        isLoading.value = false
        liveUserViewModel.value = user
    }

    override fun onAuthFailure(message: String?) {
        isLoading.value = false
        error.value = message
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