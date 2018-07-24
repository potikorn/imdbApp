package com.example.potikorn.movielists.base.ui

class NotSetLayoutException : RuntimeException("getLayoutView() not return 0")

class NotSetViewModelException(modelClass: String) : IllegalArgumentException("unknown model class $modelClass")

class NotCreateViewModelException(e: Exception) : RuntimeException(e)