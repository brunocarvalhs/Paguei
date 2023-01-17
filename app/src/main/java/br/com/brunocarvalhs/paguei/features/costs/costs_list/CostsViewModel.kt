package br.com.brunocarvalhs.paguei.features.costs.costs_list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.paguei.domain.entities.CostsEntities
import br.com.brunocarvalhs.paguei.domain.entities.UserEntities
import br.com.brunocarvalhs.paguei.domain.repositories.CostsRepository
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val repository: CostsRepository,
    sessionManager: SessionManager
) : BaseViewModel<CostsViewState>() {

    val user: UserEntities? = sessionManager.getUser()

    private var listCosts = mutableListOf<CostsEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CostsViewState.Loading
                listCosts =
                    repository.list().filter { it.paymentVoucher.isNullOrEmpty() }.toMutableList()
                mutableState.value = CostsViewState.Success(listCosts)
            } catch (error: Exception) {
                mutableState.value = CostsViewState.Error(error.message)
            }
        }
    }
}
