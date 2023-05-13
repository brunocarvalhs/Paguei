package br.com.brunocarvalhs.profile.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.domain.services.DataStore
import br.com.brunocarvalhs.domain.usecase.auth.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val dataStore: DataStore,
    private val useCase: DeleteUserUseCase
) : ViewModel() {

    fun deleteData(event: () -> Unit) {
        viewModelScope.launch {
            useCase.invoke()
                .onSuccess { event() }
                .onFailure { }
        }
    }
}