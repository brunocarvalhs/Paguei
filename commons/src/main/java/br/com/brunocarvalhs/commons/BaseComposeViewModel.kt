package br.com.brunocarvalhs.commons

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseComposeViewModel<T>(default: T) : ViewModel() {
    val mutableState = MutableStateFlow(default)
    val state: StateFlow<T> get() = mutableState
}