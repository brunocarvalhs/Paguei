package br.com.brunocarvalhs.payflow.features.homes.list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.payflow.domain.entities.HomesEntities
import br.com.brunocarvalhs.payflow.domain.repositories.HomesRepository
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomesListViewModel @Inject constructor(
    private val homesRepository: HomesRepository,
    private val sessionManager: SessionManager
) : BaseViewModel<HomesListViewState>() {

    val user = sessionManager.getUser()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = HomesListViewState.Loading
                val homes = homesRepository.list()
                mutableState.value = HomesListViewState.Success(homes)
            } catch (error: Exception) {
                mutableState.value = HomesListViewState.Error(error.message)
            }
        }
    }

    fun selected(home: HomesEntities? = null) {

    }
}