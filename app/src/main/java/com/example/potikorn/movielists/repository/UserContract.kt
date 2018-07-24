package com.example.potikorn.movielists.repository

import com.google.firebase.auth.FirebaseUser

class UserContract {

    interface AuthStateListener {
        fun onAuthSuccess(user: FirebaseUser)
        fun onAuthFailure(message: String?)
    }
}