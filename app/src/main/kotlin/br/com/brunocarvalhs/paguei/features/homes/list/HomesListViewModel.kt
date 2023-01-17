package br.com.brunocarvalhs.paguei.features.homes.list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.paguei.domain.entities.HomesEntities
import br.com.brunocarvalhs.paguei.domain.repositories.HomesRepository
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomesListViewModel @Inject constructor(
    private val homesRepository: HomesRepository,
    sessionManager: SessionManager,
) : BaseViewModel<HomesListViewState>() {

    val user = sessionManager.getUser()

    private var homes: HomesEntities? = null

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
        this.homes = home
    }
}