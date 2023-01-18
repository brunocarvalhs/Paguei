package br.com.brunocarvalhs.commons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {
    val mutableState = MutableLiveData<T>()
    val state: LiveData<T> get() = mutableState
}