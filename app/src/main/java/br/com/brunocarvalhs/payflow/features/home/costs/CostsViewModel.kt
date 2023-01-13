package br.com.brunocarvalhs.payflow.features.home.costs

import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : BaseViewModel<CostsViewState>() {
    val user: UserEntities? = sessionManager.getUser()

    fun featchUser() {

    }
}
