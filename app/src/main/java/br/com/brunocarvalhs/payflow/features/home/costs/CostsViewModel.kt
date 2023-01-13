package br.com.brunocarvalhs.payflow.features.home.costs

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities
import br.com.brunocarvalhs.payflow.domain.repositories.CostsRepository
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val repository: CostsRepository,
    private val sessionManager: SessionManager
) : BaseViewModel<CostsViewState>() {
    val user: UserEntities? = sessionManager.getUser()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CostsViewState.Loading
                val list = repository.list()
                mutableState.value = CostsViewState.Success(list)
            } catch (error: Exception) {
                mutableState.value = CostsViewState.Error(error.message)
            }
        }
    }
}
